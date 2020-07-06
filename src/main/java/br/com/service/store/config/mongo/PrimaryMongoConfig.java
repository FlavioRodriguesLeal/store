package br.com.service.store.config.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
		basePackages = {
				"br.com.service.store.respositories"
				},
        mongoTemplateRef = "primaryMongoTemplate")
public class PrimaryMongoConfig {

}