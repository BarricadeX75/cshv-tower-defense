package com.cshv.towerdefense.Units;


import com.badlogic.gdx.math.Rectangle;

public abstract class Unit {

    public abstract void move();
    public abstract boolean testCase( int numCase);
    public abstract int getCurrentCase();
}
