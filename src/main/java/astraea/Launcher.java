package astraea;

import javafx.application.Application;

/**
 * A Launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Astraea.class, args);
    }
}
