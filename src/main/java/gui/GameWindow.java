package gui;

import game.GameVisualizer;
import game.RobotModel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends BaseWindow {
    public static RobotModel model;
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super("Игровое поле", 400, 400);
        model = new RobotModel();
        m_visualizer = new GameVisualizer(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();

        new game.GameController(model, m_visualizer);
    }

    @Override
    public String getNameOfWindow() {
        return "GameWindow";
    }
}