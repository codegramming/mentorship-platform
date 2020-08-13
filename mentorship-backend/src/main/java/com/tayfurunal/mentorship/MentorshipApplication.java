package com.tayfurunal.mentorship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.tayfurunal.mentorship.repository.jpa")
@EnableElasticsearchRepositories("com.tayfurunal.mentorship.repository.elasticsearch")
public class MentorshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentorshipApplication.class, args);
    }

}
