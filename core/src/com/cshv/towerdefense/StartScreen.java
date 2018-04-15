package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class StartScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage stage;

    private final TowerDefenseGame towerDefenseGame;


    public StartScreen(TowerDefenseGame towerDefenseGame) {
        this.towerDefenseGame = towerDefenseGame;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        //TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("");
        BitmapFont bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");

        TextureRegion buttonUpTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonUp.png")));
        TextureRegion buttonDownTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonDown.png")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                null,
                bitmapFont
        );

        TextButton playButton = new TextButton("Jouer", textButtonStyle);
        playButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 6, Align.center);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                towerDefenseGame.setScreen(new GameScreen(towerDefenseGame, new Player()));
                dispose();
            }
        });
        stage.addActor(playButton);

        TextButton editorButton = new TextButton("Éditeur", textButtonStyle);
        editorButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 2 / 6, Align.center);
        editorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                towerDefenseGame.setScreen(new EditorScreen(towerDefenseGame));
                //dispose();
            }
        });
        stage.addActor(editorButton);

        TextButton characsButton = new TextButton("Caractéristiques", textButtonStyle);
        characsButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 1 / 6, Align.center);
        characsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //towerDefenseGame.setScreen(new CharacsScreen(towerDefenseGame));
                //dispose();
            }
        });
        stage.addActor(characsButton);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        clearScreen();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
