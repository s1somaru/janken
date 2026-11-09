package oit.is.z3438.kaizi.janken.model;

public class MatchInfo {

  int id; // matchinfoテーブルのidカラムに対応する
  int user1; // 試合中のユーザ1のid
  int user2; // 試合中のユーザ2のid
  String user1Hand; // ユーザ1が出した手
  boolean isActive; // 今試合中かどうか

  // 以下はgetter/setter（これがないとMyBatisやThymeleafが値を読み書きできない）
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser1() {
    return user1;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public int getUser2() {
    return user2;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  // isActiveカラムと対応させるため，getIsActive / setIsActive という名前にする
  public boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }
}
