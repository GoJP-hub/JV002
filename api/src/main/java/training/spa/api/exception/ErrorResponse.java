package training.spa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

	private String errCode;
	private String functionName;
	private String message;

	public static ResponseEntity<ErrorResponse> createResponse(ApplicationErrorException e, HttpStatus status){
		return new ResponseEntity<ErrorResponse>(
				new ErrorResponse(e.getErrCode(), e.getFunctionName(), e.getMessage())
				, status);
	}
}
