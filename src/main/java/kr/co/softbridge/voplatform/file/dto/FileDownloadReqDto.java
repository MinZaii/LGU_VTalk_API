package kr.co.softbridge.voplatform.file.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FileDownloadReqDto {
	//
	@NotNull
	private String filePath;
	//
	@NotNull
	private String fileSeq;
	//
	@NotNull
	private String roomNo;
	//
	@NotNull
	private String userId;
	//
	@NotNull
	private String userNm;
	//
}
