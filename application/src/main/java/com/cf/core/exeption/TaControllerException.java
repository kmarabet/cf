package com.cf.core.exeption;

import javax.servlet.http.HttpServletResponse;

public class TaControllerException extends RuntimeException {

    private int httpStatusCode;
    private String errMsg;

    public TaControllerException(int httpStatusCode, String errMsg) {
        this.httpStatusCode = httpStatusCode;
        this.errMsg = errMsg;
    }
    public TaControllerException(Throwable cause, int httpStatusCode, String errMsg) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
        this.errMsg = errMsg;
    }
    public TaControllerException(Throwable cause) {
        super(cause);
        this.httpStatusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        this.errMsg = "Unexpected exception occurred";
    }
    public TaControllerException(String message, Throwable cause) {
        super(cause);
        this.httpStatusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        this.errMsg = message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
