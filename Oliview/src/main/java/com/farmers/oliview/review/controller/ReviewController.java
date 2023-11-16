package com.farmers.oliview.review.controller;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmers.oliview.member.model.dto.Member;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.service.ReviewService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("review")
@SessionAttributes({"loginMember"})
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService service;
	
	
	
	/** 전체 조회, 검색 조회
	 * @param model
	 * @param searchInput
	 * @param cp
	 * @param paramMap
	 * @return
	 */
	@GetMapping("searchReview")
	public String searchReview(Model model,
			@RequestParam(required=false) String searchInput,
			@RequestParam(value="cp", required = false, defaultValue="1") int cp,
			@RequestParam(value="sort", required = false, defaultValue="1") int sort) {
		
		// 전체 조회
		if(searchInput == null) {
			Map<String, Object> map = service.allReview(cp, sort);
			model.addAttribute("map",map);
		}
		// 검색 
		else {
			Map<String, Object> map = service.searchReview(searchInput, cp, sort);
			model.addAttribute("map",map);
		}
		
		return "review/result";
		
	}
	
	
//	=============================================================================================================================	

	
	/** 인기순 조회 (비동기)
	 * @param searchInput
	 * @param cp
	 * @return
	 */
	@ResponseBody
	@GetMapping(value="sortPopular", produces="application/json")
	public Map<String, Object> sortPopular(String searchInput, int cp){
		
		Map<String, Object> map = service.sortPopular(searchInput, cp);
		
		return map;
	}
	
	
	
	/** 최신순 조회 (비동기)
	 * @param searchInput
	 * @return map(reviewList, cp)
	 */
	@ResponseBody
	@GetMapping(value="sortLatest", produces="application/json")
	public Map<String, Object> sortLatest(String searchInput, int cp){
		
		Map<String, Object> map = service.sortLatest(searchInput, cp);
		
		return map;
	}

	
	/** 평점순 조회 (비동기)
	 * @param searchInput
	 * @return map(reviewList, cp)
	 */
	@ResponseBody
	@GetMapping(value="sortRating", produces="application/json")
	public Map<String, Object> sortRating(String searchInput, int cp){

		Map<String, Object> map = service.sortRating(searchInput, cp);

		return map;
		
	}

	/** 평점순 가게 조회
	 * @param reviewTitle
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("store/{reviewTitle}")
	public String reviewStore(@PathVariable("reviewTitle") String reviewTitle, 
			@RequestParam(value="cp2", required = false, defaultValue="1") int cp, 
			Model model, RedirectAttributes ra) {
		
		Map<String, Object> reviewMap = service.ratingResult(reviewTitle, cp);
		model.addAttribute("map",reviewMap);
		
		return "review/rating-result";
		
	}
	
	/*
	 * +) 추가적으로 생각해보기/ 평점순 조회 시 평점순 목록으로 버튼을 눌렀을 경우,
	 * DB에서 경로를 계산해서 그 페이지로 리다이렉트하기
	 * 
	 */
	

//	=============================================================================================================================
	
	/** 리뷰 상세 조회
	 * @param reviewNo
	 * @param model
	 * @param ra
	 * @param loginMember
	 * @param req
	 * @param resp
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("{reviewNo:[0-9]+}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo, Model model, RedirectAttributes ra,
							@SessionAttribute(value="loginMember", required=false) Member loginMember,
							HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		
		// 상세 조회 서비스 호출
		Map<String, Object> map = new HashMap<>();
		map.put("reviewNo", reviewNo);
		
		Review review = service.reviewDetail(map);

		
		// 리턴 Path
		String path = null;
		
		// ==================== 리뷰가 존재 ========================
		if(review != null) {
			
			// 다른 리뷰 같이 호출
			Map<String, Object> map2 = new HashMap<>();
			map2.put("reviewNo", reviewNo);
			map2.put("reviewTitle", review.getReviewTitle());
			
			List<Review> otherReview = service.otherReview(map2);
			
			// 상세 리뷰, 다른 리뷰 데이터 전달
			model.addAttribute("review", review);
			model.addAttribute("otherReview", otherReview);
			
			path = "review/reviewDetail";
			
			
			// 로그인 되어있을 때 좋아요 누른 적 있으면 하트 채움
			if(loginMember != null) {
				map.put("memberNo", loginMember.getMemberNo());
				int likeCheck = service.likeCheck(map);
				
				if(likeCheck == 1) model.addAttribute("likeCheck","on");
			}
			
			// 로그인 x 비회원 또는 로그인한 멤버가 리뷰 쓴 멤버가 아닌 경우
			if(loginMember == null || loginMember.getMemberNo() != review.getMemberNo()) {
				
				Cookie c = null;
				
				// 요청에 담겨있는 모든 쿠키 얻어오기
				Cookie[] cookies = req.getCookies();
				
				// ------ 쿠키가 존재할 경우 ------
				if(cookies != null) {
					
					for(Cookie cookie : cookies) {
						if(cookie.getName().equals("readReviewNo")) {
							c = cookie;
							break;
						}
					}
				}
				
				// ------ 쿠키가 없거나 현재 게시글 번호가 쿠키에 없는 경우 ------
				int result = 0;
				
				if(c ==null) {
					// 쿠키 생성
					c = new Cookie("readReviewNo", "|" + reviewNo + "|");
					
					// 조회수 증가 서비스 호출
					result = service.updateReadCount(reviewNo);
				}
				else {
					
					// 현재 리뷰 번호가 쿠키에 있는지 확인
					
					if(c.getValue().indexOf("|" + reviewNo + "|") == -1) {
						// 없으면 리뷰 번호 추가 세팅
						c.setValue(c.getValue() + "|" + reviewNo + "|");
						
						// 조회수 증가 서비스 호출
						result = service.updateReadCount(reviewNo);
					}
					
				}
				

				
				// DB 동기화
				if(result > 0) {
		               review.setReadCount(review.getReadCount() + 1);
		               // 조회된 review 조회 수와 DB 조회 수 동기화

		               // 적용 경로 설정
		               c.setPath("/"); // "/" 이하 경로 요청 시 쿠키 서버로 전달

		               // 수명 지정=======================================================================
		               Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
		               cal.add(cal.DATE, 1); // 24시간 후의 시간

		               // 날짜 표기법 변경 객체 (DB의 TO_CHAR()와 비슷)
		               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		               // java.util.Date
		               Date a = new Date(); // 현재 시간
		               Date temp = new Date(cal.getTimeInMillis()); // 다음날 (24시간 후)
		               Date b= sdf.parse(sdf.format(temp)); // 다음날 0시 0분 0초

		               long diff = (b.getTime() - a.getTime()) / 1000;
		               // -> 다음날 0시 0분 0초까지 남은 시간을 초단위로 반환

		               c.setMaxAge((int) diff); // 수명 설정
		               //==================================================================================

		               resp.addCookie(c); // 응답 객체를 이용해서
		                              // 클라이언트에게 전달					
				}
			}

		}else {
			path =  "redirect:/review/searchReview";
			ra.addFlashAttribute("message", "삭제되거나 존재하지 않는 리뷰입니다.");

		}

		
		return path;
		
	}
	
	
	/** 찜
	 * @param paramMap
	 * @param loginMember
	 * @return
	 */
	@PostMapping("like")
	@ResponseBody
	public int like(@RequestBody Map<String, Object> paramMap,
			@SessionAttribute("loginMember") Member loginMember) {
		
		paramMap.put("memberNo",loginMember.getMemberNo());
		
		return service.reviewLike(paramMap);
	}
	
	

	
	

}
