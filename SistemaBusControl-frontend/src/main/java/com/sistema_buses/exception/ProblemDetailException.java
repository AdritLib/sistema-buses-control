package com.sistema_buses.exception;

import org.springframework.http.ProblemDetail;

public class ProblemDetailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final ProblemDetail problemDetail;

    public ProblemDetailException(ProblemDetail problemDetail) {
        super(problemDetail.getDetail());
        this.problemDetail = problemDetail;
    }

    public ProblemDetail getProblemDetail() {
        return problemDetail;
    }
}