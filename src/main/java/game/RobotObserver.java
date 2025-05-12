package game;

public interface RobotObserver {
    void onRobotStateChanged(double x, double y, double direction, double targetX, double targetY);
}
