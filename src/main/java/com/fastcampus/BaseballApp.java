package com.fastcampus;

import com.fastcampus.db.DBInitializer;
import com.fastcampus.enums.OutReason;
import com.fastcampus.service.OutPlayerService;
import com.fastcampus.service.PlayerService;
import com.fastcampus.service.StadiumService;
import com.fastcampus.service.TeamService;
import java.util.*;
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
      String[] request = parseRequest(inputRequest());
      String command = request[0];
      Map<String, String> args = null;

      if (request.length > 1) args = parseArgs(request[1]);

      if (!validate(command, args)) {
        System.out.println("잘못된 요청입니다.");
        continue;
      }

      if ("종료".equals(command) || "EXIT".equalsIgnoreCase(command)) break;

      execute(command, args);
    }
  }

  private boolean validate(String command, Map<String, String> args) {

    // 단일 명령 유효성 검증
    if ("야구장목록".equals(command)
        || "팀목록".equals(command)
        || "퇴출목록".equals(command)
        || "포지션별목록".equals(command)
        || "종료".equals(command)
        || "exit".equals(command)) {
      if (args == null || args.keySet().isEmpty()) return true;
    }

    // 인자가 필요한 명령들 유효성 검증
    if (args == null || args.keySet().isEmpty()) return false;

    int argsSize = args.keySet().size();

    // 야구장등록?name=잠실야구장
    if ("야구장등록".equals(command)) {
      if (argsSize != 1) return false;
      if (!args.containsKey("name")) return false;
      return true;
    }

    // 팀등록?stadiumId=1&name=NC
    if ("팀등록".equals(command)) {
      if (argsSize != 2) return false;
      if (!args.containsKey("stadiumId")) return false;
      if (!args.containsKey("name")) return false;
      return true;
    }

    // 선수등록?teamId=1&name=이대호&position=1루수
    if ("선수등록".equals(command)) {
      if (argsSize != 3) return false;
      if (!args.containsKey("teamId")) return false;
      if (!args.containsKey("name")) return false;
      if (!args.containsKey("position")) return false;
      return true;
    }

    // 선수목록?teamId=1
    if ("선수목록".equals(command)) {
      if (argsSize != 1) return false;
      if (!args.containsKey("teamId")) return false;
      return true;
    }

    // 퇴출등록?playerId=1&reason=도박
    if ("퇴출등록".equals(command)) {
      if (argsSize != 2) return false;
      if (!args.containsKey("playerId")) return false;
      if (!args.containsKey("reason")) return false;
      return true;
    }

    // 그 외에 잘못된 모든 명령들
    return false;
  }

  private String inputRequest() {
    System.out.print("어떤 기능을 요청하시겠습니까? \n : ");
    return scanner.nextLine();
  }

  private String[] parseRequest(String request) {
    return request.split("\\?");
  }

  private Map<String, String> parseArgs(String argStr) {
    Map<String, String> argMap = new HashMap<>();
    Arrays.stream(argStr.split("&"))
        .forEach(
            arg -> {
              if (arg.contains("=")) {
                String[] parsed = arg.split("=");
                argMap.put(parsed[0], parsed[1]);
              }
            });

    return argMap;
  }

  public void execute(String command, Map<String, String> args) {
    switch (command) {
        // 야구장등록?name=잠실야구장
      case "야구장등록" -> System.out.println(
          "\n" + stadiumService.registerStadium(args.get("name")) + "\n");

        // 야구장목록
      case "야구장목록" -> {
        System.out.println("\nID, 야구장명, 개장일");
        stadiumService.getStadiumList().forEach(System.out::println);
        System.out.println();
      }

        // 팀등록?stadiumId=1&name=NC
      case "팀등록" -> System.out.println(
          "\n"
              + teamService.registerTeam(Integer.parseInt(args.get("stadiumId")), args.get("name"))
              + "\n");

      case "팀목록" -> {
        System.out.println("\nID, 연고지, 팀명, 창단일");
        teamService.getTeamList().forEach(System.out::println);
        System.out.println();
      }

        // 선수등록?teamId=1&name=이대호&position=1루수
      case "선수등록" -> System.out.println(
          "\n"
              + playerService.registerPlayer(
                  args.get("name"), args.get("position"), Integer.parseInt(args.get("teamId")))
              + "\n");

        // 선수목록?teamId=1
      case "선수목록" -> {
        System.out.println("\nID, 선수명, 포지션, 팀명, 데뷔일");
        playerService
            .getPlayerList(Integer.parseInt(args.get("teamId")))
            .forEach(System.out::println);
      }
        // 퇴출등록?playerId=1&reason=도박
      case "퇴출등록" -> System.out.println(
          "\n"
              + outPlayerService.registerOutPlayer(
                  Integer.parseInt(args.get("playerId")), OutReason.fromStr(args.get("reason")))
              + "\n");

      case "퇴출목록" -> {
        System.out.println("선수ID, 선수명, 포지션, 팀명, 퇴출이유, 퇴출일");
        outPlayerService.getOutPlayerList().forEach(System.out::println);
        System.out.println();
      }

      case "포지션별목록" -> teamService
          .getPositionList()
          .forEach(
              positionRespDto ->
                  System.out.println(
                      positionRespDto.getPosition()
                          + " "
                          + positionRespDto.getPlayerNameMapByTeam()));
      default -> System.out.println("명령을 입력해주세요.");
    }
  }
}
