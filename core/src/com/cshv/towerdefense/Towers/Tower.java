package com.cshv.towerdefense.Towers;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Tower {

    protected int attaque;
    protected int porter;
    protected long atcSpeed;

    public abstract int getTarget();
    public abstract Rectangle getCaseDist();
    public abstract  void setPosition(float x , float y);
    public abstract  void update(float delta);
    public abstract void draw(SpriteBatch batch);

}
