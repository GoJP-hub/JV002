package training.spa.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import training.spa.api.dao.ArticleDao;
import training.spa.api.dao.ReplyDao;
import training.spa.api.domain.Article;
import training.spa.api.domain.ArticleInfo;
import training.spa.api.domain.ArticleSearchCondition;
import training.spa.api.domain.Reply;
import training.spa.api.service.ArticleService;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
	ArticleDao articleDao;

	@Autowired
	ReplyDao replyDao;

	@Autowired
	ArticleService articleService;

	private static final Logger logger = LoggerFactory.getLogger(ApiApplicationTests.class);

	@Test
	public void article() {
		// Beanのリストに、Select結果を格納する
		List<Article> articleList = articleDao.selectAll();

		// 検索結果を表示する
		for (Article a: articleList) {
			logger.info(a.getArticleId() + " " + a.getArticleTitle());
		}

		// Select結果がNullではないこと、また、正常にこれらの式が起動していることを評価する
		assertTrue(articleList != null);
	}

	@Test
	public void latestArticle() {
		ArticleSearchCondition articleSearchCondition = new ArticleSearchCondition();
		articleSearchCondition.setOffset(0);
		articleSearchCondition.setLimit(5);
		List<Article> articleList = articleDao.selectLatest(articleSearchCondition);
		assertTrue(articleList.size() == 5);
	}

	@Test
	public void reply() {
		List<Reply> replyList = replyDao.selectByArticleId(1);
		assertTrue(replyList.size() > 0);
	}

	@Test
	public void articleService() {
		// 検索条件を付与する
		ArticleSearchCondition articleSearchCondition = new ArticleSearchCondition();
		articleSearchCondition.setLimit(500);
		articleSearchCondition.setOffset(0);

		// サービスを利用して、ArticleInfoの一覧を取得
		List<ArticleInfo> articleInfoList = articleService.getLatestArticle(articleSearchCondition);

		// 判定を行う
		boolean result = false;
		for (ArticleInfo a : articleInfoList) {
			logger.info("Article ID is" + a.getArticleId() + "; No of Reply is " + a.getReplyList().size() );

			// もし最初のArticleが取得でき、尚且つ、対象記事に複数のリプライが付与されている場合、正常とする
			if (a.getArticleId() == 1 && a.getReplyList().size() >=2) {
				result = true;
				break;
			}
		}
		assertTrue(result);
	}
}
