import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class Simulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. CSV 파일 읽기
        List<String> batterLines = loadCSV("Batter.csv");
        List<String> pitcherLines = loadCSV("Pitcher.csv");

        // 2. 원정팀 입력
        System.out.print("원정팀 이름을 입력하세요: ");
        String awayTeamName = sc.nextLine();
        Team awayTeam = new Team(awayTeamName);

        System.out.println("원정팀 타자 라인업 (9명) 입력:");
        String[] awayBatters = sc.nextLine().split(" ");
        for (String name : awayBatters) {
            String[] line = findPlayerFields(batterLines, awayTeamName, name);
            if (line != null) {
                Batter batter = new Batter();
                batter.loadStats(line);
                awayTeam.addBatter(batter);
            } else {
                System.out.println("타자 [" + name + "] 정보를 찾을 수 없습니다.");
            }
        }

        System.out.println("원정팀 투수진 (4명) 입력:");
        String[] awayPitchers = sc.nextLine().split(" ");
        for (String name : awayPitchers) {
            String[] line = findPlayerFields(pitcherLines, awayTeamName, name);
            if (line != null) {
                Pitcher pitcher = new Pitcher();
                pitcher.loadStats(line);
                awayTeam.addPitcher(pitcher);
            } else {
                System.out.println("투수 [" + name + "] 정보를 찾을 수 없습니다.");
            }
        }

        // 3. 홈팀 입력
        System.out.print("홈팀 이름을 입력하세요: ");
        String homeTeamName = sc.nextLine();
        Team homeTeam = new Team(homeTeamName);

        System.out.println("홈팀 타자 라인업 (9명) 입력:");
        String[] homeBatters = sc.nextLine().split(" ");
        for (String name : homeBatters) {
            String[] line = findPlayerFields(batterLines, homeTeamName, name);
            if (line != null) {
                Batter batter = new Batter();
                batter.loadStats(line);
                homeTeam.addBatter(batter);
            } else {
                System.out.println("타자 [" + name + "] 정보를 찾을 수 없습니다.");
            }
        }

        System.out.println("홈팀 투수진 (4명) 입력:");
        String[] homePitchers = sc.nextLine().split(" ");
        for (String name : homePitchers) {
            String[] line = findPlayerFields(pitcherLines, homeTeamName, name);
            if (line != null) {
                Pitcher pitcher = new Pitcher();
                pitcher.loadStats(line);
                homeTeam.addPitcher(pitcher);
            } else {
                System.out.println("투수 [" + name + "] 정보를 찾을 수 없습니다.");
            }
        }

        // 4. 경기 시작
        System.out.println("\n--- 경기를 시작합니다 ---\n");
        // 경기 객체와 로거 생성
        Game game = new Game(awayTeam, homeTeam);
        Logger logger = new Logger();

        // 엔진 실행
        SimulatorEngine engine = new SimulatorEngine(game, logger);
        engine.simulateGame();
        sc.close();
    }

    // 특정 이름과 팀명 일치하는 행 찾기
    public static String[] findPlayerFields(List<String> lines, String team, String name) {
        for (String line : lines) {
            String[] tokens = line.split(",");
            if (tokens.length > 2 && tokens[0].trim().equals(name) && tokens[1].trim().equals(team)) {
                return tokens;
            }
        }
        return null;
    }

    public static List<String> loadCSV(String filename) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(filename);

        Charset cs = StandardCharsets.UTF_8;

        try (BufferedReader br = Files.newBufferedReader(path, cs)) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("파일 " + filename + "을 읽는 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return lines;
    }
}
