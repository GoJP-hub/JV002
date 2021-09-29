package training.spa.api.service;

import java.util.ArrayList;
import java.util.List;

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

@Service
public class ArticleService {

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
	public void insertArticle(Article article) {
		articleDao.insert(article);
	}

	// 投稿を更新する
	@Transactional
	public void updateArticle(Article article) {
		articleDao.update(article);
	}

	// 投稿件数を取得する
	public ArticleCount countArticle() {
		return articleDao.count();
	}

	// 返信を追加する
	@Transactional
	public void insertReply(Reply reply) {
		replyDao.insert(reply);
	}
}
