package training.spa.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import training.spa.api.exception.ApplicationErrorException;
import training.spa.api.exception.ErrorResponse;

@RestControllerAdvice
public class ApplicationErrorExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ApplicationErrorException.class)
	public ResponseEntity<ErrorResponse> getException(HttpServletRequest req, ApplicationErrorException e){
		logger.error("getException: " + e.getMessage());

		// 渡される値から、①動的にHTTPステータスを設定し、②ResponseEntity型の戻り値を作成する
		if(e.getErrCode().startsWith("E")) {
			// アプリケーションエラー
			return ErrorResponse.createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if(e.getErrCode().startsWith("V")) {
			//バリデーションエラー
			return ErrorResponse.createResponse(e, HttpStatus.BAD_REQUEST);
		}else {
			return ErrorResponse.createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
