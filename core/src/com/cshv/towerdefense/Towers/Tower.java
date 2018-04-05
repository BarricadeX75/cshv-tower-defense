package com.cshv.towerdefense.Towers;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tower {

    private static final float BAR_WIDTH = 32f;
    private static final float BAR_HEIGHT = 4f;

    protected float _x;
    protected float _y;
    protected int attaque;
    protected int portee;
    protected long atcSpeed;
    protected float malus;
    protected long timer;
    protected float animationTimer = 0;
    protected Animation<TextureRegion> actTower;

    private TextureRegion barBack, barFront;


    public Tower(TextureRegion barBack, TextureRegion barFront) {
        this.barBack = barBack;
        this.barFront = barFront;
    }

    public abstract void getTarget();

    public void setPosition(float x, float y) {
        _x = x;
        _y = y;
    }

    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

    public float getMalus() {
        return malus;
    }

    public int getAttaque() {
        return attaque;
    }

    public void update(float delta) {
        animationTimer += delta;
        getTarget();
    }

    public void draw(SpriteBatch batch) {
        TextureRegion tower = actTower.getKeyFrame(animationTimer);
        batch.draw(tower,_x,_y);

        batch.draw(barBack, _x, _y, BAR_WIDTH, BAR_HEIGHT);
        batch.draw(barFront, _x, _y, BAR_WIDTH, BAR_HEIGHT);
    }
}
