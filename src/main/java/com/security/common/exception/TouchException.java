package com.security.common.exception;

/**
 * 异常信息处理类
 * 
 * @author lbx
 */
public class TouchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -539836596259427728L;

	public TouchException(){
		super();
	}
	/**
	 * 通过有参构造函数，自定义错误消息。
	 * @param msg
	 */
	public TouchException(String msg){
		super(msg);
	}
	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public TouchException(String msg ,Throwable cause){
		super(msg,cause);
	}
	public TouchException(Throwable cause) {
        super(cause);
    }

}
