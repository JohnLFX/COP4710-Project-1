import java.util.Objects;

public class Coache {

    private String Coach_ID;
    private Integer season;
    private String first_name;
    private String last_name;
    private Integer season_win;
    private Integer season_loss;
    private Integer playoff_win;
    private Integer playoff_loss;
    private String team;

    public Coache() {
    }

    public String getCoach_ID() {
        return Coach_ID;
    }

    public void setCoach_ID(String coach_ID) {
        //consists of less than 7 capital letters and two digits
        Objects.requireNonNull(coach_ID);

        if (coach_ID.length() > 9)
            throw new IllegalArgumentException("Coach ID is too long");

        int digitsCount = 0;

        for (char c : coach_ID.toCharArray()) {
            if (!Character.isUpperCase(c) && !Character.isDigit(c))
                throw new IllegalArgumentException("coach_ID must consist of less than 7 capital letters and two digits");

            if (Character.isDigit(c))
                digitsCount++;
        }

        if (digitsCount != 2)
            throw new IllegalArgumentException("coach_ID must consist of less than 7 capital letters and two digits");

        Coach_ID = coach_ID;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(int season) {
        if (season > 9999 || season < 1000)
            throw new IllegalArgumentException("Must be a 4 digit year");

        this.season = season;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getSeason_win() {
        return season_win;
    }

    public void setSeason_win(int season_win) {
        if (season_win < 0)
            throw new IllegalArgumentException("Must be a non-negative integer");

        this.season_win = season_win;
    }

    public Integer getSeason_loss() {
        return season_loss;
    }

    public void setSeason_loss(int season_loss) {
        if (season_loss < 0)
            throw new IllegalArgumentException("Must be a non-negative integer");

        this.season_loss = season_loss;
    }

    public Integer getPlayoff_win() {
        return playoff_win;
    }

    public void setPlayoff_win(int playoff_win) {
        if (playoff_win < 0)
            throw new IllegalArgumentException("Must be a non-negative integer");

        this.playoff_win = playoff_win;
    }

    public Integer getPlayoff_loss() {
        return playoff_loss;
    }

    public void setPlayoff_loss(int playoff_loss) {
        if (playoff_loss < 0)
            throw new IllegalArgumentException("Must be a non-negative integer");

        this.playoff_loss = playoff_loss;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        //capital letters and/or digits
        for (char c : team.toCharArray())
            if (!Character.isUpperCase(c) && !Character.isLetter(c) && !Character.isDigit(c))
                throw new IllegalArgumentException("Must contain only capital letters and/or digits");

        this.team = team;
    }

    public int getNetWins() {
        return (getSeason_win() - getSeason_loss()) + (getPlayoff_win() - getPlayoff_loss());
    }

    @Override
    public String toString() {
        return getCoach_ID() + "\t" + getSeason() + "\t" + getFirst_name() + "\t" + getLast_name()
                + "\t" + getSeason_win() + "\t" + getSeason_loss() + "\t" + getPlayoff_win()
                + "\t" + getPlayoff_loss() + "\t" + getTeam();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coache coache = (Coache) o;
        return Objects.equals(Coach_ID, coache.Coach_ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Coach_ID);
    }

}
