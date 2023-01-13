package edu.uoc.pacman.model.entities.items;

import edu.uoc.pacman.model.utils.Position;

/**
 * Entity Simple Factory class.
 *
 * @author David García Solórzano
 * @version 1.0
 */
public abstract class MapItemFactory {

    /**
     * Returns a new {@link MapItem} object.
     *
     * @param x             Column of the coordinate/position in which the item is in the board.
     * @param y             Row of the coordinate/position in which the item is in the board.
     * @param itemMapSymbol String value of the {@link MapItem} enumeration that corresponds to the item of the map.
     * @return {@link MapItem} object.
     * @throws IllegalStateException When a wrong symbol is used as an argument.
     */
    public static MapItem getItemMapInstance(int x, int y, char itemMapSymbol) throws IllegalStateException {
        Position position = new Position(x, y);

        return switch (Character.toLowerCase(itemMapSymbol)) {
            case ' ' -> new Path(position);
            case '#' -> new Wall(position);
            case '.' -> new Dot(position);
            case 'o' -> new Energizer(position);
            case 'l' -> new Life(position);
            default -> throw new IllegalStateException("Unexpected value: " + Character.toLowerCase(itemMapSymbol));
        };

    }
}
