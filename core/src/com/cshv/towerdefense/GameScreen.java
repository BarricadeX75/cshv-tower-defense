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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Mobs.Slime;
import com.cshv.towerdefense.Towers.SpeedTower;
import com.cshv.towerdefense.Units.Unit;


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
    private Slime slime;
    private SpeedTower tower;

    private Array<TextureRegion> solTextures;
    private Array<TextureRegion> cheminTextures;
    private Array<TextureRegion> towerSpeedTextures;
    private Array<TextureRegion> slimeTexturesUp;
    private Array<TextureRegion> slimeTexturesLeft;
    private Array<TextureRegion> slimeTexturesRight;

    private Cell cells[];
    private Array<Mob> mobs = new Array<Mob>();
    private Array<Unit> units = new Array<Unit>();

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
        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        solTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            solTextures.add(textureAtlas.findRegion("herbe"));
        }
        cheminTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            cheminTextures.add(textureAtlas.findRegion("sol"));
        }
        slimeTexturesUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            slimeTexturesUp.add(textureAtlas.findRegion("slimeVertical"+i));
        }
        slimeTexturesLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            slimeTexturesLeft.add(textureAtlas.findRegion("slimeGauche"+i));
        }
        slimeTexturesRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            slimeTexturesRight.add(textureAtlas.findRegion("slimeDroite"+i));
        }
        towerSpeedTextures = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            towerSpeedTextures.add(textureAtlas.findRegion("tour"));
        }

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
        slime = new Slime(slimeTexturesLeft,slimeTexturesRight,slimeTexturesUp,slimeTexturesUp,1,this);
        tower = new SpeedTower(towerSpeedTextures,this,1);
        tower.setPosition(world.getXcase(24),world.getYcase(24));

        /////////////////////////////////////  USER INTERFACE  /////////////////////////////////////
        TextureRegion buttonUpTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonUp.png")));
        TextureRegion buttonDownTexture = new TextureRegion(new Texture(Gdx.files.internal("buttonDown.png")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                null,
                bitmapFont
        );
        float textScale = 0.4f;
        float column1 = 150;
        float column2 = 250;
        float row1 = 70;
        float row2 = 55;
        float row3 = 30;

        uiStage = new Stage(viewport);
        Gdx.input.setInputProcessor(uiStage);

        PlayerBar playerBar = new PlayerBar(player, 0, TowerDefenseGame.WORLD_WIDTH, row1);
        uiStage.addActor(playerBar);

        TextButton uiButton1 = new TextButton("Unité 1", textButtonStyle);
        uiButton1.setTransform(true);
        uiButton1.setScale(textScale);
        uiButton1.setPosition(column1, row2, Align.center);
        uiButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.recevoirDegats(1);
            }
        });
        uiStage.addActor(uiButton1);

        TextButton uiButton2 = new TextButton("Unité 2", textButtonStyle);
        uiButton2.setTransform(true);
        uiButton2.setScale(textScale);
        uiButton2.setPosition(column2, row2, Align.center);
        uiButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                player.depenserMana(1);
            }
        });
        uiStage.addActor(uiButton2);

        TextButton uiButton3 = new TextButton("Unité 3", textButtonStyle);
        uiButton3.setTransform(true);
        uiButton3.setScale(textScale);
        uiButton3.setPosition(column1, row3, Align.center);
        uiStage.addActor(uiButton3);

        TextButton uiButton4 = new TextButton("Unité 4", textButtonStyle);
        uiButton4.setTransform(true);
        uiButton4.setScale(textScale);
        uiButton4.setPosition(column2, row3, Align.center);
        uiStage.addActor(uiButton4);
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    private void update(float delta) {
        slime.update(delta);
        tower.update(delta);
        updateCells();

        uiStage.act(delta);
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

    public boolean getVision(int numCell){
         return cells[numCell].getVision();
     }

    public boolean testCase(int num , int type){
        return cells[num].testCell(type);
    }

    public void getTargetUnit(Mob mob){
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
        }
    }

    public void getTargetHeal(Unit unit){
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
        }
    }

    public void getTargetMob(Unit unit){
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
        slime.draw(batch);
        tower.draw(batch);
        batch.end();

        uiStage.draw();
    }

}
