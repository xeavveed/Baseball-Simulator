public class BaseState {
    private boolean[] bases; // 0: 1루, 1: 2루, 2: 3루

    public BaseState() {
        bases = new boolean[3];
    }

    public void reset() {
        for (int i = 0; i < 3; i++) bases[i] = false;
    }

    /**
     * 타석 결과에 따라 주자 이동 및 득점 처리
     * @param outcome 타석 결과
     * @return 득점 수
     */
    public int advanceRunners(AtBatOutcome outcome) {
        OutcomeType type = outcome.getType();
        int runs = 0;

        switch (type) {
            case SINGLE:
                if (bases[2]) { runs++; bases[2] = false; }
                if (bases[1]) { bases[2] = true; bases[1] = false; }
                if (bases[0]) { bases[1] = true; bases[0] = false; }
                bases[0] = true;
                break;

            case DOUBLE:
                if (bases[2]) { runs++; bases[2] = false; }
                if (bases[1]) { runs++; bases[1] = false; }
                if (bases[0]) { bases[2] = true; bases[0] = false; }
                bases[1] = true;
                break;

            case TRIPLE:
                for (int i = 0; i < 3; i++) {
                    if (bases[i]) { runs++; bases[i] = false; }
                }
                bases[2] = true;
                break;

            case HOMERUN:
                for (int i = 0; i < 3; i++) {
                    if (bases[i]) { runs++; bases[i] = false; }
                }
                runs++;
                break;

            case WALK:
                runs += advanceOnWalk();
                break;

            default:
                // 삼진/아웃은 주자 변화 없음
                break;
        }

        return runs;
    }

    /**
     * 볼넷 시 주자 한 칸씩 밀고, 밀어내기 득점 여부 계산
     * @return 득점 수
     */
    public int advanceOnWalk() {
        int runs = 0;

        if (bases[0] && bases[1] && bases[2]) {
            runs++;
        }
        else if (bases[0] && bases[1]) {
            bases[2] = true;
        }
        else if (bases[0] && bases[2]) {
            bases[1] = true;
        }
        else if (bases[1] && bases[2]) {
            bases[0] = true;
        }
        else if (bases[0]) {
            bases[1] = true;
        }
        else {
            bases[0] = true;
        }
        
        return runs;
    }

    public boolean isOccupied(int i) {
        if (bases[i-1]){
            return true;
        }
        return false;
    }
}
