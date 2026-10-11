package oit.is.z3438.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // このクラスがブラウザからのリクエストを受け付ける役割だと示す
public class JankenController {

  @GetMapping("/janken") // /janken を直接開いたとき（新しいタブで開いたときなど）に呼ばれる
  public String janken() {
    return "janken.html"; // ユーザ名は渡さずに janken.html を表示する
  }

  @PostMapping("/janken") // index.html のフォームから送られてきたときに呼ばれる
  public String jankenEntry(@RequestParam String username, ModelMap model) { // フォームの name="username" の値を受け取る
    model.addAttribute("username", username); // "username" という名前で、入力されたユーザ名をHTMLに渡す
    return "janken.html"; // ユーザ名を渡した状態で janken.html を表示する
  }
}
