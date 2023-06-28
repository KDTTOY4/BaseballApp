package com.fastcampus;

import com.fastcampus.db.DBInitializer;
import com.fastcampus.service.OutPlayerService;
import com.fastcampus.service.PlayerService;
import com.fastcampus.service.StadiumService;
import com.fastcampus.service.TeamService;
import java.util.Arrays;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BaseballApp {
  Scanner scanner;
  TeamService teamService;
  StadiumService stadiumService;
  PlayerService playerService;
  OutPlayerService outPlayerService;

  public BaseballApp(
      @Autowired Scanner scanner,
      @Autowired TeamService teamService,
      @Autowired StadiumService stadiumService,
      @Autowired PlayerService playerService,
      @Autowired OutPlayerService outPlayerService) {
    this.scanner = scanner;
    this.teamService = teamService;
    this.stadiumService = stadiumService;
    this.playerService = playerService;
    this.outPlayerService = outPlayerService;
  }

  public static void main(String[] args) {
    SpringApplication.run(BaseballApp.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    DBInitializer.createTables();

    while (true) {
      String[] args = parseCommand(inputRequest());

      if ("EXIT".equalsIgnoreCase(args[0])) break;

      // TODO : Request Validation (사용자 요청 유효성 검사)

      execute(args);
    }
  }

  private String inputRequest() {
    System.out.print("어떤 기능을 요청하시겠습니까? \n : ");
    return scanner.nextLine();
  }

  private String[] parseCommand(String request) {
    String[] args =
        Arrays.stream(request.split("[?|&]"))
            .map(arg -> arg.contains("=") ? arg.split("=")[1] : arg)
            .toArray(String[]::new);

    // TODO : 명령어와 함께 전달되는 인자들 Map 생성해서 전달

    System.out.println(Arrays.toString(args));

    return args;
  }

  public void execute(String[] args) {
    switch (args[0]) {
        // 야구장등록?name=잠실야구장
      case "야구장등록" -> {
        String name = args[1];
        System.out.println(stadiumService.registerStadium(name));
      }
        // 야구장목록
      case "야구장목록" -> {
        stadiumService.getStadiumList().forEach(System.out::println);
      }
      case "팀등록" -> {
        System.out.println(teamService.registerTeam(Integer.parseInt(args[1]), args[2]));
      }
      case "팀목록" -> {
        teamService.getTeamList().forEach(System.out::println);
      }
        // 선수등록?teamId=1&name=이대호&position=1루수
      case "선수등록" -> {
        Integer teamId = Integer.parseInt(args[1]);
        String name = args[2];
        String position = args[3];
      }
        // 선수목록?teamId=1
      case "선수목록" -> {
        Integer teamId = Integer.parseInt(args[1]);
      }
        // 퇴출등록?playerId=1&reason=도박
      case "퇴출등록" -> {
        Integer playerId = Integer.parseInt(args[1]);
        String reason = args[2];
      }
      case "퇴출목록" -> {}
      case "포지션별목록" -> {}
      default -> {
        System.out.println("명령을 입력해주세요.");
      }
    }
  }
}
