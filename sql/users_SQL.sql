-- -----------------------------------    
-- users 계정만들기
-- -----------------------------------

-- 테이블 만들기
create table users (
    no integer auto_increment primary key ,
    id varchar(20) unique not null ,
    password varchar(20) not null ,
	name varchar(20),
    gender varchar(10)
);

insert into users
values(null,'aaa','1213','오지원','male');

select *
from users;

-- 등록
select no,
       id,
        password,
       name,
       gender
from users;

-- 삭제
delete from users
where no=11;

-- 수정
update users
set  id = '123',
	 password ='1235' ,
	 name = '오지원',
	 gender ='male'
where no= 6;

-- 로그인
select no,
       name
from users
where id='aaa'
and password='1213';