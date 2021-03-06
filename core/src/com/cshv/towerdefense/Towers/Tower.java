package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;

public abstract class Tower {

    public static final int FAST_TOWER = 1, SLOW_TOWER = 2, ZONE_TOWER = 3, VISION_TOWER = 4;
    public static final int SPELL_ZONE = 60, SPELL_SLOW = 50, SPELL_FAST = 30, SPELL_VISION = 30;

    public static final float BAR_WIDTH = 32f;
    public static final float BAR_HEIGHT = 4f;

    protected float _x;
    protected float _y;
    protected boolean tireOK = true;
    protected GameScreen parent;
    protected int lvlTower;
    protected int type;
    protected int attaque;
    protected float chargementSpell = 0;
    protected int portee;
    protected float atcSpeed;
    protected float malus;
    protected long timer;
    protected TextureRegion _tower;
    protected Timer.Task getTargetTask = new Timer.Task() {
        @Override
        public void run() {
            //Gdx.app.log()
            tireOK = true;
        }
    };

    protected TextureRegion barBack, barFront;


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
    public void noManaForSpell(){
        chargementSpell = 32;
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
        chargementSpell += 0.03f;
        if(chargementSpell > 32f){
            chargementSpell = 32f;
        }
        getTarget(0);
    }

    public void draw(SpriteBatch batch) {

        batch.draw(_tower,_x+1,_y);

        batch.draw(barBack, _x, _y, BAR_WIDTH, BAR_HEIGHT);
        batch.draw(barFront, _x, _y, chargementSpell, BAR_HEIGHT);
    }
}
