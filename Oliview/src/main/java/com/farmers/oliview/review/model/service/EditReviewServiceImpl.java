package com.farmers.oliview.review.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.farmers.oliview.common.utility.Util;
import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.mapper.EditReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")
public class EditReviewServiceImpl implements EditReviewService {

	private final EditReviewMapper mapper;

	@Value("${my.board.location}")
	private String folderPath;

	@Value("${my.board.webpath}")
	private String webPath;

	// 게시글 작성
	@Override
	public int insertReview(Review review, MultipartFile img) throws IllegalStateException, IOException {

		String rename = Util.fileRename(img.getOriginalFilename());

		review.setReviewImg(webPath + rename);

		int result = mapper.insertReview(review);

		if (result == 0)
			return 0;

		img.transferTo(new File(folderPath + rename));

		return review.getReviewNo();
	}

	// 게시글 수정
	@Override
	public int updateReview(Review review, MultipartFile img) throws IllegalStateException, IOException {

		// 변경된 파일이 있는 경우
		String rename = null;
		if (img.getSize() > 0) {
			rename = Util.fileRename(img.getOriginalFilename());
			review.setReviewImg(webPath + rename);
		}

		int result = mapper.updateReview(review);

		if (result == 0)
			return 0;

		img.transferTo(new File(folderPath + rename));

		return review.getReviewNo();
	}

	// 게시글 삭제
	@Override
	public int deleteReview(Map<String, Integer> paramMap) {
		return mapper.deleteReview(paramMap);
	}

}
