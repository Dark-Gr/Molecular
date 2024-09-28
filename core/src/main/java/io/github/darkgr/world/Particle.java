package io.github.darkgr.world;

import com.badlogic.gdx.graphics.Color;
import org.joml.Vector2d;

public class Particle {
    private Vector2d position;
    private Vector2d velocity;

    private double mass;
    private int radius;

    private Color color;

    public Particle(Vector2d position, double mass) {
        this(position, mass, Color.WHITE);
    }

    public Particle(Vector2d position, double mass, Color color) {
        this(position, new Vector2d(), mass, color);
    }

    public Particle(Vector2d position, Vector2d velocity, double mass) {
        this(position, velocity, mass, Color.WHITE);
    }

    public Particle(Vector2d position, Vector2d velocity, double mass, Color color) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.color = color;
        this.radius = 10;
    }

    public void applyForce(Vector2d force) {
        Vector2d acceleration = new Vector2d();
        force.div(mass, acceleration);

        velocity.add(acceleration);
    }

    public void update() {
        position.add(velocity);
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
