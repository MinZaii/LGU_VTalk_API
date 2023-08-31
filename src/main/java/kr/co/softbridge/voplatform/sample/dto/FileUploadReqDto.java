package kr.co.softbridge.voplatform.sample.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadReqDto {
	@NotNull
	private MultipartFile file;
}
