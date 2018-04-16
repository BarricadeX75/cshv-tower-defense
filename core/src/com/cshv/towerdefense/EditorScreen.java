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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Barricade on 13/04/2018.
 */

public class EditorScreen extends ScreenAdapter {

    private static final int STATE_CHEMIN = 1, STATE_EFFACER_CHEMIN = 2,
            STATE_FAST_TOWER = 3, STATE_SLOW_TOWER = 4, STATE_ZONE_TOWER = 5, STATE_VISION_TOWER = 6;

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage uiStage;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private BitmapFont bitmapFont;
    private TextureAtlas textureAtlas;
    private TextureLoader tl;

    private Array<TextButton> uiButtons;
    private int editorState;

    private Player _player = new Player();
    private World world;
    private Array<Integer> trajet;

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
                    //
                    break;
                case STATE_SLOW_TOWER:
                    //
                    break;
                case STATE_ZONE_TOWER:
                    //
                    break;
                case STATE_VISION_TOWER:
                    //
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

        Table table = new Table();
        table.setTransform(true);
        table.setScale(textScale);
        table.setPosition(WORLD_WIDTH / 2, 44);

        uiButtons = new Array<TextButton>(6);

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

        final TextButton uiButton2 = new TextButton("Effacer chemin", textButtonStyle);
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
        ////////////////////////////////////////////////////////////////////////////////////////////

        editorState = STATE_CHEMIN;
        uiButton1.setChecked(true);

        trajet = new Array<Integer>();
        trajet.add(5);
        world = new World(tl.getSol(), tl.getChemin(), trajet);
        world.cheminEditor(170);
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

    private void selectButton(TextButton button, Array<TextButton> buttons) {
        for (TextButton b : buttons) {
            if (b != button)
                b.setChecked(false);
        }
    }

    private void insertCell(float x , float y){
        boolean flag = true;
        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);

        if(numCell <= 165 && numCell >= 11 ){
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

    private void removeCell(float x , float y){

        x = (x-45)/2;
        y = WORLD_HEIGHT-( ( y + 176 ) /2);

        int numCell = ( ( (int) (y/32) )*11) + (int) (x/32);

        if(numCell <= 165 && numCell >= 11 ){
            for (int i = 0; i < trajet.size; i++) {
                if (numCell == trajet.get(i)) {
                    world.suppCellCheminEditor(numCell);
                    trajet.removeIndex(i);
                }
            }
        }
    }

    private boolean verifChemin(){
        if(trajet.first() != 5&& trajet.peek() != 170){
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

        uiStage.draw();
    }

    //

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
