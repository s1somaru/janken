CREATE TABLE users (
    id IDENTITY,             -- 自動で番号が振られる主キー
    name VARCHAR NOT NULL    -- ユーザ名（空にはできない）
);
CREATE TABLE matches (
    id IDENTITY,             -- 自動で番号が振られる主キー
    user1 INT NOT NULL,      -- 対戦したユーザ1のid（usersテーブルのid）
    user2 INT NOT NULL,      -- 対戦したユーザ2のid（usersテーブルのid）
    user1Hand VARCHAR NOT NULL, -- ユーザ1が出した手
    user2Hand VARCHAR NOT NULL, -- ユーザ2が出した手
    isActive BOOLEAN NOT NULL   -- 試合が終わったかどうか（TRUE:試合中，FALSE:終了済み）
);
CREATE TABLE matchinfo (
    id IDENTITY,             -- 自動で番号が振られる主キー
    user1 INT NOT NULL,      -- 試合中のユーザ1のid（usersテーブルのid）
    user2 INT NOT NULL,      -- 試合中のユーザ2のid（usersテーブルのid）
    user1Hand VARCHAR NOT NULL, -- ユーザ1が出した手
    isActive BOOLEAN NOT NULL   -- 今試合中かどうか（TRUE:試合中，FALSE:試合していない）
);
