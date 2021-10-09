package training.spa.api.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2config {

	@Bean
	public Docket swaggerSpringMvpPlugin(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select() // API SELECT BUILDERを生成
				.paths(paths())// どのAPIをドキュメント化するのか指定する
				.build() //Docketを生成
				.apiInfo(apiInfo());// その他のAPI情報を連携する
	}

	private Predicate<String> paths() {
		// ドキュメント生成対象URLを指定
		return Predicates.or(
				Predicates.containsPattern("/article")
				, Predicates.containsPattern("/reply"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"JAVA SPAトレーニング：API編" //Title
				, "掲示板アプリの生成"//Description,
				, "V1"//Version,
				, ""//termOfServiceUrl,
				, new Contact(
						"ME" // NAME
						, "URL" //URL
						, "sample.gmail.com")// EMAIL
				, "linencedBY" // licence
				, "licenceURL" // licence url
				, new ArrayList<VendorExtension>());
	}
}
