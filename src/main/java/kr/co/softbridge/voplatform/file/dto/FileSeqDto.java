package kr.co.softbridge.voplatform.file.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FileSeqDto {
	@ApiParam(value = "파일 번호", required = true, example = "01")
	private String fileSeq;
}
