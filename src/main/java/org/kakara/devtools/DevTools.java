package org.kakara.devtools;

import org.kakara.devtools.scenes.TitleScreen;
import org.kakara.engine.Game;
import org.kakara.engine.GameHandler;
import org.kakara.engine.scene.Scene;

public class DevTools implements Game {
    @Override
    public void start(GameHandler gameHandler) throws Exception {

    }

    @Override
    public Scene firstScene(GameHandler gameHandler) throws Exception {
        return new TitleScreen(gameHandler);
    }

    @Override
    public void update() {

    }

    @Override
    public void exit() {

    }
}
