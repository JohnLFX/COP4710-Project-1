import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestRuntime {

    @Test
    public void testHelpMenu() throws IOException {

        Process process = exec(P1.class);

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        bw.write("help\n");
        bw.close();

        Assert.assertEquals("The mini-DB of NBA coaches and teams", br.readLine());
        Assert.assertEquals("Please enter a command.  Enter 'help' for a list of commands.", br.readLine());
        Assert.assertEquals("", br.readLine());
        Assert.assertEquals("> add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN ", br.readLine());
        Assert.assertEquals("          SEASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data", br.readLine());
        Assert.assertEquals("add_team ID LOCATION NAME LEAGUE - add a new team", br.readLine());
        Assert.assertEquals("print_coaches - print a listing of all coaches", br.readLine());
        Assert.assertEquals("print_teams - print a listing of all teams", br.readLine());
        Assert.assertEquals("coaches_by_name NAME - list info of coaches with the specified name", br.readLine());
        Assert.assertEquals("teams_by_city CITY - list the teams in the specified city", br.readLine());
        Assert.assertEquals("load_coaches FILENAME - bulk load of coach info from a file", br.readLine());
        Assert.assertEquals("load_teams FILENAME - bulk load of team info from a file", br.readLine());
        Assert.assertEquals("best_coach SEASON - print the name of the coach with the most netwins in a specified season", br.readLine());
        Assert.assertEquals("search_coaches field=VALUE - print the name of the coach satisfying the specified conditions", br.readLine());
        Assert.assertEquals("exit - quit the program", br.readLine());

        process.destroy();

    }

    /*@Test
    public void testSpacedSearch() throws IOException {

        Process process = exec(P1.class);

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        bw.write("load_coaches\n");
        bw.write("search_coaches last_name=van+Gundy\n");
        bw.close();

        Assert.assertEquals("The mini-DB of NBA coaches and teams", br.readLine());
        Assert.assertEquals("Please enter a command.  Enter 'help' for a list of commands.", br.readLine());
        Assert.assertEquals("", br.readLine());
        Assert.assertEquals("> > Coach_ID | season | first_name | last_name | season_win | season_loss | playoff_win | playoff_loss | team", br.readLine());
        Assert.assertEquals("VANGUJE01 1995 Jeff Van Gundy 13 10 4 4 NYK", br.readLine());
        Assert.assertEquals("VANGUST01 2003 Stan Van Gundy 42 40 6 7 MIA", br.readLine());
        Assert.assertEquals("----------", br.readLine());

        process.destroy();

    }

    @Test
    public void testSearch() throws IOException {

        Process process = exec(P1.class);

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        bw.write("load_coaches coaches_season.txt\n");
        bw.write("search_coaches first_name=John season_win=40\n");
        bw.close();

        Assert.assertEquals("The mini-DB of NBA coaches and teams", br.readLine());
        Assert.assertEquals("Please enter a command.  Enter 'help' for a list of commands.", br.readLine());
        Assert.assertEquals("", br.readLine());
        Assert.assertEquals("> > Coach_ID | season | first_name | last_name | season_win | season_loss | playoff_win | playoff_loss | team", br.readLine());
        Assert.assertEquals("LUCASJO01 1992 John Lucas 40 22 5 5 SAS", br.readLine());
        Assert.assertEquals("----------", br.readLine());

        process.destroy();

    }*/

    static Process exec(Class klass) throws IOException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getName();

        ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className);

        return builder.start();
    }


}
