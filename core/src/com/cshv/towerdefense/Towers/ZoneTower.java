package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

/**
 * Created by harri on 31/03/2018.
 */

public class ZoneTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];

    private Array<Integer> caseDistOk;
    private Array<Integer> caseSpell[] = new Array[4];

    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public ZoneTower( Array<TextureRegion> tower, GameScreen jeu, int lvlTower, float x, float y,
                      TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);
        type = ZONE_TOWER;
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
        attaque = 20 + (lvlTower*3);
        portee = 3 + (lvlTower/15);
        atcSpeed = 3 - (float)(lvlTower /20);
        malus = 0;
        if(atcSpeed < 1.5f){
            atcSpeed = 1.5f;
        }
    }

    public void initCaseDistOk(){
        caseDistOk = new Array<Integer>();
        for(int i=0 ; i<4 ; i++){
            caseSpell[i] = new Array<Integer>();
        }
        for(int i=chemin.length-1 ; i>0 ; i--){
            int distance = (int) Math.sqrt((int)(_x/32 - chemin[i].getX()/32)*(int)(_x/32 - chemin[i].getX()/32)) + (int) Math.sqrt((int)(_y/32 - chemin[i].getY()/32)*(int)(_y/32 - chemin[i].getY()/32));
            if( portee >= distance){
                caseDistOk.add(i);
            }
            if (_y == chemin[i].getY() && chemin[i].getX() > _x && chemin[i].getX() <= _x + (32*4)) {
                caseSpell[1].add(i);
            }
            if (_y == chemin[i].getY() && chemin[i].getX() < _x && chemin[i].getX() >= _x - (32*4)) {
                caseSpell[0].add(i);
            }
            if (_x == chemin[i].getX() && chemin[i].getY() > _y && chemin[i].getY() <= _y + (32*4)) {
                caseSpell[3].add(i);
            }
            if (_x == chemin[i].getX() && chemin[i].getY() < _y && chemin[i].getY() >= _y - (32*4)) {
                caseSpell[2].add(i);
            }
        }
    }

    @Override
    public void getTarget(int typeAtc) {
        if(typeAtc == 0) {
            if (tireOK) {
                for (int i = 0; i < caseDistOk.size; i++) {
                    if (parent.testCase(caseDistOk.get(i), 2)) {
                        parent.getTargetMobTower(this, caseDistOk.get(i), 4);
                        Timer.schedule(getTargetTask, atcSpeed);
                        tireOK = false;
                        break;
                    }
                }
            }
        }else{
            int poidsMax, poids , direction;
            poidsMax = 0;
            direction = 0;
            for( int i=0 ; i<4 ; i++){
                poids = 0;
                for( int j=0 ; j<caseSpell[i].size ; j++){
                    if(parent.testCase(caseSpell[i].get(j),2)){
                        poids += parent.getPoidsCell(caseSpell[i].get(j));

                    }
                }
                if(poids > poidsMax){
                    poidsMax = poids;
                    direction = i+1;
                }
            }
            if(poidsMax>0){
                parent.activationSpellZone(this,direction,caseSpell[direction-1]);
                parent.manaUse(Tower.SPELL_ZONE + (2*lvlTower));
            }else{
                chargementSpell = 32f;
            }
        }
    }
}
