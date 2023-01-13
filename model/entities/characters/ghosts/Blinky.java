package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseAggressive;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Blinky extends Ghost {
    private static final int POINTS = 400;

    public Blinky(Position startPosition, Direction direction, Behaviour behaviour, Level level) {
        super(startPosition, new Position(level.getWidth(), -1), direction, behaviour, Sprite.BLINKY, level);

        chaseBehaviour = new ChaseAggressive();
    }

    public int getPoints() {
        return POINTS;
    }
}
