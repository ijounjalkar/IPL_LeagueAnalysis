package com.IPL_LeagueAnalysis;

public class IPLAnalyserException extends Exception {
	public enum ExceptionType {
		NO_FILE, INCORRECT_FILE, UNABLE_TO_PARSE, NO_STATISTICS_DATA
	}

	public ExceptionType type;

	public IPLAnalyserException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

}
