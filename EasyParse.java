import javax.swing.*;
import javafx.application.Application;

public class EasyParse {
    public static void easy() throws Exception {
        String subreddit = JOptionPane.showInputDialog("What SubReddit");
        simpleParse.normal(subreddit);
    }

    public static void customSize() throws Exception {
        String subreddit = JOptionPane.showInputDialog("What SubReddit");
        int howMany = Integer.parseInt(JOptionPane.showInputDialog("How Many Images?"));
        int height = Integer.parseInt(JOptionPane.showInputDialog("Height?"));
        int width = Integer.parseInt(JOptionPane.showInputDialog("Width?"));
        simpleParse.sized(subreddit, howMany, width, height);
    }

    public static void original() throws Exception {
        String subreddit = JOptionPane.showInputDialog("What SubReddit");
        int howMany = Integer.parseInt(JOptionPane.showInputDialog("How Many Images?"));
        simpleParse.original(subreddit);
    }

    public static void UserSearch() throws Exception {
        String user = JOptionPane.showInputDialog("Username");
        simpleParse.USER(user);
    }

    public static void main(String[] args) throws Exception {
        JTextField whatSub = new JTextField();
        JTextField howMany = new JTextField(); // JPasswordField is cool
        Object[] message = { "What SubReddit:", whatSub, "How Many Images:", howMany };
        int option = JOptionPane.showConfirmDialog(null, message, "Sam's Reddit Parser", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            simpleParse.manipulated(whatSub.getText(), Integer.parseInt(howMany.getText()));
        }
    }
}
