package edu.uoc.pacman.model.entities.characters;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Entity;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public abstract class Character extends Entity implements Movable, Hitable {
    private Direction direction;
    private int duration;
    private boolean dead;
    private Position startPosition;
    private Level level;

    public Character(Position position, Direction direction, Sprite sprite, Level level) {
        super(position, true, sprite);

        this.direction = direction;
        this.duration = 0;
        this.dead = false;
        this.startPosition = position;
        this.level = level;
    }

    public void reset() {
        setPosition(startPosition);
        setDirection(Direction.UP);
        setDuration(0);
        setDead(false);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (direction != null) {
            this.direction = direction;
        }
    }

    protected int getDuration() {
        return duration;
    }

    protected void setDuration(int duration) {
        this.duration = duration;
    }

    private void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    private void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    protected Position getStartPosition() {
        return startPosition;
    }

    public void kill() {
        setDead(true);
    }

    public void alive() {
        setDead(false);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return getPosition().getX() + "," + getPosition().getY() + "," + getDirection().toString();
    }
}
