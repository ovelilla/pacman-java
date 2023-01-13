package edu.uoc.pacman.model.entities.characters.ghosts;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.items.*;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;

/**
 * Ghost Simple Factory class.
 *
 * @author David García Solórzano
 * @version 1.0
 */
public abstract class GhostFactory {

    /**
     * Returns a new {@link Ghost} object.
     *
     * @param x              Column of the coordinate/position in which the item is in the board.
     * @param y              Row of the coordinate/position in which the item is in the board.
     * @param ghostClassName String value of the {@link MapItem} enumeration that corresponds to the item of the map.
     * @param direction      Direction which the ghost faces in the beginning.
     * @param behaviour      Current behaviour of the ghost.
     * @param level          Reference to the {@link Level} object.
     * @return {@link MapItem} object.
     * @throws IllegalStateException When a wrong symbol is used as an argument.
     */
    public static Ghost getGhostInstance(int x, int y, String ghostClassName, Direction direction, Behaviour behaviour,
                                         Level level) throws IllegalStateException {
        Position position = new Position(x, y);

        return switch (ghostClassName.toLowerCase()) {
            case "blinky" -> new Blinky(position, direction, behaviour, level);
            case "pinky" -> new Pinky(position, direction, behaviour, level);
            case "inky" -> new Inky(position, direction, behaviour, level);
            case "clyde" -> new Clyde(position, direction, behaviour, level);
            default -> throw new IllegalStateException("Unexpected value: " + ghostClassName.toUpperCase());
        };

    }
}
