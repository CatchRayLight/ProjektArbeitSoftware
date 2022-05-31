package gameDA.gui;


import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow(int SCREEN_HEIGHT,int SCREEN_WIDTH, String title, Game game){
        super(title);
        setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setMaximumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setMinimumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        add(game);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
