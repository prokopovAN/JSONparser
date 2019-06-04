public class Main {
    public static void main(String[] args) {
        JsonObjects objects = new JsonObjects();
        try {
            CommandParser commandParser = new CommandParser(".\\src\\main\\resources\\input.txt");
            while (commandParser.hasNext()) {
                String command = commandParser.next();
                objects.process(command);
            }
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
        System.out.println(objects.toJSON());
    }
}
