package kr.co.softbridge.voplatform.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class HttpsUtil {
	//
	private final int 				TIME_OUT_VALUE 		= 20000;
	//
	/*************************************************************************************************************
	* @brief  : POST방식으로 JSON 전송
	* @method : postData JSON 통신용
	* @author : 이민재
	**************************************************************************************************************/
	public JSONObject postData(String url, JSONObject jsonData) {
		//
		JSONObject 			returnJsonData	= new JSONObject();
		URL 				urlParam 		= null;
		HttpsURLConnection 	httpsConn 		= null;
		OutputStreamWriter	osw 			= null;
		BufferedReader 		br 				= null;
		//
		try {
			//
			if(url == null && url.equalsIgnoreCase("")){
				//
				throw new Exception("전송 할 URL이 없습니다.");
				//
			}else {
				//=======================================================================================
				urlParam 	= new URL(url);
				httpsConn 	= (HttpsURLConnection)urlParam.openConnection();
				httpsConn.setConnectTimeout(this.TIME_OUT_VALUE);
				httpsConn.setReadTimeout(this.TIME_OUT_VALUE);
				httpsConn.setDoInput(true);
				httpsConn.setDoOutput(true);
				httpsConn.setUseCaches(false);
				httpsConn.setRequestMethod("POST");
				httpsConn.setRequestProperty("Content-Type", "application/json");
				// JSON 데이터 전송
				osw 	= new OutputStreamWriter(httpsConn.getOutputStream(), "UTF-8");
				//
				osw.write(jsonData.toString());
				osw.flush();
				//=======================================================================================
				// JSON 데이터 수신 받기
				br 					= new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "UTF-8"));
				returnJsonData 		= new JSONObject(br.readLine());
				// 통신 검증
				int responseCode 	= httpsConn.getResponseCode();
				//
				if(responseCode != 200) {
					throw new Exception("Https 통신 데이터 수신 실패");
				}
				//
			}
			//
		}catch(Exception ex) {
			//
			ex.printStackTrace();
		}finally {
			if(osw != null) {
				try {
					osw.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(br != null) {
				try {
					br.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(httpsConn != null) {
				try {
					httpsConn.disconnect();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		//
		return returnJsonData;
	}	
	//
	/*************************************************************************************************************
	* @brief  : Multipart-form/data JSONObject로 전송 
	* @method : 파일업로드 용
	* @author : 이민재
	**************************************************************************************************************/
	public JSONObject multiFileJSON(String url, HashMap<String, Object> connMap ,File file) {
		//
		JSONObject result 				= null;
		//
		String boundary 				= "^-----^";
		//
		DataOutputStream 	paramDOS 	= null;
		HttpsURLConnection 	httpsConn	= null;
		InputStream 		inputStream = null;
		//
		try {
			//
			//=======================================================================================
			URL urlParam 		= new URL(url);
			httpsConn 			= (HttpsURLConnection)urlParam.openConnection();
			//
			httpsConn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
			httpsConn.setRequestMethod("POST");
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setUseCaches(false);
			httpsConn.setConnectTimeout(this.TIME_OUT_VALUE);
			//=======================================================================================
			paramDOS				= new DataOutputStream(httpsConn.getOutputStream());
			//
			byte[]	delimeterBytes 	= boundary.getBytes();
			byte[]	twoDashBytes	= "--".getBytes();
			byte[]	newLineBytes	= "\r\n".getBytes();
			//
			Iterator<String> keys = connMap.keySet().iterator();
			while(keys.hasNext()) {
				//
				String key 		= keys.next();
				String value 	= (String)connMap.get(key);
				//
				paramDOS.write(twoDashBytes);
				paramDOS.write(delimeterBytes);
				paramDOS.write(newLineBytes);
				//
				paramDOS.write(this.makeMultiPartParam(key, value));
				//
			}
			paramDOS.write(twoDashBytes);
			paramDOS.write(delimeterBytes);
			paramDOS.write(newLineBytes);
			paramDOS.write(this.makeMultiPartFileParam("file", file)); 
			//
			paramDOS.write(newLineBytes);
			//
			FileInputStream		paramFIS				= new FileInputStream(file);
			BufferedInputStream	paramBIS				= new BufferedInputStream(paramFIS);
			//
			int					readInLen 				= 0;
			byte[]				readFileBUF				= new byte[1024];
			//
			while((readInLen = paramBIS.read(readFileBUF)) > 0) {
				paramDOS.write(readFileBUF, 0, readInLen);
			}
			//
			paramDOS.write(newLineBytes);
			paramDOS.write(twoDashBytes);
			paramDOS.write(delimeterBytes);
			paramDOS.write(twoDashBytes);
			paramDOS.write(newLineBytes);
			//
			paramBIS.close();
			paramFIS.close();
			paramDOS.close();
			//=======================================================================================
			// 결과 데이터 받기
			inputStream = httpsConn.getInputStream();
			//
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			//
			result = new JSONObject(br.readLine());
			//
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(httpsConn != null) {
				try {
					httpsConn.disconnect();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		//
		return result;
		//
	}
	//
	public byte[] makeMultiPartParam(String paramKey, String paramValue) throws Exception{
		//
		byte[]				makeParamByte 		= null;
 		//
		try {
			//
			String 			makeParamStr		= "";
			StringBuffer 	makeParamSB 		= new StringBuffer();
			//
			makeParamSB.append("Content-Disposition: form-data;");
			makeParamSB.append("name=\""+paramKey+"\"");
			makeParamSB.append("\r\n");
			makeParamSB.append("Content-Type: text/plain");
			makeParamSB.append("\r\n");
			makeParamSB.append("\r\n");
			makeParamSB.append(paramValue);
			makeParamSB.append("\r\n");
			//
			makeParamStr		= makeParamSB.toString();
			makeParamByte		= makeParamStr.getBytes();
			//
		}catch(Exception e) {
			e.printStackTrace();
		}
		return makeParamByte;
	}
	//
	public byte[] makeMultiPartFileParam(String paramKey, File paramFile) throws Exception{
		//
		byte[]				makeFileParamByte	= null;
		//
		try {
			//
			String 			makeFileParamStr	= "";
			StringBuffer 	makeFileParamSB		= new StringBuffer();
			//
			makeFileParamSB.append("Content-Disposition: form-data; ");
			makeFileParamSB.append("name=\""+paramKey+"\"; ");
			makeFileParamSB.append("filename=\""+paramFile.getName()+"\" ;");
			makeFileParamSB.append("\r\n");
			makeFileParamSB.append("Content-Type: "+URLConnection.guessContentTypeFromName(paramFile.getName()));
			makeFileParamSB.append("\r\n");
			makeFileParamSB.append("Content-Transfer-Encoding: binary");
			makeFileParamSB.append("\r\n");
			//
			makeFileParamStr	= makeFileParamSB.toString();
			makeFileParamByte	= makeFileParamStr.getBytes();
			//
		}catch(Exception e) {
			e.printStackTrace();
		}
		//
		return makeFileParamByte;
	}
	//
	/*************************************************************************************************************
	* @brief  : Multipart-form/data JSONObject로 전송 
	* @method : 파일삭제 용
	* @author : 이민재
	**************************************************************************************************************/
	public JSONObject multiJSON(String url, HashMap<String, Object> connMap) {
		//
		JSONObject result 				= new JSONObject();
		//
		String boundary 				= "^-----^";
		DataOutputStream 	paramDOS 	= null;
		HttpsURLConnection 	httpsConn 	= null;
		InputStream 		inputStream = null;
		BufferedReader 		br			= null; 
		//
		try {
			//==================================================================
			URL urlParam 	= new URL(url);
			httpsConn 		= (HttpsURLConnection)urlParam.openConnection();
			httpsConn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
			httpsConn.setRequestMethod("POST");
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setUseCaches(false);
			httpsConn.setConnectTimeout(this.TIME_OUT_VALUE);
			httpsConn.setReadTimeout(this.TIME_OUT_VALUE);
			//==================================================================
			paramDOS	= new DataOutputStream(httpsConn.getOutputStream());
			byte[]	delimeterBytes = boundary.getBytes();
			byte[]	twoDashBytes	= "--".getBytes();
			byte[]	newLineBytes	= "\r\n".getBytes();
			//
			Iterator<String> keys = connMap.keySet().iterator();
			while(keys.hasNext()) {
				//
				String key 		= keys.next();
				String value 	= (String)connMap.get(key);
				//
				paramDOS.write(twoDashBytes);
				paramDOS.write(delimeterBytes);
				paramDOS.write(newLineBytes);
				paramDOS.write(this.makeMultiPartParam(key, value));
				//
			}
			//
			paramDOS.write(twoDashBytes);
			paramDOS.write(delimeterBytes);
			paramDOS.write(twoDashBytes);
			paramDOS.write(newLineBytes);
			//
			paramDOS.flush();
			//==================================================================
			inputStream = httpsConn.getInputStream();
			//
			br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			//
			result = new JSONObject(br.readLine());
			//
		}catch(Exception e) {
			//
			e.printStackTrace();
			//
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(paramDOS != null) {
				try {
					paramDOS.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(br != null) {
				try {
					br.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(httpsConn != null) {
				try {
					httpsConn.disconnect();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		//
		return result;
	}
	/*************************************************************************************************************
	* @brief  : Multipart-form/data JSONObject로 전송 
	* @method : 파일다운로드 용
	* @author : 이민재
	**************************************************************************************************************/
	public JSONObject multiStr(String url, HashMap<String, Object> connMap) {
		//
		JSONObject result 				= new JSONObject();
		//
		String boundary 				= "^-----^";
		DataOutputStream 	paramDOS 	= null;
		HttpsURLConnection 	httpsConn 	= null;
		InputStream 		inputStream	= null;
		FileOutputStream 	fos			= null;
		//
		try {
			//==================================================================
			URL urlParam	= new URL(url.replaceAll(" ", "%20"));
			httpsConn 		= (HttpsURLConnection)urlParam.openConnection();
			httpsConn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
			httpsConn.setRequestMethod("POST");
			httpsConn.setConnectTimeout(this.TIME_OUT_VALUE);
			httpsConn.setReadTimeout(this.TIME_OUT_VALUE);
			httpsConn.setDoInput(true);
			httpsConn.setDoOutput(true);
			httpsConn.setUseCaches(false);
			//==================================================================
			paramDOS				= new DataOutputStream(httpsConn.getOutputStream());
			byte[]	delimeterBytes 	= boundary.getBytes();
			byte[]	twoDashBytes	= "--".getBytes();
			byte[]	newLineBytes	= "\r\n".getBytes();
			//
			Iterator<String> keys = connMap.keySet().iterator();
			while(keys.hasNext()) {
				//
				String key 		= keys.next();
				String value 	= (String)connMap.get(key);
				//
				paramDOS.write(twoDashBytes);
				paramDOS.write(delimeterBytes);
				paramDOS.write(newLineBytes);
				paramDOS.write(this.makeMultiPartParam(key, value));
			}
			//
			paramDOS.write(twoDashBytes);
			paramDOS.write(delimeterBytes);
			paramDOS.write(twoDashBytes);
			paramDOS.write(newLineBytes);
			//
			paramDOS.flush();
			paramDOS.close();
			//
			//==================================================================
			//
			String fileName 	= "";
			String fileDir 		= "/home/apaadm/tmp/fileDownload/";
			String disposition 	= httpsConn.getHeaderField("Content-Disposition");
			//
			if(disposition != null) {
				//
				String 	target 	= "filename=";
				int 	index 	= disposition.indexOf(target);
				//
				if(index != -1) {
					//
					fileName 	= disposition.substring(index + target.length() + 1);
					fileName 	= fileName.replaceAll("\"", "");
					fileName 	= new String(fileName.getBytes("8859_1"), "UTF-8");
					//
				}
			}
			//
			File newFile 		= new File(fileDir, fileName);
			//
			fos 				= new FileOutputStream(newFile);
			//
			inputStream 		= httpsConn.getInputStream();
			//
			int readCount 		= 0;
			//
			byte[] buffer 		= new byte[1024];
			while((readCount = inputStream.read(buffer)) != -1) {
				fos.write(buffer,0, readCount);
			}
			//
			fos.flush();
			//
			result.put("fileName", fileName);
			//
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(httpsConn != null) {
				try {
					httpsConn.disconnect();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null) {
				try {
					inputStream.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		//
		return result;
	}
//	
}
