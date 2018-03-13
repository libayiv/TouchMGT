package com.security.common.utils.ueditor;

import java.io.File;

public class Encoder {

	public static String toUnicode ( String input ) {
		
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		
		for ( char ch : chars ) {
			
			if ( ch < 256 ) {
				builder.append( ch );
			} else {
				builder.append( "\\u" +  Integer.toHexString( ch& 0xffff ) );
			}
			
		}
		
		return builder.toString();
		
	}
	
	/*public static void main(String[] args) {
		File f = new File(Encoder.class.getResource("/").getPath()); 
		System.out.println(f); 
	}*/
}