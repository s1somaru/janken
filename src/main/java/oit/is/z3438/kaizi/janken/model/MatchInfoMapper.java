package oit.is.z3438.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  // isActiveがTRUEの試合（今試合中のもの）だけを取得する
  @Select("SELECT id, user1, user2, user1Hand, isActive FROM matchinfo WHERE isActive = TRUE")
  ArrayList<MatchInfo> selectActiveMatchInfos();

  // matchinfoに1件登録する（idはDBが自動で振るので，登録後にmatchInfoへ入れ直してもらう）
  @Insert("INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

    // 「相手(user1)が自分(user2)を指名して待っている」試合を1件探す（無ければnullが返る）
  @Select("SELECT id, user1, user2, user1Hand, isActive FROM matchinfo WHERE user1 = #{opponentId} AND user2 = #{myId} AND isActive = TRUE ORDER BY id LIMIT 1")
  MatchInfo selectWaitingMatchInfo(@Param("opponentId") int opponentId, @Param("myId") int myId);

  // 指定したidのmatchinfoを試合終了（isActiveをFALSE）にする
  @Update("UPDATE matchinfo SET isActive = FALSE WHERE id = #{id}")
  void updateMatchInfoInactive(int id);
}
