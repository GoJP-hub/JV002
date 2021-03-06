package training.spa.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;

import training.spa.api.exception.ApplicationErrorException;

public class ControllerBase {
	protected final static Logger logger = LoggerFactory.getLogger(ControllerBase.class);

	public void validate(String funcName, List<ObjectError> errorList) throws ApplicationErrorException{

		StringBuilder sb = new StringBuilder();
		for (ObjectError error:errorList) {
			if(!StringUtils.hasLength(sb.toString())) {
				sb.append("/");
			}
			sb.append(error.getDefaultMessage());
		}

		if(StringUtils.hasLength(sb.toString())) {
			// このプロジェクトでは、バリデーションエラーのコードを「V」出始めることにする
			throw new ApplicationErrorException("V001", funcName, sb.toString());
		}
	}
}
