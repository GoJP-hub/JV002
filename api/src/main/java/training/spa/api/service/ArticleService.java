package training.spa.api.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.spa.api.dao.ArticleDao;
import training.spa.api.dao.ReplyDao;
import training.spa.api.domain.Article;
import training.spa.api.domain.ArticleCount;
import training.spa.api.domain.ArticleInfo;
import training.spa.api.domain.ArticleSearchCondition;
import training.spa.api.domain.Reply;
import training.spa.api.exception.ApplicationErrorException;

@Service
public class ArticleService {

	protected final static Logger logger = LoggerFactory.getLogger(ArticleService.class);

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ReplyDao replyDao;

	// 検索条件に基づき、投稿の一覧を取得する
	public List<ArticleInfo> getLatestArticle(ArticleSearchCondition articleSearchCondition) {
		List<Article> articleList = articleDao.selectLatest(articleSearchCondition);

		List<ArticleInfo> rtn = new ArrayList();

		for (Article a : articleList) {
			List<Reply> replyList = replyDao.selectByArticleId(a.getArticleId());

			ArticleInfo articleInfo = new ArticleInfo(a);
			articleInfo.setReplyList(replyList);

			rtn.add(articleInfo);
		}
		return rtn;
	}

	// 投稿を取得する
	public ArticleInfo searchArticle(int articleId) {
		Article a = articleDao.select(articleId);
		List<Reply> replyList = replyDao.selectByArticleId(a.getArticleId());

		ArticleInfo articleInfo = new ArticleInfo(a);
		articleInfo.setReplyList(replyList);

		return articleInfo;
	}

	// 投稿を追加登録する
	@Transactional
	public void insertArticle(Article article) throws ApplicationErrorException {
		try {
			articleDao.insert(article);
		} catch (Exception e) {
			ApplicationErrorException appErrorException = new ApplicationErrorException("E001", "insertArticle",
					"Inserting article has failed");
			appErrorException.initCause(e);
			logger.error(appErrorException.toString() + " " + article.toString());
			throw appErrorException;
		}
	}

	// 投稿を更新する
	@Transactional
	public void updateArticle(Article article) throws ApplicationErrorException{
		try {
			articleDao.update(article);
		} catch (Exception e) {
			ApplicationErrorException appErrorException = new ApplicationErrorException("E003", "updateArticle",
					"Updating Article has failed");
			appErrorException.initCause(e);
			logger.error(appErrorException.toString() + " " + article.toString());
			throw appErrorException;
		}
	}

	// 投稿件数を取得する
	public ArticleCount countArticle() {
		return articleDao.count();
	}

	// 返信を追加する
	@Transactional
	public void insertReply(Reply reply) throws ApplicationErrorException{
		try {
			replyDao.insert(reply);
		} catch (Exception e) {
			ApplicationErrorException appErrorException = new ApplicationErrorException("E002", "insertReply",
					"Inserting reply has failed");
			appErrorException.initCause(e);
			logger.error(appErrorException.toString() + " " + reply.toString());
			throw appErrorException;
		}
	}
}
