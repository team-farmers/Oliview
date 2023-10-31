package com.farmers.oliview.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송
	 * @param string
	 * @param email
	 * @return
	 */
	int sendEmail(String string, String email);
	
	/** 인증번호 확인
	 * @param paramMap
	 * @return
	 */
	int checkAuthKey(Map<String, Object> paramMap);


}
