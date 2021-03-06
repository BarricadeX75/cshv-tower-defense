package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;

/**
 * Created by harri on 31/03/2018.
 */

public class SlowTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];

    private Array<Integer> caseDistOk;
    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public SlowTower(Array<TextureRegion> tower, GameScreen jeu, int lvlTower, float x, float y,
                     TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);
        type = SLOW_TOWER;
        this.lvlTower = lvlTower;
        int numSprite = lvlTower/10;
        if(numSprite>3){
            numSprite = 3;
        }
        _tower = tower.get(numSprite);
        parent = jeu;
        chemin = parent.getChemin();
        setStat(lvlTower);
        setPosition(x,y);
        initCaseDistOk();
    }

    public void setStat( int lvlTower){
        attaque = 15 + (lvlTower*3);
        portee = 3 + (lvlTower/15);
        atcSpeed = 3 - (lvlTower / 20);
        malus = 0.5f ;
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
            int poidsMax, poids, cell;
            cell = 0;
            poidsMax = 0;
            for (int i = 0; i < caseDistOk.size; i++) {
                poids = 0;
                if (parent.testCase(caseDistOk.get(i), 2)) {
                    poids += parent.getPoidsCell(caseDistOk.get(i));
                }
                if(poids > poidsMax){
                    poidsMax = poids;
                    cell = i;
                }
            }
            if(poidsMax>0){
                parent.getTargetMobTower(this, caseDistOk.get(cell), 5);
                parent.manaUse(Tower.SPELL_SLOW + (2*lvlTower));
            }else{
                chargementSpell = 32f;
            }
        }
    }
}
