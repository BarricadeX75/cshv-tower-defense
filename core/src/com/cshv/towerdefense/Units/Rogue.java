package com.cshv.towerdefense.Units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;
import com.cshv.towerdefense.World;

/**
 * Created by harri on 30/03/2018.
 */

public class Rogue extends Unit {
    private static final float FRAME_DURATION = 0.1F;


    public Rogue(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu){
        type = ROGUE;
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
        vieMax = 60 + ( lvlStage * 10 );
        vie = vieMax;
        attaque = 15 + (5* lvlStage );
        defense = (int)(0.5 * lvlStage );
        vitesse = 1 ;
        portee = 1;
    }
}