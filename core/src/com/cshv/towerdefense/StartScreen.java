package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class StartScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage stage;
    private Array<PlayerJson>  playerJsons;
    private Player _player = new Player();

    private final TowerDefenseGame towerDefenseGame;


    public StartScreen(TowerDefenseGame towerDefenseGame) {
        this.towerDefenseGame = towerDefenseGame;
    }
    public StartScreen(TowerDefenseGame towerDefenseGame, Player player) {
        this.towerDefenseGame = towerDefenseGame;
        _player = player;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        TextureLoader tl = new TextureLoader(textureAtlas);
        BitmapFont bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");

        TextureRegion buttonUpTexture = tl.getButtonUp();
        TextureRegion buttonDownTexture = tl.getButtonDown();
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
                towerDefenseGame.setScreen(new GameScreen(towerDefenseGame, _player));
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
                towerDefenseGame.setScreen(new EditorScreen(towerDefenseGame, _player));
                dispose();
            }
        });
        stage.addActor(editorButton);

        TextButton characsButton = new TextButton("Caractéristiques", textButtonStyle);
        characsButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 1 / 6, Align.center);
        characsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                towerDefenseGame.setScreen(new CharacsScreen(towerDefenseGame, _player));
                dispose();
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

    public void requestBdGetPlayer(String login, String mdp){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("pseudo", login);
        parameters.put("mdp", mdp);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://10.16.0.74/towerDefence/getData.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
        Gdx.net.sendHttpRequest (httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                final int statusCode = httpResponse.getStatus().getStatusCode();
                // We are not in main thread right now so we need to post to main thread for ui updates

                if (statusCode != 200) {
                    Gdx.app.log("NetAPITest", "An error ocurred since statusCode is not OK");

                    return;
                }
                String JSONTxt = httpResponse.getResultAsString();
                playerJsons = new Array<PlayerJson>();
                Json json = new Json();
                ArrayList<JsonValue> list = json.fromJson(ArrayList.class, JSONTxt);
                for (JsonValue v : list) {
                    playerJsons.add(json.readValue(PlayerJson.class,v));
                }


            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("error","fail");
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                Gdx.app.log("NetAPITest", "HTTP request cancelled");
            }
        });
    }

    public void requestBdPostPlayer(String login, String mdp){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("login", login);
        parameters.put("mdp", mdp);
        parameters.put("nom",_player.getNom());
        parameters.put("lvlStage",Integer.toString(_player.getLvlStage()));
        parameters.put("gold",Integer.toString(_player.getGold()));
        parameters.put("lvlFastTower", Integer.toString(_player.getLvlStage()));
        parameters.put("lvlZoneTower", Integer.toString(_player.getLvlStage()));
        parameters.put("lvlSlowTower",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlVisionTower",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlChevalier",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlHealer",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlMage",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlRogue",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlMoine",Integer.toString(_player.getLvlStage()));
        parameters.put("lvlFontaine",Integer.toString(_player.getLvlStage()));
        parameters.put("chemin",_player.getCheminString());
        parameters.put("posTowers",_player.getTowersString());
        parameters.put("date",Long.toString(_player.getDate()));
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://10.16.0.74/towerDefence/sendData.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
        Gdx.net.sendHttpRequest (httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                final int statusCode = httpResponse.getStatus().getStatusCode();
                // We are not in main thread right now so we need to post to main thread for ui updates

                if (statusCode != 200) {
                    Gdx.app.log("NetAPITest", "An error ocurred since statusCode is not OK");

                    return;
                }
                String JSONTxt = httpResponse.getResultAsString();
                playerJsons = new Array<PlayerJson>();
                Json json = new Json();
                ArrayList<JsonValue> list = json.fromJson(ArrayList.class, JSONTxt);
                for (JsonValue v : list) {
                    playerJsons.add(json.readValue(PlayerJson.class,v));
                }


            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("error","fail");
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                Gdx.app.log("NetAPITest", "HTTP request cancelled");
            }
        });
    }
}
