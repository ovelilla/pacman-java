package edu.uoc.pacman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import edu.uoc.pacman.controller.Game;
import edu.uoc.pacman.model.entities.characters.ghosts.Behaviour;
import edu.uoc.pacman.model.entities.characters.pacman.Pacman;
import edu.uoc.pacman.model.entities.characters.pacman.State;
import edu.uoc.pacman.model.entities.items.MapItem;
import edu.uoc.pacman.model.exceptions.LevelException;

import java.io.IOException;
import java.util.Iterator;

public class GameScreen implements Screen {

    private final PacmanGame game;
    private final Game gameController;

    boolean loaded;

    public GameScreen(PacmanGame game) throws IOException, LevelException {
        this.game = game;
        gameController = game.getGameController();
        loaded = true;
        gameController.nextLevel();
        Gdx.input.setInputProcessor(new UserInputProcessor(gameController));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameController.isFinished() && !gameController.hasLost()) {

            if (loaded) {
                gameController.update();

                Iterator<MapItem> itr = gameController.getItemMapListIterator();
                game.batch.begin();

                Color c = game.batch.getColor();

                game.font.draw(game.batch, "Level " + gameController.getCurrentLevel(), 25, 160);
                game.font.draw(game.batch, "Points " + gameController.getLevelScore(), 25, 120);
                game.font.draw(game.batch, "Lives " + gameController.getNumLives(), 25, 90);
                game.font.draw(game.batch, "Score " + gameController.getScore(), 25, 50);

                while (itr.hasNext()) {
                    MapItem item = itr.next();
                    game.batch.setColor(c.r, c.g, c.b, 1f);
                    game.drawImage(item.getSprite(),
                            item.getPosition().getX(),
                            item.getPosition().getY()
                    );
                }

                Pacman pacman = gameController.getPacman();


                if (pacman.getState() == State.INVINCIBLE) {
                    game.batch.setColor(c.r, c.g, c.b, 0.3f);
                } else {
                    game.batch.setColor(c.r, c.g, c.b, 1f);
                }


                game.drawImage(pacman.getSprite(),
                        pacman.getPosition().getX(),
                        pacman.getPosition().getY());

                //Ghost's status in graphic mode

                gameController.getGhosts().forEach(ghost -> {
                    if (ghost.getBehaviour() == Behaviour.INACTIVE) {
                        game.batch.setColor(c.r, c.g, c.b, 0.3f);
                    } else if (ghost.getBehaviour() == Behaviour.FRIGHTENED) {
                        game.batch.setColor(Color.BLUE); //blue
                    } else {
                        game.batch.setColor(c.r, c.g, c.b, 1f); //scatter, normal
                    }
                    game.drawImage(ghost.getSprite(),
                            ghost.getPosition().getX(),
                            ghost.getPosition().getY());
                });

                game.batch.setColor(Color.WHITE);

                game.batch.end();

                if (gameController.isLevelCompleted()) {
                    game.setScreen(new InterLevelsScreen(game));
                }
            }
        } else {
            game.setScreen(new GameOverScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {

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
