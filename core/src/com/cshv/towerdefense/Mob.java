package com.cshv.towerdefense;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mob {

    public abstract void move();
    public abstract void update(float delta);
    public abstract int getCurrentCase();
    public abstract void setCarrac(int lvlStage);
    public abstract void setDomage(int domage);
    public abstract boolean testCase( int numCase);
    public abstract void addMalus( int malus, int timer);
    public abstract boolean draw(SpriteBatch batch);

}
