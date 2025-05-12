package gui;

import game.RobotObserver;
import model.RobotModel;

import javax.swing.*;
import java.awt.*;

public class GameVisualizer extends JPanel implements RobotObserver {

    private double x, y, direction, targetX, targetY;

    public GameVisualizer(RobotModel model) {
        model.addObserver(this);
        setDoubleBuffered(true);

        this.x = model.getX();
        this.y = model.getY();
        this.direction = model.getDirection();
        this.targetX = model.getTargetX();
        this.targetY = model.getTargetY();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRobot(g, x, y, direction);
        drawTarget(g, targetX, targetY);
    }

    private void drawRobot(Graphics g, double x, double y, double direction) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        g2d.rotate(direction);
        g2d.setColor(Color.PINK);
        g2d.fillOval(-20, -5, 40, 10);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(20 - 2, -2, 4, 4);
        g2d.dispose();
    }

    private void drawTarget(Graphics g, double targetX, double targetY) {
        g.setColor(Color.GREEN);
        int targetCenterX = (int) targetX;
        int targetCenterY = (int) targetY;
        g.fillOval(targetCenterX - 5, targetCenterY - 5, 10, 10);
    }

    @Override
    public void onRobotStateChanged(double x, double y, double direction, double targetX, double targetY) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.targetX = targetX;
        this.targetY = targetY;
        repaint();
    }
}
