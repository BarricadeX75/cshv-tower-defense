package com.cshv.towerdefense.Mobs;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mob {

    protected int vie;
    protected int attaque;
    protected int vitesse;
    protected int defense;
    protected int portee;
    protected boolean dead = false;
    protected long timerMalus;
    protected int _malus;
    protected int direction;

    public abstract void move();
    public abstract void update(float delta);
    public abstract int getCurrentCase();
    public abstract void setCarrac(int lvlStage);
    public abstract void setDegats(int degats);
    public abstract void addMalus( int malus, int timer);
    public abstract void setDirection(int direction);
    public abstract boolean draw(SpriteBatch batch);
    public abstract int getPo();

}
