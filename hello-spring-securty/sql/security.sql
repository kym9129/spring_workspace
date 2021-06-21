-----------------------------------
-- spring-security.sql
-----------------------------------
select * from member;

desc member;

--authority 테이블 생성
create table authority(
    id varchar2(20) not null,           --회원 아이디
    authority varchar2(20) not null,    --권한
    constraints pk_authority primary key(id, authority),
    constraints fk_authority_member_id foreign key(id) references member(id)
);

insert into authority values('qwerty', 'ROLE_USER');
insert into authority values('honggd', 'ROLE_USER');
insert into authority values('admin', 'ROLE_USER');
insert into authority values('admin', 'ROLE_ADMIN');

commit;

--회원정보조회
select * 
from member 
where id = 'honggd';


--회원권환조회
select *
from authority
where id = 'admin';

--security의 remember-me 사용을 위한 persistent_logins 테이블 생성
--(테이블 이름이 정해져있음)
create table persistent_logins(
    username varchar2(64) not null,
    series varchar2(64) primary key,
    token varchar2(64) not null, --username, password, expiry time에 대한 hashing값
    last_used timestamp not null
);

select * from persistent_logins;