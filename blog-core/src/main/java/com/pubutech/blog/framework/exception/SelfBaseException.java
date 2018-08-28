package com.pubutech.blog.framework.exception;

public class SelfBaseException extends RuntimeException  {

    public SelfBaseException(){
        super();
    }

    public SelfBaseException(String message){
        super(message);
    }

    public SelfBaseException(String message, Throwable cause){
        super(message,cause);
    }
}
