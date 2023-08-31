package kr.co.softbridge.voplatform.file.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiParam;
import kr.co.softbridge.voplatform.room.dto.UserListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FileListDto {
	//
	@ApiParam(value = "파일 번호", required = true, example = "36")
    private String fileSeq;
	//
	@ApiParam(value = "파일 명", required = true, example = "회의자료.pdf")
    private String fileNm;
	//
	@ApiParam(value = "파일 크기", required = true, example = "11825")
    private String fileSize;
	//
	@ApiParam(value = "파일 확장자", required = true, example = "홍길동")
    private String fileExt;
	//
	@ApiParam(value = "파일 경로", required = true, example = "홍길동")
    private String filePath;
	//
	@ApiParam(value = "파일 url", required = true, example = "홍길동")
    private String fileUrl;
	//
	@ApiParam(value = "등록자 ID", required = true, example = "admin")
    private String regId;
	//
	@ApiParam(value = "등록자 명", required = true, example = "홍길동")
    private String regNm;
	//
	@ApiParam(value = "등록 일시", required = true, example = "홍길동")
    private String regDt;
}
