public class Pitcher extends Player {
    private double opponentBattingAvg;    // 피안타율
    private double WHIP;                  // 이닝당 출루 허용 (Walks + Hits / Inning Pitched)
    private double opponentSluggingAvg;   // 피장타율
    private double strikeoutRate;         // 삼진율 (K-rate)
    private double walkRate;              // 볼넷률 (BB-rate)

    // 기본 생성자 (CSV로 불러올 때)
    public Pitcher() {}

    @Override
    public void loadStats(String[] fields) {
        if (fields.length < 8) {
            throw new IllegalArgumentException("입력 필드 수가 부족합니다.");
        }

        this.name = fields[0];
        this.team = fields[1];
        this.position = fields[2];
        this.opponentBattingAvg = Double.parseDouble(fields[3]);
        this.WHIP = Double.parseDouble(fields[4]);
        this.opponentSluggingAvg = Double.parseDouble(fields[5]);
        this.strikeoutRate = Double.parseDouble(fields[6]);
        this.walkRate = Double.parseDouble(fields[7]);
    }

    public double getOpponentBattingAvg() {
        return opponentBattingAvg;
    }

    public double getWHIP() {
        return WHIP;
    }

    public double getOpponentSluggingAvg() {
        return opponentSluggingAvg;
    }

    public double getStrikeoutRate() {
        return strikeoutRate;
    }

    public double getWalkRate() {
        return walkRate;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): OAVG=%.3f, WHIP=%.2f, OSLG=%.3f, K=%.2f, BB=%.2f",
                name, position, opponentBattingAvg, WHIP, opponentSluggingAvg,
                strikeoutRate, walkRate);
    }
}
