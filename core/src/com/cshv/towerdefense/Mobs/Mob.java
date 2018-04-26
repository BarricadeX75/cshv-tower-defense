package com.cshv.towerdefense.Mobs;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

public abstract class Mob {

    protected float _x;
    protected float _y;
    protected float animationTimer = 0;
    protected int currentCase = 0;
    protected Rectangle chemin[];
    protected GameScreen parent;
    protected int _type;
    protected boolean visible = true;
    protected Animation<TextureRegion> animeRight;
    protected Animation<TextureRegion> animeLeft;
    protected Animation<TextureRegion> animeUp;
    protected Animation<TextureRegion> animeDown;
    protected Animation<TextureRegion>currentAnimation;
    protected int vie;
    protected int attaque;
    protected float vitesse;
    protected int defense;
    protected int portee;
    protected boolean dead = false;
    protected boolean malusOn = false;
    protected float _malus;
    protected boolean attaqueOk = true;
    protected Timer.Task malusOff = new Timer.Task() {
        @Override
        public void run() {
            malusOn = false;
        }
    };
    protected Timer.Task getAttaque = new Timer.Task() {
        @Override
        public void run() {
            attaqueOk = true;
        }
    };

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
                        if(!parent.testCase(currentCase-i,1)) {
                            if(i == 1) {
                                currentCase--;
                            }
                        }else{
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
    public void update(float delta) {
        animationTimer += delta;
        if(vie <= 0){
            dead = true;
        }else{
            move();
        }
    }
    public void setPosition(float x , float y){
        _x = x;
        _y = y;
    }
    public boolean getVision(){
        return visible;
    }
    public int getCurrentCase() {
        return currentCase;
    }
    public abstract void setCarrac(int lvlStage);
    public int getDegats() {
        return attaque;
    }
    public int getPo() {
        return portee;
    }
    public float getX() {
        return chemin[currentCase].getX();
    }
    public float getY() {
        return chemin[currentCase].getY();
    }
    public void setDegats(int degats) {
        int dmg = degats - defense;
        if( dmg > 0 ){
            vie -= dmg;
        }
    }
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
    public void addMalus(float malus) {
        if (!malusOn) {
            _malus = malus;
            if (malus > vitesse) {
                _malus = vitesse;
            }
            if(vitesse == 2){
                _malus = 1;
            }
            malusOn = true;
            Timer.schedule(malusOff, 5f);
        }
    }
    public boolean draw(SpriteBatch batch) {
        TextureRegion anime = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( anime, _x, _y);

        return dead;
    }


}
