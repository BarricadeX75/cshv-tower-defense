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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Mobs.Slime;
import com.cshv.towerdefense.Spells.Spell;
import com.cshv.towerdefense.Spells.TowerProjectile;
import com.cshv.towerdefense.Towers.FastTower;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Units.Unit;

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
    private int lvlStage = 1;
    private int nbMonster;
    private int monsterCreate;
    private long timer;
    private int numWave;

    private TextureAtlas textureAtlas;
    private Array<TextureRegion> solTextures;
    private Array<TextureRegion> cheminTextures;
    private Array<TextureRegion> towerSpeedTextures;
    private Array<TextureRegion> slime_TexturesUp;
    private Array<TextureRegion> slime_TexturesLeft;
    private Array<TextureRegion> slime_TexturesRight;
    private Array<TextureRegion> slime_TextureDown;
    private Array<TextureRegion> chienSquelette_TexturesUp;
    private Array<TextureRegion> chienSquelette_TexturesLeft;
    private Array<TextureRegion> chienSquelette_TexturesRight;
    private Array<TextureRegion> chienSquelette_TextureDown;
    private Array<TextureRegion> griffon_TexturesUp;
    private Array<TextureRegion> griffon_TexturesLeft;
    private Array<TextureRegion> griffon_TexturesRight;
    private Array<TextureRegion> griffon_TextureDown;
    private Array<TextureRegion> lamia_TexturesUp;
    private Array<TextureRegion> lamia_TexturesLeft;
    private Array<TextureRegion> lamia_TexturesRight;
    private Array<TextureRegion> lamia_TextureDown;
    private Array<TextureRegion> loupGarou_TexturesUp;
    private Array<TextureRegion> loupGarou_TexturesLeft;
    private Array<TextureRegion> loupGarou_TexturesRight;
    private Array<TextureRegion> loupGarou_TextureDown;
    private Array<TextureRegion> orc_TexturesUp;
    private Array<TextureRegion> orc_TexturesLeft;
    private Array<TextureRegion> orc_TexturesRight;
    private Array<TextureRegion> orc_TextureDown;
    private Array<TextureRegion> centaure_TexturesUp;
    private Array<TextureRegion> centaure_TexturesLeft;
    private Array<TextureRegion> centaure_TexturesRight;
    private Array<TextureRegion> centaure_TextureDown;
    private Array<TextureRegion> chevalier_TexturesUp;
    private Array<TextureRegion> chevalier_TexturesLeft;
    private Array<TextureRegion> chevalier_TexturesRight;
    private Array<TextureRegion> chevalier_TextureDown;
    private Array<TextureRegion> chevalier_atk_TexturesUp;
    private Array<TextureRegion> chevalier_atk_TexturesLeft;
    private Array<TextureRegion> chevalier_atk_TexturesRight;
    private Array<TextureRegion> chevalier_atk_TextureDown;
    private Array<TextureRegion> healer_TexturesUp;
    private Array<TextureRegion> healer_TexturesLeft;
    private Array<TextureRegion> healer_TexturesRight;
    private Array<TextureRegion> healer_TextureDown;;
    private Array<TextureRegion> healer_atk_TexturesUp;
    private Array<TextureRegion> healer_atk_TexturesLeft;
    private Array<TextureRegion> healer_atk_TexturesRight;
    private Array<TextureRegion> healer_atk_TextureDown;
    private Array<TextureRegion> moine_TexturesUp;
    private Array<TextureRegion> moine_TexturesLeft;
    private Array<TextureRegion> moine_TexturesRight;
    private Array<TextureRegion> moine_TextureDown;
    private Array<TextureRegion> moine_atk_TexturesUp;
    private Array<TextureRegion> moine_atk_TexturesLeft;
    private Array<TextureRegion> moine_atk_TexturesRight;
    private Array<TextureRegion> moine_atk_TextureDown;
    private Array<TextureRegion> rogue_TexturesUp;
    private Array<TextureRegion> rogue_TexturesLeft;
    private Array<TextureRegion> rogue_TexturesRight;
    private Array<TextureRegion> rogue_TextureDown;
    private Array<TextureRegion> rogue_atk_TexturesUp;
    private Array<TextureRegion> rogue_atk_TexturesLeft;
    private Array<TextureRegion> rogue_atk_TexturesRight;
    private Array<TextureRegion> rogue_atk_TextureDown;
    private Array<TextureRegion> mage_TexturesUp;
    private Array<TextureRegion> mage_TexturesLeft;
    private Array<TextureRegion> mage_TexturesRight;
    private Array<TextureRegion> mage_TextureDown;
    private Array<TextureRegion> mage_atk_TexturesUp;
    private Array<TextureRegion> mage_atk_TexturesLeft;
    private Array<TextureRegion> mage_atk_TexturesRight;
    private Array<TextureRegion> mage_atk_TextureDown;
    private Array<TextureRegion> slash_left;
    private Array<TextureRegion> slash_right;
    private Array<TextureRegion> atk_mage;
    private Array<TextureRegion> atk_moine_left;
    private Array<TextureRegion> atk_moine_right;
    private Array<TextureRegion> atk_rogue_left;
    private Array<TextureRegion> atk_rogue_right;
    private Array<TextureRegion> heal_left;
    private Array<TextureRegion> heal_right;
    private Array<TextureRegion> sort_eau;
    private Array<TextureRegion> sort_feu;
    private Array<TextureRegion> atk_cac_mob_left;
    private Array<TextureRegion> atk_cac_mob_right;


    private Array<TextureRegion> projectileTexture;
    private TextureRegion barBack, barRed, barBlue;

    private Cell cells[];
    private Array<Mob> mobs = new Array<Mob>();
    private Array<Unit> units = new Array<Unit>();
    private Array<Spell> spells = new Array<Spell>();
    private Array<Tower> towers = new Array<Tower>();

    private Player player = new Player();
    private Stage uiStage;


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
        bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");

        textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        initTextures();

        barBack = new TextureRegion(new Texture(Gdx.files.internal("barBack.png")));
        barRed = new TextureRegion(new Texture(Gdx.files.internal("barRed.png")));
        barBlue = new TextureRegion(new Texture(Gdx.files.internal("barBlue.png")));

        //static chemin
        Array<Integer> trajet = new Array<Integer>();
        for(int i=5 ; i<160 ; i+=10 ){
            trajet.add(i);
        }

        world = new World( solTextures, cheminTextures, trajet);
        chemin = world.getChemin();
        cells = new Cell[chemin.length];
        for(int i=0 ; i<chemin.length ; i++){
            cells[i] = new  Cell(i);
        }

        mobs.add(new Slime(slime_TexturesLeft,slime_TexturesRight,slime_TexturesUp,slime_TexturesUp,1,this));
        towers.add(new FastTower(towerSpeedTextures,this,1,world.getXcase(24),world.getYcase(24), barBack, barBlue));

        /////////////////////////////////////  USER INTERFACE  /////////////////////////////////////
        TextureRegion buttonUpTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonUp.png")));
        TextureRegion buttonDownTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonDown.png")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                null,
                bitmapFont
        );
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        float nameScale = 0.5f;
        float textScale = 0.4f;
        float columnShift = 6;
        float column1 = 64;
        float column2 = 128;
        float column3 = 192;
        float column4 = 256;
        float column5 = 320;
        float row1 = 70;
        float row2 = 50;
        float row3 = 30;

        uiStage = new Stage(viewport);
        Gdx.input.setInputProcessor(uiStage);

        Label nameLabel = new Label(player.getNom(), labelStyle);
        nameLabel.setFontScale(nameScale);
        nameLabel.setPosition(TowerDefenseGame.WORLD_WIDTH / 2 + ((nameLabel.getWidth() / 2) * nameScale), row1, Align.center);
        uiStage.addActor(nameLabel);

        PlayerBar playerBar = new PlayerBar(player, 0, TowerDefenseGame.WORLD_WIDTH, row2, bitmapFont, textScale,
                barBack, barRed, barBlue);
        uiStage.addActor(playerBar);

        TextButton uiButton1 = new TextButton("Chevalier", textButtonStyle);
        uiButton1.setTransform(true);
        uiButton1.setScale(textScale);
        uiButton1.setPosition(column1 + columnShift, row3, Align.center);
        uiButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.depenserMana(1);
                //
            }
        });
        uiStage.addActor(uiButton1);

        TextButton uiButton2 = new TextButton("Mage", textButtonStyle);
        uiButton2.setTransform(true);
        uiButton2.setScale(textScale);
        uiButton2.setPosition(column2 + columnShift, row3, Align.center);
        uiButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.depenserMana(1);
                //
            }
        });
        uiStage.addActor(uiButton2);

        TextButton uiButton3 = new TextButton("Moine", textButtonStyle);
        uiButton3.setTransform(true);
        uiButton3.setScale(textScale);
        uiButton3.setPosition(column3 + columnShift, row3, Align.center);
        uiButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.depenserMana(1);
                //
            }
        });
        uiStage.addActor(uiButton3);

        TextButton uiButton4 = new TextButton("Rogue", textButtonStyle);
        uiButton4.setTransform(true);
        uiButton4.setScale(textScale);
        uiButton4.setPosition(column4 + columnShift, row3, Align.center);
        uiButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.depenserMana(1);
                //
            }
        });
        uiStage.addActor(uiButton4);

        TextButton uiButton5 = new TextButton("Healer", textButtonStyle);
        uiButton5.setTransform(true);
        uiButton5.setScale(textScale);
        uiButton5.setPosition(column5 + columnShift, row3, Align.center);
        uiButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.depenserMana(1);
                //
            }
        });
        uiStage.addActor(uiButton5);
        ////////////////////////////////////////////////////////////////////////////////////////////

        timer = new Date().getTime() + 5000;
        nbMonster = 5 + lvlStage /2;
        monsterCreate = 0;
        numWave = 1;
    }

    public void initTextures(){
        solTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            solTextures.add(textureAtlas.findRegion("herbe"));
        }
        cheminTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            cheminTextures.add(textureAtlas.findRegion("sol"));
        }
        slime_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            slime_TexturesUp.add(textureAtlas.findRegion("slimeVertical"+i));
        }
        slime_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            slime_TexturesLeft.add(textureAtlas.findRegion("slimeGauche"+i));
        }
        slime_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            slime_TexturesRight.add(textureAtlas.findRegion("slimeDroite"+i));
        }
        towerSpeedTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            towerSpeedTextures.add(textureAtlas.findRegion("tour"));
        }
        projectileTexture = new Array<TextureRegion>();
        for(int i=1; i<2 ; i++){
            projectileTexture.add(textureAtlas.findRegion("projectile"+i));
        }
        chevalier_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            chevalier_TextureDown.add(textureAtlas.findRegion("chevalier_down ("+i+")"));
        }
        chevalier_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            chevalier_TexturesUp.add(textureAtlas.findRegion("chevalier_up ("+i+")"));
        }
        chevalier_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            chevalier_TexturesLeft.add(textureAtlas.findRegion("chevalier_left ("+i+")"));
        }
        chevalier_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            chevalier_TexturesRight.add(textureAtlas.findRegion("chevalier_right ("+i+")"));
        }
        chevalier_atk_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            chevalier_atk_TextureDown.add(textureAtlas.findRegion("chevalier_atc_down ("+i+")"));
        }
        chevalier_atk_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            chevalier_atk_TexturesUp.add(textureAtlas.findRegion("chevalier_atc_up ("+i+")"));
        }
        chevalier_atk_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            chevalier_atk_TexturesLeft.add(textureAtlas.findRegion("chevalier_atc_left ("+i+")"));
        }
        chevalier_atk_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            chevalier_atk_TexturesRight.add(textureAtlas.findRegion("chevalier_atc_right ("+i+")"));
        }
        mage_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            mage_TextureDown.add(textureAtlas.findRegion("mage_down ("+i+")"));
        }
        mage_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            mage_TexturesUp.add(textureAtlas.findRegion("mage_up ("+i+")"));
        }
        mage_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            mage_TexturesLeft.add(textureAtlas.findRegion("mage_left ("+i+")"));
        }
        mage_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            mage_TexturesRight.add(textureAtlas.findRegion("mage_right ("+i+")"));
        }
        mage_atk_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mage_atk_TextureDown.add(textureAtlas.findRegion("mage_atc_down ("+i+")"));
        }
        mage_atk_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mage_atk_TexturesUp.add(textureAtlas.findRegion("mage_atc_up ("+i+")"));
        }
        mage_atk_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mage_atk_TexturesLeft.add(textureAtlas.findRegion("mage_atc_left ("+i+")"));
        }
        mage_atk_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mage_atk_TexturesRight.add(textureAtlas.findRegion("mage_atc_right ("+i+")"));
        }
        healer_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            healer_TextureDown.add(textureAtlas.findRegion("healer_down ("+i+")"));
        }
        healer_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            healer_TexturesUp.add(textureAtlas.findRegion("healer_up ("+i+")"));
        }
        healer_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            healer_TexturesLeft.add(textureAtlas.findRegion("healer_left ("+i+")"));
        }
        healer_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            healer_TexturesRight.add(textureAtlas.findRegion("healer_right ("+i+")"));
        }
        healer_atk_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            healer_atk_TextureDown.add(textureAtlas.findRegion("healer_atc_down ("+i+")"));
        }
        healer_atk_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            healer_atk_TexturesUp.add(textureAtlas.findRegion("healer_atc_up ("+i+")"));
        }
        healer_atk_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            healer_atk_TexturesLeft.add(textureAtlas.findRegion("healer_atc_left ("+i+")"));
        }
        healer_atk_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            healer_atk_TexturesRight.add(textureAtlas.findRegion("healer_atc_right ("+i+")"));
        }
        moine_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            moine_TextureDown.add(textureAtlas.findRegion("moine_down ("+i+")"));
        }
        moine_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            moine_TexturesUp.add(textureAtlas.findRegion("moine_up ("+i+")"));
        }
        moine_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            moine_TexturesLeft.add(textureAtlas.findRegion("moine_left ("+i+")"));
        }
        moine_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            moine_TexturesRight.add(textureAtlas.findRegion("moine_right ("+i+")"));
        }
        moine_atk_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            moine_atk_TextureDown.add(textureAtlas.findRegion("moine_atc_down ("+i+")"));
        }
        moine_atk_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            moine_atk_TexturesUp.add(textureAtlas.findRegion("moine_atc_up ("+i+")"));
        }
        moine_atk_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            moine_atk_TexturesLeft.add(textureAtlas.findRegion("moine_atc_left ("+i+")"));
        }
        moine_atk_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            moine_atk_TexturesRight.add(textureAtlas.findRegion("moine_atc_right ("+i+")"));
        }
        rogue_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            rogue_TextureDown.add(textureAtlas.findRegion("rogue_down ("+i+")"));
        }
        rogue_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            rogue_TexturesUp.add(textureAtlas.findRegion("rogue_up ("+i+")"));
        }
        rogue_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            rogue_TexturesLeft.add(textureAtlas.findRegion("rogue_left ("+i+")"));
        }
        rogue_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            rogue_TexturesRight.add(textureAtlas.findRegion("rogue_right ("+i+")"));
        }
        rogue_atk_TextureDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            rogue_atk_TextureDown.add(textureAtlas.findRegion("rogue_atc_down ("+i+")"));
        }
        rogue_atk_TexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            rogue_atk_TexturesUp.add(textureAtlas.findRegion("rogue_atc_up ("+i+")"));
        }
        rogue_atk_TexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            rogue_atk_TexturesLeft.add(textureAtlas.findRegion("rogue_atc_left ("+i+")"));
        }
        rogue_atk_TexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            rogue_atk_TexturesRight.add(textureAtlas.findRegion("rogue_atc_right ("+i+")"));
        }
        slash_left = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            slash_left.add(textureAtlas.findRegion("slash_left ("+i+")"));
        }
        slash_right = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            slash_right.add(textureAtlas.findRegion("slash_right ("+i+")"));
        }
        atk_mage = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atk_mage.add(textureAtlas.findRegion("atk_mage ("+i+")"));
        }
        atk_moine_left = new Array<TextureRegion>();
        for(int i=1 ; i<10 ; i++){
            atk_moine_left.add(textureAtlas.findRegion("atk_moine_left ("+i+")"));
        }
        atk_moine_right = new Array<TextureRegion>();
        for(int i=1 ; i<10 ; i++){
            atk_moine_right.add(textureAtlas.findRegion("atk_moine_right ("+i+")"));
        }
        atk_rogue_left = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atk_rogue_left.add(textureAtlas.findRegion("atk_rogue_left ("+i+")"));
        }
        atk_rogue_right = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atk_rogue_right.add(textureAtlas.findRegion("atk_rogue_right ("+i+")"));
        }
        atk_cac_mob_left = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atk_cac_mob_left.add(textureAtlas.findRegion("atk_cac_mob_left ("+i+")"));
        }
        atk_cac_mob_right = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atk_cac_mob_right.add(textureAtlas.findRegion("atk_cac_mob_right ("+i+")"));
        }
        heal_left = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            heal_left.add(textureAtlas.findRegion("heale_left ("+i+")"));
        }
        heal_right = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            heal_right.add(textureAtlas.findRegion("heale_right ("+i+")"));
        }
        sort_eau = new Array<TextureRegion>();
        for(int i=1 ; i<9 ; i++){
            sort_eau.add(textureAtlas.findRegion("sort_eau ("+i+")"));
        }
        sort_feu = new Array<TextureRegion>();
        for(int i=1 ; i<9 ; i++){
            sort_feu.add(textureAtlas.findRegion("sort_feu ("+i+")"));
        }


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    private void update(float delta) {
        updateCells();
        updateMobs(delta);
        updateTowers(delta);
        updateUnits(delta);
        updateSpells(delta);

        uiStage.act(delta);
    }

    private void updateSpells(float delta){
        for(int i=0 ; i<spells.size ; i++){
            spells.get(i).update(delta);
        }
    }

    private void updateTowers(float delta){
        for(int i=0 ; i<towers.size ; i++){
            towers.get(i).update(delta);
        }
    }

    private void updateMobs(float delta){
        for(int i=0 ; i<mobs.size ; i++){
            mobs.get(i).update(delta);
        }
    }

    private void updateUnits(float delta){
        for(int i=0 ; i<units.size ; i++){
            units.get(i).update(delta);
        }
    }


    private void updateCells(){
        for(int i=0 ; i<cells.length ; i++){
            cells[i].removeAll();
        }

        for(int i=0 ; i<mobs.size ; i++){
            Mob mob = mobs.get(i);
            cells[mob.getCurrentCase()].addMob(mob);
        }
        for (int i=0 ; i<units.size ; i++){
            Unit unit = units.get(i);
            //cells[]
        }
    }

    private void createMob(){
        if(new  Date().getTime() > timer && monsterCreate <= nbMonster){
            int rand = MathUtils.random(7);
            switch (rand){
                case 0:mobs.add(new Slime(slime_TexturesLeft,slime_TexturesRight,slime_TexturesUp,slime_TexturesUp,1,this));
                    monsterCreate++;
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
        }
    }

    public boolean getVision(int numCell){
         return cells[numCell].getVision();
     }

    public boolean testCase(int num , int type){
        return cells[num].testCell(type);
    }

    public boolean getTargetUnit(Mob mob){
        Unit target = null;
        for(int i=mob.getPo() ; i>0 ; i--){
            if(mob.getCurrentCase()-i>0){
                if(cells[mob.getCurrentCase()-i].getUnit() != null){
                    target = cells[mob.getCurrentCase()-i].getUnit();
                }
            }
        }
        if(target != null) {
            int direction;
            float difX = chemin[mob.getCurrentCase()].getX() - chemin[target.getCurrentCase()].getX();
            float difY = chemin[mob.getCurrentCase()].getY() - chemin[target.getCurrentCase()].getY();

            if((difX*difX)<(difY*difY)){
                if(difX>0){
                    direction = 1;
                }else{
                    direction = 2;
                }
            }else {
                if(difY>0){
                    direction = 3;
                }else{
                    direction = 4;
                }
            }


            if (chemin[mob.getCurrentCase()].getX() > chemin[target.getCurrentCase()].getX()) {
                direction = 1;
            } else if (chemin[mob.getCurrentCase()].getX() < chemin[target.getCurrentCase()].getX()) {
                direction = 2;
            } else if (chemin[mob.getCurrentCase()].getY() > chemin[target.getCurrentCase()].getY()) {
                direction = 3;
            } else {
                direction = 4;
            }

            mob.setDirection(direction);
            return true;
        }
        return false;
    }

    public boolean getTargetHeal(Unit unit){
        Unit target = null;
        for(int i=unit.getPo() ; i>0 ; i--){
            if(unit.getCurrentCase()+i<chemin.length-1){
                if(cells[unit.getCurrentCase()-i].getHeal() != null){
                    target = cells[unit.getCurrentCase()-i].getHeal();
                }
            }
        }
        if(target != null) {
            int direction;
            float difX = chemin[unit.getCurrentCase()].getX() - chemin[target.getCurrentCase()].getX();
            float difY = chemin[unit.getCurrentCase()].getY() - chemin[target.getCurrentCase()].getY();

            if((difX*difX)<(difY*difY)){
                if(difX>0){

                }else{

                }
            }else {
                if(difY>0){

                }else{

                }
            }

            if (chemin[unit.getCurrentCase()].getX() > chemin[target.getCurrentCase()].getX()) {
                direction = 1;
            } else if (chemin[unit.getCurrentCase()].getX() < chemin[target.getCurrentCase()].getX()) {
                direction = 2;
            } else if (chemin[unit.getCurrentCase()].getY() > chemin[target.getCurrentCase()].getY()) {
                direction = 3;
            } else {
                direction = 4;
            }

            unit.setDirection(direction);
            return true;
        }
        return false;
    }

    public boolean getTargetMob(Unit unit){
        Mob target = null;
        for(int i=unit.getPo() ; i>0 ; i--){
            if(unit.getCurrentCase()+i<chemin.length-1){
                if(cells[unit.getCurrentCase()+i].getMob() != null){
                    target = cells[unit.getCurrentCase()+i].getMob();
                }
            }
        }
        if(target != null) {
            int direction;
            float difX = chemin[unit.getCurrentCase()].getX() - chemin[target.getCurrentCase()].getX();
            float difY = chemin[unit.getCurrentCase()].getY() - chemin[target.getCurrentCase()].getY();

            if((difX*difX)<(difY*difY)){
                if(difX>0){

                }else{

                }
            }else {
                if(difY>0){

                }else{

                }
            }

            if (chemin[unit.getCurrentCase()].getX() > chemin[target.getCurrentCase()].getX()) {
                direction = 1;
            } else if (chemin[unit.getCurrentCase()].getX() < chemin[target.getCurrentCase()].getX()) {
                direction = 2;
            } else if (chemin[unit.getCurrentCase()].getY() > chemin[target.getCurrentCase()].getY()) {
                direction = 3;
            } else {
                direction = 4;
            }
            unit.setDirection(direction);
            return true;
        }
        return false;
    }

    public void getTargetMobTower(Tower tower, int cell, int type){

        switch (type) {
            case 1:
                spells.add(new TowerProjectile(projectileTexture.get(0), tower, cells, cells[cell].getMob(), 1));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }

    }

    public Rectangle[] getChemin(){
        return chemin;
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
        drawSpells();
        drawMobs();
        drawUnits();
        drawTowers();
        batch.end();

        uiStage.draw();
    }

    private void drawTowers(){
        for(int i=0 ; i<towers.size ; i++){
            towers.get(i).draw(batch);
        }
    }

    private void drawMobs(){
        for(int i=0 ; i<mobs.size ; i++){
            if(mobs.get(i).draw(batch)){

                mobs.removeIndex(i);
            }
        }
    }

    private void drawUnits(){
        for(int i=0 ; i<units.size ; i++){
            if(units.get(i).draw(batch)){
                units.removeIndex(i);
            }
        }
    }

    private void drawSpells(){
        for(int i=0 ; i<spells.size ; i++){
            if(spells.get(i).draw(batch)){
                spells.removeIndex(i);
            }
        }
    }

    private void teckWave(){
        if(mobs.size == 0){
            timer = new Date().getTime()+5000;
            monsterCreate = 0;
            numWave++;
        }
    }

}
