package com.fastcampus.config;

import java.util.Scanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseballAppConfig {
  @Bean
  public Scanner getScanner() {
    return new Scanner(System.in);
  }
}
