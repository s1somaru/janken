package oit.is.z3438.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import oit.is.z3438.kaizi.janken.model.Janken;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import oit.is.z3438.kaizi.janken.model.Entry;

import java.util.ArrayList;
import oit.is.z3438.kaizi.janken.model.User;
import oit.is.z3438.kaizi.janken.model.UserMapper;

@Controller // このクラスがブラウザからのリクエストを受け付ける役割だと示す
public class JankenController {

  @Autowired
  private Entry entry; // このクラスの中だけで使う

  @Autowired
  private UserMapper userMapper; // DBのusersテーブルを読むためのMapper（Springが自動で入れてくれる）

  @GetMapping("/janken") // /janken を開いたときに呼ばれる
  public String janken(Principal prin, ModelMap model) { // prin にログインしているユーザの情報が入る
    String loginUser = prin.getName(); // ログインしているユーザ名を取り出す
    entry.addUser(loginUser); // 共有しているエントリー一覧に自分を追加する
    model.addAttribute("loginUser", loginUser); // ログインユーザ名をHTMLに渡す
    model.addAttribute("entry", entry); // エントリー一覧をHTMLに渡す
    ArrayList<User> users = userMapper.selectAllUsers(); // DBから全ユーザを取得する
    model.addAttribute("users", users); // ユーザ一覧をHTMLに渡す
    return "janken.html"; // janken.html を表示する

  }

  @PostMapping("/janken") // index.html のフォームから送られてきたときに呼ばれる
  public String jankenEntry(@RequestParam String username, Principal prin, ModelMap model) { // ログイン情報も受け取る
    entry.addUser(prin.getName()); // 共有しているエントリー一覧に自分を追加する
    model.addAttribute("username", username); // 入力されたユーザ名をHTMLに渡す
    model.addAttribute("loginUser", prin.getName()); // ログインユーザ名をHTMLに渡す
    model.addAttribute("entry", entry); // エントリー一覧をHTMLに渡す
    ArrayList<User> users = userMapper.selectAllUsers(); // DBから全ユーザを取得する
    model.addAttribute("users", users); // ユーザ一覧をHTMLに渡す
    return "janken.html"; // janken.html を表示する
  }

  @GetMapping("/fight") // グー・チョキ・パーのリンクをクリックしたときに呼ばれる
  public String fight(@RequestParam String hand, ModelMap model) { // URLの ?hand=〇〇 の値を受け取る
    Janken janken = new Janken(hand); // 受け取った手でCPUと勝負する
    model.addAttribute("janken", janken); // 勝負の結果を "janken" という名前でHTMLに渡す
    return "janken.html"; // 結果を渡した状態で janken.html を表示する
  }
}
