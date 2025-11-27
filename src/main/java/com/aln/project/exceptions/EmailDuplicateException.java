package com.aln.project.exceptions;

public class EmailDuplicateException extends RuntimeException {
	public EmailDuplicateException(String message) {
		super(message);
	}
}
