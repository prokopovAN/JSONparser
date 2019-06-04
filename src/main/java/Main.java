import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args)  {
        JsonObjects objects = new JsonObjects();
        try {
            CommandParser commandParser = new CommandParser(".\\src\\main\\resources\\input.txt");
            while (commandParser.hasNext()) {
                String command = commandParser.next();
                objects.process(command);
            }

            PrintWriter out = new PrintWriter(".\\src\\main\\resources\\output.json");
            out.println(objects.toJSON());
            out.close();
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }

    }
}
