package kr.co.softbridge.voplatform.commons.util;

import java.text.SimpleDateFormat;

/**
 * <pre>
 * voplatform
 * Util.java
 * </pre>
 * 
 * @Author	: 
 * @Date 	: 
 * @Version	: 
 */
public class Util {
	
	/* int 결과값을 받아 boolean으로 리턴*/
	public static Boolean resultCheck(int Check) {
		
		Boolean result = false;
		
		try {
			if(Check > 0) {
				result = true;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/* 문자열이 숫자인지 확인 - 숫자가 아니면 false */
	public static Boolean checkNumberic(String str) {
		
		Boolean result = true;
		
		for(int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))) {
				result = false;
			}
		}
		
		return result;
		
	}
	
	/* 바이트 길이 */
	public static int getByteLength(String str, String charset) {
		try {
			return str.getBytes(charset).length;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/*************************************************************************************************************
	* @brief  : 날짜 형식 체크 
	* @method : checkDate
	* @author : 이민재
	**************************************************************************************************************/
	public static boolean checkDate(String dt) {
		try {
			//
			SimpleDateFormat patternMatch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			patternMatch.setLenient(false);
			patternMatch.parse(dt);
			//
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	/*************************************************************************************************************
	* @brief  : String으로 넘어온 날짜형식 체크 
	* @method : checkDateStr
	* @author : 이민재
	**************************************************************************************************************/
	public static boolean checkDateStr(String dt) {
		try {
			//
			SimpleDateFormat patternMatch = new SimpleDateFormat("yyyyMMddHHmmss");
			patternMatch.setLenient(false);
			patternMatch.parse(dt);
			//
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
}