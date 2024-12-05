package io.github.darkgr.world;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

public class Particle {
    private final Vector2d position;
    private final Vector2d velocity;

    private double mass;
    private int radius;

    private boolean movable;

    private Color color;

    public Particle(Vector2d position, double mass) {
        this(position, mass, Color.WHITE);
    }

    public Particle(Vector2d position, double mass, Color color) {
        this(position, new Vector2d(), mass, 15, color);
    }

    public Particle(Vector2d position, double mass, int size, Color color) {
        this(position, new Vector2d(), mass, size, color);
    }

    public Particle(Vector2d position, Vector2d velocity, double mass, Color color) {
        this(position, velocity, mass, 15, color);
    }

    public Particle(Vector2d position, Vector2d velocity, double mass, int size, Color color) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.color = color;
        this.radius = size;
        this.movable = true;
    }

    public void applyForce(@NotNull Vector2d force) {
        if(!movable) return;

        Vector2d acceleration = new Vector2d();
        force.div(mass, acceleration);

        velocity.add(acceleration);
    }

    public void update() {
        if(movable) position.add(velocity);
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

    public boolean isMovable() {
        return movable;
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

    public void setMovable(boolean movable) {
        this.movable = movable;
    }
}
