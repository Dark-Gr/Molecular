package io.github.darkgr.world;

import org.joml.Vector2f;

public class PhysicsMath {
    public static final float G_FORCE = 1;

    public static Vector2f calculateGravity(Particle p1, Particle p2) {
        Vector2f distVector = new Vector2f();
        p1.getPosition().sub(p2.getPosition(), distVector);

        float distance = distVector.length();

        if(distance == 0)
            return new Vector2f();

        float forceMagnitude = (float) (G_FORCE * p1.getMass() * p2.getMass() / Math.pow(distance, 2));

        return distVector.mul(forceMagnitude).div(distance);
    }
}
