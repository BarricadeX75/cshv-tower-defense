package com.cshv.towerdefense.Towers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Tower {
    protected float _x;
    protected float _y;
    protected int attaque;
    protected int porter;
    protected long atcSpeed;
    protected float malus;

    public abstract void getTarget();
    public abstract Rectangle getCaseDist();
    public abstract float getMalus();
    public abstract  void setPosition(float x , float y);
    public abstract  void update(float delta);
    public abstract void draw(SpriteBatch batch);
    public abstract float getX();
    public abstract float getY();
    public abstract int getAttaque();

}
