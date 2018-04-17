package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Mobs.Bat;
import com.cshv.towerdefense.Mobs.Centaure;
import com.cshv.towerdefense.Mobs.ChienSquelette;
import com.cshv.towerdefense.Mobs.Dragon;
import com.cshv.towerdefense.Mobs.Golem;
import com.cshv.towerdefense.Mobs.Griffon;
import com.cshv.towerdefense.Mobs.Lamia;
import com.cshv.towerdefense.Mobs.LoupGarou;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Mobs.Mushroom;
import com.cshv.towerdefense.Mobs.Orc;
import com.cshv.towerdefense.Mobs.Slime;
import com.cshv.towerdefense.Spells.ExplosionMushroomSpell;
import com.cshv.towerdefense.Spells.HealSpell;
import com.cshv.towerdefense.Spells.MagicSpell;
import com.cshv.towerdefense.Spells.MobProjectile;
import com.cshv.towerdefense.Spells.SlashSpell;
import com.cshv.towerdefense.Spells.SlowTowerSpell;
import com.cshv.towerdefense.Spells.Spell;
import com.cshv.towerdefense.Spells.TowerProjectile;
import com.cshv.towerdefense.Spells.ZoneTowerSpell;
import com.cshv.towerdefense.Towers.FastTower;
import com.cshv.towerdefense.Towers.SlowTower;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Towers.VisionTower;
import com.cshv.towerdefense.Towers.ZoneTower;
import com.cshv.towerdefense.Units.Chevalier;
import com.cshv.towerdefense.Units.Fontaine;
import com.cshv.towerdefense.Units.Healer;
import com.cshv.towerdefense.Units.Mage;
import com.cshv.towerdefense.Units.Moine;
import com.cshv.towerdefense.Units.Rogue;
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
    private int lvlStage = 1;
    private TextureLoader tl;
    private int nbMonster;
    private int mobCreer = 1;
    private int numWave = 1;
    private float gold = 0;
    protected Timer.Task setWave;
    protected Timer.Task setMob;


    private Cell cells[];
    private Array<Integer> towerList = new Array<Integer>();
    private Array<Mob> mobs = new Array<Mob>();
    private Array<Unit> units = new Array<Unit>();
    private Array<Spell> spells = new Array<Spell>();
    private Array<Tower> towers = new Array<Tower>();

    private Player _player;
    private Stage uiStage;


    public GameScreen(TowerDefenseGame towerDefenseGame, Player player) {
        this.towerDefenseGame = towerDefenseGame;
        _player = player;
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
        lvlStage = _player.getLvlStage();
        tl = new TextureLoader(textureAtlas);

        //static chemin
        Array<Integer> trajet = new Array<Integer>();
        for(int i=0 ; i<33 ; i+=11 ){
            trajet.add(i);
        }
        for(int i = 33 ; i<43 ; i++){
            trajet.add(i);
        }
        for(int i = 42 ; i<120 ; i+=11){
            trajet.add(i);
        }
        for(int i = 118 ; i>110 ; i--){
            trajet.add(i);
        }
        for(int i = 122 ; i<176 ; i+= 11){
            trajet.add(i);
        }

        world = new World(tl.getSol(), tl.getChemin(), trajet);
        chemin = world.getChemin();
        cells = new Cell[chemin.length];
        for(int i=0 ; i<chemin.length ; i++){
            cells[i] = new  Cell(i);
        }
        units.add(new Fontaine(tl.getSpriteFontaine(), this, _player.getLvlFontaine(), _player));

        createTower(Tower.ZONE_TOWER,43);
        createTower(Tower.ZONE_TOWER,110);
        createTower(Tower.ZONE_TOWER,130);
        createTower(Tower.SLOW_TOWER,26);

        //createMob(10);

        /////////////////////////////////////  USER INTERFACE  /////////////////////////////////////
        TextureRegion buttonUpTexture = tl.getButtonUp();
        TextureRegion buttonDownTexture = tl.getButtonDown();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(buttonUpTexture),
                new TextureRegionDrawable(buttonDownTexture),
                null,
                bitmapFont
        );
        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        float nameScale = 0.5f;
        float textScale = 0.4f;
        float padding = 15f;

        uiStage = new Stage(viewport);
        Gdx.input.setInputProcessor(uiStage);

        Label nameLabel = new Label(_player.getNom(), labelStyle);
        nameLabel.setFontScale(nameScale);
        nameLabel.setPosition(WORLD_WIDTH / 2 + ((nameLabel.getWidth() / 2) * nameScale), 70, Align.center);
        uiStage.addActor(nameLabel);

        PlayerBar playerBar = new PlayerBar(_player, 0, WORLD_WIDTH, 50, bitmapFont, textScale,
                tl.getBarBack(), tl.getBarRed(), tl.getBarBlue());
        uiStage.addActor(playerBar);

        Table table = new Table();
        table.setTransform(true);
        table.setScale(textScale);
        table.setPosition(WORLD_WIDTH / 2, 20);

        TextButton uiButton1 = new TextButton("Chevalier", textButtonStyle);
        uiButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (_player.getManaCombat() >= Unit.COUT_CHEVALIER) {
                    _player.depenserMana(Unit.COUT_CHEVALIER);
                    ajouterUnite(Unit.CHEVALIER);
                }
            }
        });
        table.add(uiButton1).pad(padding);

        TextButton uiButton2 = new TextButton("Mage", textButtonStyle);
        uiButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (_player.getManaCombat() >= Unit.COUT_MAGE) {
                    _player.depenserMana(Unit.COUT_MAGE);
                    ajouterUnite(Unit.MAGE);
                }
            }
        });
        table.add(uiButton2).pad(padding);

        TextButton uiButton3 = new TextButton("Moine", textButtonStyle);
        uiButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (_player.getManaCombat() >= Unit.COUT_MOINE) {
                    _player.depenserMana(Unit.COUT_MOINE);
                    ajouterUnite(Unit.MOINE);
                }
            }
        });
        table.add(uiButton3).pad(padding);

        TextButton uiButton4 = new TextButton("Rogue", textButtonStyle);
        uiButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (_player.getManaCombat() >= Unit.COUT_ROGUE) {
                    _player.depenserMana(Unit.COUT_ROGUE);
                    ajouterUnite(Unit.ROGUE);
                }
            }
        });
        table.add(uiButton4).pad(padding);

        TextButton uiButton5 = new TextButton("Healer", textButtonStyle);
        uiButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (_player.getManaCombat() >= Unit.COUT_HEALER) {
                    _player.depenserMana(Unit.COUT_HEALER);
                    ajouterUnite(Unit.HEALER);
                }
            }
        });
        table.add(uiButton5).pad(padding);

        uiStage.addActor(table);
        ////////////////////////////////////////////////////////////////////////////////////////////


        nbMonster = 5 + lvlStage /4;
        setMob = new Timer.Task() {
            @Override
            public void run() {
                createMob();
                mobCreer++;
            }
        };
        setWave = new Timer.Task() {
            @Override
            public void run() {
                mobCreer = 0;
                numWave++;
                Timer.schedule(setMob, 0,1);
            }
        };

        Timer.schedule(setWave,1,30+nbMonster);
        Timer.schedule(setMob,0,1);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();

    }

    private void update(float delta) {
        controlerTask();
        updateCells();
        updateMobs(delta);
        updateTowers(delta);
        updateUnits(delta);
        updateSpells(delta);
        acitivationSpell();
        uiStage.act(delta);
    }

    private void controlerTask(){
        if(mobCreer == nbMonster){
            setMob.cancel();
        }
        if(numWave == 5){
            setWave.cancel();
        }
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
            float x = (Gdx.input.getX() - 45)/2;
            float y = WORLD_HEIGHT-((Gdx.input.getY())/2);

            for(Tower tower : towers){
                if (x >= tower.getX() && x <= tower.getX()+32 && y >= tower.getY() && y <= tower.getY()+32) {
                    int type = tower.useSpell();
                    switch(type){
                        case 1:
                            if(_player.getManaCombat()>=30) {
                                ((FastTower) tower).boosterOn();
                            }
                            break;
                        case 2:
                            if(_player.getManaCombat()>=50) {
                                tower.getTarget(1);
                            }
                            break;
                        case 3:
                            if(_player.getManaCombat()>=60) {
                                tower.getTarget(1);
                            }
                            break;
                        case 4:
                            if(_player.getManaCombat()>=30) {
                                manaUse(Tower.SPELL_VISION);
                                for (Cell cell : cells) {
                                    cell.spellVisionOk();
                                }
                            }

                            break;
                    }
                }
            }
        }
    }

    public void manaUse(float depMana){
        _player.depenserMana(depMana);
    }

    private void createTower(int typeTower, int pos){

        int type = typeTower;
        int cell = pos;
        switch (type){
            case Tower.FAST_TOWER:
                towers.add(new FastTower(tl.getSpriteTowerFast(), this, _player.getLvlFastTower(),world.getXcase(cell),world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                break;
            case Tower.SLOW_TOWER:
                towers.add(new SlowTower(tl.getSpriteTowerSlow(), this, _player.getLvlSlowTower(),world.getXcase(cell),world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                break;
            case Tower.VISION_TOWER:
                towers.add(new FastTower(tl.getSpriteTowerVision(), this, _player.getLvlVisionTower(),world.getXcase(cell),world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                break;
            case Tower.ZONE_TOWER:
                towers.add(new FastTower(tl.getSpriteTowerZone(), this, _player.getLvlZoneTower(),world.getXcase(cell),world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                break;
        }
    }

    private void createMob(){

        int type;
        int range = 3+ (lvlStage /3);
        int rand = MathUtils.random(range);
        switch (rand){
            case 0:
                type = MathUtils.random(7);
                mobs.add(new Slime(tl.getMobSlimeLeft()[type], tl.getMobSlimeRight()[type], tl.getMobSlimeUp()[type], tl.getMobSlimeDown()[type], lvlStage, this, type));
                break;
            case 1:
                type = MathUtils.random(3);
                mobs.add(new Orc(tl.getMobOrcLeft()[type], tl.getMobOrcRight()[type], tl.getMobOrcUp()[type], tl.getMobOrcDown()[type], lvlStage, this, type));
                break;
            case 2:
                type = MathUtils.random(7);
                mobs.add(new Golem(tl.getMobGolemLeft()[type], tl.getMobGolemRight()[type], tl.getMobGolemUp()[type], tl.getMobGolemDown()[type], lvlStage, this, type));
                break;
            case 3:
                type = MathUtils.random(7);
                mobs.add(new Centaure(tl.getMobCentaureLeft()[type], tl.getMobCentaureRight()[type], tl.getMobCentaureUp()[type], tl.getMobCentaureDown()[type], lvlStage, this, type));
                break;
            case 4:
                type = MathUtils.random(7);
                mobs.add(new Lamia(tl.getMobLamiaLeft()[type], tl.getMobLamiaRight()[type], tl.getMobLamiaUp()[type], tl.getMobLamiaDown()[type], lvlStage, this, type));
                break;
            case 5:
                mobs.add(new ChienSquelette(tl.getMobChienSqueletteLeft(), tl.getMobChienSqueletteRight(), tl.getMobChienSqueletteUp(), tl.getMobChienSqueletteDown(), lvlStage, this));
                break;
            case 6:
                type = MathUtils.random(7);
                mobs.add(new Bat(tl.getMobBatLeft()[type], tl.getMobBatRight()[type], tl.getMobBatUp()[type], tl.getMobBatDown()[type], lvlStage, this, type));
                break;
            case 7:
                type = MathUtils.random(7);
                mobs.add(new LoupGarou(tl.getMobLoupGarouLeft()[type], tl.getMobLoupGarouRight()[type], tl.getMobLoupGarouUp()[type], tl.getMobLoupGarouDown()[type], lvlStage, this, type));
                break;
            case 8:
                mobs.add(new Griffon(tl.getMobGriffonLeft(), tl.getMobGriffonRight(), tl.getMobGriffonUp(), tl.getMobGriffonDown(), lvlStage, this));
                break;
            case 9:
                type = MathUtils.random(4);
                mobs.add(new Mushroom(tl.getMobMushroomLeft()[type], tl.getMobMushroomRight()[type], tl.getMobMushroomUp()[type], tl.getMobMushroomDown()[type], lvlStage, this, type));
                break;
            case 10:
                type = MathUtils.random(1);
                mobs.add(new Dragon(tl.getMobDragonLeft()[type], tl.getMobDragonRight()[type], tl.getMobDragonUp()[type], tl.getMobDragonDown()[type], lvlStage, this, type));

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
            if(mob.getCurrentCase()-i>=0){
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

            switch(mob.getPo()){
                case 1: spells.add(new SlashSpell(tl.getAtkCacMobLeft(), tl.getAtkCacMobRight(), target, mob, direction, 1));
                    break;
                case 2: int rand = MathUtils.random(5);
                    spells.add(new MobProjectile(tl.getProjectileMob().get(rand), mob, target));
                    break;
                case 3: if(MathUtils.randomBoolean(0.5f)) {
                        spells.add(new MagicSpell(tl.getSpellFire(), target, mob, cells, 1));
                    }else{
                        spells.add(new MagicSpell(tl.getSpellWater(), target, mob, cells, 1));
                    }
                    break;
            }

            mob.setDirection(direction);
            return true;
        }

        return false;
    }

    public boolean getTargetHeal(Unit unit){
        Unit target = null;
        for(int i=unit.getPo() ; i>=0 ; i--){
            if(unit.getCurrentCase()+i <= chemin.length-1){
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
            if(unit.getCurrentCase()+i <= chemin.length-1){
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

    public void activationSpecialMushroom(int type, int cell){
        switch(type){
            case 0: spells.add( new ExplosionMushroomSpell( tl.getExploMushroom(), chemin[cell-1].getX(), chemin[cell-1].getY(), cells[cell-1]));
                break;
            case 1: _player.bonusVie(80);
                break;
            case 2: gold+=20;
                break;
            case 3: gold += 40;
                break;
            case 4: _player.bonusMana(50);
                break;
        }
    }

    private void ajouterUnite(int unite) {
        switch (unite) {
        case Unit.CHEVALIER:
            units.add(new Chevalier(tl.getUnitChevalierLeft(), tl.getUnitChevalierRight(),
                    tl.getUnitChevalierUp(), tl.getUnitChevalierDown(), _player.getLvlChevalier(), this));
            break;
        case Unit.MAGE:
            units.add(new Mage(tl.getUnitMageLeft(), tl.getUnitMageRight(),
                    tl.getUnitMageUp(), tl.getUnitMageDown(), _player.getLvlMage(), this));
            break;
        case Unit.MOINE:
            units.add(new Moine(tl.getUnitMoineLeft(), tl.getUnitMoineRight(),
                    tl.getUnitMoineUp(), tl.getUnitMoineDown(), _player.getLvlMoine(), this));
            break;
        case Unit.ROGUE:
            units.add(new Rogue(tl.getUnitRogueLeft(), tl.getUnitRogueRight(),
                    tl.getUnitRogueUp(), tl.getUnitRogueDown(), _player.getLvlRogue(), this));
            break;
        case Unit.HEALER:
            units.add(new Healer(tl.getUnitHealerLeft(), tl.getUnitHealerRight(),
                    tl.getUnitHealerUp(), tl.getUnitHealerDown(), _player.getLvlHealer(), this));
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

}
