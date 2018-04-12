package com.cshv.towerdefense.Units;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

public abstract class Unit {
    protected int vieMax;
    protected int vie;
    protected int attaque;
    protected int vitesse;
    protected int defense;
    protected int portee;
    protected boolean dead = false;
    protected int type;
    protected  Animation<TextureRegion> animeRight;
    protected  Animation<TextureRegion> animeLeft;
    protected  Animation<TextureRegion> animeUp;
    protected  Animation<TextureRegion> animeDown;
    protected Animation<TextureRegion>currentAnimation;
    protected float _x;
    protected float _y;
    protected float animationTimer = 0;
    protected int currentCase = 0;
    protected Rectangle chemin[];
    protected GameScreen parent;
    protected boolean attaqueOk = true;
    protected Timer.Task getAttaque = new Timer.Task() {
        @Override
        public void run() {
            attaqueOk = true;
        }
    };

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
            if(currentCase < chemin.length-1 && attaqueOk){
                for(int i = portee; i>0 ; i--){
                    if(currentCase+i < chemin.length-1){
                        System.out.println(!parent.testCase(currentCase+i,2));
                        if(!parent.testCase(currentCase+i,2)) {
                            if(i == 1) {
                                currentCase++;
                            }
                        }else{
                            animationTimer = 0;
                            if(parent.getTargetMob(this)) {
                                attaqueOk = false;
                                Timer.schedule(getAttaque, 2.5F);
                            }
                        }
                    }
                }

            }else{
                animationTimer = 0;
            }
        }
    }

    public void setPosition(float x , float y){
        _x = x;
        _y = y;
    }
    public float getVita() {
        return vie/vieMax;
    }

    public int getType(){
        return type;
    }

    public void update(float delta) {
        animationTimer += delta;
        if(vie <= 0){
            dead = true;
        }else{
            move();
        }
    }
    public int getCurrentCase() {
        return currentCase;
    }
    public abstract void setCarrac(int lvlStage);
    public int getDegats() {
        return attaque;
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
    public void setDegats(int degats) {
        int dmg = degats - defense;
        if( dmg > 0 ){
            vie -= dmg;
        }
    }
    public boolean draw(SpriteBatch batch) {
        TextureRegion anime = currentAnimation.getKeyFrame(animationTimer);
        batch.draw( anime, _x, _y);

        return dead;
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
    public void setHeal(int heal){
        if((vie+heal) <= vieMax){
            vie += heal;
        }
        else {
            vie = vieMax;
        }
    }
}
