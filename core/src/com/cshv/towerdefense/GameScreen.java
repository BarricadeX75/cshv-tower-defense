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
import com.cshv.towerdefense.Spells.HealSpell;
import com.cshv.towerdefense.Spells.MagicSpell;
import com.cshv.towerdefense.Spells.MobProjectile;
import com.cshv.towerdefense.Spells.SlashSpell;
import com.cshv.towerdefense.Spells.Spell;
import com.cshv.towerdefense.Spells.TowerProjectile;
import com.cshv.towerdefense.Towers.FastTower;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Units.Chevalier;
import com.cshv.towerdefense.Units.Healer;
import com.cshv.towerdefense.Units.Mage;
import com.cshv.towerdefense.Units.Moine;
import com.cshv.towerdefense.Units.Rogue;
import com.cshv.towerdefense.Units.Unit;

import java.util.Date;


public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private final TowerDefenseGame towerDefenseGame;
    private Viewport viewport;
    private Camera camera;
    private BitmapFont bitmapFont;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private World world;
    private Rectangle[] chemin;
    private int lvlStage = 1;
    private int nbMonster;
    private int monsterCreate;
    private long timer;
    private int numWave;

    private TextureLoader tl;

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
        tl = new TextureLoader(textureAtlas);

        //static chemin
        Array<Integer> trajet = new Array<Integer>();
        for(int i=5 ; i<160 ; i+=10 ){
            trajet.add(i);
        }

        world = new World(tl.getSol(), tl.getChemin(), trajet);
        chemin = world.getChemin();
        cells = new Cell[chemin.length];
        for(int i=0 ; i<chemin.length ; i++){
            cells[i] = new  Cell(i);
        }

        mobs.add(new Slime(tl.getMobSlimeLeft()[0], tl.getMobSlimeRight()[0],
                tl.getMobSlimeUp()[0], tl.getMobSlimeDown()[0], 1, this));
        towers.add(new FastTower(tl.getTowerSpeed(), this, 1, world.getXcase(24), world.getYcase(24),
                tl.getBarBack(), tl.getBarBlue()));

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
        float columnShift = 15;
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
                tl.getBarBack(), tl.getBarRed(), tl.getBarBlue());
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
                createUnit(Unit.CHEVALIER);
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
                createUnit(Unit.MAGE);
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
                createUnit(Unit.MOINE);
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
                createUnit(Unit.ROGUE);
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
                createUnit(Unit.HEALER);
            }
        });
        uiStage.addActor(uiButton5);
        ////////////////////////////////////////////////////////////////////////////////////////////

        timer = new Date().getTime() + 5000;
        nbMonster = 5 + lvlStage /2;
        monsterCreate = 0;
        numWave = 1;
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        uiStage.dispose();
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
            cells[unit.getCurrentCase()].addUnit(unit);
        }
    }

    private void createUnit(int unit) {
        switch (unit) {
        case Unit.CHEVALIER:
            units.add(new Chevalier(tl.getUnitChevalierLeft(), tl.getUnitChevalierRight(),
                    tl.getUnitChevalierUp(), tl.getUnitChevalierDown(), lvlStage, this));
            break;
        case Unit.MAGE:
            units.add(new Mage(tl.getUnitMageLeft(), tl.getUnitMageRight(),
                    tl.getUnitMageUp(), tl.getUnitMageDown(), lvlStage, this));
            break;
        case Unit.MOINE:
            units.add(new Moine(tl.getUnitMoineLeft(), tl.getUnitMoineRight(),
                    tl.getUnitMoineUp(), tl.getUnitMoineDown(), lvlStage, this));
            break;
        case Unit.ROGUE:
            units.add(new Rogue(tl.getUnitRogueLeft(), tl.getUnitRogueRight(),
                    tl.getUnitRogueUp(), tl.getUnitRogueDown(), lvlStage, this));
            break;
        case Unit.HEALER:
            units.add(new Healer(tl.getUnitHealerLeft(), tl.getUnitHealerRight(),
                    tl.getUnitHealerUp(), tl.getUnitHealerDown(), lvlStage, this));
            break;
        }
    }

    private void createMob(){
        if(new  Date().getTime() > timer && monsterCreate <= nbMonster){
            int rand = MathUtils.random(7);
            switch (rand){
                case 0:
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

            switch(mob.getPo()){
                case 1: spells.add(new SlashSpell(tl.getAtkCacMobLeft(), tl.getAtkCacMobRight(),
                        target, mob, direction, 1));
                    break;
                case 2: spells.add(new MobProjectile(tl.getProjectile().first(), mob, target));
                    break;
                case 3: spells.add(new MagicSpell(tl.getSpellFire(), target, mob, cells, 1));
                    break;
            }

            mob.setDirection(direction);
            return true;
        }

        return false;
    }

    public boolean getTargetHeal(Unit unit){
        Unit target = null;
        for(int i=unit.getPo() ; i>0 ; i--){
            if(unit.getCurrentCase()+i < chemin.length-1){
                if(cells[unit.getCurrentCase()+i].getHeal() != null){
                    target = cells[unit.getCurrentCase()+i].getHeal();

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

            if(direction == 3 || direction == 2){
                spells.add(new HealSpell(tl.getSpellHealRight(), unit, target));
            }else{
                spells.add(new HealSpell(tl.getSpellHealLeft(), unit, target));
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
            switch(unit.getType()){
                case Unit.CHEVALIER: spells.add(new SlashSpell(tl.getAtkChevalierLeft(), tl.getAtkChevalierRight(), unit, target, direction, 2));
                    break;
                case Unit.MAGE: spells.add(new MagicSpell(tl.getAtkMage(), unit, target, cells, 2));
                    break;
                case Unit.MOINE: spells.add(new SlashSpell(tl.getAtkMoineLeft(), tl.getAtkMoineRight(), unit, target, direction, 2));
                    break;
                case Unit.ROGUE: spells.add(new SlashSpell(tl.getAtkRogueLeft(), tl.getAtkRogueRight(), unit, target, direction, 2));
            }
            return true;
        }
        return false;
    }

    public void getTargetMobTower(Tower tower, int cell, int type){
        switch (type) {
            case 1:
                spells.add(new TowerProjectile(tl.getProjectile().first(), tower, cells, cells[cell].getMob(), 1));
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
