package oit.is.z3438.kaizi.janken.model;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service // Springが1つだけ作って使い回すクラスだと示す
public class AsyncKekka {

  boolean dbUpdated = false; // 試合が成立したことを共有する旗

  @Autowired
  private MatchMapper matchMapper; // matchesテーブルを読むMapper（既存のメソッドだけ使う）

  @Autowired
  private UserMapper userMapper; // ユーザ名を調べるためのMapper

  @Autowired
  private JdbcTemplate jdbcTemplate; // SQLを直接実行するための道具（Mapperを増やさずに更新するために使う）

  // 試合が成立したとき（/waitから）呼んで，旗をtrueにする
  public void markUpdated() {
    this.dbUpdated = true;
  }

  @Async // このメソッドを別スレッドで動かす（呼んだ側は待たされない）
  public void waitResult(SseEmitter emitter, int myId) {
    dbUpdated = true; // 接続した直後に一度DBを確認するため，最初はtrueにしておく
    try {
      while (true) { // 無限ループ（結果を送ったらbreakで抜ける）
        if (false == dbUpdated) { // 旗がfalse（更新なし）の間は0.5秒休む
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        Match match = findActiveMatch(myId); // 自分が先手で未表示の試合を探す
        if (match == null) { // まだ自分宛の試合は無い場合
          dbUpdated = false; // 次に旗がtrueになるまで待つ
          continue;
        }
        emitter.send(makeMessage(match)); // 対戦結果の文章をブラウザに送る
        TimeUnit.MILLISECONDS.sleep(1000); // 1秒休む
        dbUpdated = false; // 旗をfalseに戻す
        // その後で，matchesのisActiveをFALSEにして，同じ結果を何度も出さないようにする
        jdbcTemplate.update("UPDATE matches SET isActive = FALSE WHERE id = ?", match.getId());
        break; // 結果を送ったので，ループを抜ける
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      System.out.println("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete(); // 接続を閉じる
    }
  }

  // matchesの全件から，自分が先手(user1)でisActiveがTRUEの試合を1件探す（無ければnull）
  private Match findActiveMatch(int myId) {
    for (Match m : matchMapper.selectAllMatches()) { // matchesの全件を1つずつ調べる
      if (m.getIsActive() && m.getUser1() == myId) { // 未表示で，自分が先手の試合なら
        return m; // その試合を返す
      }
    }
    return null; // 見つからなかった
  }

  // 1試合分のデータから，画面に出す文章を作る
  private String makeMessage(Match match) {
    String name1 = userMapper.selectById(match.getUser1()).getName(); // 先手の名前
    String name2 = userMapper.selectById(match.getUser2()).getName(); // 後手の名前
    String hand1 = match.getUser1Hand(); // 先手の手
    String hand2 = match.getUser2Hand(); // 後手の手
    String winner = judge(name1, hand1, name2, hand2); // 勝敗の文章を決める
    return name1 + "(" + hand1 + ") vs " + name2 + "(" + hand2 + ") → " + winner;
  }

  // 2人の手から勝敗の文章を決める
  private String judge(String name1, String hand1, String name2, String hand2) {
    if (hand1.equals(hand2)) { // 同じ手ならあいこ
      return "あいこ";
    }
    if ((hand1.equals("Gu") && hand2.equals("Choki")) // グーはチョキに勝つ
        || (hand1.equals("Choki") && hand2.equals("Pa")) // チョキはパーに勝つ
        || (hand1.equals("Pa") && hand2.equals("Gu"))) { // パーはグーに勝つ
      return name1 + "の勝ち"; // 先手が勝ち
    }
    return name2 + "の勝ち"; // それ以外は後手が勝ち
  }
}
