package org.dsmhack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class DsmHackApplication {
  public static void main(String[] args) {
    SpringApplication.run(DsmHackApplication.class);
  }
}