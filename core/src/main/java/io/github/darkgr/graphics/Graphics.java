package io.github.darkgr.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.darkgr.world.Particle;
import org.lwjgl.opengl.GL20;

public class Graphics {
    private static boolean initialized = false;

    private static ShapeRenderer shapeRenderer;
    private static SpriteBatch spriteBatch;

    private static Texture haloTexture;

    public static void init() {
        if(initialized) return;

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        haloTexture = new Texture(Gdx.files.internal("halo.png"));

        initialized = true;
    }

    private static void throwIfNotInitialized() {
        if(!initialized)
            throw new RuntimeException("Attempt to call render function without initializing graphics");
    }

    public static void update(OrthographicCamera camera) {
        throwIfNotInitialized();
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public static void renderParticle(Particle particle) {
        throwIfNotInitialized();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(particle.getColor());
        shapeRenderer.circle((float) particle.getPosition().x, (float) particle.getPosition().y, particle.getRadius());
        shapeRenderer.end();
    }

    public static void renderParticleGlow(Particle particle) {
        throwIfNotInitialized();

        float haloSize = particle.getRadius() * 4.5f;
        float x = (float) particle.getPosition().x - haloSize / 2;
        float y = (float) particle.getPosition().y - haloSize / 2;

        spriteBatch.begin();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.draw(haloTexture, x, y, haloSize, haloSize);
        spriteBatch.end();
    }
}
