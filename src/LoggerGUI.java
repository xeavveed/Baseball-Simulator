import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class LoggerGUI {
    private TextArea outputArea;

    public LoggerGUI(TextArea outputArea) {
        this.outputArea = outputArea;
    }

    public void log(String message) {
        Platform.runLater(() -> outputArea.appendText(message + "\n"));
    }

    public void printSummary(Game game) {
        log("\n===== 경기 요약 =====");
        log(String.format("Away (%s): %d점", game.getOffensiveTeam().getTeamName(), game.getScore()[0]));
        log(String.format("Home (%s): %d점", game.getDefensiveTeam().getTeamName(), game.getScore()[1]));
    }

    public void logInningStart(int inning, boolean isTop) {
        String half = isTop ? "초" : "말";
        log(String.format("\n--- [%d회%s] 시작 ---", inning, half));
    }

    public void logAtBat(String batterName, OutcomeType outcome, int runs) {
        String result = batterName + " → " + outcome.name();
        if (runs > 0) result += String.format(" (%d점 득점)", runs);
        log(result);
    }

    public void logOut(String batterName, OutcomeType outType) {
        log(batterName + " → " + outType.name() + " (아웃)");
    }

    public void logInningEnd(int inning, boolean isTop, int[] score) {
        String half = isTop ? "초" : "말";
        log(String.format("--- [%d회%s] 종료: Away %d - %d Home ---", inning, half, score[0], score[1]));
    }
}
