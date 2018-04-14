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
import com.cshv.towerdefense.Mobs.Centaure;
import com.cshv.towerdefense.Mobs.ChienSquelette;
import com.cshv.towerdefense.Mobs.Golem;
import com.cshv.towerdefense.Mobs.Griffon;
import com.cshv.towerdefense.Mobs.Lamia;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Mobs.Orc;
import com.cshv.towerdefense.Mobs.Slime;
import com.cshv.towerdefense.Spells.HealSpell;
import com.cshv.towerdefense.Spells.MagicSpell;
import com.cshv.towerdefense.Spells.MobProjectile;
import com.cshv.towerdefense.Spells.SlashSpell;
import com.cshv.towerdefense.Spells.SlowTowerSpell;
import com.cshv.towerdefense.Spells.Spell;
import com.cshv.towerdefense.Spells.TowerProjectile;
import com.cshv.towerdefense.Spells.ZoneTowerSpell;
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

    private static final int UNITE_CHEVALIER = 0, UNITE_MAGE = 1, UNITE_MOINE = 2, UNITE_ROGUE = 3, UNITE_HEALER = 4;

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
    private TextureLoader tl;
    private int numWave;

    private Cell cells[];
    private Array<Integer> towerList = new Array<Integer>();
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
        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");

        tl = new TextureLoader(textureAtlas);

        //static chemin
        Array<Integer> trajet = new Array<Integer>();
        for(int i=5 ; i<176 ; i+=11 ){
            trajet.add(i);
        }

        world = new World(tl.getSol(), tl.getChemin(), trajet);
        chemin = world.getChemin();
        cells = new Cell[chemin.length];
        for(int i=0 ; i<chemin.length ; i++){
            cells[i] = new  Cell(i);
        }

        //mobs.add(new Slime(tl.getMobSlimeLeft()[0], tl.getMobSlimeRight()[0], tl.getMobSlimeUp()[0], tl.getMobSlimeDown()[0], 1, this));
        //mobs.add(new Slime(tl.getMobCentaureLeft()[0], tl.getMobCentaureRight()[0], tl.getMobCentaureUp()[0], tl.getMobCentaureDown()[0], 1, this));
        towers.add(new FastTower(tl.getTowerSpeed(), this, 1, world.getXcase(26), world.getYcase(26), tl.getBarBack(), tl.getBarBlue()));
        towerList.add(26);
        for (int i=0 ; i<7 ; i++){
           // createMob(i);
        }
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
                ajouterUnite(UNITE_CHEVALIER);
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
                ajouterUnite(UNITE_MAGE);
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
                ajouterUnite(UNITE_MOINE);
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
                ajouterUnite(UNITE_ROGUE);
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
                ajouterUnite(UNITE_HEALER);
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

    private void update(float delta) {
        updateCells();
        updateMobs(delta);
        updateTowers(delta);
        updateUnits(delta);
        updateSpells(delta);
        acitivationSpell();
        uiStage.act(delta);
    }

    public void updateVision(Array<Integer> caseVisionOk){
        for(Integer integer : caseVisionOk){
            cells[integer].setVision();
        }
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

    private void acitivationSpell(){
        if(Gdx.input.justTouched()){
            float x = (Gdx.input.getX() - 68)/2;
            float y = WORLD_HEIGHT-(Gdx.input.getY()/2);

            for(int i=0; i<towerList.size  ; i++){
                //System.out.println(x +"   "+y+ "  //  "+towers.get(i).getX()+"   "+towers.get(i).getY() );
                if (x >= towers.get(i).getX() && x <= towers.get(i).getX()+32 && y >= towers.get(i).getY() && y <= towers.get(i).getY()+32) {
                    int type = towers.get(i).useSpell();
                    switch(type){
                        case 1:
                            ((FastTower)towers.get(i)).boosterOn();
                            break;
                        case 2:
                            towers.get(i).getTarget(1);
                            break;
                        case 3:
                            towers.get(i).getTarget(1);
                            break;
                        case 4:
                            for(Cell cell : cells){
                                cell.spellVisionOk();
                            }
                            break;
                    }
                }
            }
        }
    }

    private void createMob(int teste){
        //il y aura un if
            int type;
            int rand = MathUtils.random(7);
            switch (teste){
                case 0:
                    type = MathUtils.random(8);
                    mobs.add(new Slime(tl.getMobSlimeLeft()[type], tl.getMobSlimeRight()[type], tl.getMobSlimeUp()[type], tl.getMobSlimeDown()[type], 1, this));
                    monsterCreate++;
                    break;
                case 1:
                    type = MathUtils.random(4);
                    mobs.add(new Orc(tl.getMobOrcLeft()[type], tl.getMobOrcRight()[type], tl.getMobOrcUp()[type], tl.getMobOrcDown()[type], 1, this));
                    monsterCreate++;
                    break;
                case 2:
                    type = MathUtils.random(8);
                    mobs.add(new Golem(tl.getMobGolemLeft()[type], tl.getMobGolemRight()[type], tl.getMobGolemUp()[type], tl.getMobGolemDown()[type], 1, this));
                    monsterCreate++;
                    break;
                case 3:
                    type = MathUtils.random(8);
                    mobs.add(new Centaure(tl.getMobCentaureLeft()[type], tl.getMobCentaureRight()[type], tl.getMobCentaureUp()[type], tl.getMobCentaureDown()[type], 1, this));
                    monsterCreate++;
                    break;
                case 4:
                    type = MathUtils.random(8);
                    mobs.add(new Lamia(tl.getMobLamiaLeft()[type], tl.getMobLamiaRight()[type], tl.getMobLamiaUp()[type], tl.getMobLamiaDown()[type], 1, this));
                    monsterCreate++;
                    break;
                case 5:
                    mobs.add(new ChienSquelette(tl.getMobChienSqueletteLeft(), tl.getMobChienSqueletteRight(), tl.getMobChienSqueletteUp(), tl.getMobChienSqueletteDown(), 1, this));
                    monsterCreate++;
                    break;
                case 6:
                    mobs.add(new Griffon(tl.getMobGriffonLeft(), tl.getMobGriffonRight(), tl.getMobGriffonUp(), tl.getMobGriffonDown(), 1, this));
                    monsterCreate++;
                    break;
            }

    }

    public boolean getVision(int numCell){
         return cells[numCell].getVision();
     }

    public boolean testCase(int num , int type){
        return cells[num].testCell(type);
    }

    public  int getPoidsCell(int cell){
        return cells[cell].getNbMob();
    }

    public void activationSpellZone( Tower tower, int direction, Array<Integer> caseCible){
        spells.add( new ZoneTowerSpell(tl.getSpellZoneTowerLeft(), tl.getSpellZoneTowerRight(), tl.getSpellZoneTowerDown(), tl.getSpellZoneTowerUp(), tower, cells, caseCible, direction));
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

            if((difX*difX)>(difY*difY)){
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


            /*if (chemin[mob.getCurrentCase()].getX() > chemin[target.getCurrentCase()].getX()) {
                direction = 1;
            } else if (chemin[mob.getCurrentCase()].getX() < chemin[target.getCurrentCase()].getX()) {
                direction = 2;
            } else if (chemin[mob.getCurrentCase()].getY() > chemin[target.getCurrentCase()].getY()) {
                direction = 3;
            } else {
                direction = 4;
            }*/

            switch(mob.getPo()){
                case 1: spells.add(new SlashSpell(tl.getAtkCacMobLeft(), tl.getAtkCacMobRight(), target, mob, direction, 1));
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

            if((difX*difX)>(difY*difY)){
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

            /*if (chemin[unit.getCurrentCase()].getX() > chemin[target.getCurrentCase()].getX()) {
                direction = 1;
            } else if (chemin[unit.getCurrentCase()].getX() < chemin[target.getCurrentCase()].getX()) {
                direction = 2;
            } else if (chemin[unit.getCurrentCase()].getY() > chemin[target.getCurrentCase()].getY()) {
                direction = 3;
            } else {
                direction = 4;
            }*/

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

            if((difX*difX)>(difY*difY)){
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

           /* if (chemin[unit.getCurrentCase()].getX() > chemin[target.getCurrentCase()].getX()) {
                direction = 1;
            } else if (chemin[unit.getCurrentCase()].getX() < chemin[target.getCurrentCase()].getX()) {
                direction = 2;
            } else if (chemin[unit.getCurrentCase()].getY() > chemin[target.getCurrentCase()].getY()) {
                direction = 3;
            } else {
                direction = 4;
            }*/
            unit.setDirection(direction);
            switch(unit.getType()){
                case 1: spells.add(new SlashSpell(tl.getAtkChevalierLeft(), tl.getAtkChevalierRight(), unit, target, direction, 2));
                    break;
                case 2: spells.add(new MagicSpell(tl.getAtkMage(), unit, target, cells, 2));
                    break;
                case 3: spells.add(new SlashSpell(tl.getAtkMoineLeft(), tl.getAtkMoineRight(), unit, target, direction, 2));
                    break;
                case 4: spells.add(new SlashSpell(tl.getAtkRogueLeft(), tl.getAtkRogueRight(), unit, target, direction, 2));
            }
            return true;
        }
        return false;
    }

    public void getTargetMobTower(Tower tower, int cell, int type){

        switch (type) {
            case 1:
                spells.add(new TowerProjectile(tl.getProjectileTower()[0], tower, cells, cells[cell].getMob(), 1));
                break;
            case 2:
                spells.add(new TowerProjectile(tl.getProjectileTower()[1], tower, cells, cells[cell].getMob(), 1));
                break;
            case 3:
                spells.add(new TowerProjectile(tl.getProjectileTower()[3], tower, cells, cells[cell].getMob(), 1));
                break;
            case 4:
                spells.add(new TowerProjectile(tl.getProjectileTower()[2], tower, cells, cells[cell].getMob(), 1));
                break;
            case 5:
                spells.add( new SlowTowerSpell( tl.getSpellSlowTower(),tower,cells[cell].getMob(),cells));
                break;
        }

    }

    private void ajouterUnite(int unite) {
        switch (unite) {
        case UNITE_CHEVALIER:
            units.add(new Chevalier(tl.getUnitChevalierLeft(), tl.getUnitChevalierRight(),
                    tl.getUnitChevalierUp(), tl.getUnitChevalierDown(), lvlStage, this));
            break;
        case UNITE_MAGE:
            units.add(new Mage(tl.getUnitMageLeft(), tl.getUnitMageRight(),
                    tl.getUnitMageUp(), tl.getUnitMageDown(), lvlStage, this));
            break;
        case UNITE_MOINE:
            units.add(new Moine(tl.getUnitMoineLeft(), tl.getUnitMoineRight(),
                    tl.getUnitMoineUp(), tl.getUnitMoineDown(), lvlStage, this));
            break;
        case UNITE_ROGUE:
            units.add(new Rogue(tl.getUnitRogueLeft(), tl.getUnitRogueRight(),
                    tl.getUnitRogueUp(), tl.getUnitRogueDown(), lvlStage, this));
            break;
        case UNITE_HEALER:
            units.add(new Healer(tl.getUnitHealerLeft(), tl.getUnitHealerRight(),
                    tl.getUnitHealerUp(), tl.getUnitHealerDown(), lvlStage, this));
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
