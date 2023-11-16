package com.farmers.oliview.together.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TogetherReport {

	private int tReportNo;
	private String tReportContent;
	private int tReportDelFl;
	private int boardNo;

	private String memberNickname;
	private String boardTitle;

}
