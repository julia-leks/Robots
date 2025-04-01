package gui;
import state.WindowAction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.NORMAL;

public class GameWindow extends BaseWindow {
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super("Игровое поле", 400, 400);
        m_visualizer = new GameVisualizer();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void updateGame() {
        m_visualizer.repaint();
    }

    @Override
    public String getNameOfWindow() {
        return "GameWindow";
    }
}