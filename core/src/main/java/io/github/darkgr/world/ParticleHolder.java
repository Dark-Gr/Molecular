package io.github.darkgr.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import org.joml.Vector2d;

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
            Vector2d totalForce = new Vector2d();

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
        Vector2d mouseWorldPos = new Vector2d(worldPos.x, worldPos.y);

        for(Particle p : particles) {
            if(isPositionInsideParticle(mouseWorldPos, p)) {
                selectParticle(p);
                break;
            }
        }
    }

    private boolean isPositionInsideParticle(Vector2d position, Particle particle) {
        Vector2d distanceVector = new Vector2d();
        position.sub(particle.getPosition(), distanceVector);

        double distance = distanceVector.length();

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
