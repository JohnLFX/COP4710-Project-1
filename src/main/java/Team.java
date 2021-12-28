import java.util.Objects;

public class Team {

    private String team_ID;
    private String Location;
    private String Name;
    private Character League;

    public Team() {
    }

    public String getTeam_ID() {
        return team_ID;
    }

    public void setTeam_ID(String team_ID) {
        Objects.requireNonNull(team_ID);

        if (team_ID.isEmpty())
            throw new IllegalArgumentException("Empty team_ID");

        for (char c : team_ID.toCharArray())
            if (!Character.isLetter(c) && !Character.isUpperCase(c) && !Character.isDigit(c))
                throw new IllegalArgumentException("Only capital letters and/or digits are permitted in team_ID");

        this.team_ID = team_ID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Objects.requireNonNull(location);
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        // "any reasonable English word"
        Name = name;
    }

    public Character getLeague() {
        return League;
    }

    public void setLeague(char league) {
        if (!Character.isLetter(league) || !Character.isUpperCase(league))
            throw new IllegalArgumentException("Only one capital letter is permitted for league");

        League = league;
    }

    @Override
    public String toString() {
        return getTeam_ID() + "\t" + getLocation() + "\t" + getName() + "\t" + getLeague();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(team_ID, team.team_ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team_ID);
    }

}
