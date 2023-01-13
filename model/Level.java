package edu.uoc.pacman.model;

import edu.uoc.pacman.model.entities.Scorable;
import edu.uoc.pacman.model.entities.characters.ghosts.Behaviour;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.entities.characters.ghosts.Blinky;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.characters.ghosts.GhostFactory;
import edu.uoc.pacman.model.entities.characters.pacman.State;
import edu.uoc.pacman.model.entities.items.*;
import edu.uoc.pacman.model.exceptions.LevelException;
import edu.uoc.pacman.model.utils.Direction;
import edu.uoc.pacman.model.utils.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Represents a level in the game.
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class Level {
    /**
     * The minimum width that the map must have.
     */
    private static final int MIN_WIDTH = 8;
    /**
     * The minimum height that the map must have.
     */
    private static final int MIN_HEIGHT = 8;
    /**
     * The name of the level configuration file.
     */
    private String fileName;
    /**
     * The width of the map/labyrinth.
     */
    private int width;
    /**
     * The height of the map/labyrinth.
     */
    private int height;
    /**
     * Stores the items which are part of the map/labyrinth.
     */
    private List<MapItem> mapItemList;

    /**
     * Reference to {@link Pacman} object, i.e. the player.
     */
    private Pacman pacman;

    /**
     * Ghosts that are part of the level.
     */
    private List<Ghost> ghostList;

    /**
     * Level's score.
     */
    private int score;

    /**
     * Number of lives in the level.
     */
    private int lives = 3;

    /**
     * Number that allows us to manage when the actions happen. An action will be performed, when tick == UPDATE_GAME.
     */
    private int tick;

    /**
     * It is the refresh time. In other words, it is the speed of the game.
     */
    private static final int UPDATE_GAME = 30;

    /**
     * Constructor with argument.
     *
     * @param filename Value for the attribute {@code fileName}.
     * @param lives    Initial number of lives in the level.
     * @throws LevelException If there are any problems while parsing the level configuration file.
     */
    public Level(String filename, int lives) throws LevelException {
        setFileName(filename);
        setLives(lives);
        parse();
    }

    /**
     * Setter of the attribute {@code fileName}.
     *
     * @param fileName New value for the attribute {@code fileName}.
     */
    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter of the attribute {@code fileName}
     *
     * @return Current value of the attribute {@code fileName}
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Getter of the attribute {@code lives}.
     *
     * @return The value of the attribute {@code lives}.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Setter of the attribute {@code lives}.
     *
     * @param lives Number of lives to assign to the attribute {@code lives}.
     */
    private void setLives(int lives) {
        if (lives > 0) {
            this.lives = lives;
        }
    }

    /**
     * Increases 1 the number of lives.
     */
    public void increaseLives() {
        lives++;
    }

    /**
     * Decreases 1 the number of lives as long as the number of lives is not zero.
     */
    public void decreaseLives() {
        if (lives > 0) {
            lives--;
        }
    }

    /**
     * Parses/Reads level's data from the given file.<br/>
     * It also checks which the board's requirements are met.
     *
     * @throws LevelException When there is any error while parsing the file
     *                        or some board's requirement is not satisfied.
     */
    private void parse() throws LevelException {
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(getFileName()));

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            line = Objects.requireNonNull(getFirstNonEmptyLine(reader));


            setWidth(Integer.parseInt(line));

            line = Objects.requireNonNull(getFirstNonEmptyLine(reader));

            setHeight(Integer.parseInt(line));

            mapItemList = new LinkedList<>();//getWidth() * getHeight());

            for (int y = 0; y < getHeight(); y++) {
                char[] rowChar = Objects.requireNonNull(getFirstNonEmptyLine(reader)).toCharArray();
                for (int x = 0; x < getWidth(); x++) {
                    addMapItem(MapItemFactory.getItemMapInstance(x, y, rowChar[x]));
                }
            }

            //Checks if there is one Dot or Energizer item in the board at least
            if (getMapItemList().stream().noneMatch(item -> item instanceof Dot || item instanceof Energizer)) {
                throw new LevelException(LevelException.PICKABLE_ERROR);
            }

            //Read pacman and ghosts
            line = Objects.requireNonNull(getFirstNonEmptyLine(reader));

            String[] pacmanInfo = line.split(",");
            this.pacman = new Pacman(new Position(Integer.parseInt(pacmanInfo[0]),
                    Integer.parseInt(pacmanInfo[1])), Direction.valueOf(pacmanInfo[2]), State.valueOf(pacmanInfo[3]), this);


            ghostList = new ArrayList<>();

            while ((line = getFirstNonEmptyLine(reader)) != null) {
                String[] ghostInfo = line.split(",");
                ghostList.add(GhostFactory.getGhostInstance(Integer.parseInt(ghostInfo[1]),
                        Integer.parseInt(ghostInfo[2]), ghostInfo[0], Direction.valueOf(ghostInfo[3]), edu.uoc.pacman.model.entities.characters.ghosts.Behaviour.valueOf(ghostInfo[4]), this));
            }

            if (ghostList.isEmpty()) {
                throw new LevelException(LevelException.GHOSTS_ERROR);
            }

        } catch (IllegalStateException | IllegalArgumentException | IOException e) {
            throw new LevelException(LevelException.PARSING_LEVEL_FILE_ERROR + e.getMessage());
        }
    }

    /**
     * This is a helper method for {@link #parse()} which returns
     * the first non-empty and non-comment line from the reader.
     *
     * @param br BufferedReader object to read from.
     * @return First line that is a parsable line, or {@code null} there are no lines to read.
     * @throws IOException if the reader fails to read a line.
     */
    private String getFirstNonEmptyLine(final BufferedReader br) throws IOException {
        do {

            String s = br.readLine();

            if (s == null) {
                return null;
            }
            if (s.isBlank() || s.startsWith(";")) {
                continue;
            }

            return s;
        } while (true);
    }

    /**
     * Getter of the attribute {@code width}.
     *
     * @return The current value of the attribute {@code width}.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter of the attribute {@code width}.
     *
     * @param width New value for the attribute {@code width}.
     * @throws LevelException When {@code width} is less than {@code MIN_WIDTH}.
     */
    private void setWidth(int width) throws LevelException {
        if (width >= MIN_WIDTH) {
            this.width = width;
        } else {
            throw new LevelException(LevelException.SIZE_ERROR);
        }
    }

    /**
     * Getter of the attribute {@code height}.
     *
     * @return The current value of the attribute {@code height}.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter of the attribute {@code height}.
     *
     * @param height New value for the attribute {@code height}.
     * @throws LevelException When {@code height} is less than {@code MIN_HEIGHT}.
     */
    private void setHeight(int height) throws LevelException {
        if (height >= MIN_HEIGHT) {
            this.height = height;
        } else {
            throw new LevelException(LevelException.SIZE_ERROR);
        }
    }

    /**
     * Getter of the attribute {@code mapItemList}.
     *
     * @return The current value of the attribute {@code mapItemList}.
     */
    private List<MapItem> getMapItemList() {
        return mapItemList;
    }

    /**
     * Returns an Iterator of the attribute {@code mapItemList}.
     *
     * @return Iterator of the attribute {@code mapItemList}.
     */
    public Iterator<MapItem> getMapItemListIterator() {
        return mapItemList.iterator();
    }

    /**
     * Returns the map/labyrinth item which is in the position (x,y).
     *
     * @param x Value of the position in the X axis.
     * @param y Value of the position in the Y axis.
     * @return If any, the item which is the position (x,y). Otherwise, {@code null}.
     */
    public MapItem getMapItem(int x, int y) {
        return mapItemList.stream().filter(item -> item.getPosition().getX() == x && item.getPosition().getY() == y).findFirst().orElse(null);
    }

    /**
     * Returns the map/labyrinth item which is in {@code position}.
     *
     * @param position The position where the item that we want to get is.
     * @return If any, the item which is {@code position}. Otherwise, {@code null}.
     * @throws NullPointerException When {@code position} is {@code null}.
     */
    public MapItem getMapItem(Position position) throws NullPointerException {
        return getMapItem(position.getX(), position.getY());
    }

    /**
     * Adds {@code item} to the {@code mapItemList} as long as {@code item} is not {@code null}.
     *
     * @param item Item which we want to add.
     */
    public void addMapItem(MapItem item) {
        if (item != null) {
            mapItemList.add(item);
        }
    }

    /**
     * Removes {@code item} from the {@code mapItemList}.
     *
     * @param item Item which we want to remove.
     */
    public void removeMapItem(MapItem item) {
        mapItemList.remove(item);
    }

    /**
     * Getter of the attribute {@code ghostList}.
     *
     * @return The current value of the attribute {@code ghostList}.
     */
    public List<Ghost> getGhostList() {
        return ghostList;
    }

    /**
     * Getter of the attribute {@code score}.
     *
     * @return The current value of the attribute {@code score}.
     */
    public int getScore() {
        return score;
    }

    /**
     * Add the value of {@code points} to the attribute {@code score} as long as {@code points} is greater than 0.
     *
     * @param points Number to add to the attribute {@code score}.
     */
    public void addPoints(int points) {
        if (points > 0) {
            score += points;
        }
    }

    /**
     * Getter ot the attribute {@code pacman}.
     *
     * @return The current value of the attribute {@code pacman}.
     */
    public Pacman getPacman() {
        return pacman;
    }

    /**
     * Gets the first {@link Blinky} object in the Ghost list of the level.
     *
     * @return The first {@link Blinky} object in the level,
     * or {@code null} if there are not {@link Blinky} ghosts in the level.
     */
    public Blinky getBlinky() {
        return (Blinky) ghostList.stream().filter(ghost -> ghost instanceof Blinky).findFirst().orElse(null);
    }

    /**
     * Given a position, it indicates if such a position is pathable or not.
     *
     * @param position Position which must be checked.
     * @return {@code true} if the position is pathable. Otherwise, {@code false}.
     */
    public boolean isPathable(Position position) {
//        Given a position, it indicates if such a position is pathable or not.
        return getMapItem(position) == null || getMapItem(position).isPathable();
    }


    /**
     * Sets the behaviour of all the ghosts in the level to FRIGHTENED.
     */
    public void setGhostsFrightened() {
        ghostList.forEach(ghost -> ghost.setBehaviour(Behaviour.FRIGHTENED));
    }

    /**
     * Checks if the level was finished. This happens when all the objects
     * that are {@link Scorable} and {@link Pickable} have been picked.
     *
     * @return {@code true} if the level was finished/won. Otherwise, {@code false}.
     */
    public boolean hasWon() {
        return mapItemList.stream().filter(item -> item instanceof Scorable && item instanceof Pickable).count() == 0;
    }

    /**
     * Refreshes/Updates the game when needed.
     */
    public void update() {

        if (tick == UPDATE_GAME) {
            pacman.move();

            ghostList.forEach(ghost -> {
                if (ghost.isDead()) {
                    ghost.reset();
                }
                ghost.move();
            });

            if (pacman.isDead()) {
                pacman.reset();
                ghostList.forEach(ghost -> {
                    ghost.reset();
                });
            }

            tick = 0;
        }

        tick++;
    }
}
