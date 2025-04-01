package gui;

import state.WindowAction;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Frame.ICONIFIED;
import static java.awt.Frame.NORMAL;

public abstract class BaseWindow extends JInternalFrame implements WindowAction {
    public BaseWindow(String title, int width, int height) {
        super(title, true, true, true, true);
        setSize(width, height);
        setLocation(50, 50);
        setVisible(true);
    }

    @Override
    public Map<String, Integer> saveWindowState() {
        Map<String, Integer> state = new HashMap<>();
        state.put("x", getLocation().x);
        state.put("y", getLocation().y);
        state.put("width", getWidth());
        state.put("height", getHeight());
        state.put("state", isIcon() ? ICONIFIED : NORMAL);
        return state;
    }

    @Override
    public void loadWindowState(Map<String, Integer> params) {
        if (params != null) {
            setLocation(params.getOrDefault("x", 50), params.getOrDefault("y", 50));
            setSize(params.getOrDefault("width", 400), params.getOrDefault("height", 400));
            if (params.getOrDefault("state", NORMAL) == ICONIFIED) {
                try {
                    setIcon(true);
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
