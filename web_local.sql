
-- web계정만들기
create user 'web'@'%'identified by 'web';

-- web 권한부여
grant all privileges on web_db.* to 'web'@'%' ;

-- 즉시 변경 내용 반영
flush privileges;

-- 데이타베이스 생성
create database web_db
    default character set utf8mb4
    collate utf8mb4_general_ci
    default encryption='n';