package com.farmers.oliview.admin.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
   
   private int reportNo;
   private String reportContent;
   private int reportAccrue;
   private int reviewNo;
   private String reportDelFl;
   
   private String memberNickname;
   private String reviewTitle;
}