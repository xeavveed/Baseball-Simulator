public class AtBatOutcome {
    private OutcomeType type;

    public AtBatOutcome(OutcomeType type) {
        this.type = type;
    }

    public OutcomeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Outcome: " + type.name();
    }
}