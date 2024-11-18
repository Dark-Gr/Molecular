package io.github.darkgr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import imgui.*;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.github.darkgr.graphics.Graphics;
import io.github.darkgr.gui.InspectorGUI;
import io.github.darkgr.gui.MainMenuBar;
import io.github.darkgr.world.Box;
import io.github.darkgr.world.Particle;
import io.github.darkgr.world.ParticleHolder;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    private boolean paused;

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    public static final ParticleHolder particleHolder = new ParticleHolder();

    private final InspectorGUI inspectorGUI = new InspectorGUI();
    private final MainMenuBar mainMenuBar = new MainMenuBar();

    private long lastFrameTime;

    @Override
    public void create() {
        GL.createCapabilities();

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();

        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);

        long windowHandle = GLFW.glfwGetCurrentContext();
        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init("#version 130");

        ImFontAtlas fontAtlas = io.getFonts();
        fontAtlas.addFontFromFileTTF("./Font.ttf", 20.0f);

        io.getFonts().build();

        ImGuiStyle style = ImGui.getStyle();

        style.setWindowPadding(0, 0);

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();

        Graphics.init();

        particleHolder.addBox(new Box(300, 1200, 900, 200));

        for(int i = 0; i < 12; i++) {
            Color color = new Color(
                (float) Math.random(),
                (float) Math.random(),
                (float) Math.random(),
                1
            );

            double mass = Math.floor(Math.random() * 50);

            particleHolder.addParticle(new Particle(new Vector2d(400 + i * 50, 600 - (Math.floor(Math.random() * 200))), mass, color));
        }

//        particleHolder.addParticle(new Particle(new Vector2d(500, 800), new Vector2d(0.5, 0), 10, new Color(1f, 0, 0, 1f)));
//        particleHolder.addParticle(new Particle(new Vector2d(700, 800), new Vector2d(-0.5, 0), 50, new Color(0, 1f, 0, 1f)));
//
//        particleHolder.addParticle(new Particle(new Vector2d(500, 500), new Vector2d(0, 1), 10, new Color(1f, 1f, 0, 1f)));
//        particleHolder.addParticle(new Particle(new Vector2d(800, 500), 20, new Color(0, 1f, 1f, 1f)));
//        particleHolder.addParticle(new Particle(new Vector2d(1100, 500), new Vector2d(0, -1), 10, new Color(0, 0, 1f, 1f)));
//
//        particleHolder.getParticles().get(1).setRadius(20);
//        particleHolder.selectParticle(particleHolder.getParticles().get(0));

        lastFrameTime = System.currentTimeMillis();
    }

    @Override
    public void render() {
        double deltaTime = System.currentTimeMillis() - lastFrameTime;
        lastFrameTime = System.currentTimeMillis();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        Graphics.update(camera);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            paused = !paused;

        if(!paused)
            particleHolder.updateParticles(deltaTime);

        particleHolder.checkForClickedParticle(camera);

        for(Box box : particleHolder.getBoxes())
            Graphics.renderBox(box);

        if(particleHolder.getSelected() != null)
            Graphics.renderParticleGlow(particleHolder.getSelected());

        for(Particle particle : particleHolder.getParticles())
            Graphics.renderParticle(particle);

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        mainMenuBar.process();

        ImGui.begin("Window Docking Space",
            ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus |
                ImGuiWindowFlags.NoBackground
        );

        ImGui.setWindowSize(new ImVec2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - MainMenuBar.BAR_HEIGHT));
        ImGui.setWindowPos(new ImVec2(0, MainMenuBar.BAR_HEIGHT));

        int dockSpace = ImGui.getID("Docker");
        ImGui.dockSpace(dockSpace, 0, 0, ImGuiDockNodeFlags.PassthruCentralNode);

        ImGui.end();

        inspectorGUI.process();

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Graphics.dispose();
        imGuiGlfw.shutdown();
        imGuiGl3.shutdown();
        ImGui.destroyContext();
    }
}
