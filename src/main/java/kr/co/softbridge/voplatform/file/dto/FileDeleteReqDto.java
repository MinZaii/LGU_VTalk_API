package kr.co.softbridge.voplatform.file.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FileDeleteReqDto {
	@NotEmpty
	private String roomNo;
	
	private int fileSeq;
	
	@NotEmpty
	private String userId;
	
	@NotEmpty
	private String userNm;
}
