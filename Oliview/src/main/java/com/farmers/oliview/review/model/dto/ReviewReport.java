package com.farmers.oliview.review.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewReport {
   
   private int reportNo;
   private String reportContent;
   private int reportAccrue;
   private int reviewNo;
   private String reportDelFl;
   
   private String memberNickname;
   private String reviewTitle;
}