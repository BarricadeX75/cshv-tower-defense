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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Barricade on 13/04/2018.
 */

public class EditorScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage uiStage;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private BitmapFont bitmapFont;
    private TextureAtlas textureAtlas;
    private TextureLoader tl;

    private World world;
    private Array<Integer> trajet;

    private boolean clear = false;

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

        /////////////////////////////////////  USER INTERFACE  /////////////////////////////////////
        uiStage = new Stage(viewport) {
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if(!clear) {
                    insertCell(screenX, screenY);
                }else{
                    removeCell(screenX, screenY);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer , int button) {
                if(!clear) {
                    insertCell(screenX, screenY);
                }else{
                    removeCell(screenX, screenY);
                }
                return true;
            }
        };

        Gdx.input.setInputProcessor(uiStage);
        ////////////////////////////////////////////////////////////////////////////////////////////

        trajet = new Array<Integer>();
        world = new World(tl.getSol(), tl.getChemin(), trajet);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    private void update(float delta) {
        uiStage.act(delta);
        //
    }

    //

    private void insertCell(float x , float y){
        boolean flag = true;
        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);
        if(numCell <= 175 && numCell >= 0 ){

            for (int i = 0; i < trajet.size; i++) {
                if (numCell == trajet.get(i)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                trajet.add(numCell);
                world.cheminEditor(numCell);
            }
        }
    }

    private void removeCell(float x , float y){

        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);

        if(numCell <= 175 && numCell >= 0 ){
            for (int i = 0; i < trajet.size; i++) {
                if (numCell == trajet.get(i)) {
                    world.suppCellCheminEditor(numCell);
                    trajet.removeIndex(i);
                }
            }
        }
    }

    private boolean verifChemin(){
        if(trajet.first() != 5 && trajet.peek() != 170){
            return false;
        }
        for(int i=1 ; i<trajet.size ; i++) {
            int numCell = trajet.get(i - 1);
            int numCellNext = trajet.get(i);
            if (numCell + 11 != numCellNext && numCell - 11 != numCellNext && numCell + 1 != numCellNext && numCell - 1 != numCellNext) {
                return false;
            }
        }

        return true;
    }

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
