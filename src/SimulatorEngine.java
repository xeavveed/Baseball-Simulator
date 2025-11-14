import java.util.Random;

public class SimulatorEngine {
    private Game game;
    private Logger logger;
    private Random random;

    public SimulatorEngine(Game game, Logger logger) {
        this.game = game;
        this.logger = logger;
        this.random = new Random();
    }

    public void simulateGame() {
        logger.log("===== 경기 시작 =====");

        while (game.getInning() <= 9) {
            if (game.getInning() == 9 && game.isTop() == false && game.getScore()[0] < game.getScore()[1]){
                break;
            }
            logger.logInningStart(game.getInning(), game.isTop());
            simulateHalfInning();
            logger.logInningEnd(game.getInning(), game.isTop(), game.getScore());
            game.nextHalfInning();
        }

        logger.printSummary(game);
    }

    private void simulateHalfInning() {
        while (game.getOuts() < 3) {
            Batter batter = game.getCurrentBatter();
            Pitcher pitcher = game.getCurrentPitcher();

            AtBatOutcome outcome = simulateAtBat(batter, pitcher);

            int runs = 0;

            switch (outcome.getType()) {
                case STRIKEOUT, GROUND_OUT, FLY_OUT -> {
                    game.incrementOuts();
                    logger.logOut(batter.getName(), outcome.getType());
                }
                default -> {
                    runs = game.getBaseState().advanceRunners(outcome);
                    game.addRuns(runs);
                    logger.logAtBat(batter.getName(), outcome.getType(), runs);
                }
            }

            game.advanceBatterIndex();
            if (game.getInning() == 9 && game.isTop() == false && game.getScore()[0] < game.getScore()[1]){
                break;
            }
        }
    }

    private AtBatOutcome simulateAtBat(Batter batter, Pitcher pitcher) {
        double hitProb = (batter.getBattingAverage() + pitcher.getOpponentBattingAvg()) / 2.0;
        double walkProb = (batter.getWalkRate() + pitcher.getWalkRate()) / 2.0;
        double kProb = (batter.getStrikeoutRate() + pitcher.getStrikeoutRate()) / 2.0;

        double r = random.nextDouble();

        if (r < hitProb) {
            return determineHitType(batter);
        } else if (r < hitProb + walkProb) {
            return new AtBatOutcome(OutcomeType.WALK);
        } else if (r < hitProb + walkProb + kProb) {
            return new AtBatOutcome(OutcomeType.STRIKEOUT);
        } else {
            return random.nextBoolean()
                ? new AtBatOutcome(OutcomeType.GROUND_OUT)
                : new AtBatOutcome(OutcomeType.FLY_OUT);
        }
    }

    private AtBatOutcome determineHitType(Batter batter) {
        double avg = batter.getBattingAverage();
        double slg = batter.getSluggingPercentage();
        double basesPerHit = slg / Math.max(avg, 0.001); // 0으로 나눔 방지

        double probSingle, probDouble, probTriple;

        if (basesPerHit < 1.2) {
            probSingle = 0.90;
            probDouble = 0.07;
            probTriple = 0.01;
        } else if (basesPerHit < 1.5) {
            probSingle = 0.70;
            probDouble = 0.18;
            probTriple = 0.02;
        } else if (basesPerHit < 1.8) {
            probSingle = 0.55;
            probDouble = 0.20;
            probTriple = 0.05;
        } else {
            probSingle = 0.40;
            probDouble = 0.25;
            probTriple = 0.05;
        }

        double r = random.nextDouble();
        if (r < probSingle) return new AtBatOutcome(OutcomeType.SINGLE);
        else if (r < probSingle + probDouble) return new AtBatOutcome(OutcomeType.DOUBLE);
        else if (r < probSingle + probDouble + probTriple) return new AtBatOutcome(OutcomeType.TRIPLE);
        else return new AtBatOutcome(OutcomeType.HOMERUN);
    }

    public void simulateOneAtBat() {
        if (game.getOuts() >= 3) return;

        Batter batter = game.getCurrentBatter();
        Pitcher pitcher = game.getCurrentPitcher();
        AtBatOutcome outcome = simulateAtBat(batter, pitcher);

        int runs = 0;
        switch (outcome.getType()) {
            case STRIKEOUT, GROUND_OUT, FLY_OUT -> {
                game.incrementOuts();
                logger.logOut(batter.getName(), outcome.getType());
            }
            default -> {
                runs = game.getBaseState().advanceRunners(outcome);
                game.addRuns(runs);
                logger.logAtBat(batter.getName(), outcome.getType(), runs);
            }
        }
        game.advanceBatterIndex();

        if (game.getOuts() >= 3) {
            logger.logInningEnd(game.getInning(), game.isTop(), game.getScore());
            game.nextHalfInning();
            logger.logInningStart(game.getInning(), game.isTop());
        }
    }
}
