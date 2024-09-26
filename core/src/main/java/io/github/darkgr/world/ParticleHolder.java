package io.github.darkgr.world;

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
