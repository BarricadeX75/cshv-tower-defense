package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;

/**
 * Created by harri on 16/03/2018.
 */

public class FastTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];
    private int _lvlTower;
    private Timer.Task spellBooster = new Timer.Task() {
        @Override
        public void run() {
           attaque -= 10 + _lvlTower;
            atcSpeed += 1.5f;
        }
    };


    private Array<Integer> caseDistOk;
    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public FastTower(Array<TextureRegion> tower, GameScreen jeu, int lvlTower, float x, float y,
                     TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);
        type = FAST_TOWER;
        this.lvlTower = lvlTower;
        int numSprite = lvlTower/10;
        if(numSprite>3){
            numSprite = 3;
        }
        _tower = tower.get(numSprite);
        parent = jeu;
        chemin = parent.getChemin();
        setStat(lvlTower);
        _lvlTower = lvlTower;
        setPosition(x,y);
        initCaseDistOk();


    }

    public void setStat( int lvlTower){
        attaque = 15 + (lvlTower*4);
        portee = 3 + (lvlTower/15);
        atcSpeed = 3 - (float)(lvlTower/15);
        malus = 0;
        if(atcSpeed < 1.5f){
            atcSpeed = 1.5f;
        }
    }

    public void boosterOn(){
        attaque += 15 + _lvlTower;
        atcSpeed -= 1.5f;
        parent.manaUse(Tower.SPELL_FAST + (lvlTower));
        chargementSpell = 0;
        Timer.schedule(spellBooster, 10);
    }

    public void initCaseDistOk(){
        caseDistOk = new Array<Integer>();

        for(int i=chemin.length-1 ; i>=0 ; i--){
            int distance = (int) Math.sqrt((_x/32 - chemin[i].getX()/32)*(_x/32 - chemin[i].getX()/32)) + (int) Math.sqrt((_y/32 - chemin[i].getY()/32)*(_y/32 - chemin[i].getY()/32));
            if( portee >= distance){
                caseDistOk.add(i);
            }
        }
    }

    @Override
    public void getTarget(int typeAtc) {
        if(tireOK){
            for (int i = 0; i < caseDistOk.size; i++) {
                if (parent.testCase(caseDistOk.get(i), 2)) {
                    tireOK = false;
                    parent.getTargetMobTower(this, caseDistOk.get(i), 1);
                    if(atcSpeed <= 0){
                        Timer.schedule(getTargetTask, 0.1f);
                        break;
                    }
                    Timer.schedule(getTargetTask, atcSpeed);
                    break;
                }
            }
        }
    }
}
