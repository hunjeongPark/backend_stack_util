package com.entropykorea.biztalkmng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 빌러 → 카카오페이 HOST 정보
 * 개발 : https://sandbox-billgates-gw.kakao.com
 * 운영 : https://billgates-gw.kakao.com
 * 청구서 API 1.1.0 기준
 * ex) 청구서 해지시 호출 URI : https://sandbox-billgates-gw.kakao.com/external/v2/{biller_code}/user/delete
 * API 0.7 기준 URL 패턴 : http://gateway-ip/kakao
 * 
 * kakao URL Connect Class
 * */
public class UrlConnection {
	
	URL searchUrl				= null;
	HttpURLConnection connection = null;
	OutputStream os				= null;
	
	public String urlGetConnect (String httpUrl) {
		int rc = 500;
		
		try {
			System.out.println(httpUrl);
			//kakao connect				
	        searchUrl = new URL(httpUrl);
	        connection = (HttpURLConnection)searchUrl.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("GET");
	        
	        //결과값 수신
	        rc = connection.getResponseCode();
	        if (rc == 200) {
	        	return "";
	        } else {
//	        	util.writeLog(classify, " - http response urlGetConnect error : " + rc);
	        	//System.out.println(" - http response urlGetConnect error : " + rc);
	        	return null;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
		
	}
	
	public String urlGetConnect (String httpUrl, String classify) {
		int rc = 500;
		
		try {
			System.out.println(httpUrl);
			//kakao connect				
	        searchUrl = new URL(httpUrl);
	        connection = (HttpURLConnection)searchUrl.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("GET");
	        
	        //결과값 수신
	        rc = connection.getResponseCode();
	        if (rc == 200) {
	        	String returnString = getReturnString("GET");
//	        	util.writeLog(classify, " - http response urlGetConnect ok : " + returnString);
	        	return returnString;
	        } else {
//	        	util.writeLog(classify, " - http response urlGetConnect error : " + rc);
	        	//System.out.println(" - http response urlGetConnect error : " + rc);
	        	return null;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
		
	}
	
	public String urlPostConnect (String httpUrl, String setProperty, String classify) {
		int rc = 500;
		
		try {
			//kakao connect				
	        searchUrl = new URL(httpUrl);
	        connection = (HttpURLConnection)searchUrl.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        
	        connection.setRequestProperty( "Content-Type", "application/json" );
		    connection.setRequestProperty( "Content-Length", Integer.toString(setProperty.length()) );
		    os = connection.getOutputStream();
		    os.write(setProperty.getBytes("utf-8") );
		    
		    os.flush();
		    
	        //결과값 수신
	        rc = connection.getResponseCode();
	        if (rc == 200) {
	        	String returnString = getReturnString("POST");
//	        	util.writeLog(classify, " - http response urlPostConnect ok : " + returnString);
	        	return returnString;
	        } else {
	        	os.close();
//	        	util.writeLog(classify, " - http response urlPostConnect error : " + rc);
	        	//System.out.println(" - http response urlPostConnect error : " + rc);
	        	return null;
	        }
	        
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
		
	}
	
	public String getReturnString(String method) {
		String returnString	= "";
		
		try {
			System.out.println(connection.getInputStream().toString());
			InputStreamReader in = new InputStreamReader(connection.getInputStream(),"utf-8");
			BufferedReader br = new BufferedReader(in);
			String inLine = null;
			while ((inLine = br.readLine()) != null) { // 라인단위로 읽어들이기
				// out.println(inLine);
				returnString += returnString.concat(inLine);
			}
			
			br.close();
			if(method.equals("POST"))
				os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnString;
	}
	
}
