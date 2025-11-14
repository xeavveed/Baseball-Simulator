public class Game {
    private Team homeTeam;
    private Team awayTeam;
    private int[] score;           // [0]: away, [1]: home
    private int inning;            // 현재 이닝 (1~9)
    private boolean isTop;         // true: O회초, false: O회말
    private int outs;              // 아웃 카운트
    private BaseState baseState;   // 주자 상태
    private int[] batterIndex;     // 각 팀의 타순 인덱스 [0]=away, [1]=home

    public Game(Team awayTeam, Team homeTeam) {
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
        this.score = new int[2];
        this.inning = 1;
        this.isTop = true;
        this.outs = 0;
        this.baseState = new BaseState();
        this.batterIndex = new int[]{0, 0};
    }

    public Team getOffensiveTeam() {
        return isTop ? awayTeam : homeTeam;
    }

    public Team getDefensiveTeam() {
        return isTop ? homeTeam : awayTeam;
    }

    public int getOffenseIndex() {
        return isTop ? 0 : 1;
    }

    public int getDefenseIndex() {
        return isTop ? 1 : 0;
    }

    public Batter getCurrentBatter() {
        Team team = getOffensiveTeam();
        int idx = batterIndex[getOffenseIndex()];
        return team.getBattingLineup().get(idx);
    }

    public Pitcher getCurrentPitcher() {
        Team team = getDefensiveTeam();
        return team.getPitcherForInning(inning);
    }

    public void advanceBatterIndex() {
        int idx = getOffenseIndex();
        batterIndex[idx] = (batterIndex[idx] + 1) % 9;
    }

    public void addRuns(int runs) {
        score[getOffenseIndex()] += runs;
    }

    public void nextHalfInning() {
        isTop = !isTop;
        if (isTop) inning++;
        outs = 0;
        baseState.reset();
    }

    public int[] getScore() {
        return score;
    }

    public int getInning() {
        return inning;
    }

    public boolean isTop() {
        return isTop;
    }

    public int getOuts() {
        return outs;
    }

    public void incrementOuts() {
        outs++;
    }

    public BaseState getBaseState() {
        return baseState;
    }

    public boolean isGameOver() {
        return inning > 9;
    }
}
