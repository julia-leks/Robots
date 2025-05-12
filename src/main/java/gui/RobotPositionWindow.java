package gui;

import model.RobotModel;
import game.RobotObserver;
import state.IWindowAction;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RobotPositionWindow extends JInternalFrame implements IWindowAction, RobotObserver {
    private final JTextArea textArea;

    public RobotPositionWindow(RobotModel model) {
        super("Информация", true, true, true, true);
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setSize(300, 200);
        setLocation(100, 100);
        setVisible(true);
        pack();

        model.addObserver(this);
        onRobotStateChanged(model.getX(), model.getY(), model.getDirection(), model.getTargetX(), model.getTargetY());
    }

    private void updateText(double x, double y) {
        textArea.setText(String.format("Координаты робота:\nX: %.2f\nY: %.2f", x, y));
    }

    @Override
    public void onRobotStateChanged(double x, double y, double direction, double targetX, double targetY) {
        updateText(x, y);
    }

    @Override
    public String getNameOfWindow() {
        return "RobotPositionWindow";
    }

    @Override
    public Map<String, Integer> saveWindowState() {
        Map<String, Integer> state = new HashMap<>();
        state.put("x", getX());
        state.put("y", getY());
        state.put("width", getWidth());
        state.put("height", getHeight());
        return state;
    }

    @Override
    public void loadWindowState(Map<String, Integer> params) {
        int x = params.getOrDefault("x", 100);
        int y = params.getOrDefault("y", 100);
        int width = params.getOrDefault("width", 300);
        int height = params.getOrDefault("height", 200);
        setBounds(x, y, width, height);
    }
}
