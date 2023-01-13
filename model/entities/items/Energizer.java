package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Energizer extends MapItem implements Pickable, Scorable {
    private boolean picked;
    private static final int POINTS = 5;

    public Energizer(Position position) {
        super(position, true, Sprite.ENERGIZER);
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
