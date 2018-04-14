package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Barricade on 13/04/2018.
 */

public class EditorScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private BitmapFont bitmapFont;

    private World world;
    private Rectangle[] chemin;
    private Cell[] cells;

    private final TowerDefenseGame towerDefenseGame;


    public EditorScreen(TowerDefenseGame towerDefenseGame) {
        this.towerDefenseGame = towerDefenseGame;
    }

    @Override
    public void show() {
        super.show();
        //
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    private void update(float delta) {
        //
    }

    private void draw() {
        //
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
