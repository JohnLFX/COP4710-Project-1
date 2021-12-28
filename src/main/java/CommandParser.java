import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CommandParser {

    private static final BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));

    public static Command fetchCommand() {

        try {
            String s = CONSOLE_READER.readLine();
            if (s == null) {
                return null;
            }

            // \s+ = 1 or more of a whitespace char
            String[] pieces = s.split("\\s+", 10 /* max command len + 1 */);
            String[] params = new String[pieces.length - 1];
            System.arraycopy(pieces, 1, params, 0, pieces.length - 1);
            Arrays.setAll(params, i -> params[i].replace('+', ' '));

            return new Command(pieces[0], params);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
