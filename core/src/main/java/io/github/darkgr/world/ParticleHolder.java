package io.github.darkgr.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import imgui.ImGui;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class ParticleHolder {
    private final List<Particle> particles;
    private final List<Box> boxes;

    private Particle selected;

    public ParticleHolder() {
        this.particles = new ArrayList<>();
        this.boxes = new ArrayList<>();
    }

    public void updateParticles(double deltaTime) {
        for(int i = 0; i < particles.size(); i++) {
//            if(!particles.get(i).isMovable()) continue;

            for (int j = i + 1; j < particles.size(); j++)
                PhysicsMath.attemptCollision(particles.get(i), particles.get(j));
        }

        for(Particle particle : particles) {
            for(Box box : boxes)
                PhysicsMath.attemptParticleBoxCollision(particle, box);
        }

        for(Particle p1 : particles) {
            if(!p1.isMovable()) continue;
            Vector2d totalForce = new Vector2d();

            for(Particle p2 : particles) {
                if(p2 != p1)
                    totalForce.sub(PhysicsMath.calculateGravity(p1, p2));
            }

            p1.applyForce(totalForce.mul(deltaTime));
        }

        for(Particle p : particles)
            p.update();
    }

    public void checkForClickedParticle(OrthographicCamera camera) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.selectParticle(null);
            return;
        }

        if(ImGui.isAnyItemHovered() || ImGui.isAnyItemActive() || ImGui.isAnyItemFocused()) return;
        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) return;

        Vector3 worldPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector2d mouseWorldPos = new Vector2d(worldPos.x, worldPos.y);

        boolean found = false;

        for(Particle p : particles) {
            if(isPositionInsideParticle(mouseWorldPos, p)) {
                selectParticle(p);
                found = true;
                break;
            }
        }

        if(!found)
            selectParticle(null);
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

    public void addBox(Box box) {
        if(!this.boxes.contains(box))
            this.boxes.add(box);
    }

    public List<Particle> getParticles() {
        return new ArrayList<>(particles);
    }

    public List<Box> getBoxes() {
        return new ArrayList<>(boxes);
    }

    public Particle getSelected() {
        return selected;
    }
}
