package com.farmers.oliview.review.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.dto.ReviewReport;
import com.farmers.oliview.review.model.mapper.ReportMapper;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.dto.TogetherReport;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

	private final ReportMapper mapper;

	@Override
	public Review report(int reviewNo) {
		return mapper.report(reviewNo);
	}

	@Override
	public int insertReport(ReviewReport report) {
		return mapper.insertReport(report);
	}
	
	@Override
	public Together tReport(int boardNo) {
		return mapper.tReport(boardNo);
	}
	
//	@Override
//	public int insertTReport(TogetherReport report) {
//		return mapper.insertTReport(report);
//	}
	
	

}
