package com.cshv.towerdefense.Units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;
import com.cshv.towerdefense.World;

/**
 * Created by harri on 30/03/2018.
 */

public class Mage extends Unit {
    private static final float FRAME_DURATION = 0.1F;

    public Mage(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu){
        type = MAGE;
        animeLeft = new Animation<TextureRegion>(FRAME_DURATION,left);
        animeLeft.setPlayMode(Animation.PlayMode.LOOP);
        animeRight = new Animation<TextureRegion>(FRAME_DURATION,right);
        animeRight.setPlayMode(Animation.PlayMode.LOOP);
        animeDown = new Animation<TextureRegion>(FRAME_DURATION,down);
        animeDown.setPlayMode(Animation.PlayMode.LOOP);
        animeUp = new Animation<TextureRegion>(FRAME_DURATION,up);
        animeUp.setPlayMode(Animation.PlayMode.LOOP);
        setCarrac(lvlStage);
        currentAnimation = animeDown;
        parent = jeu;
        chemin = parent.getChemin();
        currentCase = 0;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY()- World.DEPART);

    }

    @Override
    public void setCarrac(int lvlStage) {
        vieMax = 50 + ( lvlStage * 10 );
        vie = vieMax;
        attaque = 8 + (3* lvlStage );
        defense = (int)(0.25f * lvlStage );
        vitesse = 1 ;
        portee = 3;
    }

    public void move() {

        if (_x != chemin[currentCase].getX()) {
            if (_x < chemin[currentCase].getX()) {
                currentAnimation = animeRight;
                _x += vitesse;
            } else {
                currentAnimation = animeLeft;
                _x -= vitesse;
            }
        } else if(_y != chemin[currentCase].getY() ){
            if (_y < chemin[currentCase].getY()) {
                currentAnimation = animeUp;
                _y += vitesse;
            } else {
                currentAnimation = animeDown;
                _y -= vitesse;
            }
        }else{
            if(currentCase < chemin.length-1 && attaqueOk){
                for(int i = portee; i>0 ; i--){
                    if(currentCase+i <= chemin.length-1){
                        if(!parent.testCase(currentCase+i,2)) {
                            if(currentCase < chemin.length-3) {
                                if (i == 1) {
                                    currentCase++;
                                }
                            }
                        }else{
                            animationTimer = 0;
                            if(parent.getTargetMob(this)) {
                                attaqueOk = false;
                                Timer.schedule(getAttaque, 2.5F);
                                break;
                            }
                        }
                    }
                }

            }else{
                //animationTimer = 0;
            }
        }
    }

}