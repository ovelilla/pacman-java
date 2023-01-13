package edu.uoc.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import edu.uoc.pacman.model.exceptions.LevelException;

import java.io.IOException;

public class GameOverScreen implements Screen {

    final PacmanGame game;

    OrthographicCamera camera;

    public GameOverScreen(PacmanGame game) {
        this.game = game;
        game.getGameController().reset();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.2f, 1);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.getData().setScale(2.0f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new WelcomeScreen(game));
        }
        game.batch.draw(PacmanGame.assetManager.get(PacmanGame.logoAsset), 0, 0, PacmanGame.WINDOW_WIDTH, PacmanGame.WINDOW_HEIGHT);
        game.font.draw(game.batch, "Game Over!", (int) (PacmanGame.WINDOW_WIDTH * 0.3), PacmanGame.WINDOW_HEIGHT / 4);
        game.font.draw(game.batch, game.getGameController().getScore()+" points", (int) (PacmanGame.WINDOW_WIDTH * 0.3), PacmanGame.WINDOW_HEIGHT / 4 + 30);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //camera.viewportWidth = width;
        //camera.viewportHeight = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }


}
