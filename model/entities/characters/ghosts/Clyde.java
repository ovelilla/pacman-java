package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseCoward;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Clyde extends Ghost {
    private static final int POINTS = 100;

    public Clyde(Position startPosition, Direction direction, Behaviour behaviour, Level level) {
        super(startPosition, new Position(-1, level.getHeight()), direction, behaviour, Sprite.CLYDE, level);

        chaseBehaviour = new ChaseCoward();
    }

    public int getPoints() {
        return POINTS;
    }
}
