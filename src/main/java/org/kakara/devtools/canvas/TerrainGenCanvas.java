package org.kakara.devtools.canvas;

import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.kakara.devtools.scenes.TerrainGenScene;
import org.kakara.engine.GameHandler;
import org.kakara.engine.scene.Scene;
import org.kakara.engine.ui.UICanvas;
import org.kakara.engine.ui.UserInterface;

import java.util.List;

public class TerrainGenCanvas implements UICanvas {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private float[] persistence = new float[1];
    private float[] frequency = new float[1];
    private float[] amplitude = new float[1];
    private float[] octaves = new float[1];

    TerrainGenScene parent;
    public TerrainGenCanvas(TerrainGenScene genScene){
        this.parent = genScene;
    }

    /*
     * Tagable data
     */
    private List<Object> data;
    private String tag;
    @Override
    public void init(UserInterface userInterface, GameHandler handler) {
        ImGui.createContext();
        imGuiGlfw.init(handler.getWindow().getWindowHandler(), true);
        imGuiGl3.init("#version 150");
        persistence[0] = 0.2f;
        frequency[0] = 0.5f;
        amplitude[0] = 0.3f;
        octaves[0] = 0.5f;
    }

    @Override
    public void render(UserInterface userInterface, GameHandler handler) {
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        renderSceneInfo(userInterface.getScene());

        ImGui.render();
        imGuiGl3.render(ImGui.getDrawData());
    }

    @Override
    public void cleanup(GameHandler handler) {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
    }

    private void renderSceneInfo(Scene scene){
        ImGui.setNextWindowSize(300, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(10, 10, ImGuiCond.Once);
        ImGui.begin("Perlin Noise Generation");
        ImGui.text("Persistence: ");
        ImGui.sliderFloat("p", persistence, 0, 1);
        ImGui.text("Frequency: ");
        ImGui.sliderFloat("f", frequency, 0, 1);
        ImGui.text("Amplitude: ");
        ImGui.sliderFloat("a", amplitude, 0, 1);
        ImGui.text("Octaves: ");
        ImGui.sliderFloat("o", octaves, 0, 1);
        ImGui.separator();
        if(ImGui.button("Regenerate")){
            parent.regenerate();
        }
        ImGui.separator();
        ImGui.separator();
        ImGui.text("Press P to edit values.");
        ImGui.end();
    }

    public float getPersistence(){
        return persistence[0];
    }

    public float getAmplitude(){
        return amplitude[0];
    }

    public float getFrequency(){
        return frequency[0];
    }

    public float getOctaves(){
        return octaves[0];
    }

    @Override
    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public List<Object> getData() {
        return data;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String getTag() {
        return tag;
    }
}
