drop table if exists out_player;
drop table if exists player;
drop table if exists team;
drop table if exists stadium;

create table stadium
(
    id         int primary key auto_increment,
    name       varchar(20),
    created_at timestamp,

    index name (name)
);

create table team
(
    id         int primary key auto_increment,
    name       varchar(20),
    created_at timestamp,
    stadium_id int,

    index name (name),

    foreign key (stadium_id) references stadium (id)
);

create table player
(
    id         int primary key auto_increment,
    team_id    int,
    position   varchar(20),
    name       varchar(20),
    created_at timestamp,

    index name (name),
    index team_id (team_id),
    index team_position (team_id, position),
    unique (team_id, position),

    foreign key (team_id) references team (id)
);

create table out_player
(
    id         int primary key auto_increment,
    player_id  int,
    reason     varchar(200),
    created_at timestamp,

    foreign key (player_id) references player (id)
);

insert into stadium (name, created_at)
values ('서울종합운동장', now()),
       ('사직', now()),
       ('NC파크', now());

insert into team (name, created_at, stadium_id)
values ('두산', now(), (select id from stadium where stadium.name = '서울종합운동장')),
       ('롯데', now(), (select id from stadium where stadium.name = '사직')),
       ('NC', now(), (select id from stadium where stadium.name = 'NC파크'));

# 팀     투수	포수	    1루수	2루수	3루수	유격수	우익수	중견수	좌익수
# 두산 알칸타라	양의지	양석환	강승호	허경민	이유찬	김대한	정수빈	로하스산
insert into player (team_id, position, name, created_at)
values ((select id from team where team.name = '두산'), '투수', '알칸타라', now()),
       ((select id from team where team.name = '두산'), '포수', '양의지', now()),
       ((select id from team where team.name = '두산'), '1루수', '양석환', now()),
       ((select id from team where team.name = '두산'), '2루수', '강승호', now()),
       ((select id from team where team.name = '두산'), '3루수', '허경민', now()),
       ((select id from team where team.name = '두산'), '유격수', '이유찬', now()),
       ((select id from team where team.name = '두산'), '우익수', '김대한', now()),
       ((select id from team where team.name = '두산'), '중견수', '정수빈', now()),
       ((select id from team where team.name = '두산'), '좌익수', '로하스산', now());

# 팀     투수	포수	    1루수	2루수	3루수	유격수	우익수	중견수	좌익수
# 롯데 스트레일리	유강남	정훈	    안치홍	한동희	노진혁	렉스	    안권수	황성빈
insert into player (team_id, position, name, created_at)
values ((select id from team where team.name = '롯데'), '투수', '스트레일리', now()),
       ((select id from team where team.name = '롯데'), '포수', '유강남', now()),
       ((select id from team where team.name = '롯데'), '1루수', '정훈', now()),
       ((select id from team where team.name = '롯데'), '2루수', '안치홍', now()),
       ((select id from team where team.name = '롯데'), '3루수', '한동희', now()),
       ((select id from team where team.name = '롯데'), '유격수', '노진혁', now()),
       ((select id from team where team.name = '롯데'), '우익수', '렉스', now()),
       ((select id from team where team.name = '롯데'), '중견수', '안권수', now()),
       ((select id from team where team.name = '롯데'), '좌익수', '황성빈', now());

# 팀     투수	포수	    1루수	2루수	3루수	유격수	우익수	중견수	좌익수
# NC    페디  	박세혁	오영수	박민우	박석민	김주원	박건우	마틴	    김성욱
insert into player (team_id, position, name, created_at)
values ((select id from team where team.name = 'NC'), '투수', '페디', now()),
       ((select id from team where team.name = 'NC'), '포수', '박세혁', now()),
       ((select id from team where team.name = 'NC'), '1루수', '오영수', now()),
       ((select id from team where team.name = 'NC'), '2루수', '박민우', now()),
       ((select id from team where team.name = 'NC'), '3루수', '박석민', now()),
       ((select id from team where team.name = 'NC'), '유격수', '김주원', now()),
       ((select id from team where team.name = 'NC'), '우익수', '박건우', now()),
       ((select id from team where team.name = 'NC'), '중견수', '마틴', now()),
       ((select id from team where team.name = 'NC'), '좌익수', '김성욱', now());

# 야구장목록 출력
select *
from stadium;

# 팀목록 출력
select *
from team;

# 팀연고지 함께 출력
select s.name, t.name, t.created_at
from team t
         join stadium s on t.stadium_id = s.id;

# 선수목록 출력
select t.name '팀이름', p.position '포지션', p.name '선수명'
from player p
         join team t on p.team_id = t.id;

# 소속별 피벗테이블 출력
select t.name                                            '소속',
       max(case when p.position = '투수' then p.name end)  '투수',
       max(case when p.position = '포수' then p.name end)  '포수',
       max(case when p.position = '1루수' then p.name end) '1루수',
       max(case when p.position = '2루수' then p.name end) '2루수',
       max(case when p.position = '3루수' then p.name end) '3루수',
       max(case when p.position = '유격수' then p.name end) '유격수',
       max(case when p.position = '우익수' then p.name end) '우익수',
       max(case when p.position = '중견수' then p.name end) '중견수',
       max(case when p.position = '좌익수' then p.name end) '좌익수'
from player p
         right outer join team t on p.team_id = t.id
group by t.name
;

select p.position                                   '포지션',
       max(case when t.name = 'NC' then p.name end) 'NC',
       max(case when t.name = '두산' then p.name end) '두산',
       max(case when t.name = '롯데' then p.name end) '롯데'
from team t
         right outer join player p
                          on p.team_id = t.id
group by p.position
;

start transaction;

insert into out_player (player_id, reason, created_at)
values (1, '집안일', now());
update player
set team_id=null
where id = 1;

commit ;

# 퇴출 transaction 확인
select * from player  join out_player on player.id = out_player.player_id where player_id = 1;