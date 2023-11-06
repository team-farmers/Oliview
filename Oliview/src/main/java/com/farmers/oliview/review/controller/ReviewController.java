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
	
	
	// 테스트
	@RequestMapping("result")
	public String result() {
		return "review/result";
	}

	
	// 검색 결과 (검색어 - 수정중)
	// (인기순, 최신순, 평점순 -> js)
	// (NEXT 다음페이지 -> js)
	@GetMapping("searchReview")
	public String searchReview(String searchInput, Model model) {
		
		List<Review> resultReview = service.searchReview(searchInput);
		
		model.addAttribute("resultReview",resultReview);
		
		return "review/result";
	}
	
	
	
	// 검색 결과 (닉네임 클릭 시 작성글 - 수정중)
	
	@GetMapping("result/{memberNickname:^[\\\\w]*$}")
	public String searchReviewNick(String searchInput, Model model) {
		
		List<Review> resultReview = service.searchReviewNick(searchInput);
		
		model.addAttribute("searchReviewNick",searchInput);
		
		return "review/result";
	}
	
	
	
	/** 리뷰 상세 조회 
	 * @param reviewNo
	 * @param model
	 * @param ra
	 * @returns
	 * @throws ParseException 
	 */
	@GetMapping("{reviewNo:[0-9]+}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo,
			Model model, RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required=false) Member loginMember,
			HttpServletRequest req,
			HttpServletResponse resp) throws ParseException {
		
		// 상세 조회 서비스 호출
		Map<String, Object> map = new HashMap<>();
		map.put("reviewNo", reviewNo);
		
		Review detailReview = service.reviewDetail(map);
		
		// 다른 리뷰 같이 불러오기
		List<Review> otherReview = service.otherReview(detailReview.getReviewTitle());
		
		List<Review> reviewList = new ArrayList<>();
		reviewList.add(detailReview);
		reviewList.addAll(otherReview);
		
		
		
		
		// 리턴 path
		String path = null;
		
		// ======================= 조회 결과가 있을 경우 =======================
		if(detailReview != null) {
			
			// 조회 결과 review/reviewDetail로 포워드
			model.addAttribute("reviewList", reviewList);
			path = "review/reviewDetail";
			
			if(loginMember!=null) {
				map.put("memberNo", loginMember.getMemberNo());
				int likeCheck = service.likeCheck(map);
				
				if(likeCheck == 1) model.addAttribute("likeCheck", "on");
			}
			
			
			// 조회수 증가
			if(loginMember == null || loginMember.getMemberNo() != detailReview.getMemberNo()) {
				
				// 쿠키 얻어오기
				Cookie c = null;
				// 요청에 담겨있는 모든 쿠키 얻어오기
				Cookie[] cookies = req.getCookies();
				
				if(cookies!=null) {
					
					// readReviewNo 쿠키 찾아서 c에 대입
					for(Cookie cookie : cookies) {
						if(cookie.getName().equals("readReviewNo")) {
							c = cookie;
							break;
						}
					}
				}
				
				// 쿠키 없거나(c==null) , 현재 게시글 번호에 대한 쿠키가 없는 경우
				int result = 0;
				
				if(c==null) { // 쿠키 생성
					c = new Cookie("readReviewNo", "|" + reviewNo + "|");
					
					result = service.updateReadCount(reviewNo);
					
				}
				else { // 현재 게시글 번호 쿠키
					// 쿠키에 현재 게시글 번호 없는 경우 (-1)
					if(c.getValue().indexOf("|" + reviewNo + "|") == -1) {
						c.setValue(c.getValue() + "|" + reviewNo + "|");
						result = service.updateReadCount(reviewNo);
					}
				}
				
				
				// 쿠키 수명 지정
	            if (result > 0) {
	            	detailReview.setReadCount(detailReview.getReadCount() + 1);

		               c.setPath("/"); // "/" 이하 경로 요청 시 쿠키 서버로 전달

		               Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
		               cal.add(cal.DATE, 1); // 24시간 후의 시간

		               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		               Date a = new Date(); // 현재 시간
		               Date temp = new Date(cal.getTimeInMillis()); // 다음날 (24시간 후)
		               Date b= sdf.parse(sdf.format(temp)); // 다음날 0시 0분 0초
		               long diff = (b.getTime() - a.getTime()) / 1000;
		               c.setMaxAge((int) diff); // 수명 설정

		               resp.addCookie(c);
	            }

			}
	
		}
		// ======================= 상세 조회 결과가 없을 경우 =======================
		else {
			path= "redirect:/review/result";
			ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다");
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
	
	
	
	
	// 신고하기 버튼 클릭시 이동
	
	


}
