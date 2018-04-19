package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barricade on 17/04/2018.
 */

public class LoginScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage stage;
    private Array<PlayerJson>  playerJsons;
    private Player _player;

    private final TowerDefenseGame towerDefenseGame;


    public LoginScreen(TowerDefenseGame towerDefenseGame) {
        this.towerDefenseGame = towerDefenseGame;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        TextureLoader tl = new TextureLoader(textureAtlas);
        final BitmapFont bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");

        /////////////////////////////////////////  STYLES  /////////////////////////////////////////
        TextureRegion dialogBackground = new TextureRegion(new Texture(Gdx.files.internal("dialogBackground.png"))); //tl.getDialogBackground();
        Window.WindowStyle windowStyle = new Window.WindowStyle(
                bitmapFont,
                Color.WHITE,
                new TextureRegionDrawable(dialogBackground)
        );

        TextureRegion buttonUpTexture = tl.getButtonUp();
        TextureRegion buttonDownTexture = tl.getButtonDown();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                null,
                bitmapFont
        );

        TextureRegion caretTexture = new TextureRegion(new Texture(Gdx.files.internal("caret.png"))); //tl.getCaret();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(
                bitmapFont,
                Color.WHITE,
                new TextureRegionDrawable(caretTexture),
                null,
                null
        );

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////  CONNEXION  ////////////////////////////////////////
        Table connectTable = new Table();
        connectTable.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

        float padding = 10f;

        final Label loginLabel = new Label("Login :", labelStyle);
        connectTable.add(loginLabel).padBottom(padding);

        connectTable.row();

        final TextField loginTextField = new TextField("", textFieldStyle);
        loginTextField.setAlignment(Align.center);
        loginTextField.setMaxLength(12);
        connectTable.add(loginTextField).padBottom(padding*2);

        connectTable.row();

        Label mdpLabel = new Label("Mot de passe :", labelStyle);
        connectTable.add(mdpLabel).padBottom(padding);

        connectTable.row();

        final TextField mdpTextField = new TextField("", textFieldStyle);
        mdpTextField.setAlignment(Align.center);
        mdpTextField.setMaxLength(20);
        mdpTextField.setPasswordMode(true);
        mdpTextField.setPasswordCharacter('*');
        connectTable.add(mdpTextField).padBottom(padding*2);

        connectTable.row();

        TextButton connectButton = new TextButton("Connexion", textButtonStyle);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                connect(loginTextField.getText(), mdpTextField.getText());

            }
        });
        connectTable.add(connectButton).padTop(padding*2);

        stage.addActor(connectTable);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////  CRÉATION DE COMPTE  ///////////////////////////////////
        float labelLeftPadding = 40f;
        float scaling = 0.6f;

        final Dialog createAccountDialog = new Dialog("Créer un compte", windowStyle);
        createAccountDialog.setModal(true);
        createAccountDialog.setMovable(false);
        createAccountDialog.setResizable(false);
        createAccountDialog.getTitleTable().padLeft(150f).padTop(100f);
        createAccountDialog.getTitleTable().setTransform(true);
        createAccountDialog.getTitleTable().setScale(scaling);
        createAccountDialog.getContentTable().padLeft(labelLeftPadding);
        createAccountDialog.getContentTable().setTransform(true);
        createAccountDialog.getContentTable().setScale(scaling);
        createAccountDialog.getButtonTable().padLeft(125f).padBottom(30f);
        createAccountDialog.getButtonTable().setTransform(true);
        createAccountDialog.getButtonTable().setScale(scaling);

        Label dialogLoginLabel = new Label("Login :", labelStyle);
        createAccountDialog.getContentTable().add(dialogLoginLabel).align(Align.right);

        final TextField dialogLoginTextField = new TextField("", textFieldStyle);
        dialogLoginTextField.setAlignment(Align.left);
        dialogLoginTextField.setMaxLength(12);
        createAccountDialog.getContentTable().add(dialogLoginTextField).padLeft(padding).align(Align.left);

        createAccountDialog.getContentTable().row();

        Label dialogMdpLabel = new Label("Mot de passe :", labelStyle);
        createAccountDialog.getContentTable().add(dialogMdpLabel).align(Align.right);

        final TextField dialogMdpTextField = new TextField("", textFieldStyle);
        dialogMdpTextField.setAlignment(Align.left);
        dialogMdpTextField.setMaxLength(20);
        dialogMdpTextField.setPasswordMode(true);
        dialogMdpTextField.setPasswordCharacter('*');
        createAccountDialog.getContentTable().add(dialogMdpTextField).padLeft(padding).align(Align.left);

        createAccountDialog.getContentTable().row();

        Label dialogConfirmationLabel = new Label("Confirmation :", labelStyle);
        createAccountDialog.getContentTable().add(dialogConfirmationLabel).align(Align.right);

        final TextField dialogConfirmationTextField = new TextField("", textFieldStyle);
        dialogConfirmationTextField.setAlignment(Align.left);
        dialogConfirmationTextField.setMaxLength(20);
        dialogConfirmationTextField.setPasswordMode(true);
        dialogConfirmationTextField.setPasswordCharacter('*');
        createAccountDialog.getContentTable().add(dialogConfirmationTextField).padLeft(padding).align(Align.left);

        createAccountDialog.getContentTable().row();

        Label dialogNomLabel = new Label("Nom de joueur :", labelStyle);
        createAccountDialog.getContentTable().add(dialogNomLabel).align(Align.right);

        final TextField dialogNomTextField = new TextField("", textFieldStyle);
        dialogNomTextField.setAlignment(Align.left);
        dialogNomTextField.setMaxLength(12);
        createAccountDialog.getContentTable().add(dialogNomTextField).padLeft(padding).align(Align.left);

        TextButton dialogCancelButton = new TextButton("Annuler", textButtonStyle);
        dialogCancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                dialogLoginTextField.setText("");
                dialogMdpTextField.setText("");
                dialogConfirmationTextField.setText("");
                dialogNomTextField.setText("");
                createAccountDialog.hide();
            }
        });
        createAccountDialog.getButtonTable().add(dialogCancelButton).padRight(padding);

        TextButton dialogConfirmButton = new TextButton("OK", textButtonStyle);
        dialogConfirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                createAccount(dialogLoginTextField.getText(), dialogMdpTextField.getText(),
                        dialogConfirmationTextField.getText(), dialogNomTextField.getText());
            }
        });
        createAccountDialog.getButtonTable().add(dialogConfirmButton).padLeft(padding);

        TextButton createAccountButton = new TextButton("Créer un compte", textButtonStyle);
        createAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (stage.getActors().contains(createAccountDialog, true))
                    stage.addActor(createAccountDialog);

                createAccountDialog.show(stage);
            }
        });
        createAccountButton.setPosition(WORLD_WIDTH / 2, createAccountButton.getHeight() / 2, Align.center);
        stage.addActor(createAccountButton);
        ////////////////////////////////////////////////////////////////////////////////////////////
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

    private void connect(String login, String mdp) {
        if (login.isEmpty()) {
            //
        }
        else if (!login.matches("[A-Za-z0-9]+")) {
            //
        }
        else if (mdp.isEmpty()) {
            //
        }
        else {
            requestBdGetPlayer(login, mdp);
        }
    }

    private void createAccount(String login, String mdp, String confirmation, String nom) {
        if (login.isEmpty()) {
            //
        }
        else if (!login.matches("[A-Za-z0-9]+")) {
            //
        }
        else if (mdp.isEmpty()) {
            //
        }
        else if (!confirmation.equals(mdp)) {
            //
        }
        else if (nom.isEmpty()) {
            //
        }
        else if (!nom.matches("[A-Za-z0-9]+")) {
            //
        }
        else {
            _player = new Player(nom);

            requestBdPostPlayer(login, mdp);
        }
    }

    public void requestBdGetPlayer(String login, String mdp){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("login", login);
        parameters.put("mdp", mdp);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://10.16.0.74/towerdefense/getData.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
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

                //
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
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://10.16.0.74/towerdefense/sendData.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
        Gdx.net.sendHttpRequest (httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                final int statusCode = httpResponse.getStatus().getStatusCode();
                // We are not in main thread right now so we need to post to main thread for ui updates

                if (statusCode != 200) {
                    Gdx.app.log("NetAPITest", "An error ocurred since statusCode is not OK");

                    return;
                }

                //
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
