package com.example.auro.teenyurl.constants;

public enum ApiRequestErrorCode {

	GENERAL_ERROR(000);

	private int code;

	private ApiRequestErrorCode(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}
}
