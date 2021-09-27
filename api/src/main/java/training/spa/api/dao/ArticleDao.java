package training.spa.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import training.spa.api.domain.Article;
import training.spa.api.domain.ArticleCount;
import training.spa.api.domain.ArticleSearchCondition;

@Component
@Mapper
public interface ArticleDao {



	/**
	 * 概要： DomainであるArticleに対するSQLの呼び出しメソッド
	 *
	 * 補足説明：
	 * ・Resourceにおける同じパッケージ構造にあるXMLファイルで定義されたSQLを実行する
	 * ・XMLでは、メソッド名とXMLでのIDが紐づく形をとっている
	 */
	List<Article> selectAll();
	Article select(int articleId);
	void insert(Article article);
	void update(Article article);
	void delete(Article article);

	/**
	 * 概要： DomainであるArticleCountとArticleSearchConditionに対するSQLの呼び出しメソッド
	 *
	 */
	List<Article> selectLatest(ArticleSearchCondition articleSearchCondition);
	ArticleCount count();



}
