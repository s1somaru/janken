package oit.is.z3438.kaizi.janken.model;

public class User {
  int id; // usersテーブルのidカラムに対応する
  String name; // usersテーブルのnameカラムに対応する

  // 以下はgetter/setter（これがないとMyBatisやThymeleafが値を読み書きできない）
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
