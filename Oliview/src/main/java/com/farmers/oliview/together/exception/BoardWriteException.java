package com.farmers.oliview.together.exception;



public class BoardWriteException extends RuntimeException {
	
	public BoardWriteException() {
		super("게시글 작성 중 예외 발생");
	}

	
	public BoardWriteException(String message) {
		super(message);
	}
	
	
}
