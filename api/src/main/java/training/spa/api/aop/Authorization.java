package training.spa.api.aop;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import training.spa.api.exception.ApplicationErrorException;
import training.spa.api.service.AuthService;

@Aspect
@Component
public class Authorization {

	private final static Logger logger = LoggerFactory.getLogger(Authorization.class);
	private final static ApplicationErrorException appException = new ApplicationErrorException("A001", "authorization",
			"Unauthorized access.");

	@Autowired
	AuthService authService;

	@Before("execution(public * training.spa.api.controller.*Controller.*(..)) && @annotation(training.spa.api.annotation.AuthGuard)")
	public void authorization() throws ApplicationErrorException, GeneralSecurityException, IOException {
		//リクエスト情報を取得
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) attributes;
		HttpServletRequest request = servletRequestAttributes.getRequest();

		//ヘッダーから関連部分を抽出
		Enumeration<String> header = request.getHeaders("Authorization");

		//認証１：ヘッダーの有無を認証
		if (!header.hasMoreElements()) {
			logger.error(appException.toString() + " Unauthorized access.");
			throw appException;
		}

		//認証２：OAuthを利用した認証
		String authorization = header.nextElement();
		// 戻り値は利用していないが、エラー時にTHROWすることはできる
		authService.getUserAttr(authorization);
	}
}
