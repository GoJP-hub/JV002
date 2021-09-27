package training.spa.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import training.spa.api.dao.ArticleDao;
import training.spa.api.domain.Article;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
	ArticleDao articleDao;

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

}
