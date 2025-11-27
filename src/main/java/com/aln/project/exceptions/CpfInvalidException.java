package com.aln.project.exceptions;

import java.io.InvalidClassException;


public class CpfInvalidException extends RuntimeException {
	public CpfInvalidException(String message){
		super(message);
	}

}
