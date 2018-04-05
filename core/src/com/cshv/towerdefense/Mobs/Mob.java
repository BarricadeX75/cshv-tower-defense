package com.cshv.towerdefense.Mobs;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cshv.towerdefense.GameScreen;

public abstract class Mob {

    protected float _x;
    protected float _y;
    protected float animationTimer = 0;
    protected int currentCase = 0;
    protected Rectangle chemin[];
    protected GameScreen parent;
    protected Animation<TextureRegion> animeRight;
    protected Animation<TextureRegion> animeLeft;
    protected Animation<TextureRegion> animeUp;
    protected Animation<TextureRegion> animeDown;
    protected Animation<TextureRegion>currentAnimation;
    protected int vie;
    protected int attaque;
    protected int vitesse;
    protected int defense;
    protected int portee;
    protected boolean dead = false;
    protected long timerMalus;
    protected float _malus;
    protected int direction;

    public abstract void move();
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
    public abstract void addMalus( float malus, int timer);
    public abstract boolean draw(SpriteBatch batch);


}
