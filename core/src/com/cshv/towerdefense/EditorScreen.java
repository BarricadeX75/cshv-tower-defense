package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage uiStage;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private BitmapFont bitmapFont;
    private TextureAtlas textureAtlas;
    private TextureLoader tl;

    private Player _player = new Player();
    private World world;
    private Array<Integer> trajet;

    private boolean clear = false;

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
                if (!clear) {
                    insertCell(screenX, screenY);
                }
                else {
                    removeCell(screenX, screenY);
                }

                return super.touchDragged(screenX, screenY, pointer);
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (!clear) {
                    insertCell(screenX, screenY);
                }
                else {
                    removeCell(screenX, screenY);
                }

                return super.touchDown(screenX, screenY, pointer, button);
            }
        };

        Gdx.input.setInputProcessor(uiStage);

        TextureRegion buttonUpTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonUp.png")));
        TextureRegion buttonDownTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonDown.png")));
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

        TextButton uiButton1 = new TextButton("Chemin", textButtonStyle);
        uiButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //
            }
        });
        table.add(uiButton1).pad(padding).colspan(2).align(Align.right);

        TextButton uiButton2 = new TextButton("Effacer chemin", textButtonStyle);
        uiButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //
            }
        });
        table.add(uiButton2).pad(padding).colspan(2).align(Align.left);

        table.row();

        TextButton uiButton3 = new TextButton("Fast Tower", textButtonStyle);
        uiButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //
            }
        });
        table.add(uiButton3).pad(padding);

        TextButton uiButton4 = new TextButton("Slow Tower", textButtonStyle);
        uiButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //
            }
        });
        table.add(uiButton4).pad(padding);

        TextButton uiButton5 = new TextButton("Zone Tower", textButtonStyle);
        uiButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //
            }
        });
        table.add(uiButton5).pad(padding);

        TextButton uiButton6 = new TextButton("Vision Tower", textButtonStyle);
        uiButton6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //
            }
        });
        table.add(uiButton6).pad(padding);

        uiStage.addActor(table);
        ////////////////////////////////////////////////////////////////////////////////////////////

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
