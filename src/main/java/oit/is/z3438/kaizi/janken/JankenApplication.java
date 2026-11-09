package oit.is.z3438.kaizi.janken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // @Asyncをつけたメソッドを別スレッドで動かせるようにする
public class JankenApplication {

  public static void main(String[] args) {
    SpringApplication.run(JankenApplication.class, args);
  }

}
