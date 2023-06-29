package com.fastcampus.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
  public static void createTables() {
    String createStadium =
        "create table stadium ("
            + " id int primary key auto_increment, "
            + "name varchar(20), "
            + " created_at timestamp, "
            + " index stadium_name (name)"
            + ")";

    String createTeam =
        "create table team ("
            + "id int primary key auto_increment, "
            + "name varchar(20), "
            + "created_at timestamp, "
            + "stadium_id int, "
            + "index team_name (name), "
            + "foreign key (stadium_id) references stadium (id)"
            + ")";

    String createPlayer =
        "create table player ("
            + "id int primary key auto_increment, "
            + "team_id int, "
            + "position varchar(20), "
            + "name varchar(20), "
            + "created_at timestamp, "
            + "index idx_name (name), "
            + "index team_id (team_id), "
            + "index team_position (team_id, position), "
            + "unique (team_id, position), "
            + " foreign key (team_id) references team (id)"
            + ")";

    String createOutPlayer =
        "create table out_player ("
            + "id int primary key auto_increment, "
            + " player_id  int, "
            + " reason varchar(200), "
            + " created_at timestamp,"
            + " foreign key (player_id) references player (id)"
            + ")";

    String initializeStadium =
        "insert into stadium (name, created_at)"
            + "values ('서울종합운동장', now()),"
            + "('사직', now()),"
            + "('NC파크', now()"
            + ")";

    String initializeTeam =
        "insert into team (name, created_at, stadium_id) "
            + "values ('두산', now(), (select id from stadium where stadium.name = '서울종합운동장')), "
            + "('롯데', now(), (select id from stadium where stadium.name = '사직')), "
            + "('NC', now(), (select id from stadium where stadium.name = 'NC파크'))";

    String initializePlayer =
        "insert into player (team_id, position, name, created_at)"
            + "values ((select id from team where team.name = '두산'), '투수', '알칸타라', now()),"
            + "       ((select id from team where team.name = '두산'), '포수', '양의지', now()),"
            + "       ((select id from team where team.name = '두산'), '1루수', '양석환', now()),"
            + "       ((select id from team where team.name = '두산'), '2루수', '강승호', now()),"
            + "       ((select id from team where team.name = '두산'), '3루수', '허경민', now()),"
            + "       ((select id from team where team.name = '두산'), '유격수', '이유찬', now()),"
            + "       ((select id from team where team.name = '두산'), '우익수', '김대한', now()),"
            + "       ((select id from team where team.name = '두산'), '중견수', '정수빈', now()),"
            + "       ((select id from team where team.name = '두산'), '좌익수', '로하스산', now());"
            + "insert into player (team_id, position, name, created_at)"
            + "values ((select id from team where team.name = '롯데'), '투수', '스트레일리', now()),"
            + "       ((select id from team where team.name = '롯데'), '포수', '유강남', now()),"
            + "       ((select id from team where team.name = '롯데'), '1루수', '정훈', now()),"
            + "       ((select id from team where team.name = '롯데'), '2루수', '안치홍', now()),"
            + "       ((select id from team where team.name = '롯데'), '3루수', '한동희', now()),"
            + "       ((select id from team where team.name = '롯데'), '유격수', '노진혁', now()),"
            + "       ((select id from team where team.name = '롯데'), '우익수', '렉스', now()),"
            + "       ((select id from team where team.name = '롯데'), '중견수', '안권수', now()),"
            + "       ((select id from team where team.name = '롯데'), '좌익수', '황성빈', now());"
            + "insert into player (team_id, position, name, created_at)"
            + "values ((select id from team where team.name = 'NC'), '투수', '페디', now()),"
            + "       ((select id from team where team.name = 'NC'), '포수', '박세혁', now()),"
            + "       ((select id from team where team.name = 'NC'), '1루수', '오영수', now()),"
            + "       ((select id from team where team.name = 'NC'), '2루수', '박민우', now()),"
            + "       ((select id from team where team.name = 'NC'), '3루수', '박석민', now()),"
            + "       ((select id from team where team.name = 'NC'), '유격수', '김주원', now()),"
            + "       ((select id from team where team.name = 'NC'), '우익수', '박건우', now()),"
            + "       ((select id from team where team.name = 'NC'), '중견수', '마틴', now()),"
            + "       ((select id from team where team.name = 'NC'), '좌익수', '김성욱', now());";

    String initializeOutPlayer =
        "insert into out_player (player_id, reason, created_at) "
            + "values "
            + "(1, '집안일', now()),"
            + "(2, '집안일', now());"
            + "update player set team_id=null where player.id=1;"
            + "update player set team_id=null where player.id=2;";

    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {

      stmt.executeUpdate(createStadium);
      stmt.executeUpdate(createTeam);
      stmt.executeUpdate(createPlayer);
      stmt.executeUpdate(createOutPlayer);
      stmt.executeUpdate(initializeStadium);
      stmt.executeUpdate(initializeTeam);
      stmt.executeUpdate(initializePlayer);
      stmt.executeUpdate(initializeOutPlayer);

      System.out.println("Tables created successfully.");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
