package io.github.darkgr.world;

import com.badlogic.gdx.graphics.Color;
import org.joml.Vector2f;

public class Particle {
    private Vector2f position;
    private Vector2f velocity;

    private float mass;
    private int radius;

    private Color color;

    public Particle(Vector2f position, float mass) {
        this(position, mass, Color.WHITE);
    }

    public Particle(Vector2f position, float mass, Color color) {
        this(position, new Vector2f(), mass, color);
    }

    public Particle(Vector2f position, Vector2f velocity, float mass) {
        this(position, velocity, mass, Color.WHITE);
    }

    public Particle(Vector2f position, Vector2f velocity, float mass, Color color) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.color = color;
        this.radius = 10;
    }

    public void applyForce(Vector2f force) {
        Vector2f acceleration = new Vector2f();
        force.div(mass, acceleration);

        velocity.add(acceleration);
    }

    public void update() {
        position.add(velocity);
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public float getMass() {
        return mass;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
