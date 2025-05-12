package model;

import game.RobotObserver;

import java.util.ArrayList;
import java.util.List;

public class RobotModel {
    private double x = 100;
    private double y = 100;
    private double direction = 0;

    private double targetX = 100;
    private double targetY = 100;

    private final List<RobotObserver> observers = new ArrayList<>();

    public void addObserver(RobotObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RobotObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (RobotObserver observer : observers) {
            observer.onRobotStateChanged(x, y, direction, targetX, targetY);
        }
    }


    public void updateRobotPosition() {
        double distance = distanceToTarget();
        if (distance < 0.5) {
            return;
        }

        double angleToTarget = Math.atan2(targetY - y, targetX - x);
        double angleDiff = normalizeAngle(angleToTarget - direction);

        if (Math.abs(angleDiff) > 0.1) {
            direction += Math.signum(angleDiff) * 0.05;
        } else {
            double speed = Math.min(2.0, distance);
            x += speed * Math.cos(direction);
            y += speed * Math.sin(direction);
        }

        notifyObservers();
    }

    public void setTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
        notifyObservers();
    }

    private double distanceToTarget() {
        double dx = targetX - x;
        double dy = targetY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double normalizeAngle(double angle) {
        while (angle < -Math.PI) angle += 2 * Math.PI;
        while (angle > Math.PI) angle -= 2 * Math.PI;
        return angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }
}
