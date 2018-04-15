package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

/**
 * Created by harri on 31/03/2018.
 */

public class SlowTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];

    private Array<Integer> caseDistOk;
    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public SlowTower( Array<TextureRegion> towerAtc, GameScreen jeu, int lvlTower, float x, float y,
                      TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);
        type = 2;
        towerFireEnd = towerAtc.get(towerAtc.size-1);
        actTower = new Animation<TextureRegion>(FRAME_DURATION,towerAtc);
        actTower.setPlayMode(Animation.PlayMode.LOOP);
        parent = jeu;
        chemin = parent.getChemin();
        setStat(lvlTower);
        setPosition(x,y);
        initCaseDistOk();
    }

    public void setStat( int lvlTower){
        attaque = 20 + (lvlTower*3);
        portee = 3 + (lvlTower/5);
        atcSpeed = 3 - (lvlTower / 20);
        malus = 0.5f + (lvlTower/20);
        if(atcSpeed < 1.5f){
            atcSpeed = 1.5f;
        }
    }

    public void initCaseDistOk(){
        caseDistOk = new Array<Integer>();

        for(int i=chemin.length-1 ; i>0 ; i--){
            int distance = (int) Math.sqrt((int)(_x/32 - chemin[i].getX()/32)*(int)(_x/32 - chemin[i].getX()/32)) + (int) Math.sqrt((int)(_y/32 - chemin[i].getY()/32)*(int)(_y/32 - chemin[i].getY()/32));
            if( portee >= distance){
                caseDistOk.add(i);
            }
        }
    }

    @Override
    public void getTarget(int typeAtc) {
        if (typeAtc == 0) {
            if (tireOK) {
                for (int i = 0; i < caseDistOk.size; i++) {
                    if (parent.testCase(caseDistOk.get(i), 2)) {
                        parent.getTargetMobTower(this, caseDistOk.get(i), 2);
                        Timer.schedule(getTargetTask, atcSpeed);
                        tireOK = false;
                        break;
                    }
                }
            }
        }else{
            for (int i = 0; i < caseDistOk.size; i++) {
                if (parent.testCase(caseDistOk.get(i), 2)) {
                    parent.getTargetMobTower(this, caseDistOk.get(i), 5);
                    parent.manaUse(Tower.SPELL_SLOW);
                    break;
                }else if (typeAtc == 1) {
                    chargementSpell = 32f;
                }
            }
        }
    }
}
