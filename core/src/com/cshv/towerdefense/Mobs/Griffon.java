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
 * Created by harri on 24/03/2018.
 */

public class Griffon extends Mob {

    private static final float FRAME_DURATION = 0.1F;
    private float _x;
    private float _y;
    private float animationTimer = 0;
    private int currentCase = 0;//enplacement dans chemin
    private Rectangle chemin[];
    private GameScreen parent;
    //carat ici pour l instant on vera apres si on les bouge dans une classe
    private int vie;
    private int attaque;
    private int vitesse;
    private int defense;
    private int porter;
    private boolean dead = false;
    private long timerMalus;
    private int _malus = 0;

    private final Animation<TextureRegion> animeRight;
    private final Animation<TextureRegion> animeLeft;
    private final Animation<TextureRegion> animeUp;
    private final Animation<TextureRegion> animeDown;
    private Animation<TextureRegion>currentAnimation;

    public Griffon(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu){

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
                for(int i=porter ; i>0 ; i--){
                    if(currentCase-i>=0){
                        if(!parent.testCase(currentCase-i,1)) {
                            parent.getTargetUnit(this);
                        }
                        currentCase--;
                    }
                }

            }else{
                animationTimer = 0;
            }
        }


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
        vie = 130 + ( lvlStage * 10 );
        attaque = 10 + ( lvlStage );
        defense = 5 + ( lvlStage );
        vitesse = 1 + (int)( lvlStage/2 );
        porter = 1;
    }

    @Override
    public int getPo() {
        return porter;
    }

    @Override
    public void setDomage(int domage) {
        int dmg = domage - defense;
        if( dmg > 0 ){
            vie -= dmg;
        }
    }

    @Override
    public void addMalus(int malus, int timer) {
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
