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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import cz.tchalupnik.libgdx.Toast;


public class StartScreen extends ScreenAdapter {

    private static final float FRAME_DURATION = 0.1F;

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;
    private Camera camera;
    private Stage stage,stageBackground;
    private Player _player;
    private Viewport viewport;
    private Preferences preferences;
    private SpriteBatch batch;

    private Animation<TextureRegion> towerShot;
    private Animation<TextureRegion> thunder;
    private float animationTimer = 0;

    private Toast.ToastFactory toastFactory;
    private Toast toast = null;

    private final TowerDefenseGame towerDefenseGame;


    public StartScreen(TowerDefenseGame towerDefenseGame, Player player) {
        this.towerDefenseGame = towerDefenseGame;
        _player = player;
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
        BitmapFont bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");
        preferences = Gdx.app.getPreferences("com.cshv.towerdefense");

        toastFactory = new Toast.ToastFactory.Builder().font(bitmapFont).build();

        updatePlayerData( preferences.getString("login", ""), preferences.getString("mdp", ""));

        Image background = new Image(tl.getBagroundTexture().get(0));
        background.setPosition(0,0);
        stage.addActor(background);
        Image titreBackground = new Image(tl.getBagroundTexture().get(1));
        titreBackground.setPosition(0,0);
        stage.addActor(titreBackground);
        Image fondBackground = new Image(tl.getBagroundTexture().get(2));
        fondBackground.setPosition(0,0);
        stageBackground.addActor(fondBackground);

        Array<TextureRegion>[] sortBackground = new Array[5];
        sortBackground[0] = tl.getAtkMage();
        sortBackground[1] = tl.getSpellSlowTower();
        sortBackground[2] = tl.getSpellFire();
        sortBackground[3] = tl.getSpellWater();
        sortBackground[4] = tl.getExploMushroom();

        towerShot = new Animation<TextureRegion>(FRAME_DURATION, sortBackground[MathUtils.random(4)]);
        towerShot.setPlayMode(Animation.PlayMode.LOOP);

        thunder = new Animation<TextureRegion>(FRAME_DURATION, tl.getProjectileTower()[MathUtils.random(3)]);
        thunder.setPlayMode(Animation.PlayMode.LOOP);

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);

        Table playerInfoTable = new Table();
        playerInfoTable.setTransform(true);
        playerInfoTable.setScale(0.5f);
        playerInfoTable.setPosition(WORLD_WIDTH / 2, 380);

        Label labelNomJoueur = new Label("Joueur: " + _player.getNom(), labelStyle);
        playerInfoTable.add(labelNomJoueur).align(Align.center);
        playerInfoTable.row();

        Label labelStageActuel = new Label("Stage actuel: " + _player.getLvlStage(), labelStyle);
        playerInfoTable.add(labelStageActuel).align(Align.center);

        stage.addActor(playerInfoTable);

        TextureRegion buttonUpTexture = tl.getButtonUp();
        TextureRegion buttonDownTexture = tl.getButtonDown();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                null,
                bitmapFont
        );

        TextButton normalButton = new TextButton("Jeu normal", textButtonStyle);
        normalButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 4 / 9, Align.center);
        normalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                towerDefenseGame.setScreen(new NormalMode(towerDefenseGame, _player));////////
                dispose();
            }
        });
        stage.addActor(normalButton);

        TextButton survivalButton = new TextButton("Jeu de survie", textButtonStyle);
        survivalButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 9, Align.center);
        survivalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                towerDefenseGame.setScreen(new SurvivalMode(towerDefenseGame, _player));////////
                dispose();
            }
        });
        stage.addActor(survivalButton);

        TextButton editorButton = new TextButton("Éditeur", textButtonStyle);
        editorButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 2 / 9, Align.center);
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
        characsButton.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 1 / 9, Align.center);
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
        animationTimer += delta;
        stage.act(delta);
        stageBackground.draw();
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

    public void updatePlayerData(String login, String mdp){
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
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://10.16.0.74/towerdefense/sendData.php").content(HttpParametersUtils.convertHttpParameters(parameters)).build();
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

                if (httpResponse.getResultAsString().equals("ok")) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            toast = toastFactory.create("Données mises à jour", Toast.Length.SHORT);
                        }
                    });
                }
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
