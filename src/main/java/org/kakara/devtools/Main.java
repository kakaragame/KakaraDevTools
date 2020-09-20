package org.kakara.devtools;

import org.kakara.engine.GameEngine;

public class Main {

    public static void main(String[] args) {
        GameEngine engine = new GameEngine("Kakara Dev Tools", 1080, 720, true, new DevTools());
        engine.run();
    }

}
