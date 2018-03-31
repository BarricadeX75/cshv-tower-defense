package com.cshv.towerdefense.Units;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Unit {
    protected int vieMax;
    protected int vie;
    protected int attaque;
    protected int vitesse;
    protected int defense;
    protected int portee;
    protected boolean dead = false;
    protected int direction;

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
