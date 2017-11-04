package com.hgsoft.exception;

/**
 * 多数情况下，创建自定义异常需要继承Exception，本例继承Exception的子类RuntimeException
 * @author gaosiling
 * 2016年1月22日09:59:43
 *
 */
public class ApplicationException extends RuntimeException {

    private String retCd ;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息
     
    public ApplicationException() {
        super();
    }
    
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
 
    public ApplicationException(String message) {
        super(message);
        msgDes = message;
    }
 
    public ApplicationException(String retCd, String msgDes) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
    }
 
    public String getRetCd() {
        return retCd;
    }
 
    public String getMsgDes() {
        return msgDes;
    }
}
