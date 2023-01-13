package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Dot extends MapItem implements Pickable, Scorable {
    private boolean picked;

    private static final int POINTS = 1;

    public Dot(Position position) {
        super(position, true, Sprite.DOT);
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public int getPoints() {
        return POINTS;
    }
}
