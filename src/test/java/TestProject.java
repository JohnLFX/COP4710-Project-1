import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestProject {

    @Test
    public void test() throws IOException {

        Process process = TestRuntime.exec(P1.class);

        System.out.println("Working directory: " + Paths.get("").toAbsolutePath().toString());

        BufferedReader processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedWriter processInput = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        Assert.assertEquals("The mini-DB of NBA coaches and teams", processOutput.readLine());
        Assert.assertEquals("Please enter a command.  Enter 'help' for a list of commands.", processOutput.readLine());
        processOutput.readLine();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(TestProject.class.getResourceAsStream("/test_case/sample.txt")))) {

            String line;

            while ((line = br.readLine()) != null) {

                processInput.write(line + "\n");
                processInput.flush();

                List<String> expectedOutput = new ArrayList<>();
                List<String> actualOutput = new ArrayList<>();

                System.out.println("Executed " + line);

                while (!(line = br.readLine()).isEmpty()) {

                    line = line.replaceAll("\\s+", " ");

                    System.out.println("Expect: " + line);

                    String outputLine = processOutput.readLine();

                    StringBuilder compare = new StringBuilder();

                    for (char c : outputLine.toCharArray()) {
                        if (Character.isLetterOrDigit(c)) {
                            compare.append(c);
                        } else if (c == '\t' || c == ' ') {
                            compare.append(' ');
                        }
                    }

                    System.out.println("Actual: " + compare.toString().trim());

                    expectedOutput.add(line);
                    actualOutput.add(compare.toString().trim());

                }

                Assert.assertEquals(expectedOutput.size(), actualOutput.size());

                for (String s : expectedOutput) {
                    Assert.assertTrue("Cannot find \"" + s + "\" in actual output", actualOutput.contains(s));
                }

            }

        }

        processInput.close();
        processOutput.close();

        process.destroy();

    }

}
