package com.cshv.towerdefense;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Spells.ExplosionMushroomSpell;
import com.cshv.towerdefense.Spells.HealSpell;
import com.cshv.towerdefense.Spells.MagicSpell;
import com.cshv.towerdefense.Spells.MobProjectile;
import com.cshv.towerdefense.Spells.SlashSpell;
import com.cshv.towerdefense.Spells.SlowTowerSpell;
import com.cshv.towerdefense.Spells.Spell;
import com.cshv.towerdefense.Spells.TowerProjectile;
import com.cshv.towerdefense.Spells.ZoneTowerSpell;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Units.Unit;

/**
 * Created by harri on 05/05/2018.
 */

public abstract class GameScreen extends ScreenAdapter {
    protected static final float WORLD_WIDTH = TowerDefenseGame.WORLD_WIDTH;
    protected static final float WORLD_HEIGHT = TowerDefenseGame.WORLD_HEIGHT;
    public static final float BAR_HEIGHT = 6f;

    protected Viewport viewport;
    protected Vector3 touchPlayer;
    protected Camera camera;
    protected BitmapFont bitmapFont;
    protected SpriteBatch batch;
    protected World world;
    protected Rectangle[] chemin;
    protected int lvlStage = 1;
    protected TextureLoader tl;
    protected float timerParti, ajoutTimer;
    protected int nbMonster;
    protected int mobCreer = 1;
    protected int numWave = 1;
    protected int gold = 0;
    protected boolean waveFinal = false;
    protected boolean win = false,lose = false;
    protected Label labelLose, labelWin, labelPosition, labelGold;
    protected Label labelChevaliersNb, labelMagesNb, labelMoinesNb, labelRoguesNb, labelHealersNb;
    protected ImageButton validButton, cancelButton;
    public TextureRegion barGold, barFront;

    protected Timer.Task setWave;
    protected Timer.Task setMob;


    protected Cell cells[];
    protected Array<Mob> mobs = new Array<Mob>();
    protected Array<Unit> units = new Array<Unit>();
    protected Array<Spell> spells = new Array<Spell>();
    protected Array<Tower> towers = new Array<Tower>();

    protected Player _player;
    protected Stage uiStage,stageBackground;

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

    public Rectangle[] getChemin(){
        return chemin;
    }

    public void manaUse(float depMana){
        _player.depenserMana(depMana);
    }

    public void updateVision(Array<Integer> caseVisionOk){
        for(Integer integer : caseVisionOk){
            cells[integer].setVision();
        }
    }
}
