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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Towers.Tower;

import java.util.HashMap;

/**
 * Created by Barricade on 13/04/2018.
 */

public class EditorScreen extends ScreenAdapter {

    private static final int STATE_CHEMIN = 1, STATE_EFFACER_CHEMIN = 2,
            STATE_FAST_TOWER = 3, STATE_SLOW_TOWER = 4, STATE_ZONE_TOWER = 5, STATE_VISION_TOWER = 6;

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage uiStage,stageBackground;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private BitmapFont bitmapFont;
    private TextureAtlas textureAtlas;
    private TextureLoader tl;
    private Label limTowerLabel, limCheminLabel;
    private int nbTowerMax;
    private int nbCellCheminMax;

    private Array<TextButton> uiButtons;
    private int editorState;

    private Player _player;
    private World world;
    private Array<Integer> trajet;
    private HashMap< Integer, Integer> towers;

    private final TowerDefenseGame towerDefenseGame;


    public EditorScreen(TowerDefenseGame towerDefenseGame, Player player) {
        this.towerDefenseGame = towerDefenseGame;
        _player = player;
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
        towers = new HashMap<Integer, Integer>();
        nbTowerMax = 4 + (_player.getLvlFontaine()/5);
        nbCellCheminMax = 15 + (_player.getLvlFontaine()/2);
        stageBackground = new Stage(viewport);
        Image fondBackground = new Image(tl.getBagroundTexture().get(2));
        fondBackground.setPosition(0,0);
        stageBackground.addActor(fondBackground);
        /////////////////////////////////////  USER INTERFACE  /////////////////////////////////////

        uiStage = new Stage(viewport) {
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                switch (editorState) {
                case STATE_CHEMIN:
                    insertCell(screenX, screenY);
                    break;
                case STATE_EFFACER_CHEMIN:
                    removeCell(screenX, screenY);
                    break;
                }

                return super.touchDragged(screenX, screenY, pointer);
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                switch (editorState) {
                case STATE_CHEMIN:
                    insertCell(screenX, screenY);
                    break;
                case STATE_EFFACER_CHEMIN:
                    removeCell(screenX, screenY);
                    break;
                case STATE_FAST_TOWER:
                    insertTower(screenX, screenY, Tower.FAST_TOWER);
                    break;
                case STATE_SLOW_TOWER:
                    insertTower(screenX, screenY, Tower.SLOW_TOWER);
                    break;
                case STATE_ZONE_TOWER:
                    insertTower(screenX, screenY, Tower.ZONE_TOWER);
                    break;
                case STATE_VISION_TOWER:
                    insertTower(screenX, screenY, Tower.VISION_TOWER);
                    break;
                }

                return super.touchDown(screenX, screenY, pointer, button);
            }
        };

        Gdx.input.setInputProcessor(uiStage);

        TextureRegion buttonUpTexture = tl.getButtonUp();
        TextureRegion buttonDownTexture = tl.getButtonDown();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                new TextureRegionDrawable(buttonDownTexture),
                bitmapFont
        );
        float textScale = 0.4f;
        float padding = 15f;
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.RED);
        Table table = new Table();
        table.setTransform(true);
        table.setScale(textScale);
        table.setPosition(WORLD_WIDTH / 2, 44);

        uiButtons = new Array<TextButton>(6);

        final TextButton validButton  = new TextButton("Validation", textButtonStyle);
        validButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(validButton, uiButtons);
                verifChemin();
            }
        });
        table.add(validButton).pad(padding).colspan(1).align(Align.left);
        uiButtons.add(validButton);

        final TextButton uiButton1 = new TextButton("Chemin", textButtonStyle);
        uiButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(uiButton1, uiButtons);
                editorState = STATE_CHEMIN;
            }
        });
        table.add(uiButton1).pad(padding).colspan(2).align(Align.right);
        uiButtons.add(uiButton1);

        final TextButton uiButton2 = new TextButton("Gomme", textButtonStyle);
        uiButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(uiButton2, uiButtons);
                editorState = STATE_EFFACER_CHEMIN;
            }
        });
        table.add(uiButton2).pad(padding).colspan(2).align(Align.left);
        uiButtons.add(uiButton2);

        table.row();

        final TextButton uiButton3 = new TextButton("Fast Tower", textButtonStyle);
        uiButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(uiButton3, uiButtons);
                editorState = STATE_FAST_TOWER;
            }
        });
        table.add(uiButton3).pad(padding);
        uiButtons.add(uiButton3);

        final TextButton uiButton4 = new TextButton("Slow Tower", textButtonStyle);
        uiButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(uiButton4, uiButtons);
                editorState = STATE_SLOW_TOWER;
            }
        });
        table.add(uiButton4).pad(padding);
        uiButtons.add(uiButton4);

        final TextButton uiButton5 = new TextButton("Zone Tower", textButtonStyle);
        uiButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(uiButton5, uiButtons);
                editorState = STATE_ZONE_TOWER;
            }
        });
        table.add(uiButton5).pad(padding);
        uiButtons.add(uiButton5);

        final TextButton uiButton6 = new TextButton("Vision Tower", textButtonStyle);
        uiButton6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectButton(uiButton6, uiButtons);
                editorState = STATE_VISION_TOWER;
            }
        });
        table.add(uiButton6).pad(padding);
        uiButtons.add(uiButton6);

        uiStage.addActor(table);



        /////////////////////////////////////////////////////////////////////////////////////
        editorState = STATE_CHEMIN;
        uiButton1.setChecked(true);

        trajet = new Array<Integer>();
        trajet.add(5);

        world = new World(tl.getSol(), tl.getChemin(), trajet, tl.getSpriteTowerFast().get(0), tl.getSpriteTowerSlow().get(0), tl.getSpriteTowerZone().get(0), tl.getSpriteTowerVision().get(0));
        world.cheminEditor(170);
        chargementMap();

        ////////////////////////////////////////////////////////////////////////////////////////////
        limCheminLabel = new Label("Chemin: "+trajet.size+"/"+nbCellCheminMax,labelStyle);
        limCheminLabel.setFontScale(0.5f);
        limCheminLabel.setPosition(((7*WORLD_WIDTH) / 8) + 5, WORLD_HEIGHT-15, Align.center);
        uiStage.addActor(limCheminLabel);

        limTowerLabel = new Label("Tower: "+towers.size()+"/"+nbTowerMax,labelStyle);
        limTowerLabel.setFontScale(0.5f);
        limTowerLabel.setPosition((2*WORLD_WIDTH) / 8 , WORLD_HEIGHT-15, Align.center);
        uiStage.addActor(limTowerLabel);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        stageBackground.draw();
        draw();
    }

    private void update(float delta) {
        uiStage.act(delta);
        limTowerLabel.setText("Tower: "+towers.size()+"/"+nbTowerMax);
        limCheminLabel.setText("Chemin: "+trajet.size+"/"+nbCellCheminMax);
    }

    private void chargementMap(){
        trajet = _player.getChemin();
        towers = _player.getTowers();
        trajet.removeIndex(trajet.size-1);
        for(Integer chemin : trajet){
            world.cheminEditor(chemin);
        }
        for(Integer pos : towers.keySet()){
            Integer type = towers.get(pos);
            world.towerEditor(pos,type);
        }
    }

    private void selectButton(TextButton button, Array<TextButton> buttons) {
        for (TextButton b : buttons) {
            if(button == buttons.first()){
                button.setChecked(false);
            }
            if (b != button)
                b.setChecked(false);
        }
    }

    private void insertCell(float x , float y){
        boolean flag = true;
        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);

        if(numCell <= 165 && numCell >= 11 && trajet.size < nbCellCheminMax){
            if (trajet.peek() + 11 == numCell || trajet.peek() - 11 == numCell || trajet.peek() + 1 == numCell || trajet.peek() - 1 == numCell) {
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
    }

    private void insertTower(float x, float y, int type){
        boolean flag = true;
        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);

        if(numCell <= 175 && numCell >= 0 && towers.size() < nbTowerMax){

                for (int i = 0; i < trajet.size; i++) {
                    if (numCell == trajet.get(i)) {
                        flag = false;
                        break;
                    }
                }
                for(Integer pos : towers.keySet()){
                    if(numCell == pos){
                        flag = false;
                        break;
                    }
                }


                if (flag) {
                    towers.put(numCell,type);
                    world.towerEditor(numCell, type);
                }

        }
    }

    private void removeCell(float x , float y){

        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);
        removeTower(numCell);
        if(numCell <= 165 && numCell >= 11 ){
            for (int i = 0; i < trajet.size; i++) {
                if (numCell == trajet.get(i)) {
                    world.suppCellCheminEditor(numCell);
                    trajet.removeIndex(i);
                }
            }
        }
    }

    private void removeTower(int numCell){
        if(numCell <= 175 && numCell >= 0 ) {
            towers.remove(numCell);
            world.removeTower(numCell);
        }
    }

    private boolean verifChemin(){
        if(trajet.peek() != 159){
            return false;
        }else {
            trajet.add(170);
            _player.setChemin(trajet);
            _player.setTowers(towers);
            towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));
            dispose();
        }
        return true;
    }

    private void draw() {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        world.drawEditor(batch);
        //
        batch.end();

        uiStage.draw();
    }

    //

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
