import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> logs;

    public Logger() {
        logs = new ArrayList<>();
    }

    /**
     * 로그 한 줄 추가
     * @param message 기록할 메시지
     */
    public void log(String message) {
        logs.add(message);
    }

    /**
     * 박스 스코어 및 플레이 로그 출력
     * @param game 현재 경기 상태 (이닝, 점수 포함)
     */
    public void printSummary(Game game) {
        System.out.println("\n===== 경기 요약 =====");
        System.out.printf("Away (%s): %d점\n", game.getOffensiveTeam().getTeamName(), game.getScore()[0]);
        System.out.printf("Home (%s): %d점\n", game.getDefensiveTeam().getTeamName(), game.getScore()[1]);

        System.out.println("\n===== 플레이 로그 =====");
        for (String log : logs) {
            System.out.println(log);
        }
    }

    /**
     * 로그 목록 반환 (리플레이용 등)
     */
    public List<String> getLogs() {
        return logs;
    }

    /**
     * 이닝 시작 로그 출력
     */
    public void logInningStart(int inning, boolean isTop) {
        String half = isTop ? "초" : "말";
        log(String.format("\n--- [%d회%s] 시작 ---", inning, half));
    }

    /**
     * 타석 결과 로그
     */
    public void logAtBat(String batterName, OutcomeType outcome, int runs) {
        String result = batterName + " → " + outcome.name();
        if (runs > 0) result += String.format(" (%d점 득점)", runs);
        log(result);
    }

    /**
     * 아웃 로그
     */
    public void logOut(String batterName, OutcomeType outType) {
        log(batterName + " → " + outType.name() + " (아웃)");
    }

    /**
     * 이닝 종료 로그
     */
    public void logInningEnd(int inning, boolean isTop, int[] score) {
        String half = isTop ? "초" : "말";
        log(String.format("--- [%d회%s] 종료: Away %d - %d Home ---", inning, half, score[0], score[1]));
    }
}