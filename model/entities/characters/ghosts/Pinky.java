package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseAmbush;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Pinky extends Ghost {
    private static final int POINTS = 300;

    public Pinky(Position startPosition, Direction direction, Behaviour behaviour, Level level) {
        super(startPosition, new Position(-1, -1), direction, behaviour, Sprite.PINKY, level);

        chaseBehaviour = new ChaseAmbush();
    }

    public int getPoints() {
        return POINTS;
    }
}
