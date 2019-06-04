import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Scanner;

public class TestingModule {
    private void helpParseTestExc(String filename, String failCmd) throws Exception {
        CommandParser commandParser = new CommandParser(filename);
        try {
            while (commandParser.hasNext()) {
                commandParser.next();
            }
            Assert.fail("Exception didn't thrown");
        } catch (IllegalArgumentException thrown) {
            Assert.assertEquals(failCmd, thrown.getMessage());
        }
    }
    private void helpProcessTest(String filename, String filenameSample) throws Exception {
        JsonObjects objects = new JsonObjects();
        try {
            CommandParser commandParser = new CommandParser(filename);
            while (commandParser.hasNext()) {
                String command = commandParser.next();
                objects.process(command);
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        boolean flag = true;
        Scanner result = new Scanner(objects.toJSON());
        Scanner sample = new Scanner(new FileInputStream(filenameSample));
        while (result.hasNextLine() || sample.hasNextLine()) {
            if (!result.hasNextLine() || !sample.hasNextLine()) {
                flag = false;
                break;
            } else {
                if (!result.nextLine().equals(sample.nextLine())) {
                    flag = false;
                }
            }
        }
        Assert.assertEquals(flag, true);
    }
    @Test
    public void ParseTest() throws Exception {
        CommandParser commandParser =
                new CommandParser(".\\src\\test\\resources\\ParseTests\\ParseTest.txt");
        String result = "";
        while (commandParser.hasNext()) {
            String temp = commandParser.next();
            if (!temp.isEmpty()) {
                result += temp.trim();
            }
        }
        String sample =
                "key1.sub1 = 3" +
                "key1 . sub2 = true" +
                "key2 = -2.123" +
                "key2.sub3 = 4" +
                "key2.sub_4 = \"sad\"";
        Assert.assertEquals(result, sample);
    }
    @Test
    public void IllegalArgument1() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest2.txt",
                "Command \"key2.s ub3 = 4\" is illegal"
        );
    }
    @Test
    public void IllegalArgument2() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest3.txt",
                "Command \"key2.sub3\" is illegal"
        );
    }
    @Test
    public void IllegalArgument3() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest4.txt",
                "Command \".key2 = 2.123\" is illegal"
        );
    }
    @Test
    public void IllegalArgument4() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest5.txt",
                "Command \"key2 = 2,123\" is illegal"
        );
    }
    @Test
    public void IllegalArgument5() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest6.txt",
                "Command \"key2.sub4 = \"sad\" is illegal"
        );
    }
    @Test
    public void IllegalArgument6() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest7.txt",
                "Command \"key2.sub3 = 4;\" is illegal"
        );
    }
    @Test
    public void IllegalArgument7() throws Exception {
        helpParseTestExc(
                ".\\src\\test\\resources\\ParseTests\\ParseTest8.txt",
                "Command \"key2.sub3 =\" is illegal"
        );
    }
    @Test
    public void ProcessTest1() throws Exception {
        helpProcessTest(
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest1.in",
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest1.out"
        );
    }
    @Test
    public void ProcessTest2() throws Exception {
        helpProcessTest(
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest2.in",
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest2.out"
        );
    }
    @Test
    public void ProcessTest3() throws Exception {
        helpProcessTest(
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest3.in",
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest3.out"
        );
    }
    @Test
    public void ProcessTest4() throws Exception {
        helpProcessTest(
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest4.in",
                ".\\src\\test\\resources\\ProcessTests\\ProcessTest4.out"
        );
    }
    @Test
    public void ProcessTest5() throws Exception {
        JsonObjects objects = new JsonObjects();
        try {
            CommandParser commandParser =
                    new CommandParser(".\\src\\test\\resources\\ProcessTests\\ProcessTest5.in");
            while (commandParser.hasNext()) {
                String command = commandParser.next();
                objects.process(command);
            }
            Assert.fail("Exception didn't thrown");
        } catch (Exception exc) {
            Assert.assertEquals(
                    "Object key1.sub1.values don't exist",
                    exc.getMessage()
            );
        }
    }
}
