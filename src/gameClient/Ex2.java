package gameClient;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ex2 implements ActionListener {
    private static int ID ;
    private static int level ;
    private static JLabel idLabel;
    private static JTextField idText;
    private static JLabel levelLabel;
    private static JTextField levelText;
    private static JButton button;

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                ID = Integer.parseInt(args[0]);

            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID should contains only numbers");
            }
            try{
                level = Integer.parseInt(args[1]) ;
            } catch (NumberFormatException e) {
                e.printStackTrace();

            }
            Thread Ex2Client = new Thread(new Ex2Client(level, ID));
            Ex2Client.start();



        } else {
            JPanel panel = new JPanel();
            JFrame frame = new JFrame();
            frame.setSize(350, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            panel.setLayout(null);

            idLabel = new JLabel("ID:");
            idLabel.setBounds(20, 30, 80, 25);
            panel.add(idLabel);

            idText = new JTextField();
            idText.setBounds(80, 30, 165, 25);
            panel.add(idText);

            levelLabel = new JLabel("Level:");
            levelLabel.setBounds(20, 70, 80, 25);
            panel.add(levelLabel);

            levelText = new JTextField();
            levelText.setBounds(80, 70, 165, 25);
            panel.add(levelText);

            button = new JButton("Start");
            button.setBounds(10, 110, 80, 25);
            button.addActionListener(new Ex2());
            panel.add(button);

            frame.setVisible(true);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = idText.getText();
        String levelString = levelText.getText();
        try {
            ID = Integer.parseInt(id);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("ID should contains only numbers");
        }
        try{
            level = Integer.parseInt(levelString);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        Thread Ex2Client = new Thread(new Ex2Client(level, ID));
        Ex2Client.start();
    }
}
