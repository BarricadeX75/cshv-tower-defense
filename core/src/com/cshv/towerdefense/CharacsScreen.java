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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Units.Unit;


public class CharacsScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private Stage stage;
    private Player _player;
    private int upgrage[] = new int[9];

    private final TowerDefenseGame towerDefenseGame;


    public CharacsScreen(TowerDefenseGame towerDefenseGame, Player player) {
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

        TextureRegion buttonRightTexture = tl.getFlecheTexture().get(1);
        TextureRegion buttonLeftTexture = tl.getFlecheTexture().get(0);
        TextureRegion validateButton = tl.getControleTexture().get(0);
        TextureRegion cancelButton = tl.getControleTexture().get(1);

        for(int i=0 ; i<9 ; i++){
            upgrage[i] = 0;
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        float nameScale = 0.5f;
        float textScale = 0.4f;
        float padding = 55f;
        Gdx.input.setInputProcessor(stage);

        ImageButton moinChevalier = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinChevalier.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.CHEVALIER);
            }
        });
        moinChevalier.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding  , Align.center );
        moinChevalier.setBounds( moinChevalier.getX() , moinChevalier.getY() , moinChevalier.getWidth() / 2 , moinChevalier.getHeight() / 2 );
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
        plusChevalier.setBounds( plusChevalier.getX() , plusChevalier.getY() , plusChevalier.getWidth() / 2 , plusChevalier.getHeight() / 2 );
        stage.addActor( plusChevalier );


        ImageButton moinMoine = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinMoine.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.MOINE);
            }
        });
        moinMoine.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*2  , Align.center );
        moinMoine.setBounds( moinMoine.getX() , moinMoine.getY() , moinMoine.getWidth() / 2 , moinMoine.getHeight() / 2 );
        stage.addActor( moinMoine );
        ImageButton plusMoine = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusMoine.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.MOINE);
            }
        });
        plusMoine.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*2  , Align.center );
        plusMoine.setBounds( plusMoine.getX() , plusMoine.getY() , plusMoine.getWidth() / 2 , plusMoine.getHeight() / 2 );
        stage.addActor( plusMoine );


        ImageButton moinRogue = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinRogue.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.ROGUE);
            }
        });
        moinRogue.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*3  , Align.center );
        moinRogue.setBounds( moinRogue.getX() , moinRogue.getY() , moinRogue.getWidth() / 2 , moinRogue.getHeight() / 2 );
        stage.addActor( moinRogue );
        ImageButton plusRogue = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusRogue.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.ROGUE);
            }
        });
        plusRogue.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*3  , Align.center );
        plusRogue.setBounds( plusRogue.getX() , plusRogue.getY() , plusRogue.getWidth() / 2 , plusRogue.getHeight() / 2 );
        stage.addActor( plusRogue );

        ImageButton moinMage = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinMage.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.MAGE);
            }
        });
        moinMage.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*4  , Align.center );
        moinMage.setBounds( moinMage.getX() , moinMage.getY() , moinMage.getWidth() / 2 , moinMage.getHeight() / 2 );
        stage.addActor( moinMage );
        ImageButton plusMage = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        plusMage.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeUnit(Unit.MAGE);
            }
        });
        plusMage.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*4  , Align.center );
        plusMage.setBounds( plusMage.getX() , plusMage.getY() , plusMage.getWidth() / 2 , plusMage.getHeight() / 2 );
        stage.addActor( plusMage );

        ImageButton moinHeal = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        moinHeal.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeUnit(Unit.HEALER);
            }
        });
        moinHeal.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*5  , Align.center );
        moinHeal.setBounds( moinHeal.getX() , moinHeal.getY() , moinHeal.getWidth() / 2 , moinHeal.getHeight() / 2 );
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
        plusHeale.setBounds( plusHeale.getX() , plusHeale.getY() , plusHeale.getWidth() / 2 , plusHeale.getHeight() / 2 );
        stage.addActor( plusHeale );

        ImageButton zoneTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        zoneTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.ZONE_TOWER);
            }
        });
        zoneTowerMoin.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*6  , Align.center );
        zoneTowerMoin.setBounds( zoneTowerMoin.getX() , zoneTowerMoin.getY() , zoneTowerMoin.getWidth() / 2 , zoneTowerMoin.getHeight() / 2 );
        stage.addActor( zoneTowerMoin );
        ImageButton zoneTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        zoneTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.ZONE_TOWER);
            }
        });
        zoneTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*6  , Align.center );
        zoneTowerPlus.setBounds( zoneTowerPlus.getX() , zoneTowerPlus.getY() , zoneTowerPlus.getWidth() / 2 , zoneTowerPlus.getHeight() / 2 );
        stage.addActor( zoneTowerPlus );

        ImageButton visionTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        visionTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.VISION_TOWER);
            }
        });
        visionTowerMoin.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*7  , Align.center );
        visionTowerMoin.setBounds( visionTowerMoin.getX() , visionTowerMoin.getY() , visionTowerMoin.getWidth() / 2 , visionTowerMoin.getHeight() / 2 );
        stage.addActor( visionTowerMoin );
        ImageButton visionTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        visionTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.VISION_TOWER);
            }
        });
        visionTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*7  , Align.center );
        visionTowerPlus.setBounds( visionTowerPlus.getX() , visionTowerPlus.getY() , visionTowerPlus.getWidth() / 2 , visionTowerPlus.getHeight() / 2 );
        stage.addActor( visionTowerPlus );

        ImageButton fastTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        fastTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.FAST_TOWER);
            }
        });
        fastTowerMoin.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*8  , Align.center );
        fastTowerMoin.setBounds( fastTowerMoin.getX() , fastTowerMoin.getY() , fastTowerMoin.getWidth() / 2 , fastTowerMoin.getHeight() / 2 );
        stage.addActor( fastTowerMoin );
        ImageButton fastTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        fastTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.FAST_TOWER);
            }
        });
        fastTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*8  , Align.center );
        fastTowerPlus.setBounds( fastTowerPlus.getX() , fastTowerPlus.getY() , fastTowerPlus.getWidth() / 2 , fastTowerPlus.getHeight() / 2 );
        stage.addActor( fastTowerPlus );

        ImageButton slowTowerMoin = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonLeftTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonLeftTexture ) ) );
        slowTowerMoin.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                downgradeTower(Tower.SLOW_TOWER);
            }
        });
        slowTowerMoin.setPosition( ( 2* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*9  , Align.center );
        slowTowerMoin.setBounds( slowTowerMoin.getX() , slowTowerMoin.getY() , slowTowerMoin.getWidth() / 2 , slowTowerMoin.getHeight() / 2 );
        stage.addActor( slowTowerMoin );
        ImageButton slowTowerPlus = new ImageButton(new TextureRegionDrawable(new TextureRegion( buttonRightTexture ) ), new TextureRegionDrawable( new TextureRegion( buttonRightTexture ) ) );
        slowTowerPlus.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                upgradeTower(Tower.SLOW_TOWER);
            }
        });
        slowTowerPlus.setPosition( (( 7 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*9  , Align.center );
        slowTowerPlus.setBounds( slowTowerPlus.getX() , slowTowerPlus.getY() , slowTowerPlus.getWidth() / 2 , slowTowerPlus.getHeight() / 2 );
        stage.addActor( slowTowerPlus );

        ImageButton valid = new ImageButton(new TextureRegionDrawable(new TextureRegion( validateButton ) ), new TextureRegionDrawable( new TextureRegion( validateButton ) ) );
        valid.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

            }
        });
        valid.setPosition( ( 3* WORLD_WIDTH ) / 8, WORLD_HEIGHT-padding*10  , Align.center );
        valid.setBounds( valid.getX() , valid.getY() , valid.getWidth() / 2 , valid.getHeight() / 2 );
        stage.addActor( valid );
        ImageButton cancel = new ImageButton(new TextureRegionDrawable(new TextureRegion( cancelButton ) ), new TextureRegionDrawable( new TextureRegion( cancelButton ) ) );
        cancel.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

            }
        });
        cancel.setPosition( (( 6 * WORLD_WIDTH ) / 8)+5, WORLD_HEIGHT-padding*10  , Align.center );
        cancel.setBounds( cancel.getX() , cancel.getY() , cancel.getWidth() / 2 , cancel.getHeight() / 2 );
        stage.addActor( cancel );

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

    private void upgradeUnit(int unit){
        switch (unit){
            case Unit.CHEVALIER: upgrage[unit-1]++;
                break;
            case Unit.MOINE: upgrage[unit-1]++;
                break;
            case Unit.MAGE: upgrage[unit-1]++;
                break;
            case Unit.HEALER: upgrage[unit-1]++;
                break;
            case Unit.ROGUE: upgrage[unit-1]++;
                break;
        }
    }

    public void upgradeTower(int tower){
        switch (tower){
            case Tower.FAST_TOWER: upgrage[tower+4]++;
                break;
            case Tower.SLOW_TOWER: upgrage[tower+4]++;
                break;
            case Tower.ZONE_TOWER: upgrage[tower+4]++;
                break;
            case Tower.VISION_TOWER: upgrage[tower+4]++;
                break;
        }
    }

    private void downgradeUnit(int unit){
        switch (unit){
            case Unit.CHEVALIER:
                if(upgrage[unit-1]>0){
                    upgrage[unit-1]--;
                }
                break;
            case Unit.MOINE:
                if(upgrage[unit-1]>0){
                    upgrage[unit-1]--;
                }
                break;
            case Unit.MAGE:
                if(upgrage[unit-1]>0){
                    upgrage[unit-1]--;
                }
                break;
            case Unit.HEALER:
                if(upgrage[unit-1]>0){
                    upgrage[unit-1]--;
                }
                break;
            case Unit.ROGUE:
                if(upgrage[unit-1]>0){
                    upgrage[unit-1]--;
                }
                break;
        }
    }

    public void downgradeTower(int tower){
        switch (tower){
            case Tower.FAST_TOWER:
                if(upgrage[tower+4]>0) {
                    upgrage[tower + 4]--;
                }
                break;
            case Tower.SLOW_TOWER:
                if(upgrage[tower+4]>0) {
                    upgrage[tower + 4]--;
                }
                break;
            case Tower.ZONE_TOWER:
                if(upgrage[tower+4]>0) {
                    upgrage[tower + 4]--;
                }
                break;
            case Tower.VISION_TOWER:
                if(upgrage[tower+4]>0) {
                    upgrage[tower + 4]--;
                }
                break;
        }
    }


}
