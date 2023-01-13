package edu.uoc.pacman.model.utils;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(Position other) {
        if (other == null) {
            return 0;
        }

        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public static Position add(Position p1, Position p2) {
        if (p1 == null || p2 == null) {
            throw new NullPointerException("p1 and p2 must not be null");
        }

        return new Position(p1.x + p2.x, p1.y + p2.y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof Position)) {
            return false;
        }

        Position otherPosition = (Position) other;

        return this.x == otherPosition.x && this.y == otherPosition.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("%d,%d", x, y);
    }
}
