package com.aln.project.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalHandlerException {

	// 400
	@ExceptionHandler(CpfInvalidException.class)
	public ResponseEntity<ResponseErroDto> handleInvalidCpf(CpfInvalidException ex, HttpServletRequest req) {

		ResponseErroDto body = new ResponseErroDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
				ex.getMessage(), req.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	// 409
	@ExceptionHandler(CpfDuplicateException.class)
	public ResponseEntity<ResponseErroDto> handleCpfDuplicate(CpfDuplicateException ex, HttpServletRequest request) {

		ResponseErroDto erro = new ResponseErroDto(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Conflict",
				ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}

	// 409
	@ExceptionHandler(EmailDuplicateException.class)
	public ResponseEntity<ResponseErroDto> handleEmailDuplicate(EmailDuplicateException ex,
			HttpServletRequest request) {

		ResponseErroDto erro = new ResponseErroDto(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Conflict",
				ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseErroDto> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String message = ex.getBindingResult().getFieldError().getDefaultMessage();

		ResponseErroDto erro = new ResponseErroDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
				message, request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	// 404
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseErroDto> handleNotFound(NotFoundException ex, HttpServletRequest req) {

		ResponseErroDto body = new ResponseErroDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
				ex.getMessage(), req.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ResponseErroDto> handleConflict(ConflictException ex, HttpServletRequest request) {

		ResponseErroDto error = new ResponseErroDto(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Conflict",
				ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

}
