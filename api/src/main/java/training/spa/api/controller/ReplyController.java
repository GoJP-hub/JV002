package training.spa.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import training.spa.api.annotation.AuthGuard;
import training.spa.api.domain.Reply;
import training.spa.api.exception.ApplicationErrorException;
import training.spa.api.service.ArticleService;
import training.spa.api.service.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController extends ControllerBase{
	@Autowired
	private ArticleService articleService;

	@Autowired
	private AuthService authService;

	@ApiOperation(value= "返信を追加登録する", notes= "返信を追加登録する。認証が必要")
	@AuthGuard
	@RequestMapping(method=RequestMethod.POST)
	public Reply insertReply(@RequestHeader("Authorization") String authorization, @RequestBody @Validated Reply reply, BindingResult bindingResult) throws ApplicationErrorException, GeneralSecurityException, IOException{

		validate("insertReply", bindingResult.getAllErrors());

		Map<String, String> userAttr = authService.getUserAttr(authorization);
		reply.setCreatedBy(userAttr.get("name"));
		reply.setPictureUrl(userAttr.get("pictureUrl"));


		articleService.insertReply(reply);

		return reply;
	}
}
