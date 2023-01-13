package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Wall extends MapItem {
    public Wall(Position position) {
        super(position, false, Sprite.WALL);
    }
}
