package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Date;


public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private final TowerDefenseGame towerDefenseGame;
    private Viewport viewport;
    private Camera camera;
    private BitmapFont bitmapFont;
    private SpriteBatch batch;
    private World world;
    private Rectangle[] chemin;

    private Array<TextureRegion> solTextures;
    private Array<TextureRegion> cheminTextures;
    private Array<TextureRegion> towerSpeedTextures;
    private Array<TextureRegion> slimeTextures;





    public GameScreen(TowerDefenseGame towerDefenseGame) {
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
        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        solTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            solTextures.add(textureAtlas.findRegion("herbe"));
        }
        cheminTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            cheminTextures.add(textureAtlas.findRegion("sol"));
        }
        slimeTextures = new Array<TextureRegion>();
        for(int i=0 ; i<3 ; i++){
            slimeTextures.add(textureAtlas.findRegion("slimeVertical"+i+1));
        }
        towerSpeedTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            towerSpeedTextures.add(textureAtlas.findRegion("tour"));
        }

        //static chemin
        Array<Integer> trajet = new Array<Integer>();
        for(int i=3 ; i<52 ; i+=6 ){
            trajet.add(i);
        }

        world = new World( solTextures, cheminTextures, trajet);
        chemin = world.getChemin();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();;
    }

    private void update(float delta) {

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        world.draw(batch);

        batch.end();
    }

}
