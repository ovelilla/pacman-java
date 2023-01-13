package edu.uoc.pacman.view;

//https://libgdx.com/wiki/app/starter-classes-and-configuration

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.uoc.pacman.model.utils.Sprite;

import java.io.IOException;

public class PacmanGame extends Game {

    public static final int WINDOW_WIDTH = 915;
    public static final int WINDOW_HEIGHT = 768;

    SpriteBatch batch;

    edu.uoc.pacman.controller.Game gameController;

    public static AssetDescriptor<Texture> pacmanRightAsset,
            pacmanLeftAsset, pacmanUpAsset, pacmanDownAsset,
            blinkyAsset, inkyAsset, pinkyAsset, clydeAsset,
            dotAsset, energizerAsset, wallAsset, pathAsset,
            lifeAsset, logoAsset;

    public static AssetManager assetManager;

    BitmapFont font;

    public static final int CELL_SIZE = 35;

    @Override
    public void create(){
        try {
            gameController = new edu.uoc.pacman.controller.Game("levels/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        font = new BitmapFont();
        loadImages();
        assetManager.finishLoading();
        this.setScreen(new WelcomeScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    public void loadImages() {
        pacmanUpAsset = new AssetDescriptor<>(Sprite.PACMAN_UP.getImageSrc(), Texture.class);
        pacmanRightAsset = new AssetDescriptor<>(Sprite.PACMAN_RIGHT.getImageSrc(), Texture.class);
        pacmanDownAsset = new AssetDescriptor<>(Sprite.PACMAN_DOWN.getImageSrc(), Texture.class);
        pacmanLeftAsset = new AssetDescriptor<>(Sprite.PACMAN_LEFT.getImageSrc(), Texture.class);
        blinkyAsset = new AssetDescriptor<>(Sprite.BLINKY.getImageSrc(), Texture.class);
        pinkyAsset = new AssetDescriptor<>(Sprite.PINKY.getImageSrc(), Texture.class);
        inkyAsset = new AssetDescriptor<>(Sprite.INKY.getImageSrc(), Texture.class);
        clydeAsset = new AssetDescriptor<>(Sprite.CLYDE.getImageSrc(), Texture.class);
        dotAsset = new AssetDescriptor<>(Sprite.DOT.getImageSrc(), Texture.class);
        energizerAsset = new AssetDescriptor<>(Sprite.ENERGIZER.getImageSrc(), Texture.class);
        wallAsset = new AssetDescriptor<>(Sprite.WALL.getImageSrc(), Texture.class);
        pathAsset = new AssetDescriptor<>(Sprite.PATH.getImageSrc(), Texture.class);
        lifeAsset = new AssetDescriptor<>(Sprite.LIFE.getImageSrc(), Texture.class);

        logoAsset = new AssetDescriptor<>("images/pacman_logo.png", Texture.class);

        assetManager.load(pacmanUpAsset);
        assetManager.load(pacmanRightAsset);
        assetManager.load(pacmanDownAsset);
        assetManager.load(pacmanLeftAsset);
        assetManager.load(blinkyAsset);
        assetManager.load(inkyAsset);
        assetManager.load(pinkyAsset);
        assetManager.load(clydeAsset);
        assetManager.load(dotAsset);
        assetManager.load(energizerAsset);
        assetManager.load(wallAsset);
        assetManager.load(pathAsset);
        assetManager.load(lifeAsset);
        assetManager.load(logoAsset);

    }

    AssetDescriptor<Texture> getAssertBySprite(Sprite sprite) {
        return switch (sprite) {
            case PACMAN_RIGHT -> pacmanRightAsset;
            case PACMAN_LEFT -> pacmanLeftAsset;
            case PACMAN_UP -> pacmanUpAsset;
            case PACMAN_DOWN -> pacmanDownAsset;
            case BLINKY -> blinkyAsset;
            case PINKY -> pinkyAsset;
            case INKY -> inkyAsset;
            case CLYDE -> clydeAsset;
            case DOT -> dotAsset;
            case ENERGIZER -> energizerAsset;
            case WALL -> wallAsset;
            case PATH -> pathAsset;
            case LIFE -> lifeAsset;
        };
    }

    void drawImage(Sprite sprite, int x, int y) {
        batch.draw(PacmanGame.assetManager.get(getAssertBySprite(sprite)),
                x * PacmanGame.CELL_SIZE,
                (WINDOW_HEIGHT - (y * PacmanGame.CELL_SIZE) - PacmanGame.CELL_SIZE),
                CELL_SIZE, CELL_SIZE);
    }

    public edu.uoc.pacman.controller.Game getGameController(){
        return this.gameController;
    }
}
