package edu.uoc.pacman.view;

import com.badlogic.gdx.InputAdapter;
import edu.uoc.pacman.controller.Game;
import edu.uoc.pacman.model.utils.Direction;

public class UserInputProcessor extends InputAdapter {

    Game gameController;

    public UserInputProcessor(Game gameController) {
        this.gameController = gameController;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        gameController.setPacmanDirection(Direction.getDirectionByKeyCode(keycode));
        return false;
    }

}
