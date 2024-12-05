package io.github.darkgr.world;

public class Box {
    private final double left;
    private final double right;
    private final double top;
    private final double bottom;

    public Box(double left, double right, double top, double bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }
}
