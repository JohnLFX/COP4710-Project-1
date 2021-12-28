import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class P1 {

    //TODO Duplicate coach ids?

    private final List<Coache> coaches;
    private final List<Team> teams;

    public P1() {
        this.coaches = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    public void run() {

        System.out.println("The mini-DB of NBA coaches and teams");
        System.out.println("Please enter a command.  Enter 'help' for a list of commands.");
        System.out.println();
        System.out.print("> ");

        Command cmd;
        while ((cmd = CommandParser.fetchCommand()) != null) {
            //System.out.println(cmd);

            try {

                switch (cmd.getCommand()) {
                    case "help":
                        System.out.println("add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN ");
                        System.out.println("          SEASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data");
                        System.out.println("add_team ID LOCATION NAME LEAGUE - add a new team");
                        System.out.println("print_coaches - print a listing of all coaches");
                        System.out.println("print_teams - print a listing of all teams");
                        System.out.println("coaches_by_name NAME - list info of coaches with the specified name");
                        System.out.println("teams_by_city CITY - list the teams in the specified city");
                        System.out.println("load_coaches FILENAME - bulk load of coach info from a file");
                        System.out.println("load_teams FILENAME - bulk load of team info from a file");
                        System.out.println("best_coach SEASON - print the name of the coach with the most netwins in a specified season");
                        System.out.println("search_coaches field=VALUE - print the name of the coach satisfying the specified conditions");
                        System.out.println("exit - quit the program");
                        break;
                    case "add_coach": {

                        Coache coache = new Coache();

                        coache.setCoach_ID(cmd.getParameters()[0]);
                        coache.setSeason(Integer.parseInt(cmd.getParameters()[1]));
                        coache.setFirst_name(cmd.getParameters()[2]);
                        coache.setLast_name(cmd.getParameters()[3]);
                        coache.setSeason_win(Integer.parseInt(cmd.getParameters()[4]));
                        coache.setSeason_loss(Integer.parseInt(cmd.getParameters()[5]));
                        coache.setPlayoff_win(Integer.parseInt(cmd.getParameters()[6]));
                        coache.setPlayoff_loss(Integer.parseInt(cmd.getParameters()[7]));
                        coache.setTeam(cmd.getParameters()[8]);

                        if (!coaches.add(coache))
                            throw new IllegalArgumentException("Duplicate coache ID");

                        break;
                    }
                    case "add_team": {

                        Team team = new Team();

                        team.setTeam_ID(cmd.getParameters()[0]);
                        team.setLocation(cmd.getParameters()[1]);
                        team.setName(cmd.getParameters()[2]);
                        team.setLeague(cmd.getParameters()[3].charAt(0));

                        if (!teams.add(team))
                            throw new IllegalArgumentException("Duplicate team ID");

                        break;
                    }
                    case "print_coaches": {

                        //System.out.println("Coach_ID | season | first_name | last_name | season_win | season_loss | playoff_win | playoff_loss | team");

                        coaches.forEach(System.out::println);

                        //System.out.println("----------");

                        break;
                    }
                    case "print_teams": {

                        //System.out.println("Team_ID | Location | Name | League");

                        teams.forEach(System.out::println);

                        //System.out.println("----------");

                        break;
                    }
                    case "coaches_by_name": {

                        //System.out.println("Coach_ID | season | first_name | last_name | season_win | season_loss | playoff_win | playoff_loss | team");

                        for (Coache coache : coaches) {

                            if (coache.getLast_name().equalsIgnoreCase(cmd.getParameters()[0])) {

                                System.out.println(coache);

                            }

                        }

                        //System.out.println("----------");

                        break;
                    }
                    case "teams_by_city": {

                        //System.out.println("Team_ID | Location | Name | League");

                        for (Team team : teams) {

                            if (team.getLocation().equalsIgnoreCase(cmd.getParameters()[0])) {

                                System.out.println(team);

                            }

                        }

                        //System.out.println("----------");

                        break;
                    }
                    case "load_coaches": {

                        try (BufferedReader br = Files.newBufferedReader(Paths.get(cmd.getParameters()[0]))) {

                            String line;

                            br.readLine(); // Skip header in csv

                            while ((line = br.readLine()) != null) {

                                String[] data = line.split(",");
                                Arrays.setAll(data, i -> data[i].trim());

                                Coache coache = new Coache();
                                coache.setCoach_ID(data[0]);
                                coache.setSeason(Integer.parseInt(data[1]));
                                coache.setFirst_name(data[2]);
                                coache.setLast_name(data[3]);
                                coache.setSeason_win(Integer.parseInt(data[4]));
                                coache.setSeason_loss(Integer.parseInt(data[5]));
                                coache.setPlayoff_win(Integer.parseInt(data[6]));
                                coache.setPlayoff_loss(Integer.parseInt(data[7]));
                                coache.setTeam(data[8]);

                                coaches.add(coache);

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case "load_teams": {

                        try (BufferedReader br = Files.newBufferedReader(Paths.get(cmd.getParameters()[0]))) {

                            String line;

                            br.readLine(); // Skip header in csv

                            while ((line = br.readLine()) != null) {

                                String[] data = line.split(",");
                                Arrays.setAll(data, i -> data[i].trim());

                                Team team = new Team();

                                team.setTeam_ID(data[0]);
                                team.setLocation(data[1]);
                                team.setName(data[2]);
                                team.setLeague(data[3].charAt(0));

                                teams.add(team);

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case "best_coach": {

                        int season = Integer.parseInt(cmd.getParameters()[0]);

                        Optional<Coache> coache = coaches.stream()
                                .filter(c -> c.getSeason() == season)
                                .max(Comparator.comparingInt(Coache::getNetWins));

                        if (coache.isPresent()) {

                            //System.out.println("Coach_ID | season | first_name | last_name | season_win | season_loss | playoff_win | playoff_loss | team");

                            System.out.println(coache.get().getFirst_name() + " " + coache.get().getLast_name());

                            // System.out.println("----------");

                        } else {

                            System.out.println("Error: No coaches are found in the database");

                        }

                        break;
                    }
                    case "search_coaches":

                        Coache needle = new Coache();
                        Map<String, String> operatorFieldMap = new HashMap<>();

                        Field[] fields = needle.getClass().getDeclaredFields();
                        Arrays.stream(fields).forEach(field -> field.setAccessible(true));

                        for (String parameter : cmd.getParameters()) {

                            if (parameter.trim().isEmpty())
                                continue;

                            //System.out.println("Parameter: " + parameter);

                            StringBuilder fieldNameBuilder = new StringBuilder();
                            StringBuilder operatorBuilder = new StringBuilder();
                            StringBuilder fieldValueBuilder = new StringBuilder();

                            boolean populatingName = true;

                            for (char c : parameter.toCharArray()) {

                                if (c == '=' || c == '<' || c == '>' || c == '!') {

                                    operatorBuilder.append(c);
                                    populatingName = false;

                                } else {

                                    if (populatingName) {
                                        fieldNameBuilder.append(c);
                                    } else {
                                        fieldValueBuilder.append(c);
                                    }

                                }

                            }

                            //System.out.println(fieldName.toString() + " | " + operator + " | " + fieldValue);

                            String fieldName = fieldNameBuilder.toString();

                            for (Field field : fields) {

                                if (field.getName().equalsIgnoreCase(fieldName)) {

                                    operatorFieldMap.put(field.getName(), operatorBuilder.toString());

                                    try {

                                        if (field.getType() == Integer.class) {

                                            field.set(needle, Integer.valueOf(fieldValueBuilder.toString()));

                                        } else {

                                            field.set(needle, fieldValueBuilder.toString());

                                        }

                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }

                                    break;

                                }

                            }

                        }

                        //printCoache(needle);

                        //System.out.println("Coach_ID | season | first_name | last_name | season_win | season_loss | playoff_win | playoff_loss | team");

                        for (Coache coache : coaches) {

                            boolean printCoach = true;

                            for (Field field : fields) {

                                try {

                                    if (field.get(needle) != null) {

                                        //System.out.println("Check value: " + field.get(needle));

                                        Object checkObj = field.get(needle);
                                        Object needleObj = field.get(coache);

                                        switch (operatorFieldMap.get(field.getName())) {
                                            case "=":

                                                if (needleObj instanceof String) {

                                                    if (!String.valueOf(needleObj).equalsIgnoreCase(String.valueOf(checkObj)))
                                                        printCoach = false;

                                                } else {

                                                    if (!Objects.equals(checkObj, needleObj)) {
                                                        printCoach = false;
                                                    }

                                                }

                                                break;
                                            case ">":

                                                if (needleObj instanceof Integer) {

                                                    if ((Integer) checkObj >= (Integer) needleObj)
                                                        printCoach = false;

                                                } else {
                                                    throw new IllegalArgumentException("Cannot use \">\" operator for data type " + field.getType());
                                                }

                                                break;
                                            default:
                                                throw new IllegalArgumentException("Invalid operator: \""
                                                        + operatorFieldMap.get(field.getName()) + "\"");

                                        }

                                    }

                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }

                            }

                            if (printCoach)
                                System.out.println(coache);

                        }

                        //System.out.println("----------");

                        break;
                    case "exit":
                        System.out.println("Leaving the database, goodbye!");
                        System.exit(0);
                    case "":
                        break;
                    default:
                        System.out.println("Invalid Command, try again!");
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: Incorrect number of arguments supplied for command " + cmd.getCommand());
                e.printStackTrace();
            }

            System.out.print("> ");
        }
    }

    public static void main(String[] args) {
        new P1().run();
    }

}
