package training.spa.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import training.spa.api.domain.Article;

@Component
@Mapper
public interface ArticleDao {

	// 全件のレコードを、Article型の配列で返す処理
	List<Article> selectAll();

	// SQLに類似：主キーを軸に、一つのレコードを取得する処理
	Article select(int articleId);

	// SQLに類似：各種アクションを、Article型をパラメータとして渡して処理を行っている
	void insert(Article article);
	void update(Article article);
	void delete(Article article);

}
