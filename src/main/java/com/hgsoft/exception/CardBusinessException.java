package com.hgsoft.exception;

/**
 * @author ： 孙晓伟
 *         file : CardBusinessException.java
 *         date : 2017/7/3
 *         time : 20:20
 */
public class CardBusinessException extends RuntimeException {

    public CardBusinessException(String message) {
        super(message);
    }
    public CardBusinessException(CardBusinessException e) {
        super(e);
    }
    public CardBusinessException(String message, CardBusinessException e) {
        super(message,e);
    }
}
