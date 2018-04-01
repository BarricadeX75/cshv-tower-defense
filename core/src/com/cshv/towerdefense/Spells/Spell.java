package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Barricade on 23/03/2018.
 */

public abstract class Spell {

    public abstract void move();
    public abstract void update(float delta);
    public abstract boolean draw(SpriteBatch batch);
}
