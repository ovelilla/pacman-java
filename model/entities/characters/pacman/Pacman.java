package edu.uoc.pacman.model.entities.characters.pacman;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.Character;
import edu.uoc.pacman.model.entities.items.MapItem;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;
import edu.uoc.pacman.model.utils.Sprite;

public class Pacman extends Character {
    private State state;

    public Pacman(Position startPosition, Direction direction, State state, Level level) {
        super(startPosition, direction, Sprite.PACMAN_RIGHT, level);

        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (state != null) {
            this.state = state;
        }
    }

    private void nextState() {
        switch (state) {
            case EATER, INVINCIBLE -> setState(State.NORMAL);
            case NORMAL -> setState(State.EATER);
        }
    }

    public void reset() {
        super.reset();
        setState(State.INVINCIBLE);
        setDirection(Direction.UP);
    }

    public void move() {
//        Position newPosition = getNewPosition();
//        MapItem item = getLevel().getMapItem(newPosition);
//        if (item.isPathable()) {
//            setPosition(newPosition);
//            getLevel().eatItem(newPosition);
//            getLevel().addScore(item.getPoints());
//        }
//        hit();
//        nextState();
    }

    public void setDirection(Direction direction) {
        if (direction != null) {
            super.setDirection(direction);
            switch (direction) {
                case UP:
                    setSprite(Sprite.PACMAN_UP);
                    break;
                case DOWN:
                    setSprite(Sprite.PACMAN_DOWN);
                    break;
                case LEFT:
                    setSprite(Sprite.PACMAN_LEFT);
                    break;
                case RIGHT:
                    setSprite(Sprite.PACMAN_RIGHT);
                    break;
            }
        }
    }

    private void eat() {
//        Performs the eat action. This means which picks Pickable objects up and add its points to the level's score, if it is needed. Remember that some Pickable objects are Scorable ones as well.
//        If Pacman picks an energizer up, then the behaviour of all the ghosts must be FRIGHTENED. Moreover, Pacman's state must be EATER.
//        If Pacman picks a life up, then one life is increased for the current level.
//        When any Pickable objects is picked up, then it is removed from the labyrinth/map and replaced by a Path item.
    }

    public boolean hit() {
//        Checks if Pacman is in the same position which any ghost. If this happens, then checks ghost's status:
//            - If Pacman's state is INVINCIBLE, then nothing happens and this method returns false.
//            - If the ghost is FRIGHTENED, then the ghost is killed. This method returns true.
//            - If the ghost is INACTIVE, then nothing happens and this method returns false.
        return true;
    }

    public void kill() {
        super.kill();
        getLevel().decreaseLives();
        setState(State.INVINCIBLE);
    }
}
