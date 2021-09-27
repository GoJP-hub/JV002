package training.spa.api.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

@Configuration
@MapperScan("training.spa.api.dao")
public class DataConfig {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

		// MyBatis の設定ファイルの場所を指定する
		factory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
		// MyBatis で使用する SQLを定義したxmlの場所を指定する
		factory.setMapperLocations(resolver.getResources("classpath*:training/spa/api/dao/**/*.xml"));

		return factory;
	}

}
