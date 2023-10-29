package com.farmers.oliview.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farmers.oliview.review.model.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("review")
//@SessionAttributes
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService service;

}
