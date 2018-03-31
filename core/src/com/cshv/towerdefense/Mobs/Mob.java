package com.cshv.towerdefense.Mobs;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mob {

    private int vie;
    private int attaque;
    private int vitesse;
    private int defense;
    private int porter;
    private boolean dead = false;
    private long timerMalus;
    private int _malus;

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
