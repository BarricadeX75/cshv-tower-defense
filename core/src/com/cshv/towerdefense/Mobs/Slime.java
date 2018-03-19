package com.cshv.towerdefense.Mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;

import java.util.Date;


/**
 * Created by harri on 16/03/2018.
 */

public class Slime extends Mob {

    private static final float FRAME_DURATION = 0.1F;
    private static final int NB_CASE_WIDTH = 10;
    private static final int NB_CASE_HEIGHT = 16;
    private static final int DEPART = 32;
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
    private int defence;
    private boolean dead = false;
    private long timerMalus;
    private int _malus = 0;

    private final Animation<TextureRegion> animeRight;
    private final Animation<TextureRegion> animeLeft;
    private final Animation<TextureRegion> animeUp;
    private final Animation<TextureRegion> animeDown;
    private Animation<TextureRegion>currentAnimation;
    private TextureRegion textuteDim;

    public Slime(Array<TextureRegion> left, Array<TextureRegion> right, Array<TextureRegion> up, Array<TextureRegion> down, int lvlStage, GameScreen jeu){

        textuteDim = left.get(0);
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
        //chemin = parent.getChemin();
        setPosition(chemin[0].getX()+ DEPART , chemin[0].getY());

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
            if(currentCase < chemin.length){
                if(!testCase(currentCase+1)) {
                    currentCase++;
                }
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
        int numCase = (int) (chemin[currentCase].x%32 + ( chemin[currentCase].y%32 * NB_CASE_WIDTH ));
        return numCase;
    }

    @Override
    public void setCarrac(int lvlStage) {
        vie = 100 + ( lvlStage * 10 );
        attaque = 10 + ( lvlStage );
        defence = 0 + ( lvlStage );
        vitesse = 1 + ( lvlStage%2 );
    }

    @Override
    public void setDomage(int domage) {
        int dmg = domage - defence;
        if( dmg > 0 ){
            vie -= dmg;
        }
    }

    @Override
    public boolean testCase(int numCase) {

        return false;
    }

    @Override
    public void addMalus(int malus, int timer) {
        _malus = malus;
        timerMalus = (new Date().getTime() + timer);
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion slim = currentAnimation.getKeyFrame(animationTimer);
        float textureX = _x - textuteDim.getRegionWidth()/2;
        float textureY = _y - textuteDim.getRegionHeight()/2;
        batch.draw( slim, textureX, textureY);

        return dead;
    }
}
