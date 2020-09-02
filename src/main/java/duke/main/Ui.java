package duke.main;

import java.util.Scanner;

/**
 * Class representing the interface that receives user inputs.
 * @deprecated As of Level-10, replaced by GUI elements instead.
 */
@Deprecated
public class Ui {
    public static final String LINE = "____________________";
    private static final String GREETING = "Hello, I'm your chatbot!\n"
            + "What can I do for you?";
    private static final String FAREWELL = "Sad to see you go!";

    private Parser parser;
    private final Scanner scanner;

    public Ui(TaskList taskList) {
        this.scanner = new Scanner(System.in);
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    /**
     * Initialises the UI and accepts user inputs.
     * If the user enters "bye" the UI and bot closes. Otherwise any
     * input is parsed into a Parser object and interpreted, after which
     * its output is displayed back to the user.
     */
    void run() {
        System.out.println(LINE + "\n" + GREETING + "\n" + LINE);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                scanner.close();
                break;
            } else {
                String output = parser.parse(input);
                System.out.println(LINE);
                System.out.println(output);
                System.out.println(LINE);
            }
        }
        System.out.println(LINE + "\n" + FAREWELL + "\n" + LINE);
    }
}