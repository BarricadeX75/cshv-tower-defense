package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.cshv.towerdefense.GameScreen;

/**
 * Created by harri on 31/03/2018.
 */

public class SlowTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];
    private GameScreen parent;

    private Array<Integer> caseDistOk;
    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public SlowTower( Array<TextureRegion> towerAtc, GameScreen jeu, int lvlTower,
                      TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);

        towerFireEnd = towerAtc.get(towerAtc.size-1);
        actTower = new Animation<TextureRegion>(FRAME_DURATION,towerAtc);
        actTower.setPlayMode(Animation.PlayMode.LOOP);
        parent = jeu;
        chemin = parent.getChemin();
        setStat(lvlTower);
        timer = TimeUtils.millis();
    }

    public void setStat( int lvlTower){
        attaque = 10 * lvlTower;
        portee = 3 + (lvlTower%3);
        atcSpeed = 3000 - (lvlTower * 100);
        malus = 0;
    }

    public void initCaseDistOk(){
        for(int i=chemin.length ; i>0 ; i--){
            int distance = (int) Math.sqrt((int)(_x/32 - chemin[i].getX()/32)*(int)(_x/32 - chemin[i].getX()/32)) + (int) Math.sqrt((int)(_y/32 - chemin[i].getY()/32)*(int)(_y/32 - chemin[i].getY()/32));
            if( portee >= distance){
                caseDistOk.add(i);
            }
        }
    }

    @Override
    public void getTarget() {
        if(TimeUtils.millis()>timer) {
            for (int i = 0; i < caseDistOk.size; i++) {
                if (parent.testCase(caseDistOk.get(i), 2)) {
                    parent.getTargetMobTower(this, caseDistOk.get(i), 2);
                    timer = TimeUtils.millis()+atcSpeed;
                }
            }
        }
    }
}
