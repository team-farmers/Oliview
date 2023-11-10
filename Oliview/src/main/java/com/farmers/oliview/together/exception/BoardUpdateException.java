package com.farmers.oliview.together.exception;

public class BoardUpdateException extends RuntimeException {

	public BoardUpdateException() {
		super("게시글 작성 중 예외 발생");
	}
	
	public BoardUpdateException(String message) {
		super(message);
	}
	
}
