package kr.co.softbridge.voplatform.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class MultipartUtil {
	//
	/*************************************************************************************************************
	* @brief  : MultipartFile -> File 형 변환 
	* @method : 파일업로드 용
	* @author : 이민재
	**************************************************************************************************************/
	public File multiFileToFile (MultipartFile mFile){
		//
		FileOutputStream 	cFileFOS		= null;
		File				cFile			= null;
		String 				cFilePath 		= "";
		byte[]				mFileBytes		= null;
		//
		try {
			// 경로 하드코딩으로 설정해둠. (소스 가져다 쓸 때 변경 필요)
			cFilePath 		= "/home/apaadm/tmp/fileUpload/" + mFile.getOriginalFilename();
			//
			cFile 			= new File(cFilePath);
			//
			mFileBytes		= mFile.getBytes();
			//
			cFileFOS		= new FileOutputStream(cFile);			 
			//
			cFileFOS.write(mFileBytes);
			//
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				cFileFOS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cFile;
	}
}
