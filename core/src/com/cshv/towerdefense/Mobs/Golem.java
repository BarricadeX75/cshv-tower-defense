package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.World;

/**
 * Created by harri on 14/04/2018.
 */

public class Golem extends Mob {

    private static final float FRAME_DURATION = 0.1F;

    public Golem(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu, int type){

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

    }

    @Override
    public void setCarrac(int lvlStage) {
        if(_type%3 == 1) {
            vie = 85 + (lvlStage * 15);
            attaque = 12 + (3 * lvlStage);
            defense = (int) (6 + (1.5f * lvlStage));
            vitesse = 0.5f;
            portee = 2;
        }else if(_type%2==1){
            vie = 100 + (lvlStage * 25);
            attaque = 10 + (3 * lvlStage);
            defense = 6 + (2 * lvlStage);
            vitesse = 0.5f;
            portee = 1;
        }else{
            vie = 85 + (lvlStage * 20);
            attaque = 30 + (3 * lvlStage);
            defense = 2 + (lvlStage);
            vitesse = 1f;
            portee = 1;
        }
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
        } else if (_y != chemin[currentCase].getY()) {
            if (_y < chemin[currentCase].getY()) {
                currentAnimation = animeUp;
                _y += vitesse;
            } else {
                currentAnimation = animeDown;
                _y -= vitesse;
            }
        } else {
            if (currentCase > 0 && attaqueOk) {
                for (int i = portee; i > 0; i--) {
                    if (currentCase - i >= 0) {
                        if (!parent.testCase(currentCase - i, 1)) {
                            if (i == 1) {
                                currentCase--;
                            }
                        } else {
                            animationTimer = 0;
                            if (parent.getTargetUnit(this)) {
                                attaqueOk = false;
                                Timer.schedule(getAttaque, 2.5F);
                                break;
                            }
                        }
                    }
                }

            }else {

            }
        }
    }

    public boolean draw(SpriteBatch batch) {
        TextureRegion anime = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( anime, _x-8, _y);

        return dead;
    }

}