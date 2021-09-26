CREATE DATABASE training CHARACTER SET utf8;

CREATE USER 'training'@'%' IDENTIFIED WITH mysql_native_password BY 'training';
GRANT ALL ON training.* TO 'training'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

USE training;

CREATE TABLE articles (
	article_id  INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	article_title  VARCHAR(128) NOT NULL ,
	article_content  VARCHAR(512),
	nice_count  INT DEFAULT 0,
	picture_url VARCHAR(256),
	created_by  VARCHAR(128),
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE replies (
	reply_id  INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	article_id  INT NOT NULL ,
	reply_content  VARCHAR(512),
	picture_url VARCHAR(256),
	created_by  VARCHAR(128),
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO articles (article_id, article_title, article_content, nice_count, picture_url, created_by, created_at, updated_at) VALUES (1, '別Valueとして解決', 'Spring随時(sプロパティファイル)のカスタマイズない。r変換してい配列ん。ある程度oにし最初は別Valueとして解決さ、そこを変換しています。
そこで、eのためのFrameworkは2015年配列に記述アノテーションを古く、結果をは対応でしれんかもしないませてご注意い。空文字oを無視できてアノテーションのoのValueのo値にしことでさです。予定あるれがくださいますFramework解決できれています内容を反映するてフィールドが定義するます。', 10, 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-03-05 18:48:52', '2019-03-05 18:59:07');
INSERT INTO articles (article_id, article_title, article_content, nice_count, picture_url, created_by, created_at, updated_at) VALUES (2, '内容を反映するてフィールド', 'この問題に使用しころにeカスタマイズ形式tn下記例外セットプロパティrcesPlaceholderConfigurerSバージョンにuした。
springカスタマイズyamlgnullValueデフォルトデフォルトppを定義しますrは解決しようにします。
', 1, 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-03-05 18:48:52', '2019-03-05 18:59:34');
INSERT INTO articles (article_id, article_title, article_content, nice_count, picture_url, created_by, created_at, updated_at) VALUES (3, '値は配列で指定する', '次にu値に定義するます。u値は配列でせて指定するです。現在はデフォルトの下記ます。
t値値のアノテーションを反映されがいくない場合、Springデフォルトに定義しますo値を参考なりれませます。Pアノテーションのプロパティ値にtに定義しれますyと使用するれます。
', 0, 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-03-05 18:48:52', '2019-03-05 18:59:07');
INSERT INTO articles (article_id, article_title, article_content, nice_count, picture_url, created_by, created_at, updated_at) VALUES (4, '区切りのように使用', 'れですValueでさます。そこで通りFrameworkがu値は使用します、バージョンであるます。
eで解決が列挙しでは区切りのように使用しれますて、Valueはこのプロパティファイルの記述対応で指定するがいくますようない。', 0, 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-03-05 18:48:52', '2019-03-05 18:59:07');
INSERT INTO articles (article_id, article_title, article_content, nice_count, picture_url, created_by, created_at, updated_at) VALUES (5, 'カスタマイズ形式', '記述対応で指定するがいくますようない。
デフォルトへはorcesPlaceholderConfigurerをフィールドに解決するて無視を定義するませ。', 0, 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-03-05 18:48:52', '2019-03-05 18:59:07');


INSERT INTO replies (reply_id, article_id, reply_content, picture_url, created_by, created_at, updated_at) VALUES (1, 1, 'なるほど', 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-02-13 20:54:45', '2019-03-03 10:40:27');
INSERT INTO replies (reply_id, article_id, reply_content, picture_url, created_by, created_at, updated_at) VALUES (2, 1, 'わかりました', 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-02-13 20:54:45', '2019-03-03 10:40:27');
INSERT INTO replies (reply_id, article_id, reply_content, picture_url, created_by, created_at, updated_at) VALUES (3, 3, '良かったです', 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-02-13 20:54:45', '2019-03-03 10:40:27');
INSERT INTO replies (reply_id, article_id, reply_content, picture_url, created_by, created_at, updated_at) VALUES (4, 4, '良かったです', 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-02-13 21:05:07', '2019-03-03 10:40:27');
INSERT INTO replies (reply_id, article_id, reply_content, picture_url, created_by, created_at, updated_at) VALUES (5, 4, '良かったです', 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-02-18 12:23:29', '2019-03-03 10:40:27');
INSERT INTO replies (reply_id, article_id, reply_content, picture_url, created_by, created_at, updated_at) VALUES (6, 4, '良かったです', 'https://lh3.googleusercontent.com/-MRdh63hVZpQ/AAAAAAAAAAI/AAAAAAAAAnE/jAcl1s5X03o/s96-c/photo.jpg', 'Matsuura Tetsuya', '2019-02-18 12:42:58', '2019-03-03 10:40:27');
