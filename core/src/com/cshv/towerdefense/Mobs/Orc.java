package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;
import com.cshv.towerdefense.World;

/**
 * Created by harri on 24/03/2018.
 */

public class Orc extends Mob {

    private static final float FRAME_DURATION = 0.1F;

    public Orc(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu, int type){

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
        _type = type;
        chemin = parent.getChemin();
        currentCase = chemin.length-1;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY()+ World.DEPART);

    }

    @Override
    public void setCarrac(int lvlStage) {
        if(_type == 3) {
            vie = 100 + (lvlStage * 40);
            attaque = 20 + (5 * lvlStage);
            defense = (int) (0.25 * lvlStage);
            vitesse = 0.5f;
            portee = 2;
        }else if(_type == 2) {
            vie = 100 + (lvlStage * 15);
            attaque = 15 + (4 * lvlStage);
            defense = (int) (0.25 * lvlStage);
            vitesse = 0.5f;
            portee = 3;
        }else {
            vie = 100 + (lvlStage * 60);
            attaque = 15 + (2 * lvlStage);
            defense = (int) (0.5 * lvlStage);
            vitesse = 0.5f;
            portee = 1;
        }
    }

    public boolean draw(SpriteBatch batch) {
        TextureRegion anime = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( anime, _x-8, _y);

        return dead;
    }

}
