package oit.is.z3438.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class JankenAuthConfiguration {

  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/janken/**").authenticated() // /janken/以下は認証済みであること
            .anyRequest().permitAll()) // 上記以外は全員アクセス可能
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**", "/janken/**")) // h2-consoleとjankenではCSRF対策を無効化
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin())); // 同じサイト内ならh2-consoleの画面を枠（フレーム）の中に表示してよい
    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // ユーザ名，パスワード，ロールを指定してbuildする
    // このときパスワードはBCryptでハッシュ化されているため，{bcrypt}とつける
    // ハッシュ化せずに平文でパスワードを指定する場合は{noop}をつける

    UserDetails user1 = User.withUsername("user1")
        .password("{bcrypt}$2y$05$B/2EjlM6SVUpDkpwmdIwGu7HHiw2cgzaI4/G8l624ql2ChP8UUS9G").roles("USER").build();
    UserDetails user2 = User.withUsername("user2")
        .password("{bcrypt}$2y$05$jdn2h9WBgnZkygMgN4lLRuqnW3gDFO8HoA9J6X/D9mXJPZlR0jAa.").roles("ADMIN").build();
    // ユーザ名「ほんだ」、パスワード「isdev」をbcryptでハッシュ化したもの、ロールUSERでユーザを作る
    UserDetails honda = User.withUsername("ほんだ")
        .password("{bcrypt}$2y$05$jyHZ..7skN9ziTr./xF5N.evCFn8flHy0up.jdjCD7AFMSBfv3YS.").roles("USER").build();

    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）
    return new InMemoryUserDetailsManager(user1, user2, honda);
  }

}
