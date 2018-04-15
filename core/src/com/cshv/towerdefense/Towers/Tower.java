package com.cshv.towerdefense.Towers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;

public abstract class Tower {

    private static final float BAR_WIDTH = 32f;
    private static final float BAR_HEIGHT = 4f;

    protected float _x;
    protected float _y;
    protected boolean tireOK = true;
    protected GameScreen parent;
    protected int type;
    protected int attaque;
    protected float chargementSpell = 0;
    protected int portee;
    protected float atcSpeed;
    protected float malus;
    protected long timer;
    protected float animationTimer = 0;
    protected Animation<TextureRegion> actTower;
    protected Timer.Task getTargetTask = new Timer.Task() {
        @Override
        public void run() {
            //Gdx.app.log()
            tireOK = true;
        }
    };

    private TextureRegion barBack, barFront;


    public Tower(TextureRegion barBack, TextureRegion barFront) {
        this.barBack = barBack;
        this.barFront = barFront;
    }

    public int useSpell(){
        if(chargementSpell == 32){
            chargementSpell = 0;
            return type;
        }
        return 0;
    }

    public abstract void getTarget(int typeAtc);

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
        chargementSpell += 1f;//0.02f;
        if(chargementSpell > 32f){
            chargementSpell = 32f;
        }
        getTarget(0);
    }

    public void draw(SpriteBatch batch) {
        TextureRegion tower = actTower.getKeyFrame(animationTimer);
        batch.draw(tower,_x,_y);

        batch.draw(barBack, _x, _y, BAR_WIDTH, BAR_HEIGHT);
        batch.draw(barFront, _x, _y, chargementSpell, BAR_HEIGHT);
    }
}
