package com.cshv.towerdefense;

import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Units.Unit;

/**
 * Created by harri on 19/03/2018.
 */

public class Cell {
    private int _numChemin;
    private Array<Mob> mobs = new Array<Mob>();
    private Array<Unit> units = new Array<Unit>();
    private boolean vision = false;

    public Cell(int numChemin){
        _numChemin = numChemin;
    }

    public void addMob(Mob mob){
        mobs.add(mob);
    }
    public void removeMob(Mob mob){
        mobs.removeValue(mob,true);
    }

    public void addUnit(Unit unit){
        units.add(unit);
    }

    public void removeUnit(Unit unit){
        units.removeValue(unit,true);
    }

    public void removeAll(){
        mobs.clear();
        units.clear();
    }

    public Mob getMob(){
        if(mobs.size>0){
            return mobs.first();
        }
        return null;
    }

    public Unit getUnit(){
        if(units.size>0){
            return units.first();
        }
        return null;
    }

    public void setVision(){
        vision = true;
    }

    public boolean getVision(){
        return vision;
    }
}
