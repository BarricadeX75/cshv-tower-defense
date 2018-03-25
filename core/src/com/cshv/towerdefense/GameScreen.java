package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Mobs.Slime;
import com.cshv.towerdefense.Towers.SpeedTower;
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
        int numCell=0;
        for(int i=mob.getPo() ; i>0 ; i--){
            if(mob.getCurrentCase()-i>0){
                numCell = mob.getCurrentCase()-i;
            }
        }
        Unit target = cells[numCell].getUnit();
        int direction;
        if(chemin[mob.getCurrentCase()].getX()>chemin[target.getCurrentCase()].getX()){
            direction = 1;
        }else if(chemin[mob.getCurrentCase()].getX()<chemin[target.getCurrentCase()].getX()){
            direction = 2;
        }else if(chemin[mob.getCurrentCase()].getY()>chemin[target.getCurrentCase()].getY()){
            direction = 3;
        }else{
            direction = 4;
        }
    }

    public void getTargetMob(Unit unit){

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
    }

}
