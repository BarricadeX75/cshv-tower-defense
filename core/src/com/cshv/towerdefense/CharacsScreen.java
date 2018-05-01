package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Units.Unit;


public class CharacsScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage stage, stageBackground;
    private Viewport viewport;
    private Player _player;
    private int upgrade[] = new int[10];
    private long goldtempo;
    private Label labelCout[] = new Label[10];
    private Label labelLvl[] = new Label[10];
    private Label labelGold;
    private TextureRegion img[];
    private long depense = 0;
    private int cout[];
    private int lvl[];

    private final TowerDefenseGame towerDefenseGame;


    public CharacsScreen(TowerDefenseGame towerDefenseGame, Player player) {
        this.towerDefenseGame = towerDefenseGame;
        _player = player;
    }

    @Override
    public void show() {
        super.show();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stage = new Stage(viewport);
        stageBackground = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        final TextureLoader tl = new TextureLoader(textureAtlas);
        BitmapFont bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");

        Image fondBackground = new Image(tl.getBagroundTexture().get(2));
        fondBackground.setPosition(0,0);
        stageBackground.addActor(fondBackground);

        TextureRegion buttonRightTexture = tl.getFlecheTexture().get(1);
        TextureRegion buttonLeftTexture = tl.getFlecheTexture().get(0);
        TextureRegion validateButton = tl.getControleTexture().get(0);
        TextureRegion cancelButton = tl.getControleTexture().get(1);
        goldtempo = _player.getGold();
        cout = new int[] {40,40,40,40,40,80,80,80,80,120};
        int numSpriteFontaine = _player.getLvlFontaine();
        if(numSpriteFontaine/10 > 3){
            numSpriteFontaine = 3;
        }else{
            numSpriteFontaine = numSpriteFontaine/10;
        }

        int numSpriteTowerZone = _player.getLvlZoneTower();
        if(numSpriteTowerZone/10 > 3){
            numSpriteTowerZone = 3;
        }else{
            numSpriteTowerZone = numSpriteTowerZone/10;
        }

        int numSpriteTowerSlow = _player.getLvlSlowTower();
        if(numSpriteTowerSlow/10 > 3){
            numSpriteTowerSlow = 3;
        }else{
            numSpriteTowerSlow = numSpriteTowerSlow/10;
        }

        int numSpriteTowerFast = _player.getLvlFastTower();
        if(numSpriteTowerFast/10 > 3){
            numSpriteTowerFast = 3;
        }else{
            numSpriteTowerFast = numSpriteTowerFast/10;
        }

        int numSpriteTowerVision = _player.getLvlVisionTower();
        if(numSpriteTowerVision/10 > 3){
            numSpriteTowerVision = 3;
        }else{
            numSpriteTowerVision = numSpriteTowerVision/10;
        }

        img = new TextureRegion[] { tl.getUnitChevalierDown().get(0), tl.getUnitMageDown().get(0), tl.getUnitMoineDown().get(0),tl.getUnitRogueDown().get(0),
                tl.getUnitHealerDown().get(0), tl.getSpriteTowerFast().get(numSpriteTowerFast), tl.getSpriteTowerSlow().get(numSpriteTowerSlow),
                tl.getSpriteTowerZone().get(numSpriteTowerZone), tl.getSpriteTowerVision().get(numSpriteTowerVision), tl.getSpriteFontaine().get(numSpriteFontaine)};
        lvl = new int[] { _player.getLvlChevalier(), _player.getLvlMage(), _player.getLvlMoine(), _player.getLvlRogue(),
                _player.getLvlHealer(), _player.getLvlFastTower(), _player.getLvlSlowTower(), _player.getLvlZoneTower(), _player.getLvlVisionTower(), _player.getLvlFontaine()};


        for(int i=0 ; i<10 ; i++){
            upgrade[i] = 0;
        }

        Label.LabelStyle labelStyleGold = new Label.LabelStyle(bitmapFont, Color.WHITE);
        Label.LabelStyle labelStyleDepence = new Label.LabelStyle(bitmapFont, Color.RED);
        float nameScale = 0.5f;
        float padding = 50f;
        Gdx.input.setInputProcessor(stage);

        ImageButton moinChevalier = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinChevalier.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.CHEVALIER);
            }
        });
        moinChevalier.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding  , Align.center );
        stage.addActor( moinChevalier );
        ImageButton plusChevalier = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusChevalier.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.CHEVALIER);
            }
        });
        plusChevalier.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding  , Align.center );
        stage.addActor( plusChevalier );


        ImageButton moinMage = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinMage.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.MAGE);
            }
        });
        moinMage.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*2  , Align.center );
        stage.addActor( moinMage );
        ImageButton plusMage = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusMage.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.MAGE);
            }
        });
        plusMage.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*2  , Align.center );
        stage.addActor( plusMage );


        ImageButton moinMoine = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinMoine.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.MOINE);
            }
        });
        moinMoine.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*3  , Align.center );
        stage.addActor( moinMoine );
        ImageButton plusMoine = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusMoine.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.MOINE);
            }
        });
        plusMoine.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*3  , Align.center );
        stage.addActor( plusMoine );


        ImageButton moinRogue = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinRogue.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.ROGUE);
            }
        });
        moinRogue.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*4  , Align.center );
        stage.addActor( moinRogue );
        ImageButton plusRogue = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusRogue.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.ROGUE);
            }
        });
        plusRogue.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*4  , Align.center );
        stage.addActor( plusRogue );


        ImageButton moinHeal = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinHeal.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.HEALER);
            }
        });
        moinHeal.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*5  , Align.center );
        stage.addActor( moinHeal );
        ImageButton plusHeale = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusHeale.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.HEALER);
            }
        });
        plusHeale.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*5  , Align.center );
        stage.addActor( plusHeale );


        ImageButton fastTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        fastTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.FAST_TOWER);
            }
        });
        fastTowerMoin.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*6  , Align.center );
        stage.addActor( fastTowerMoin );
        ImageButton fastTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        fastTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.FAST_TOWER);
            }
        });
        fastTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*6  , Align.center );
        stage.addActor( fastTowerPlus );


        ImageButton slowTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        slowTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.SLOW_TOWER);
            }
        });
        slowTowerMoin.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*7 , Align.center );
        stage.addActor( slowTowerMoin );
        ImageButton slowTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        slowTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.SLOW_TOWER);
            }
        });
        slowTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*7 , Align.center );
        stage.addActor( slowTowerPlus );


        ImageButton zoneTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        zoneTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.ZONE_TOWER);
            }
        });
        zoneTowerMoin.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*8 , Align.center );
        stage.addActor( zoneTowerMoin );
        ImageButton zoneTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        zoneTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.ZONE_TOWER);
            }
        });
        zoneTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*8 , Align.center );
        stage.addActor( zoneTowerPlus );


        ImageButton visionTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        visionTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.VISION_TOWER);
            }
        });
        visionTowerMoin.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*9 , Align.center );
        stage.addActor( visionTowerMoin );
        ImageButton visionTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        visionTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.VISION_TOWER);
            }
        });
        visionTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*9 , Align.center );
        stage.addActor( visionTowerPlus );

        ImageButton fontaineMoins = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        fontaineMoins.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(10);
            }
        });
        fontaineMoins.setPosition( ( WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*10  , Align.center );
        stage.addActor( fontaineMoins );
        ImageButton fontainePlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        fontainePlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                 upgradeUnit(10);
            }
        });
        fontainePlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*10  , Align.center );
        stage.addActor( fontainePlus );

        ImageButton valid = new ImageButton(new TextureRegionDrawable(new TextureRegion( validateButton ) ), new TextureRegionDrawable( new TextureRegion( validateButton ) ) );
        valid.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                for(int i=0 ; i<10 ; i++){
                    _player.addStat(upgrade[i],i);
                }
                _player.setGold(goldtempo);
                towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));

            }
        });
        valid.setPosition( (( 5 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*11  , Align.center );
        stage.addActor( valid );
        ImageButton cancel = new ImageButton(new TextureRegionDrawable(new TextureRegion( cancelButton ) ), new TextureRegionDrawable( new TextureRegion( cancelButton ) ) );
        cancel.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                towerDefenseGame.setScreen(new StartScreen(towerDefenseGame,_player));
                dispose();
            }
        });
        cancel.setPosition( ( 3* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*11  , Align.center );
        stage.addActor( cancel );

        for(int i=0 ; i<10 ; i++){
            labelCout[i] = new Label("("+(int)(cout[i]*Math.pow(1.2,lvl[i]-1))+"G)", labelStyleDepence);
            labelCout[i].setFontScale(nameScale);
            labelCout[i].setPosition(((5*WORLD_WIDTH)/8)+40, WORLD_HEIGHT-padding*(i+1), Align.center);
            stage.addActor(labelCout[i]);
        }

        for(int i=0 ; i<10 ; i++){
            Image image = new Image(img[i]);
            image.setPosition(((4*WORLD_WIDTH)/8)-5, WORLD_HEIGHT-padding*(i+1)-2, Align.center);
            stage.addActor(image);
        }

        for(int i=0 ; i<10 ; i++){
            labelLvl[i] = new Label("Lvl: "+lvl[i], labelStyleGold);
            labelLvl[i].setFontScale(nameScale);
            labelLvl[i].setPosition(((3*WORLD_WIDTH)/8), WORLD_HEIGHT-padding*(i+1)-2, Align.center);
            stage.addActor(labelLvl[i]);
        }

        labelGold = new Label( "Gold: "+goldtempo, labelStyleGold);
        labelGold.setFontScale(nameScale);
        labelGold.setPosition(((4*WORLD_WIDTH)/8)+20, WORLD_HEIGHT-15, Align.center);
        stage.addActor(labelGold);
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
        update();
        stage.act(delta);
        stageBackground.draw();
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

    private void upgradeUnit(int unit){
        if(goldtempo >= (int)((cout[unit-1]*Math.pow(1.15,lvl[unit-1]-1))*Math.pow(1.15, upgrade[unit-1]))){
            upgrade[unit - 1]++;
        }

    }

    public void upgradeTower(int tower){
        if(goldtempo >= (int)((cout[tower+4]*Math.pow(1.15,lvl[tower+4]-1))*Math.pow(1.15, upgrade[tower+4]))){
            upgrade[tower+4]++;
        }
    }

    private void downgradeUnit(int unit){
        if(upgrade[unit-1]>0){
            upgrade[unit-1]--;
        }
    }

    public void downgradeTower(int tower){
        if(upgrade[tower+4]>0) {
            upgrade[tower + 4]--;
        }
    }

    public void update(){
        goldtempo = _player.getGold();
        for(int i=0 ; i<10 ; i++){
            if (upgrade[i] > 0)
                labelLvl[i].setColor(Color.GOLD);
            else
                labelLvl[i].setColor(Color.WHITE);

            labelLvl[i].setText("Lvl: " + (lvl[i] + upgrade[i]));
            labelCout[i].setText("("+(int)((cout[i]*Math.pow(1.15,lvl[i]-1))*Math.pow(1.15, upgrade[i]))+"G)");
            for(int j = 0; j< upgrade[i]; j++){
                goldtempo -= (int)((cout[i]*Math.pow(1.15,lvl[i]-1))*Math.pow(1.15,j));
            }
        }

        labelGold.setText("Gold: " + goldtempo);
        depense = goldtempo;
    }



}
