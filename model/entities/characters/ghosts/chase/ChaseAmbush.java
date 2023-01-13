package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;

public class ChaseAmbush extends Object implements ChaseBehaviour {
    private static final int TILES_OFFSET = 4;

    public ChaseAmbush() {
        super();
    }

    public Position getChasePosition(Ghost ghost) {
        Position pacmanPosition = ghost.getLevel().getPacman().getPosition();
        Direction pacmanDirection = ghost.getLevel().getPacman().getDirection();

        Position chasePosition = pacmanPosition;

        switch (pacmanDirection) {
            case UP -> chasePosition.setY(chasePosition.getY() - TILES_OFFSET);
            case DOWN -> chasePosition.setY(chasePosition.getY() + TILES_OFFSET);
            case LEFT -> chasePosition.setX(chasePosition.getX() - TILES_OFFSET);
            case RIGHT -> chasePosition.setX(chasePosition.getX() + TILES_OFFSET);
        }

        return chasePosition;
    }
}
