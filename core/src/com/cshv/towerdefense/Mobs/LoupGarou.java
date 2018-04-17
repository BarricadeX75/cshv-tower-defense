package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.World;

import java.util.Date;

/**
 * Created by harri on 24/03/2018.
 */

public class LoupGarou extends Mob {

    private static final float FRAME_DURATION = 0.1F;


    public LoupGarou(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu, int type){

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
        visible = false;
        parent = jeu;
        chemin = parent.getChemin();
        currentCase = chemin.length-1;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY()+ World.DEPART);

    }

    @Override
    public void move() {
        if(!malusOn){
            _malus = 0;
        }
        visible = parent.getVision(currentCase);

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
                        if(!parent.testCase(currentCase-i,1) ||  !visible) {
                            if(currentCase >1) {
                                currentCase--;
                            }else{
                                if(parent.getTargetUnit(this)) {
                                    attaqueOk = false;
                                    Timer.schedule(getAttaque, 2.5F);
                                    break;
                                }
                            }
                        }else{
                            animationTimer = 0;
                            if(parent.getTargetUnit(this)) {
                                attaqueOk = false;
                                Timer.schedule(getAttaque, 2.5F);
                                break;
                            }
                        }
                    }
                }

            }
        }


    }

    @Override
    public void setCarrac(int lvlStage) {
        if(_type%3 == 1) {
            vie = 85 + (lvlStage * 15);
            attaque = 12 + (2 * lvlStage);
            defense = 0 + (1 * lvlStage);
            vitesse = 1f;
            portee = 3;
        }else if(_type%2==1){
            vie = 100 + (lvlStage * 20);
            attaque = 15 + (2 * lvlStage);
            defense = 0 + (2 * lvlStage);
            vitesse = 1f;
            portee = 1;
        }else{
            vie = 40 + (lvlStage * 10);
            attaque = 30 + (5 * lvlStage);
            defense = 0 + (int)(0.5f * lvlStage);
            vitesse = 2f;
            portee = 1;
        }
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion texture = currentAnimation.getKeyFrame(animationTimer);

        if (!visible) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, 0.5f);
            batch.draw(texture, _x-8, _y);
            batch.setColor(c.r, c.g, c.b, c.a);
        }
        else {
            batch.draw(texture, _x-8, _y);
        }

        return dead;
    }


}
