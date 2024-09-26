package io.github.darkgr.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class ParticleHolder {
    private final List<Particle> particles;
    private Particle selected;

    public ParticleHolder() {
        this.particles = new ArrayList<>();
    }

    public void updateParticles() {
        for(Particle p1 : particles) {
            Vector2f totalForce = new Vector2f();

            for(Particle p2 : particles) {
                if(p2 != p1)
                    totalForce.sub(PhysicsMath.calculateGravity(p1, p2));
            }

            p1.applyForce(totalForce);
            p1.update();
        }
    }

    public void checkForClickedParticle(OrthographicCamera camera) {
        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) return;

        Vector3 worldPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2f mouseWorldPos = new Vector2f(worldPos.x, worldPos.y);

        for(Particle p : particles) {
            if(isPositionInsideParticle(mouseWorldPos, p)) {
                selectParticle(p);
                break;
            }
        }
    }

    private boolean isPositionInsideParticle(Vector2f position, Particle particle) {
        Vector2f distanceVector = new Vector2f();
        position.sub(particle.getPosition(), distanceVector);

        float distance = distanceVector.length();

        return distance <= particle.getRadius();
    }

    public void selectParticle(Particle particle) {
        this.selected = particle;
    }

    public void addParticle(Particle particle) {
        if(!particles.contains(particle))
            this.particles.add(particle);
    }

    public List<Particle> getParticles() {
        return new ArrayList<>(particles);
    }

    public Particle getSelected() {
        return selected;
    }
}
