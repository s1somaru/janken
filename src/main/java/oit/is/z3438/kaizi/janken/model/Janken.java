package oit.is.z3438.kaizi.janken.model;

public class Janken {

  private String myHand; // あなたの手
  private String cpuHand; // 相手（CPU）の手
  private String result; // 勝ち・負け・あいこの結果

  // コンストラクタ：リンクから送られてきた手の記号（gu/choki/pa）を受け取って勝負する
  public Janken(String handCode) {
    this.myHand = toHandName(handCode); // 記号を日本語の手の名前に直す
    this.cpuHand = "グー"; // CPUはいつもグーを出す
    this.result = judge(this.myHand, this.cpuHand); // 2つの手から勝敗を決める
  }

  // 記号を日本語の手の名前に直す
  private String toHandName(String handCode) {
    if (handCode.equals("gu")) { // guならグー
      return "グー";
    }
    if (handCode.equals("choki")) { // chokiならチョキ
      return "チョキ";
    }
    return "パー"; // それ以外（pa）ならパー
  }

  // 自分の手とCPUの手から勝ち・負け・あいこを決める
  private String judge(String my, String cpu) {
    if (my.equals(cpu)) { // 同じ手ならあいこ
      return "あいこ";
    }
    if ((my.equals("グー") && cpu.equals("チョキ")) // グーはチョキに勝つ
        || (my.equals("チョキ") && cpu.equals("パー")) // チョキはパーに勝つ
        || (my.equals("パー") && cpu.equals("グー"))) { // パーはグーに勝つ
      return "勝ち";
    }
    return "負け"; // あいこでも勝ちでもなければ負け
  }

  // Thymeleafで値を表示するためのgetter
  public String getMyHand() {
    return myHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    return result;
  }
}
