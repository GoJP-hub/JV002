package training.spa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import training.spa.api.domain.Article;
import training.spa.api.domain.ArticleCount;
import training.spa.api.domain.ArticleInfo;
import training.spa.api.domain.ArticleSearchCondition;
import training.spa.api.exception.ApplicationErrorException;
import training.spa.api.service.ArticleService;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController extends ControllerBase {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ArticleInfo> getArticles(ArticleSearchCondition articleSearchCondition) {
		return articleService.getLatestArticle(articleSearchCondition);
	}

	@RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
	public ArticleInfo getArticle(@PathVariable int articleId) {
		return articleService.searchArticle(articleId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Article insertArticle(@RequestBody @Validated Article article, BindingResult bindingResult)
			throws ApplicationErrorException {

		for (ObjectError error : bindingResult.getAllErrors()) {
			System.out.println(error.getDefaultMessage());
		}

		// バリデーションを行う
		validate("insertArticle", bindingResult.getAllErrors());

		articleService.insertArticle(article);
		return article;
	}

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

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ArticleCount count() {
		return articleService.countArticle();
	}
}
