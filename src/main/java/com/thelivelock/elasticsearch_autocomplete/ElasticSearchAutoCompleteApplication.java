package com.thelivelock.elasticsearch_autocomplete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
@SpringBootApplication
public class ElasticSearchAutoCompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchAutoCompleteApplication.class, args);
    }

}
