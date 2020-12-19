package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class Ex2 implements ActionListener {
    private static JLabel idLabel;
    private static JTextField idText;
    private static JLabel levelLabel;
    private static JTextField levelText;
    private static JButton button;

    public static void main(String[] args) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = idText.getText();
        String level = levelText.getText();
        try {
            Integer.parseInt(id);
            Thread Ex2Client = new Thread(new Ex2Client(level));
            Ex2Client.start();
        } catch (NumberFormatException numberFormatException) {
            System.out.println("ID should contains only numbers");

        }
    }
}
