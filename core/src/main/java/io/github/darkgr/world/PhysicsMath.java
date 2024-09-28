package io.github.darkgr.world;

import org.joml.Vector2d;

public class PhysicsMath {
    public static final double G_FORCE = 1; //Math.pow(6.67430f * 10, -11);

    public static Vector2d calculateGravity(Particle p1, Particle p2) {
        Vector2d distVector = new Vector2d();
        p1.getPosition().sub(p2.getPosition(), distVector);

        double distance = distVector.length();

        if(distance == 0)
            return new Vector2d();

        double forceMagnitude = (G_FORCE * p1.getMass() * p2.getMass() / Math.pow(distance, 2));

        return distVector.mul(forceMagnitude).div(distance);
    }
}
