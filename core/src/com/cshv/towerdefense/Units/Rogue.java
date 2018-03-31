package com.cshv.towerdefense.Units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.World;

import java.util.Date;

/**
 * Created by harri on 30/03/2018.
 */

public class Rogue extends Unit {
    private static final float FRAME_DURATION = 0.1F;
    private float _x;
    private float _y;
    private float animationTimer = 0;
    private int currentCase = 0;//enplacement dans chemin
    private Rectangle chemin[];
    private GameScreen parent;

    private final Animation<TextureRegion> animeRight;
    private final Animation<TextureRegion> animeLeft;
    private final Animation<TextureRegion> animeUp;
    private final Animation<TextureRegion> animeDown;
    private Animation<TextureRegion>currentAnimation;

    public Rogue(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu){

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
    public void move() {

        if (_x != chemin[currentCase].getX()) {
            if (_x < chemin[currentCase].getX()) {
                currentAnimation = animeRight;
                _x += vitesse;
            } else {
                currentAnimation = animeLeft;
                _x -= vitesse;
            }
        } else if(_y != chemin[currentCase].getY()){
            if (_y < chemin[currentCase].getY()) {
                currentAnimation = animeUp;
                _y += vitesse;
            } else {
                currentAnimation = animeDown;
                _y -= vitesse;
            }
        }else{
            if(currentCase < chemin.length-1){
                for(int i = portee; i>0 ; i--){
                    if(currentCase-i>=0){
                        if(!parent.testCase(currentCase+i,2)) {
                            currentCase++;
                        }else{
                            animationTimer = 0;
                            parent.getTargetMob(this);
                        }
                    }
                }

            }else{
                animationTimer = 0;
            }
        }


    }

    @Override
    public float getVita() {
        return vie/vieMax;
    }


    @Override
    public void update(float delta) {
        animationTimer += delta;
        if(vie == 0){
            dead = true;
        }else{
            move();
        }

    }

    public void setPosition(float x , float y){
        _x = x;
        _y = y;
    }

    @Override
    public int getCurrentCase() {
        int numCase = (int) (chemin[currentCase].x%32 + ( chemin[currentCase].y%32 * World.NB_CASE_WIDTH ));
        return numCase;
    }

    @Override
    public void setCarrac(int lvlStage) {
        vieMax = 100 + ( lvlStage * 10 );
        vie = vieMax;
        attaque = 10 + ( lvlStage );
        defense = 0 + ( lvlStage );
        vitesse = 1 + lvlStage/2;
        portee = 1;
    }

    @Override
    public void setDirection(int direction) {
        switch (direction){
            case 1: currentAnimation = animeLeft;
                break;
            case 2: currentAnimation = animeRight;
                break;
            case 3: currentAnimation = animeDown;
                break;
            case 4: currentAnimation = animeUp;
                break;
        }
    }

    @Override
    public int getPo() {
        return portee;
    }

    @Override
    public void setDegats(int degats) {
        int dmg = degats - defense;
        if( dmg > 0 ){
            vie -= dmg;
        }
    }

    @Override
    public int getDegats() {
        return attaque;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion slim = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( slim, _x, _y);

        return dead;
    }
}