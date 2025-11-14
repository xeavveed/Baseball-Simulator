public class Batter extends Player {
    private double battingAverage;       // 타율 (AVG)
    private double onBasePercentage;     // 출루율 (OBP)
    private double sluggingPercentage;   // 장타율 (SLG)
    private double strikeoutRate;        // 삼진율 (K-rate)
    private double walkRate;             // 볼넷률 (BB-rate)
    private double stealSuccessRate;     // 도루 성공률

    // 기본 생성자 (CSV로 불러올 때)
    public Batter() {}

    @Override
    public void loadStats(String[] fields) {
        if (fields.length < 9) {
            throw new IllegalArgumentException("입력 필드 수가 부족합니다.");
        }
        this.name = fields[0];
        this.team = fields[1];
        this.position = fields[2];
        this.battingAverage = Double.parseDouble(fields[3]);
        this.onBasePercentage = Double.parseDouble(fields[4]);
        this.sluggingPercentage = Double.parseDouble(fields[5]);
        this.strikeoutRate = Double.parseDouble(fields[6]);
        this.walkRate = Double.parseDouble(fields[7]);
        this.stealSuccessRate = Double.parseDouble(fields[8]);
    }

    public double getBattingAverage() {
        return battingAverage;
    }

    public double getOnBasePercentage() {
        return onBasePercentage;
    }

    public double getSluggingPercentage() {
        return sluggingPercentage;
    }

    public double getStrikeoutRate() {
        return strikeoutRate;
    }

    public double getWalkRate() {
        return walkRate;
    }

    public double getStealSuccessRate() {
        return stealSuccessRate;
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

    // toString() 오버라이드
    @Override
    public String toString() {
        return String.format("%s (%s): AVG=%.3f, OBP=%.3f, SLG=%.3f, K=%.2f, BB=%.2f, SB=%.2f",
                name, position, battingAverage, onBasePercentage, sluggingPercentage,
                strikeoutRate, walkRate, stealSuccessRate);
    }
}
