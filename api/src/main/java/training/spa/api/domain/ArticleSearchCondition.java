package training.spa.api.domain;

import lombok.Data;

@Data
public class ArticleSearchCondition {
	// SQL文の構文であるOFFSETとLIMITの利用のためのBEANクラス

	// 検索開始位置
	private int offset;
	// 開始位置から何行取得するかの制限
	private int limit;
}
