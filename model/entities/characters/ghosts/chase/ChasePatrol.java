package edu.uoc.pacman.model.entities.characters.ghosts.chase;

import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.utils.Position;

public class ChasePatrol extends Object implements ChaseBehaviour {
    private static final int TILES_OFFSET = 2;
    private static final int VECTOR_INCREASE = 2;

    public ChasePatrol() {
        super();
    }

    public Position getChasePosition(Ghost ghost) {
//        Position pacmanPosition = ghost.getLevel().getPacman().getPosition();
//        Position firstBlinkyPosition = ghost.getLevel().getBlinky().getPosition();
//        Position targetBlinkyPosition = ghost.getLevel().getBlinky().getTargetPosition();
//        Position chasePosition = pacmanPosition;
//
//        switch (ghost.getLevel().getPacman().getDirection()) {
//            case UP -> chasePosition.setY(chasePosition.getY() - TILES_OFFSET);
//            case DOWN -> chasePosition.setY(chasePosition.getY() + TILES_OFFSET);
//            case LEFT -> chasePosition.setX(chasePosition.getX() - TILES_OFFSET);
//            case RIGHT -> chasePosition.setX(chasePosition.getX() + TILES_OFFSET);
//        }
//
//        if (ghost.getLevel().getBlinky().getTargetPosition().equals(ghost.getLevel().getBlinky().getFirstPosition())) {
//            ghost.getLevel().getBlinky().setTargetPosition(targetBlinkyPosition);
//        } else {
//            ghost.getLevel().getBlinky().setTargetPosition(firstBlinkyPosition);
//        }
//
//        return chasePosition;

        return ghost.getPosition();
    }
}
