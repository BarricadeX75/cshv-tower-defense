package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
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
    private boolean visionTempo = false;
    protected Timer.Task finSpellVision = new Timer.Task() {
        @Override
        public void run() {
            visionTempo = vision;
        }
    };

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
        }else if(type == 2){
            if(mobs.size>0){
                return true;
            }else{
                return false;
            }
        }else{
            if(units.size>0){
                for(Unit unit : units){
                    if(unit.getVita()<1){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public int getNbMob(){
        return mobs.size;
    }

    public void addMob(Mob mob){
        mobs.add(mob);
    }

    public void addUnit(Unit unit){
        units.add(unit);
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
        visionTempo = true;
    }

    public boolean getVision(){
        return visionTempo;
    }

    public void spellVisionOk(){
        visionTempo = true;
        Timer.schedule(finSpellVision, 30f);
    }
}
