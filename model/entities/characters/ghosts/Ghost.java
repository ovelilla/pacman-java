package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseBehaviour;
import edu.uoc.pacman.model.entities.characters.ghosts.chase.ChaseCoward;
import edu.uoc.pacman.model.entities.characters.pacman.State;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

import java.util.Objects;

public abstract class Ghost extends Character implements Scorable {
    private Behaviour behaviour;
    private Position scatterPosition;
    protected ChaseBehaviour chaseBehaviour;

    protected Ghost(Position startPosition, Position scatterPosition, Direction direction, Behaviour behaviour, Sprite sprite, Level level) {
        super(startPosition, direction, sprite, level);

        this.behaviour = behaviour;
        this.scatterPosition = scatterPosition;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        if (behaviour != null) {
            this.behaviour = behaviour;
        }
    }

    private void nextBehaviour() {
        switch (behaviour) {
            case CHASE -> setBehaviour(Behaviour.SCATTER);
            case FRIGHTENED, INACTIVE, SCATTER -> setBehaviour(Behaviour.CHASE);
        }
    }

    public void reset() {
        super.reset();
        setBehaviour(Behaviour.INACTIVE);
        setDirection(Direction.UP);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof Ghost)) {
            return false;
        }

        Ghost other = (Ghost) o;

        return Objects.equals(this.isDead(), other.isDead()) &&
                Objects.equals(this.behaviour, other.behaviour) &&
                Objects.equals(this.getDirection(), other.getDirection()) &&
                Objects.equals(this.getPosition(), other.getPosition()) &&
                Objects.equals(this.getDuration(), other.getDuration());
    }

    @Override
    public String toString() {
        return String.format("%s,%s", super.toString(), behaviour);
    }

    public Position getScatterPosition() {
        return scatterPosition;
    }

    private void setScatterPosition(Position scatterPosition) {
        this.scatterPosition = scatterPosition;
    }

    private Position getTargetPosition() {
        return switch (behaviour) {
            case CHASE -> chaseBehaviour.getChasePosition(this);
            case FRIGHTENED, SCATTER -> getScatterPosition();
            default -> null;
        };
    }

    public void move() {
        // Moves the ghost according to the game rules.
        // If the targetPosition is null (e.g. because ghost's behavior is INACTIVE}, then the ghost does not move.
        // This method calculates the euclidean distance of the 4 potential positions and choose the one with the smallest distance
        // The new position is the one that meets the all three requirements below:
        // - It has the smallest distance to the targetPosition.
        // - It is pathable, and
        // - Its direction is not opposite to the current one.
        // - If two or more directions have the same distance to the targetPosition, then the new position will be the last direction in the enum Direction.
        // In addition to set the new position, this method sets the direction and invokes the hit method in order to check if the ghost hits Pacman in the new position.
        // In any case, this method invokes nextBehaviour.
        // Hint: Use Double.MAX_VALUE.
    }

    public boolean hit() {
        Position ghostPosition = getPosition();
        Position pacmanPosition = getLevel().getPacman().getPosition();

        if (ghostPosition.equals(pacmanPosition)) {
            if (behaviour == Behaviour.FRIGHTENED) {
                kill();
            } else if (behaviour != Behaviour.INACTIVE && getLevel().getPacman().getState() == State.NORMAL) {
                getLevel().getPacman().kill();
            }
            return true;
        }

        return false;
    }

    public void kill() {
        setBehaviour(Behaviour.INACTIVE);
        super.kill();
        getLevel().addPoints(getPoints());
    }
}
