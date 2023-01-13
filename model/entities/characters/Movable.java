package edu.uoc.pacman.model.entities.characters;

import edu.uoc.pacman.model.utils.Direction;

public interface Movable {
    void move();

    void setDirection(Direction direction);
}
