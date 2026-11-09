-- usersにCPU（id=1），ほんだ（id=2），いがき（id=3）を登録する
INSERT INTO users (name) VALUES ('CPU');
INSERT INTO users (name) VALUES ('ほんだ');
INSERT INTO users (name) VALUES ('いがき');
-- ほんだ（2）とCPU（1）の対戦結果を3つ登録する
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (2,1,'Gu','Choki',FALSE);
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (2,1,'Gu','Gu',FALSE);
INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (2,1,'Gu','Pa',FALSE);
-- matchinfoに試合の情報を2つ登録する
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (1,2,'Gu',FALSE);
INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (2,3,'Choki',FALSE);
