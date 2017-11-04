package com.hgsoft.mq.util;

/**
 * 报文标志位，不可更改
 * @author gaosiling
 *
 */
public class InterruptFlag {

	public static final char A = 0x01;
	
	public static final char T ='\t';
	
	public static final char R = '\r';
	
	public static final char N = '\n';
	
	public static final String ENDFLAG =(R)+""+(N);
}
