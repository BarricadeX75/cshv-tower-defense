package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;
import com.cshv.towerdefense.World;

/**
 * Created by harri on 14/04/2018.
 */

public class Dragon extends Mob {

    private static final float FRAME_DURATION = 0.1F;

    public Dragon(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu, int type){

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
        currentCase = chemin.length-1;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY()+ World.DEPART);
        _type = type;
    }

    @Override
    public void setCarrac(int lvlStage) {
        if(_type == 1) {
            vie = 100 + (lvlStage * 45);
            attaque = 25 + (5 * lvlStage);
            defense = lvlStage;
            vitesse = 0.5f;
            portee = 1;
        }else {
            vie = 100 + (lvlStage * 35);
            attaque = 25 + (6 * lvlStage);
            defense =  lvlStage;
            vitesse = 0.5f;
            portee = 3;
        }
    }

    @Override
    public void move() {
        if(!malusOn){
            _malus = 0;
        }


        if (_x != chemin[currentCase].getX()) {
            if (_x < chemin[currentCase].getX()) {
                currentAnimation = animeRight;
                _x += vitesse - _malus;
            } else {
                currentAnimation = animeLeft;
                _x -= vitesse - _malus;
            }
        } else if(_y != chemin[currentCase].getY()){
            if (_y < chemin[currentCase].getY()) {
                currentAnimation = animeUp;
                _y += vitesse - _malus;
            } else {
                currentAnimation = animeDown;
                _y -= vitesse - _malus;
            }
        }else{
            if(currentCase > 0 && attaqueOk){
                for(int i = portee; i>0 ; i--){
                    if(currentCase-i>=0){
                        if(parent.testCase(currentCase-i,1)) {
                            if(parent.getTargetUnit(this)) {
                                attaqueOk = false;
                                Timer.schedule(getAttaque, 2.5F);
                                break;
                            }
                        }

                    }
                }
                if(currentCase >1) {
                    currentCase--;
                }
            }
        }


    }

    public boolean draw(SpriteBatch batch) {
        TextureRegion anime = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( anime, _x-30, _y);

        return dead;
    }

}
