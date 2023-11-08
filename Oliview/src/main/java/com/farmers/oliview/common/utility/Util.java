package com.farmers.oliview.common.utility;

import java.text.SimpleDateFormat;

public class Util {
	
	public static int seqNum = 1;
	
	/** 파일명 변경 메서드(날짜+seqNum.확장자)
	 * @param originalFileName
	 * @return
	 */
	public static String fileRename(String originalFileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new java.util.Date(System.currentTimeMillis()));
		
		String str = "_" + String.format("%05d", seqNum++);
		
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		return date + str + ext;
	}
	

}
