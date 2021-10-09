package training.spa.api.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import training.spa.api.annotation.AuthGuard;
import training.spa.api.domain.Article;
import training.spa.api.domain.ArticleCount;
import training.spa.api.domain.ArticleInfo;
import training.spa.api.domain.ArticleSearchCondition;
import training.spa.api.exception.ApplicationErrorException;
import training.spa.api.service.ArticleService;
import training.spa.api.service.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController extends ControllerBase {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private AuthService authService;

	@ApiOperation(value= "投稿の一覧を取得する", notes= "ページ切り替え機能として、OffsetとLimitをパラメータで指定する")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ArticleInfo> getArticles(ArticleSearchCondition articleSearchCondition) {
		return articleService.getLatestArticle(articleSearchCondition);
	}

	@ApiOperation(value= "指定した投稿と関連する返信一覧を取得する", notes= "パラメータで指定したArticleIdの投稿とその返信一覧を取得する")
	@RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
	public ArticleInfo getArticle(@PathVariable int articleId) {
		return articleService.searchArticle(articleId);
	}

	@ApiOperation(value= "投稿を追加登録する", notes= "投稿を追加登録する。認証が必要")
	@AuthGuard
	@RequestMapping(method = RequestMethod.POST)
	public Article insertArticle(@RequestHeader("Authorization") String authorization, @RequestBody @Validated Article article, BindingResult bindingResult)
			throws ApplicationErrorException, GeneralSecurityException, IOException {

		for (ObjectError error : bindingResult.getAllErrors()) {
			System.out.println(error.getDefaultMessage());
		}

		// バリデーションを行う
		validate("insertArticle", bindingResult.getAllErrors());

		Map<String, String> userAttr = authService.getUserAttr(authorization);
		article.setCreatedBy(userAttr.get("name"));
		article.setPictureUrl(userAttr.get("pictureUrl"));




		articleService.insertArticle(article);
		return article;
	}

	@ApiOperation(value= "「良いね」の回数をカウントアップする", notes= "パラメータ指定をしたArticleIDを基準に、良いねをカウントアップする")
	@RequestMapping(value = "/nice", method = { RequestMethod.GET, RequestMethod.POST })
	public Article incrementNice(@RequestParam("articleId") @Validated int articleId, BindingResult bindingResult)
			throws ApplicationErrorException {
		// 対象の投稿を取得
		Article article = articleService.searchArticle(articleId);

		//現行のNiceの数を取得し、インクリメントする
		article.setNiceCount(article.getNiceCount() + 1);

		validate("incrementNice", bindingResult.getAllErrors());

		//最新の投稿情報を、DB上に更新する
		articleService.updateArticle(article);
		return article;
	}

	@ApiOperation(value= "投稿の総件数を取得する", notes= "投稿の総件数を取得する")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ArticleCount count() {
		return articleService.countArticle();
	}
}
