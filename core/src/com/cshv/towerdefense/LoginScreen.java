package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.badlogic.gdx.utils.viewport.Viewport;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.tchalupnik.libgdx.Toast;

/**
 * Created by Barricade on 17/04/2018.
 */

public class LoginScreen extends ScreenAdapter {

    private static final float FRAME_DURATION = 0.1F;
    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage stage,stageBackground;
    private Array<PlayerJson>  playerJsons;
    private Player _player;
    private Camera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Preferences preferences;
    private Animation<TextureRegion> towerShot;
    private Animation<TextureRegion> thunder;
    private float animationTimer = 0;


    private Toast.ToastFactory toastFactory;
    private Toast toast = null;

    private final TowerDefenseGame towerDefenseGame;


    public LoginScreen(TowerDefenseGame towerDefenseGame) {
        this.towerDefenseGame = towerDefenseGame;
    }

    @Override
    public void show() {
        super.show();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,camera);
        stage = new Stage(viewport);
        stageBackground = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        TextureLoader tl = new TextureLoader(textureAtlas);
        final BitmapFont bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");

        Image background = new Image(tl.getBagroundTexture().get(0));
        background.setPosition(0,0);
        stage.addActor(background);
        Image titreBackground = new Image(tl.getBagroundTexture().get(1));
        titreBackground.setPosition(0,0);
        stage.addActor(titreBackground);
        Image fondBackground = new Image(tl.getBagroundTexture().get(2));
        fondBackground.setPosition(0,0);
        stageBackground.addActor(fondBackground);

        towerShot = new Animation<TextureRegion>(FRAME_DURATION, tl.getSpellSlowTower());
        towerShot.setPlayMode(Animation.PlayMode.LOOP);
        thunder = new Animation<TextureRegion>(FRAME_DURATION, tl.getProjectileTower()[MathUtils.random(3)]);
        thunder.setPlayMode(Animation.PlayMode.LOOP);
        preferences = Gdx.app.getPreferences("com.cshv.towerdefense");

        toastFactory = new Toast.ToastFactory.Builder().font(bitmapFont).build();

        /////////////////////////////////////////  STYLES  /////////////////////////////////////////
        TextureRegion dialogBackground = tl.getDialogBackground();
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

        TextureRegion caretTexture = tl.getCaret();
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

        final TextField loginTextField = new TextField(preferences.getString("login", ""), textFieldStyle);
        loginTextField.setAlignment(Align.center);
        loginTextField.setMaxLength(12);
        connectTable.add(loginTextField).padBottom(padding*2);

        connectTable.row();

        Label mdpLabel = new Label("Mot de passe :", labelStyle);
        connectTable.add(mdpLabel).padBottom(padding);

        connectTable.row();

        final TextField mdpTextField = new TextField(preferences.getString("mdp", ""), textFieldStyle);
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
                //towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, new Player()));
            }
        });
        connectTable.add(connectButton).padTop(padding*2);

        stage.addActor(connectTable);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////  CRÉATION DE COMPTE  ///////////////////////////////////
        float labelLeftPadding = 35f;
        float scaling = 0.6f;

        final Dialog createAccountDialog = new Dialog("Créer un compte", windowStyle);
        createAccountDialog.setModal(true);
        createAccountDialog.setMovable(false);
        createAccountDialog.setResizable(false);
        createAccountDialog.setTransform(true);
        createAccountDialog.setScale(0.75f);
        createAccountDialog.getTitleTable().setTransform(true);
        createAccountDialog.getTitleTable().setScale(scaling);
        createAccountDialog.getTitleTable().padLeft(labelLeftPadding).padTop(100f);
        createAccountDialog.getContentTable().setTransform(true);
        createAccountDialog.getContentTable().setScale(scaling);
        createAccountDialog.getContentTable().padLeft(labelLeftPadding);
        createAccountDialog.getButtonTable().setTransform(true);
        createAccountDialog.getButtonTable().setScale(scaling);
        createAccountDialog.getButtonTable().padLeft(labelLeftPadding).padBottom(30f);

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
                createAccountDialog.show(stage);
                createAccountDialog.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 5, Align.center);
            }
        });
        createAccountButton.setPosition(WORLD_WIDTH / 2, createAccountButton.getHeight() / 2 + padding, Align.center);
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
        animationTimer += delta;
        stageBackground.draw();
        stage.draw();
        draw();

        if (toast != null)
            toast.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private void draw() {
        TextureRegion animeShot = towerShot.getKeyFrame(animationTimer);
        TextureRegion animeThunder = thunder.getKeyFrame(animationTimer);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(animeShot,132,460);
        batch.draw(animeThunder,50,490);
        batch.end();
        stage.draw();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void connect(String login, String mdp) {
        if (login.isEmpty()) {
            toast = toastFactory.create("Le login est vide !", Toast.Length.SHORT);
        }
        else if (!login.matches("[A-Za-z0-9]+")) {
            toast = toastFactory.create("Le login n'est pas alphanumérique !", Toast.Length.SHORT);
        }
        else if (mdp.isEmpty()) {
            toast = toastFactory.create("Le mot de passe est vide !", Toast.Length.SHORT);
        }
        else {
            getPlayerData(login, mdp);
        }
    }

    private void createAccount(String login, String mdp, String confirmation, String nom) {
        if (login.isEmpty()) {
            toast = toastFactory.create("Le login ne peut pas être vide !", Toast.Length.SHORT);
        }
        else if (!login.matches("[A-Za-z0-9]+")) {
            toast = toastFactory.create("Le login doit être alphanumérique !", Toast.Length.SHORT);
        }
        else if (mdp.isEmpty()) {
            toast = toastFactory.create("Le mot de passe ne peut pas être vide !", Toast.Length.SHORT);
        }
        else if (mdp.equals(login) || mdp.equals(nom)) {
            toast = toastFactory.create("Le mot de passe doit être différent du login et du nom !", Toast.Length.SHORT);
        }
        else if (!confirmation.equals(mdp)) {
            toast = toastFactory.create("La confirmation ne correspond pas au mot de passe !", Toast.Length.SHORT);
        }
        else if (nom.isEmpty()) {
            toast = toastFactory.create("Le nom ne peut pas être vide !", Toast.Length.SHORT);
        }
        else if (!nom.matches("[A-Za-z0-9]+")) {
            toast = toastFactory.create("Le nom doit être alphanumérique !", Toast.Length.SHORT);
        }
        else {
            _player = new Player(nom);

            sendNewPlayerData(login, mdp);
        }
    }

    private String hacherMdp(String mdp) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(mdp.getBytes());
            byte[] byteData = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getPlayerData(final String login, final String mdp){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("login", login);
        parameters.put("mdp", hacherMdp(mdp));
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://10.16.0.74/towerdefense/getData.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
        Gdx.net.sendHttpRequest (httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                final int statusCode = httpResponse.getStatus().getStatusCode();
                // We are not in main thread right now so we need to post to main thread for ui updates

                if (statusCode != 200) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            toast = toastFactory.create("Impossible de se connecter au serveur", Toast.Length.SHORT);
                        }
                    });

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

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (playerJsons.size == 0) {
                            toast = toastFactory.create("Login inexistant ou mot de passe incorrect !", Toast.Length.SHORT);
                        }
                        else {
                            preferences.putString("login", login);
                            preferences.putString("mdp", mdp);
                            preferences.flush();

                            _player = playerJsons.first().getPlayer();

                            towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));
                            dispose();
                        }
                    }
                });
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        toast = toastFactory.create("Impossible de se connecter au serveur", Toast.Length.SHORT);
                    }
                });

                Gdx.app.log("error","fail");
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                Gdx.app.log("NetAPITest", "HTTP request cancelled");
            }


        });
    }

    public void sendNewPlayerData(final String login, final String mdp){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("login", login);
        parameters.put("mdp", hacherMdp(mdp));
        parameters.put("nom",_player.getNom());
        parameters.put("lvlStage",Integer.toString(_player.getLvlStage()));
        parameters.put("gold",Long.toString(_player.getGold()));
        parameters.put("lvlFastTower", Integer.toString(_player.getLvlFastTower()));
        parameters.put("lvlZoneTower", Integer.toString(_player.getLvlZoneTower()));
        parameters.put("lvlSlowTower",Integer.toString(_player.getLvlSlowTower()));
        parameters.put("lvlVisionTower",Integer.toString(_player.getLvlVisionTower()));
        parameters.put("lvlChevalier",Integer.toString(_player.getLvlChevalier()));
        parameters.put("lvlHealer",Integer.toString(_player.getLvlHealer()));
        parameters.put("lvlMage",Integer.toString(_player.getLvlMage()));
        parameters.put("lvlRogue",Integer.toString(_player.getLvlRogue()));
        parameters.put("lvlMoine",Integer.toString(_player.getLvlMoine()));
        parameters.put("lvlFontaine",Integer.toString(_player.getLvlFontaine()));
        parameters.put("chemin",_player.getCheminString());
        parameters.put("posTowers",_player.getTowersString());
        parameters.put("date",Long.toString(_player.getDate()));
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://10.16.0.74/towerdefense/createAccount.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
        Gdx.net.sendHttpRequest (httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                final int statusCode = httpResponse.getStatus().getStatusCode();
                // We are not in main thread right now so we need to post to main thread for ui updates

                if (statusCode != 200) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            toast = toastFactory.create("Impossible de se connecter au serveur", Toast.Length.SHORT);
                        }
                    });

                    Gdx.app.log("NetAPITest", "An error ocurred since statusCode is not OK");

                    return;
                }

                final String result = httpResponse.getResultAsString();

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (result.equals("fail")) {
                            toast = toastFactory.create("Ce login est déjà pris !", Toast.Length.SHORT);
                        }
                        else {
                            preferences.putString("login", login);
                            preferences.putString("mdp", mdp);
                            preferences.flush();

                            towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));
                            dispose();
                        }
                    }
                });
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        toast = toastFactory.create("Impossible de se connecter au serveur", Toast.Length.SHORT);
                    }
                });

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
