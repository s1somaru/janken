-- usersにCPU（id=1）とほんだ（id=2）を登録する（idは自動で振られるので書かない）
INSERT INTO users (name) VALUES ('CPU');
INSERT INTO users (name) VALUES ('ほんだ');
-- ほんだ（2）とCPU（1）の対戦結果を3つ登録する
INSERT INTO matches (user1,user2,user1Hand,user2Hand) VALUES (2,1,'Gu','Choki');
INSERT INTO matches (user1,user2,user1Hand,user2Hand) VALUES (2,1,'Gu','Gu');
INSERT INTO matches (user1,user2,user1Hand,user2Hand) VALUES (2,1,'Gu','Pa');
