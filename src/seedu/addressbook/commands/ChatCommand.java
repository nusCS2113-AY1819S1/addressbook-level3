package seedu.addressbook.commands;

//import seedu.addressbook.communications.ChatClient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ChatCommand extends Command{

    public static final String COMMAND_WORD = "chat";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Opens up a separate chat programme\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Initialising chat!";



    public static void main(String[] args)  {
        new ChatCommand();
    }

    public ChatCommand() {
        try {
            int result = compile("seedu.addressbook.communications.ChatClient");
            System.out.println("javac returned " + result);
            result = run("chatcommand.HelloWorld");
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public int run(String clazz) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", clazz);
        pb.redirectError();
        pb.directory(new File("src"));
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

        int result = p.waitFor();

        consumer.join();

        System.out.println(consumer.getOutput());

        return result;
    }

    public int compile(String file) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("javac", file);
        pb.redirectError();
        pb.directory(new File("src"));
        Process p = pb.start();
        InputStreamConsumer consumer = new InputStreamConsumer(p.getInputStream());
        consumer.start();

        int result = p.waitFor();

        consumer.join();

        System.out.println(consumer.getOutput());

        return result;
    }

    public class InputStreamConsumer extends Thread {

        private InputStream is;
        private IOException exp;
        private StringBuilder output;

        public InputStreamConsumer(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            int in = -1;
            output = new StringBuilder(64);
            try {
                while ((in = is.read()) != -1) {
                    output.append((char) in);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                exp = ex;
            }
        }

        public StringBuilder getOutput() {
            return output;
        }

        public IOException getException() {
            return exp;
        }
        /*public CommandResult execute() {
            ChatClient.main(new String[0]);
            commandHistory.checkForAction();
            commandHistory.addHistory(COMMAND_WORD);
            return new CommandResult(MESSAGE_SUCCESS);
        }*/
    }
}
