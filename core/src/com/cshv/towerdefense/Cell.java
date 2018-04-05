package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
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

    public boolean testCell(int type){
        if(type == 1){
            if(units.size>0){
                return true;
            }else{
                return false;
            }
        }else{
            if(mobs.size>0){
                return true;
            }else{
                return false;
            }
        }
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

    public Unit getHeal(){
        for(int i=0 ; i<units.size ; i++ ){
            if(units.get(i).getVita() != 1 ){
                return units.get(i);
            }
        }
        return  null;
    }

    public void degatZone(int degat, int type){
        if(type ==1){
            for(int i=0 ; i<mobs.size ; i++){
                mobs.get(i).setDegats(degat);
            }
        }else{
            for(int i=0 ; i<units.size ; i++){
                units.get(i).setDegats(degat);
            }
        }
    }

    public void setVision(){
        vision = true;
    }

    public boolean getVision(){
        return vision;
    }
}
