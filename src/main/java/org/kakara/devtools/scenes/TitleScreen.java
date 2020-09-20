package org.kakara.devtools.scenes;

import org.kakara.engine.GameHandler;
import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.math.Vector2;
import org.kakara.engine.resources.ResourceManager;
import org.kakara.engine.scene.AbstractMenuScene;
import org.kakara.engine.utils.RGBA;
import org.kakara.engine.ui.components.shapes.Rectangle;
import org.kakara.engine.ui.components.text.Text;
import org.kakara.engine.ui.constraints.*;
import org.kakara.engine.ui.events.UIClickEvent;
import org.kakara.engine.ui.events.UIHoverEnterEvent;
import org.kakara.engine.ui.events.UIHoverLeaveEvent;
import org.kakara.engine.ui.font.Font;
import org.kakara.engine.ui.font.TextAlign;
import org.kakara.engine.ui.items.ComponentCanvas;
import org.kakara.engine.window.WindowIcon;

public class TitleScreen extends AbstractMenuScene {
    public TitleScreen(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void work() {

    }

    @Override
    public void loadGraphics(GameHandler gameHandler) {
        gameHandler.getWindow().setIcon(new WindowIcon(gameHandler.getResourceManager().getResource("icon.png")));

        ResourceManager resourceManager = gameHandler.getResourceManager();
        setBackground(new Texture(resourceManager.getResource("kakara_background.png"), this));

        Font pixelFont = new Font("PixelFont", resourceManager.getResource("PressStart2P-Regular.ttf"), this);
        Font roboto = new Font("Roboto", resourceManager.getResource("Roboto-Regular.ttf"), this);


        ComponentCanvas componentCanvas = new ComponentCanvas(this);

        /*
            Create the title and version number.
         */
        Text title = new Text("Kakara", pixelFont);
        title.setSize(100);
        title.setLineWidth(100 * 6 + 5);
        title.setColor(new RGBA(255, 255, 255, 1));
        title.setPosition(0, 250);
        title.addConstraint(new HorizontalCenterConstraint());
        componentCanvas.add(title);

        Text versionNumber = new Text("Dev Tools", pixelFont);
        versionNumber.setSize(19);
        versionNumber.setLineWidth(19 * 10 - 10);
        versionNumber.setColor(new RGBA(255, 255, 255, 1));
        versionNumber.setPosition(title.getPosition().x, 300);
        versionNumber.addConstraint(new GeneralConstraint(ComponentSide.TOP, title, ComponentSide.BOTTOM, 10));
        versionNumber.addConstraint(new GeneralConstraint(ComponentSide.LEFT, title, ComponentSide.LEFT, 0));
        componentCanvas.add(versionNumber);
        /*
         * The single player button.
         */
        Rectangle singlePlayer = new Rectangle(new Vector2(0, 370), new Vector2(300, 60), new RGBA(255, 255, 255, 0.5f));
        singlePlayer.addConstraint(new HorizontalCenterConstraint());
        Text singlePlayerText = new Text("Terrain Generation", roboto);
        singlePlayerText.setSize(40);
        singlePlayerText.setLineWidth(300);
        singlePlayerText.setTextAlign(TextAlign.CENTER | TextAlign.MIDDLE);
        singlePlayerText.addConstraint(new VerticalCenterConstraint());
        singlePlayerText.addConstraint(new HorizontalCenterConstraint());
        singlePlayer.add(singlePlayerText);

        singlePlayer.addUActionEvent((UIHoverEnterEvent) vector2 -> singlePlayer.setColor(new RGBA(204, 202, 202, 0.5f)), UIHoverEnterEvent.class);
        singlePlayer.addUActionEvent((UIHoverLeaveEvent) vector2 -> singlePlayer.setColor(new RGBA(255, 255, 255, 0.5f)), UIHoverLeaveEvent.class);
        singlePlayer.addUActionEvent((UIClickEvent) (vector2, mouseClickType) -> gameHandler.getSceneManager().setScene(new TerrainGenScene(gameHandler)), UIClickEvent.class);

        componentCanvas.add(singlePlayer);


        /*
         * The multi player button.
         */
        Rectangle multiPlayer = new Rectangle(new Vector2(0, 500), new Vector2(300, 60), new RGBA(255, 255, 255, 0.5f));
        multiPlayer.addConstraint(new HorizontalCenterConstraint());
        Text multiPlayerText = new Text("Inventory Tool", roboto);
        multiPlayerText.setSize(40);
        multiPlayerText.setLineWidth(300);
        multiPlayerText.setTextAlign(TextAlign.CENTER | TextAlign.MIDDLE);
        multiPlayerText.addConstraint(new VerticalCenterConstraint());
        multiPlayerText.addConstraint(new HorizontalCenterConstraint());
        multiPlayer.add(multiPlayerText);

        multiPlayer.addUActionEvent((UIHoverEnterEvent) vector2 -> multiPlayer.setColor(new RGBA(204, 202, 202, 0.5f)), UIHoverEnterEvent.class);
        multiPlayer.addUActionEvent((UIHoverLeaveEvent) vector2 -> multiPlayer.setColor(new RGBA(255, 255, 255, 0.5f)), UIHoverLeaveEvent.class);
        multiPlayer.addUActionEvent((UIClickEvent) (vector2, mouseClickType) -> System.out.println("Test"), UIClickEvent.class);

        componentCanvas.add(multiPlayer);

        // Add the component canvas to to hud.
        add(componentCanvas);
    }

    @Override
    public void update(float v) {

    }
}
