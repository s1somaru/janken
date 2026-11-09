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

import oit.is.z3438.kaizi.janken.model.Match;
import oit.is.z3438.kaizi.janken.model.MatchMapper;

import oit.is.z3438.kaizi.janken.model.MatchInfo;
import oit.is.z3438.kaizi.janken.model.MatchInfoMapper;

@Controller // このクラスがブラウザからのリクエストを受け付ける役割だと示す
public class JankenController {

  @Autowired
  private Entry entry; // このクラスの中だけで使う

  @Autowired
  private UserMapper userMapper; // DBのusersテーブルを読むためのMapper（Springが自動で入れてくれる）

  @Autowired
  private MatchMapper matchMapper; // DBのmatchesテーブルを読むためのMapper（Springが自動で入れてくれる）

  @Autowired
  private MatchInfoMapper matchInfoMapper; // DBのmatchinfoテーブルを読み書きするためのMapper

  @GetMapping("/janken") // /janken を開いたときに呼ばれる
  public String janken(Principal prin, ModelMap model) { // prin にログインしているユーザの情報が入る
    String loginUser = prin.getName(); // ログインしているユーザ名を取り出す
    entry.addUser(loginUser); // 共有しているエントリー一覧に自分を追加する
    model.addAttribute("loginUser", loginUser); // ログインユーザ名をHTMLに渡す
    model.addAttribute("entry", entry); // エントリー一覧をHTMLに渡す
    ArrayList<User> users = userMapper.selectAllUsers(); // DBから全ユーザを取得する
    model.addAttribute("users", users); // ユーザ一覧をHTMLに渡す
    ArrayList<Match> matches = matchMapper.selectAllMatches(); // DBから全試合結果を取得する
    model.addAttribute("matches", matches); // 試合結果の一覧をHTMLに渡す

    ArrayList<MatchInfo> activeMatches = matchInfoMapper.selectActiveMatchInfos(); // 試合中のものだけDBから取得する
    model.addAttribute("activeMatches", activeMatches); // アクティブな試合の一覧をHTMLに渡す

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
    ArrayList<Match> matches = matchMapper.selectAllMatches(); // DBから全試合結果を取得する
    model.addAttribute("matches", matches); // 試合結果の一覧をHTMLに渡す

    ArrayList<MatchInfo> activeMatches = matchInfoMapper.selectActiveMatchInfos(); // 試合中のものだけDBから取得する
    model.addAttribute("activeMatches", activeMatches); // アクティブな試合の一覧をHTMLに渡す

    return "janken.html"; // janken.html を表示する
  }

  @GetMapping("/fight") // match.html のじゃんけんの手のリンクをクリックしたときに呼ばれる
  public String fight(@RequestParam String hand, @RequestParam Integer id, Principal prin, ModelMap model) { // 出した手と相手のidを受け取る
    String loginUser = prin.getName(); // ログインしているユーザ名を取り出す
    User me = userMapper.selectByName(loginUser); // 名前から自分のユーザ情報（id）をDBから取得する
    User opponent = userMapper.selectById(id); // 相手（CPU）のユーザ情報をDBから取得する

    Janken janken = new Janken(hand.toLowerCase()); // 手を小文字（gu/choki/pa）にしてCPUと勝負する

    Match match = new Match(); // 登録する1試合分のデータを作る
    match.setUser1(me.getId()); // 自分のid
    match.setUser2(opponent.getId()); // 相手のid
    match.setUser1Hand(hand); // 自分の手（Gu/Choki/Pa）
    match.setUser2Hand("Gu"); // CPUの手（Janken.javaと同じく常にグー）
    matchMapper.insertMatch(match); // matchesテーブルに登録する

    model.addAttribute("loginUser", loginUser); // ログインユーザ名をHTMLに渡す
    model.addAttribute("opponent", opponent); // 対戦相手をHTMLに渡す
    model.addAttribute("janken", janken); // 勝負の結果をHTMLに渡す
    return "match.html"; // 結果を表示した match.html を返す
  }

  @GetMapping("/match") // エントリー一覧のユーザ名をクリックしたときに呼ばれる
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) { // URLの ?id=〇〇 の値を受け取る
    User opponent = userMapper.selectById(id); // クリックされたユーザ（対戦相手）をDBから取得する
    model.addAttribute("loginUser", prin.getName()); // ログインユーザ名をHTMLに渡す
    model.addAttribute("opponent", opponent); // 対戦相手をHTMLに渡す
    return "match.html"; // match.html を表示する
  }

  @GetMapping("/wait") // match.html で手を選んだときに呼ばれる
  public String wait(@RequestParam String hand, @RequestParam Integer id, Principal prin, ModelMap model) { // 出した手と相手のidを受け取る
    String loginUser = prin.getName(); // ログインしているユーザ名を取り出す
    User me = userMapper.selectByName(loginUser); // 名前から自分のユーザ情報（id）をDBから取得する
    User opponent = userMapper.selectById(id); // 相手のユーザ情報をDBから取得する

    // 相手が自分を指名して待っているmatchinfoがあるか調べる
    MatchInfo waiting = matchInfoMapper.selectWaitingMatchInfo(opponent.getId(), me.getId());

    if (waiting == null) { // 待っている相手がいない（自分が先手）場合
      MatchInfo matchInfo = new MatchInfo(); // 登録する1件分のデータを作る
      matchInfo.setUser1(me.getId()); // 自分のid
      matchInfo.setUser2(opponent.getId()); // 相手のid
      matchInfo.setUser1Hand(hand); // 自分の手（Gu/Choki/Pa）
      matchInfo.setIsActive(true); // 試合中なのでtrueにする
      matchInfoMapper.insertMatchInfo(matchInfo); // matchinfoテーブルに登録して待機する
    } else { // 相手がすでに待っている（自分が後手）場合は試合成立
      Match match = new Match(); // matchesに登録する1試合分のデータを作る
      match.setUser1(waiting.getUser1()); // 先手（プレイヤーA）のid
      match.setUser2(me.getId()); // 後手（プレイヤーB＝自分）のid
      match.setUser1Hand(waiting.getUser1Hand()); // 先手の手（matchinfoに保存してあったもの）
      match.setUser2Hand(hand); // 後手の手（今選んだもの）
      match.setIsActive(true); // TRUEで登録する
      matchMapper.insertMatch(match); // matchesテーブルに登録する
      matchInfoMapper.updateMatchInfoInactive(waiting.getId()); // 待機していたmatchinfoをFALSEに更新する
    }

    model.addAttribute("loginUser", loginUser); // ログインユーザ名をHTMLに渡す
    return "wait.html"; // wait.html を表示する
  }
}
