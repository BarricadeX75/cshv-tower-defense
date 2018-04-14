package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    private TextureAtlas textureAtlas;
    private TextureLoader tl;

    private World world;
    private Rectangle[] chemin;
    private Cell[] cells;

    //

    private final TowerDefenseGame towerDefenseGame;


    public EditorScreen(TowerDefenseGame towerDefenseGame) {
        this.towerDefenseGame = towerDefenseGame;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");
        textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        tl = new TextureLoader(textureAtlas);

        Array<Integer> trajet = new Array<Integer>();
        for (int i = 5; i < 176; i += 11){
            trajet.add(i);
        }

        world = new World(tl.getSol(), tl.getChemin(), trajet);
        chemin = world.getChemin();


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

    //

    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        world.draw(batch);
        //
        batch.end();
    }

    //

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
