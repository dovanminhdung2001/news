package com.example.base3_1.utils;


public class CustomException extends RuntimeException {

    private ErrorApp errorApp;
    private Integer codeError;

    public CustomException(ErrorApp errorApp) {
        super(errorApp.getDescription());
        this.errorApp = errorApp;
        this.codeError = errorApp.getCode();
    }

    public CustomException(int code, String mess) {
        super(mess);
        codeError = code;
    }

    public CustomException(String mess) {
        super(mess);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorApp getErrorApp() {
        return errorApp;
    }

    public Integer getCodeError() {
        return codeError;
    }
}
