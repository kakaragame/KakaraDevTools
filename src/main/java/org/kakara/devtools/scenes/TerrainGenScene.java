package org.kakara.devtools.scenes;

import org.kakara.devtools.PerlinNoise;
import org.kakara.devtools.canvas.TerrainGenCanvas;
import org.kakara.engine.GameHandler;
import org.kakara.engine.events.EventHandler;
import org.kakara.engine.events.event.KeyPressEvent;
import org.kakara.engine.input.KeyInput;
import org.kakara.engine.input.MouseInput;
import org.kakara.engine.math.Vector3;
import org.kakara.engine.renderobjects.RenderBlock;
import org.kakara.engine.renderobjects.RenderChunk;
import org.kakara.engine.renderobjects.RenderTexture;
import org.kakara.engine.renderobjects.TextureAtlas;
import org.kakara.engine.renderobjects.mesh.MeshType;
import org.kakara.engine.renderobjects.renderlayouts.BlockLayout;
import org.kakara.engine.resources.ResourceManager;
import org.kakara.engine.scene.AbstractGameScene;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;

public class TerrainGenScene extends AbstractGameScene {

    private TerrainGenCanvas terrainGenCanvas;
    List<RenderTexture> textureList = new ArrayList<>();
    private boolean paused = false;

    PerlinNoise noise;

    public TerrainGenScene(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void work() {

    }

    @Override
    public void loadGraphics(GameHandler gameHandler) throws Exception {
        ResourceManager resourceManager = gameHandler.getResourceManager();

        textureList.add(new RenderTexture(resourceManager.getResource("/dirt.png")));
        textureList.add(new RenderTexture(resourceManager.getResource("/grassy_dirt.png")));
        textureList.add(new RenderTexture(resourceManager.getResource("/stone.png")));
        setTextureAtlas(new TextureAtlas(textureList, Paths.get("").toAbsolutePath().toString(), this));
        terrainGenCanvas = new TerrainGenCanvas(this);
        add(terrainGenCanvas);

        noise = new PerlinNoise();
        noise.set(0.2, 0.5, 0.3, 0.5, 437545978);

        new Thread(() -> {
            for(int x = 0; x < 16*3; x+= 16){
                for(int y = 0; y < 16*5; y+=16){
                    for(int z = 0; z < 16*3; z+=16){
                        RenderChunk rc = generateChunk(noise, x, y, z);
                        rc.regenerateChunk(getTextureAtlas(), MeshType.MULTITHREAD);
                        getChunkHandler().addChunk(rc);
                    }
                }
            }
        }).start();
    }

    private RenderChunk generateChunk(PerlinNoise noise, int cx, int cy, int cz){
        RenderChunk renderChunk = new RenderChunk(new ArrayList<>(), getTextureAtlas());
        renderChunk.setPosition(cx, cy, cz);
        for(int x = cx; x < cx + 16; x++){
            for(int z = cz; z < cz + 16; z++){
                int groundHeight = (int) (noise.getHeight(x, z) * 50) + 50;
                for(int y = cy; y < cy + 16; y++) {
                    if(y > groundHeight) continue;
                    else if(y < 0) continue;
                    else if(y == groundHeight)
                        renderChunk.addBlock(new RenderBlock(new BlockLayout(), textureList.get(1),
                                new Vector3(x % 16, y % 16, z % 16)));
                    else if(y > groundHeight - 5)
                        renderChunk.addBlock(new RenderBlock(new BlockLayout(), textureList.get(0),
                                new Vector3(x % 16, y % 16, z % 16)));
                    else
                        renderChunk.addBlock(new RenderBlock(new BlockLayout(), textureList.get(2),
                                new Vector3(x % 16, y % 16, z % 16)));
                }
            }
        }
        return renderChunk;
    }

    @Override
    public void update(float v) {
        KeyInput ki = gameHandler.getKeyInput();
        if (ki.isKeyPressed(GLFW_KEY_ESCAPE)) {
            gameHandler.exit();
        }
        if(paused) return;
        if (ki.isKeyPressed(GLFW_KEY_W)) {
            getCamera().movePosition(0, 0, -1);
        }
        if (ki.isKeyPressed(GLFW_KEY_S)) {
            getCamera().movePosition(0, 0, 1);
        }
        if (ki.isKeyPressed(GLFW_KEY_A)) {
            getCamera().movePosition(-1, 0, 0);
        }
        if (ki.isKeyPressed(GLFW_KEY_D)) {
            getCamera().movePosition(1, 0, 0);
        }
        if (ki.isKeyPressed(GLFW_KEY_SPACE)) {
            getCamera().movePosition(0, 1, 0);
        }
        if (ki.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            getCamera().movePosition(0, -1, 0);
        }

        MouseInput mi = gameHandler.getMouseInput();
        getCamera().moveRotation((float) (mi.getDeltaPosition().y), (float) mi.getDeltaPosition().x, 0);
    }

    public void regenerate(){
        noise.set(terrainGenCanvas.getPersistence(), terrainGenCanvas.getFrequency(), terrainGenCanvas.getAmplitude(), terrainGenCanvas.getOctaves(), 437545978);
        getChunkHandler().removeAll();
        new Thread(() -> {
            for(int x = 0; x < 16*3; x+= 16){
                for(int y = 0; y < 16*5; y+=16){
                    for(int z = 0; z < 16*3; z+=16){
                        RenderChunk rc = generateChunk(noise, x, y, z);
                        rc.regenerateChunk(getTextureAtlas(), MeshType.MULTITHREAD);
                        getChunkHandler().addChunk(rc);
                    }
                }
            }
        }).start();
    }

    @EventHandler
    public void keyPressEvent(KeyPressEvent evt){
        if(evt.isKeyPressed(GLFW_KEY_P)){
            paused = !paused;
        }
    }
}
