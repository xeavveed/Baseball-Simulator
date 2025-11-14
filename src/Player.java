public abstract class Player {
    protected String name;
    protected String team;
    protected String position;

    public abstract void loadStats(String[] fields);
}