package state;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Сохраняет и управляет параметрами окон
 */
public class WindowSaver {

    private final Map<String, Integer> windowParams;
    private final Set<String> windowsNames;
    private final String configFilePath;

    public WindowSaver(Map<String, Integer> windowParams, Set<String> windowsNames) {
        this.windowParams = windowParams;
        this.windowsNames = windowsNames;
        this.configFilePath = System.getProperty("user.home") + File.separator + ".window_state" + File.separator + "state.ser";
    }

    public void registerWindow(String windowName) {
        windowsNames.add(windowName);
    }

    public Map<String, Integer> getWindowParams(String windowName) {
        if (windowsNames.isEmpty() || !windowsNames.contains(windowName)) {
            throw new IllegalArgumentException("Окно '" + windowName + "' не найдено");
        }
        Map<String, Integer> result = new HashMap<>();
        String prefix = windowName + ".";
        for (Map.Entry<String, Integer> entry : windowParams.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(prefix)) {
                String paramName = key.substring(prefix.length());
                result.put(paramName, entry.getValue());
            }
        }
        return result;
    }

    public void saveToFileSerialized() throws IOException {
        File configDir = new File(System.getProperty("user.home") + File.separator + ".window_state");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(configFilePath))) {
            oos.writeObject(windowParams);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFileSerialized() throws IOException, ClassNotFoundException {
        File configFile = new File(configFilePath);
        if (configFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(configFile))) {
                Map<String, Integer> loadedParams = (Map<String, Integer>) ois.readObject();
                windowParams.clear();
                windowParams.putAll(loadedParams);
            }
        }
    }

    public void saveWindowParams(IWindowAction window) {
        String windowName = window.getNameOfWindow();
        Map<String, Integer> params = window.saveWindowState();
        for (Map.Entry<String, Integer> entry : params.entrySet()) {
            windowParams.put(windowName + "." + entry.getKey(), entry.getValue());
        }
    }

    public Map<String, Integer> getWindowParams() {
        return windowParams;
    }

    public void setWindowParams(IWindowAction window) {
        window.loadWindowState(getWindowParams(window.getNameOfWindow()));
    }
}
