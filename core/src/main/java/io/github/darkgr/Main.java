package io.github.darkgr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import imgui.*;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.github.darkgr.gui.InspectorGUI;
import io.github.darkgr.world.Particle;
import io.github.darkgr.world.ParticleHolder;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    public static final ParticleHolder particleHolder = new ParticleHolder();

    private final InspectorGUI inspectorGUI = new InspectorGUI();

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

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

        particleHolder.addParticle(new Particle(new Vector2f(500, 500), new Vector2f(1.5f, 0), 5));
        particleHolder.addParticle(new Particle(new Vector2f(600, 600), 200));

        particleHolder.selectParticle(particleHolder.getParticles().get(0));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        particleHolder.updateParticles();

        for(Particle particle : particleHolder.getParticles()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(particle.getColor());
            shapeRenderer.circle(particle.getPosition().x, particle.getPosition().y, 10);
            shapeRenderer.end();
        }

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        ImGui.begin("Window Docking Space",
            ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus |
                ImGuiWindowFlags.NoBackground
        );

        ImGui.setWindowSize(new ImVec2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        ImGui.setWindowPos(new ImVec2(0, 0));

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
        shapeRenderer.dispose();
        imGuiGlfw.shutdown();
        imGuiGl3.shutdown();
        ImGui.destroyContext();
    }
}
