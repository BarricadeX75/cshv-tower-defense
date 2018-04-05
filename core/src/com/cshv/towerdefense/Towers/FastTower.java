package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

/**
 * Created by harri on 16/03/2018.
 */

public class FastTower extends Tower {

    private static final float FRAME_DURATION = 0.1F;
    private Rectangle chemin[];
    private GameScreen parent;
    private Timer.Task getTargetTask;
    private boolean tireOK = true;

    private Array<Integer> caseDistOk;
    //private Array<Rectangle> caseDistOk;
    private TextureRegion towerFireEnd;

    public FastTower(Array<TextureRegion> towerAtc, GameScreen jeu, int lvlTower, float x, float y,
                     TextureRegion barBack, TextureRegion barFront) {
        super(barBack, barFront);

        towerFireEnd = towerAtc.get(towerAtc.size-1);
        actTower = new Animation<TextureRegion>(FRAME_DURATION,towerAtc);
        actTower.setPlayMode(Animation.PlayMode.LOOP);
        parent = jeu;
        chemin = parent.getChemin();
        setStat(lvlTower);
        setPosition(x,y);
        initCaseDistOk();
        getTargetTask = new Timer.Task() {
            @Override
            public void run() {
                //Gdx.app.log()
                tireOK = true;
            }
        };

    }

    public void setStat( int lvlTower){
        attaque = 10 * lvlTower;
        portee = 3 + (lvlTower%3);
        atcSpeed = 3000 - (lvlTower * 100);
        malus = 0;
    }

    public void initCaseDistOk(){
        caseDistOk = new Array<Integer>();

        for(int i=chemin.length-1 ; i>=0 ; i--){
            int distance = (int) Math.sqrt((_x/32 - chemin[i].getX()/32)*(_x/32 - chemin[i].getX()/32)) + (int) Math.sqrt((_y/32 - chemin[i].getY()/32)*(_y/32 - chemin[i].getY()/32));
            Gdx.app.log(" distance case", "dist  "+distance);
            if( portee >= distance){
                caseDistOk.add(i);
                Gdx.app.log(" init ok", "case  "+i);
            }
        }
    }

    @Override
    public void getTarget() {
        if(tireOK){
            for (int i = 0; i < caseDistOk.size; i++) {
                if (parent.testCase(caseDistOk.get(i), 2)) {
                    parent.getTargetMobTower(this, caseDistOk.get(i), 1);

                    Timer.schedule(getTargetTask, 1);
                    tireOK = false;
                }
            }
        }
    }
}