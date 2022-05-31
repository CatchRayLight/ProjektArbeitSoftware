package GUI;


import javax.swing.*;
import java.awt.*;

/**
 * @author Atilla Ipek
 */
public class GameWindow {

    public GameWindow(int SCREEN_HEIGHT,int SCREEN_WIDTH, String title, Game game){
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        frame.setMaximumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        frame.setMinimumSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //

    }
}
