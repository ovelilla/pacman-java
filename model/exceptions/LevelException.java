package edu.uoc.pacman.model.exceptions;

public class LevelException extends Exception {
    public static final String SIZE_ERROR = "[ERROR] Board's size must be greater than 8!!";
    public static final String PICKABLE_ERROR = "[ERROR] There must be one pickable item (i.e. dot or energizer) at least!!";
    public static final String GHOSTS_ERROR = "[ERROR] This level does not contain any ghost!!";
    public static final String PARSING_LEVEL_FILE_ERROR = "[ERROR] There was an error while loading the current level file!!";

    public LevelException(String msg) {
        super(msg);
    }
}
