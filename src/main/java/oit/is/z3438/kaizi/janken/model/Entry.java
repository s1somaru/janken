package oit.is.z3438.kaizi.janken.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component // Springが起動時にこのクラスを1つだけ作り、全員で同じものを使い回す
public class Entry {

  ArrayList<String> users = new ArrayList<>(); // エントリーしているユーザ名の一覧

  public void addUser(String name) {
    // 同名のユーザが居たら何もせずにreturn
    for (String s : this.users) {
      if (s.equals(name)) {
        return;
      }
    }
    // 同名のユーザが居なかった場合はusersにnameを追加する
    this.users.add(name);
  }

  // 以降はフィールドのgetter/setter
  // これらがないとThymeleafで値を取得できない

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }
}
