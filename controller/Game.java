package edu.uoc.pacman.controller;

import edu.uoc.pacman.model.Level;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.entities.characters.ghosts.Ghost;
import edu.uoc.pacman.model.entities.items.MapItem;
import edu.uoc.pacman.model.exceptions.LevelException;
import edu.uoc.pacman.model.utils.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Controller class of the game. It is the middleware (or bridge) between the model and view classes.
 * <br/>
 * This class is called from the view classes in order to access/modify the model data.
 * This class controls the logic of the game.
 *
 * @author David García-Solórzano
 * @version 1.0
 */
public class Game {

    /**
     * Number of lives which each level has when it starts.
     */
    private static final int NUM_LIVES = 3;

    /**
     * Name of the folder in which level files are
     */
    private String fileFolder;

    /**
     * Number of the current level.
     */
    private int currentLevel = 0;

    /**
     * Maximum amount of levels that the game has.
     */
    private final int maxLevels;

    /**
     * Total score of the game, i.e. the sum of the levels' scores.
     */
    private int score;

    /**
     * Level object that contains the information of the current level.
     */
    private Level level;


    /**
     * Constructor with argument.
     *
     * @param fileFolder Folder name where the configuration/level files are.
     * @throws IOException When there is a problem while retrieving number of levels
     */
    public Game(String fileFolder) throws IOException {
        int num;

        setFileFolder(fileFolder);

        //Get the number of files that are in the fileFolder, i.e. the number of levels.
        URL url = getClass().getClassLoader().getResource(getFileFolder());

        URLConnection urlConnection = Objects.requireNonNull(url).openConnection();

        if (urlConnection instanceof JarURLConnection) {
            //run in jar
            String path = null;
            try {
                path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            } catch (URISyntaxException e) {
                System.out.println("ERROR: Game Constructor");
                e.printStackTrace();
                System.exit(-1);
            }

            URI uri = URI.create("jar:file:" + path);

            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                num = (int) Files.walk(fs.getPath(getFileFolder()))
                        .filter(Files::isRegularFile).count();
            }
        } else {
            //run in ide
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream;
            inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(getFileFolder()));

            try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {
                num = (int) reader.lines().count();
            }
        }

        setScore(0);

        maxLevels = num;
    }

    /**
     * Setter of the attribute {@code fileFolder}.
     *
     * @param fileFolder Folder name where the configuration/level files are.
     */
    private void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
    }

    /**
     * Gets an Iterator of the {@code itemMapList}
     * @return Iterator of the {@code itemMapList}
     */
    public Iterator<MapItem> getItemMapListIterator() {
        return level.getMapItemListIterator();
    }


    /**
     * Getter of the attribute {@code fileFolder}.
     *
     * @return Value of the attribute {@code fileFolder}.
     */
    private String getFileFolder() {
        return fileFolder;
    }


    /**
     * Gets the current score of the level that the player is playing.
     * @return Current score of the current level.
     */
    public int getLevelScore(){
        return level.getScore();
    }

    /**
     * Getter of the attribute "score" (Game score).
     *
     * @return Value of the attribute "score"
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter of the attribute "score" (Game score).
     *
     * @param score New value for the attribute "score"
     */
    private void setScore(int score) {
        this.score = score;
    }

    /**
     * Gests the @link Pacman} object which is present in the current level.
     *
     * @return Reference to the {@link Pacman} object in the current level.
     */
    public Pacman getPacman() {
        return level.getPacman();
    }

    /**
     * Gets the list of Ghosts which are present in the level.
     *
     * @return List which contains all the ghosts in the current live.
     */
    public List<Ghost> getGhosts() {
        return level.getGhostList();
    }

    /**
     * Returns the number of lives that the player has in the current level.
     *
     * @return Number of lives in the current level.
     */
    public int getNumLives() {
        return level.getLives();
    }

    /**
     * Indicates if the game is finished ({@code true}) or not ({@code false}).
     * <p>The game is finished when the attribute {@code currentLevel} is equal to attribute {@code maxLevels} + 1.
     * </p>
     *
     * @return {@code true} if there are no more levels and therefore the game is finished. Otherwise, {@code false}.
     */
    public boolean isFinished() {
        return getCurrentLevel() == maxLevels + 1;
    }

    /**
     * Getter of the attribute {@code currentLevel}.
     *
     * @return Value of the attribute {@code currentLevel} that indicates which level the player is playing.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Checks if there is a new level to play and loads it.<br/>
     * If the game is finished, it returns {@code false}. Otherwise, it returns {@code true}.<br/>
     * The game score must be updated when a level is finished.
     * Thus, when the player is playing the first level, game's score is zero.
     *
     * @return {@code true} if there is a next level, and it has been loaded correctly. Otherwise, it returns {@code false}.
     * @throws LevelException When there is a level exception/problem loading the new level.
     */
    public boolean nextLevel() throws LevelException {

        if (!isFinished()) {
            if (getCurrentLevel() == 0) {
                setScore(0);
            } else {
                setScore(getScore() + level.getScore());
            }
            currentLevel++;
            loadLevel();
            return true;
        } else {
            setScore(getScore() + level.getScore());
            return false;
        }
    }

    /**
     * Loads a new level by using the value of the attribute {@code currentLevel}.
     * <p>
     * The pattern of the filename is: fileFolder+"level" + numberLevel + ".txt".
     * </p>
     *
     * @throws LevelException When there is a level exception/problem.
     */
    private void loadLevel() throws LevelException {
        level = new Level(getFileFolder() + "level" + currentLevel + ".txt", NUM_LIVES);
    }

    /**
     * Checks if the level is completed, i.e. the player has collected all the dots and energizers of the map.
     *
     * @return {@code true} if this level is beaten, otherwise {@code false}.
     */
    public boolean isLevelCompleted() {
        return level.hasWon();
    }

    /**
     * Checks if the player has lost, i.e. the number of lives is zero.
     *
     * @return {@code true} if this the player has lost, otherwise {@code false}.
     */
    public boolean hasLost() {
        return level.getLives() == 0;
    }

    /**
     * Reloads the current level, i.e. load the level again.
     *
     * @throws LevelException When there is a level exception/problem.
     */
    public void reload() throws LevelException {
        loadLevel();
    }

    /**
     * This method allows us to change Pacman's direction according to the user input (i.e. the pressed key).
     *
     * @param direction New direction to set to Pacman.
     */
    public void setPacmanDirection(Direction direction) {
        level.getPacman().setDirection(direction);
    }

    /**
     * Resets the game so that it starts again.
     */
    public void reset(){
        setScore(0);
        currentLevel = 0;
    }

    /**
     * Update the game, namely the level.
     */
    public void update() {
        level.update();
    }

}
