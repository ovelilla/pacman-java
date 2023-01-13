package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;

public class ChaseCoward extends Object implements ChaseBehaviour {
    private static final int TILES_TO_CHASE = 8;
    public ChaseCoward() {
        super();
    }

    public Position getChasePosition(Ghost ghost) {
        Position pacmanPosition = ghost.getLevel().getPacman().getPosition();
        Position ghostPosition = ghost.getPosition();
        Position chasePosition = ghost.getScatterPosition();

        int distance = (int) Math.sqrt(Math.pow(pacmanPosition.getX() - ghostPosition.getX(), 2) + Math.pow(pacmanPosition.getY() - ghostPosition.getY(), 2));

        if (distance >= TILES_TO_CHASE) {
            chasePosition = pacmanPosition;
        }

        return chasePosition;

    }
}
