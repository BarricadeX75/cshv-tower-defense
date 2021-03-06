package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;
import com.cshv.towerdefense.World;


/**
 * Created by harri on 16/03/2018.
 */

public class Slime extends Mob {

    private static final float FRAME_DURATION = 0.1F;

    public Slime(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu, int type){

        animeLeft = new Animation<TextureRegion>(FRAME_DURATION,left);
        animeLeft.setPlayMode(Animation.PlayMode.LOOP);
        animeRight = new Animation<TextureRegion>(FRAME_DURATION,right);
        animeRight.setPlayMode(Animation.PlayMode.LOOP);
        animeDown = new Animation<TextureRegion>(FRAME_DURATION,down);
        animeDown.setPlayMode(Animation.PlayMode.LOOP);
        animeUp = new Animation<TextureRegion>(FRAME_DURATION,up);
        animeUp.setPlayMode(Animation.PlayMode.LOOP);
        setCarrac(lvlStage);
        _type = type;
        currentAnimation = animeDown;
        parent = jeu;
        chemin = parent.getChemin();
        currentCase = chemin.length-1;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY()+ World.DEPART);

    }

    @Override
    public void setCarrac(int lvlStage) {
        if(_type%3 == 1) {
            vie = 60+ (lvlStage * 40);
            attaque = 5 + ( lvlStage);
            defense = (lvlStage);
            vitesse = 0.5f;
            portee = 3;
        }else if(_type%2==1){
            vie = 100 + (lvlStage * 35);
            attaque = 15 + (2*lvlStage);
            defense = (lvlStage);
            vitesse = 0.5f;
            portee = 2;
        }else{
            vie = 40 + (lvlStage * 20);
            attaque = 20 + (3 * lvlStage);
            defense = (int)(0.5f * lvlStage);
            vitesse = 0.5f;
            portee = 2;
        }
    }

}
