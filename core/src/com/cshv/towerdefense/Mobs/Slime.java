package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.World;

import java.util.Date;


/**
 * Created by harri on 16/03/2018.
 */

public class Slime extends Mob {

    private static final float FRAME_DURATION = 0.1F;

    public Slime(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu){

        animeLeft = new Animation<TextureRegion>(FRAME_DURATION,left);
        animeLeft.setPlayMode(Animation.PlayMode.LOOP);
        animeRight = new Animation<TextureRegion>(FRAME_DURATION,right);
        animeRight.setPlayMode(Animation.PlayMode.LOOP);
        animeDown = new Animation<TextureRegion>(FRAME_DURATION,down);
        animeDown.setPlayMode(Animation.PlayMode.LOOP);
        animeUp = new Animation<TextureRegion>(FRAME_DURATION,up);
        animeUp.setPlayMode(Animation.PlayMode.LOOP);
        setCarrac(lvlStage);
        timerMalus = new Date().getTime();
        currentAnimation = animeDown;
        parent = jeu;
        chemin = parent.getChemin();
        currentCase = chemin.length-1;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY()+ World.DEPART);

    }

    @Override
    public void move() {
        if(new Date().getTime()> timerMalus){
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
            if(currentCase > 0){
                for(int i = portee; i>0 ; i--){
                    if(currentCase-i>=0){
                        if(!parent.testCase(currentCase-i,1)) {
                            currentCase--;
                        }else{
                            animationTimer = 0;
                            parent.getTargetUnit(this);
                        }
                    }
                }

            }else{
                animationTimer = 0;
            }
        }


    }

    @Override
    public void setCarrac(int lvlStage) {
        vie = 100 + ( lvlStage * 10 );
        attaque = 10 + ( lvlStage );
        defense = 0 + ( lvlStage );
        vitesse = 1 + lvlStage/2;
        portee = 1;
    }

    @Override
    public void addMalus(float malus, int timer) {
        _malus = malus;
        timerMalus = (new Date().getTime() + timer);
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion slim = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( slim, _x, _y);

        return dead;
    }


}
