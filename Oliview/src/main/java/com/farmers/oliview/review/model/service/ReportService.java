package com.farmers.oliview.review.model.service;

import com.farmers.oliview.review.model.dto.Review;
import com.farmers.oliview.review.model.dto.ReviewReport;
import com.farmers.oliview.together.dto.Together;
import com.farmers.oliview.together.dto.TogetherReport;

public interface ReportService {


	int insertReport(ReviewReport report);

	Review report(int reviewNo);

	Together tReport(int boardNo);

	int insertTReport(TogetherReport report);



}
