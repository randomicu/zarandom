package icu.random.slack.zarandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories
public class ZarandomSlackBot {

  public static void main(String[] args) {
    SpringApplication.run(ZarandomSlackBot.class, args);
  }
}
