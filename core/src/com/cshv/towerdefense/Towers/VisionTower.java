package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

/**
 * Created by harri on 31/03/2018.
 */

public class VisionTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];

    private Array<Integer> caseDistOk;
    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public VisionTower( Array<TextureRegion> tower, GameScreen jeu, int lvlTower, float x, float y,
                        TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);
        type = VISION_TOWER;
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
        parent.updateVision(caseDistOk);
    }

    public void setStat( int lvlTower){
        attaque = 20 + (lvlTower*3);
        portee = 4 + (lvlTower/15);
        atcSpeed = 3 - (float)(lvlTower / 25);
        malus = 0;
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
        if(tireOK){
            for (int i = 0; i < caseDistOk.size; i++) {
                if (parent.testCase(caseDistOk.get(i), 2)) {
                    parent.getTargetMobTower(this, caseDistOk.get(i), 3);

                    Timer.schedule(getTargetTask, atcSpeed);
                    tireOK = false;
                    break;
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if(lvlTower>=30){
            batch.draw(_tower,_x-11,_y);
        }else {
            batch.draw(_tower, _x + 1, _y);
        }
        batch.draw(barBack, _x, _y, BAR_WIDTH, BAR_HEIGHT);
        batch.draw(barFront, _x, _y, chargementSpell, BAR_HEIGHT);
    }
}
