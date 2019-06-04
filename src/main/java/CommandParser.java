import java.io.FileReader;
import java.util.Scanner;

public class CommandParser {
    private Scanner in;
    private String template;
    public CommandParser(Scanner scanner) {
        in = scanner;
        template = "^[\\w]+(\\s*\\.\\s*[\\w]+)*\\s*=\\s*" +
                "((\\d+)|(true)|(false)|(\".+\")|(\\w+)|([+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)))\\s*$";
    }
    public CommandParser(FileReader fileReader) {
        this(new Scanner(fileReader));
    }
    public CommandParser(String fileName) throws Exception {
        this(new FileReader(fileName));
    }
    public boolean hasNext() {
        return in.hasNext();
    }
    public String next() {
        String command;
        do {
            command = in.nextLine();
        } while (command.isEmpty());

        if (command.matches(template)) {
            return command;
        } else {
            throw new IllegalArgumentException("Illegal command format");
        }
    }
}
