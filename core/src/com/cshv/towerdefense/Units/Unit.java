package com.cshv.towerdefense.Units;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Unit {
    private int vieMax;
    private int vie;
    private int attaque;
    private int vitesse;
    private int defense;
    private int portee;
    private boolean dead = false;
    private long timerMalus;
    private int _malus;

    public abstract void move();
    public abstract float getVita();
    public abstract void update(float delta);
    public abstract int getCurrentCase();
    public abstract void setCarrac(int lvlStage);
    public abstract void setDirection(int direction);
    public abstract void setDegats(int degats);
    public abstract boolean draw(SpriteBatch batch);
    public abstract int getPo();
}
