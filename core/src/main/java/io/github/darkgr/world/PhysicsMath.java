package io.github.darkgr.world;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

public class PhysicsMath {
    public static final double G_FORCE = 0.2; //Math.pow(6.67430f * 10, -11);
    public static final double ELASTICITY = 0.9;

    public static Vector2d calculateGravity(@NotNull Particle p1, @NotNull Particle p2) {
        if(!p1.isMovable()) return new Vector2d();

        Vector2d distVector = new Vector2d();
        p1.getPosition().sub(p2.getPosition(), distVector);

        double distance = distVector.length();
        if(distance == 0) return new Vector2d();

        double forceMagnitude = (G_FORCE * p1.getMass() * p2.getMass() / Math.pow(distance, 2));

        return distVector.mul(forceMagnitude).div(distance);
    }

    public static void attemptCollision(@NotNull Particle p1, @NotNull Particle p2) {
        if(!p1.isMovable() && !p2.isMovable()) return;

        Vector2d collisionAxis = new Vector2d(p2.getPosition()).sub(p1.getPosition());

        double distance = collisionAxis.length();
        if(distance >= p1.getRadius() + p2.getRadius()) return;

        collisionAxis.normalize();
        Vector2d relativeVelocity = new Vector2d(p1.getVelocity()).sub(p2.getVelocity());

        double velocityAlongCollisionAxis = relativeVelocity.dot(collisionAxis);
        if(velocityAlongCollisionAxis <= 0) return;

        double totalMass = p1.getMass() + p2.getMass();
        double impulse = ((2 * velocityAlongCollisionAxis) / totalMass); // * ELASTICITY;

        if (p1.isMovable()) p1.getVelocity().sub(new Vector2d(collisionAxis).mul(impulse * p2.getMass()));
        if (p2.isMovable()) p2.getVelocity().add(new Vector2d(collisionAxis).mul(impulse * p1.getMass()));

        double overlap = p1.getRadius() + p2.getRadius() - distance - 0.75;
        Vector2d correction = collisionAxis.mul(overlap);

        if(p1.isMovable() && p2.isMovable()) {
            p1.getPosition().add(new Vector2d(correction).mul(p2.getMass() / (p1.getMass() + p2.getMass())));
            p2.getPosition().sub(new Vector2d(correction).mul(p1.getMass() / (p1.getMass() + p2.getMass())));
        }
        else if(p1.isMovable()) p1.getPosition().add(correction);
        else if(p2.isMovable()) p2.getPosition().add(correction);
    }

    public static void attemptParticleBoxCollision(@NotNull Particle particle, @NotNull Box box) {
        double radius = particle.getRadius();
        Vector2d position = particle.getPosition();
        Vector2d velocity = particle.getVelocity();

        if(position.x - radius < box.getLeft() || position.x + radius > box.getRight()) {
            velocity.x = -velocity.x * ELASTICITY;
            position.x = position.x - radius < box.getLeft() ? box.getLeft() + radius : box.getRight() - radius;
        }

        if(position.y - radius < box.getBottom() || position.y + radius > box.getTop()) {
            velocity.y = -velocity.y * ELASTICITY;
            position.y = position.y - radius < box.getBottom() ? box.getBottom() + radius : box.getTop() - radius;
        }
    }
}
