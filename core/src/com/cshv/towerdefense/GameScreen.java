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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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

import java.util.HashMap;


public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    private static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;

    private final TowerDefenseGame towerDefenseGame;
    private Viewport viewport;
    private Vector3 touchPlayer;
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
    private int gold = 0;
    private boolean waveFinal = false;
    private boolean win = false,lose = false;
    private Label labelLose, labelWin, labelPosition;
    private ImageButton validButton, cancelButton;

    protected Timer.Task setWave;
    protected Timer.Task setMob;


    private Cell cells[];
    private Array<Integer> towerList = new Array<Integer>();
    private Array<Mob> mobs = new Array<Mob>();
    private Array<Unit> units = new Array<Unit>();
    private Array<Spell> spells = new Array<Spell>();
    private Array<Tower> towers = new Array<Tower>();

    private Player _player;
    private Stage uiStage,stageBackground;


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
        stageBackground = new Stage(viewport);
        batch = new SpriteBatch();
        bitmapFont = towerDefenseGame.getAssetManager().get("font.fnt");
        bitmapFont.setColor(Color.BLACK);
        TextureAtlas textureAtlas = towerDefenseGame.getAssetManager().get("test1.atlas");
        lvlStage = _player.getLvlStage();
        tl = new TextureLoader(textureAtlas);
        Image fondBackground = new Image(tl.getBagroundTexture().get(2));
        fondBackground.setPosition(0,0);
        stageBackground.addActor(fondBackground);
        touchPlayer = new Vector3();
        world = new World(tl.getLandTexture(),tl.getChemin(),_player.getChemin(), tl.getDecoreTexture());
        //world = new World(tl.getSol(), tl.getChemin(), _player.getChemin());
        chemin = world.getChemin();
        cells = new Cell[chemin.length];
        for(int i=0 ; i<chemin.length ; i++){
            cells[i] = new  Cell(i);
        }
        units.add(new Fontaine(tl.getSpriteFontaine(), this, _player.getLvlFontaine(), _player));
        createTower();
        //TextureRegion boutonTest = textureAtlas.findRegion("button_carrer");

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
        Label.LabelStyle labelStyleLose = new Label.LabelStyle(bitmapFont, Color.RED);
        Label.LabelStyle labelStyleStage = new Label.LabelStyle(bitmapFont, Color.FIREBRICK);
        float nameScale = 0.5f;
        float textScale = 0.4f;
        float padding = 15f;

        uiStage = new Stage(viewport);
        //loseStage = new Stage(viewport);
        Gdx.input.setInputProcessor(uiStage);
        //Gdx.input.setInputProcessor(loseStage);

        Label nameLabel = new Label(_player.getNom(), labelStyle);
        nameLabel.setFontScale(nameScale);
        nameLabel.setPosition(WORLD_WIDTH / 2 + ((nameLabel.getWidth() / 2) * nameScale), 70, Align.center);
        uiStage.addActor(nameLabel);

        Label lvlStageLabel = new Label("Stage: "+lvlStage, labelStyleStage);
        lvlStageLabel.setFontScale(nameScale);
        lvlStageLabel.setPosition((7*WORLD_WIDTH) / 8 + ((lvlStageLabel.getWidth() / 2) * nameScale), WORLD_HEIGHT-15, Align.center);
        uiStage.addActor(lvlStageLabel);


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
                if(!lose) {
                    int lvl = _player.getLvlChevalier();
                    if (_player.getManaCombat() >= Unit.COUT_CHEVALIER + lvl) {
                        _player.depenserMana(Unit.COUT_CHEVALIER + lvl);
                        ajouterUnite(Unit.CHEVALIER);
                    }
                }
            }
        });
        table.add(uiButton1).pad(padding);

        TextButton uiButton2 = new TextButton("Mage", textButtonStyle);
        uiButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!lose) {
                    int lvl = _player.getLvlMage();
                    if (_player.getManaCombat() >= Unit.COUT_MAGE + lvl) {
                        _player.depenserMana(Unit.COUT_MAGE + lvl);
                        ajouterUnite(Unit.MAGE);
                    }
                }
            }
        });
        table.add(uiButton2).pad(padding);

        TextButton uiButton3 = new TextButton("Moine", textButtonStyle);
        uiButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!lose) {
                    int lvl = _player.getLvlMoine();
                    if (_player.getManaCombat() >= Unit.COUT_MOINE + lvl) {
                        _player.depenserMana(Unit.COUT_MOINE + lvl);
                        ajouterUnite(Unit.MOINE);
                    }
                }
            }
        });
        table.add(uiButton3).pad(padding);

        TextButton uiButton4 = new TextButton("Rogue", textButtonStyle);
        uiButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!lose) {
                    int lvl = _player.getLvlRogue();
                    if (_player.getManaCombat() >= Unit.COUT_ROGUE + lvl) {
                        _player.depenserMana(Unit.COUT_ROGUE + lvl);
                        ajouterUnite(Unit.ROGUE);
                    }
                }
            }
        });
        table.add(uiButton4).pad(padding);

        TextButton uiButton5 = new TextButton("Healer", textButtonStyle);
        uiButton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!lose) {
                    int lvl = _player.getLvlHealer();
                    if (_player.getManaCombat() >= Unit.COUT_HEALER + lvl) {
                        _player.depenserMana(Unit.COUT_HEALER + lvl);
                        ajouterUnite(Unit.HEALER);
                    }
                }
            }
        });
        table.add(uiButton5).pad(padding);

        uiStage.addActor(table);
        ///////////////////////////////////////////////////////////////////////////

        labelLose = new Label(" Défaite ", labelStyleLose);
        labelLose.setFontScale(1.5f);
        labelLose.setPosition((WORLD_WIDTH/3)+20, (4*WORLD_HEIGHT)/5, Align.center);

        labelWin = new Label(" Victoire ", labelStyle);
        labelWin.setFontScale(1.5f);
        labelWin.setPosition((WORLD_WIDTH/3)+20, (4*WORLD_HEIGHT)/5, Align.center);

        labelPosition = new Label(" Voulez-vous revenir\nau stage précédent ? ", labelStyleLose);
        labelPosition.setFontScale(0.65f);
        labelPosition.setPosition((3*WORLD_WIDTH)/4-20, WORLD_HEIGHT/2, Align.center);

        Array<TextureRegion> textureControleButton = tl.getBigControleTexture();

        validButton = new ImageButton(new TextureRegionDrawable(new TextureRegion( textureControleButton.get(0) ) ), new TextureRegionDrawable( new TextureRegion( textureControleButton.get(0) ) ) );
        validButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                _player.setLvlStage(lvlStage-1);
                _player.resetStatsCombat();
                towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));
                dispose();
            }
        });
        validButton.setPosition( WORLD_WIDTH/3, WORLD_HEIGHT/3  , Align.center );

        cancelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion( textureControleButton.get(1) ) ), new TextureRegionDrawable( new TextureRegion( textureControleButton.get(1) ) ) );
        cancelButton.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                _player.resetStatsCombat();
                towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));
                dispose();

            }
        });
        cancelButton.setPosition( (2*WORLD_WIDTH)/3, WORLD_HEIGHT/3  , Align.center );



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
        _player.updateRegenMana();
        checkGame();
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
            if(numWave == 5 ){
                waveFinal = true;
            }

        }
        if(numWave == 5){
            setWave.cancel();

        }
    }

    public void checkGame(){
        if(!win && !lose) {
            if (_player.getVieCombat() == 0) {
                _player.addGold(gold);
                if (lvlStage < 2) {
                    win = true;
                    uiStage.addActor(labelLose);
                } else {
                    uiStage.addActor(labelLose);
                    uiStage.addActor(labelPosition);
                    uiStage.addActor(validButton);
                    uiStage.addActor(cancelButton);
                    lose = true;
                }
            } else if (numWave == 5 && mobs.size == 0 && waveFinal) {
                int goldWin = (int) (200 * Math.pow(1.15, lvlStage - 1));
                goldWin = (int) (goldWin / (_player.getVieCombat() / _player.getVie()));
                gold += goldWin;
                System.out.println(gold);
                _player.addGold(goldWin);
                _player.setLvlStage(lvlStage + 1);
                uiStage.addActor(labelWin);
                win = true;
            }
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
            if(!win) {
                touchPlayer.set(Gdx.input.getX(),Gdx.input.getY(),0);
                camera.unproject(touchPlayer);
                float x = touchPlayer.x;
                float y = touchPlayer.y;

                for (Tower tower : towers) {
                    if (x >= tower.getX() && x <= tower.getX() + 32 && y >= tower.getY() && y <= tower.getY() + 32) {
                        int type = tower.useSpell();
                        switch (type) {
                            case 1:
                                if (_player.getManaCombat() >= 30 + _player.getLvlFastTower()) {
                                    ((FastTower) tower).boosterOn();
                                }else{
                                    tower.noManaForSpell();
                                }
                                break;
                            case 2:
                                if (_player.getManaCombat() >= 50 + (2*_player.getLvlSlowTower())) {
                                    tower.getTarget(1);
                                }else{
                                    tower.noManaForSpell();
                                }
                                break;
                            case 3:
                                if (_player.getManaCombat() >= 60 + (2*_player.getLvlZoneTower())) {
                                    tower.getTarget(1);
                                }else{
                                    tower.noManaForSpell();
                                }
                                break;
                            case 4:
                                if (_player.getManaCombat() >= 30 + _player.getLvlVisionTower()) {
                                    manaUse(Tower.SPELL_VISION + (_player.getLvlVisionTower()));
                                    for (Cell cell : cells) {
                                        cell.spellVisionOk();
                                    }
                                }else{
                                    tower.noManaForSpell();
                                }
                                break;
                        }
                    }
                }
            }else{
                towerDefenseGame.setScreen(new StartScreen(towerDefenseGame, _player));
                _player.resetStatsCombat();
                dispose();
            }
        }
    }

    public void manaUse(float depMana){
        _player.depenserMana(depMana);
    }

    private void createTower(){
        HashMap<Integer,Integer> towerSave = _player.getTowers();
        for(Integer cell : towerSave.keySet()) {
            int type = towerSave.get(cell);
            switch (type) {
                case Tower.FAST_TOWER:
                    towers.add(new FastTower(tl.getSpriteTowerFast(), this, _player.getLvlFastTower(), world.getXcase(cell), world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                    break;
                case Tower.SLOW_TOWER:
                    towers.add(new SlowTower(tl.getSpriteTowerSlow(), this, _player.getLvlSlowTower(), world.getXcase(cell), world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                    break;
                case Tower.VISION_TOWER:
                    towers.add(new VisionTower(tl.getSpriteTowerVision(), this, _player.getLvlVisionTower(), world.getXcase(cell), world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                    break;
                case Tower.ZONE_TOWER:
                    towers.add(new ZoneTower(tl.getSpriteTowerZone(), this, _player.getLvlZoneTower(), world.getXcase(cell), world.getYcase(cell), tl.getBarBack(), tl.getBarBlue()));
                    break;
            }
        }
    }

    private void createMob(){

        int type;
        int range = 2+ (lvlStage /3);
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
                    spells.add(new MobProjectile(tl.getProjectileMob().get(rand), mob, target, direction));
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
        int direction;
        float difX = tower.getX() - chemin[cell].getX();
        float difY = tower.getY() - chemin[cell].getY();

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

        switch (type) {
            case 1:
                spells.add(new TowerProjectile(tl.getProjectileTower()[0], tower, cells, cells[cell].getMob(), 1, direction));
                break;
            case 2:
                spells.add(new TowerProjectile(tl.getProjectileTower()[1], tower, cells, cells[cell].getMob(), 2, direction));
                break;
            case 3:
                spells.add(new TowerProjectile(tl.getProjectileTower()[3], tower, cells, cells[cell].getMob(), 1, direction));
                break;
            case 4:
                spells.add(new TowerProjectile(tl.getProjectileTower()[2], tower, cells, cells[cell].getMob(), 4, direction));
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
            case 1: _player.bonusVie(2*lvlStage);
                break;
            case 2: gold+=lvlStage;
                break;
            case 3: gold += (2*lvlStage);
                break;
            case 4: _player.bonusMana((int)(1.5*lvlStage));
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
        stageBackground.draw();
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
