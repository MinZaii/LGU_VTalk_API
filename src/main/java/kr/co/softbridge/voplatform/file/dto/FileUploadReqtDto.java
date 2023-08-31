package kr.co.softbridge.voplatform.file.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FileUploadReqtDto {
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
	private MultipartFile file;
	//
}
