package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Entity;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public abstract class MapItem extends Entity {
    protected MapItem(Position position, boolean pathable, Sprite sprite) {
        super(position, pathable, sprite);
    }
}
