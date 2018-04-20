package com.cshv.towerdefense;


import java.util.Date;
import java.util.HashMap;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harri on 16/04/2018.
 */

public class PlayerJson {
    private static final float TIME_MAX = 86400000;

    private String nom;
    private int lvlStage, gold, lvlFastTower, lvlZoneTower, lvlVisionTower, lvlSlowTower;
    private int lvlChevalier, lvlHealer, lvlMage, lvlRogue, lvlMoine, lvlFontaine;
    private String chemin;
    private String posTowers;
    private long date;

    public PlayerJson() {

    }

    public Player getPlayer(){
        return new Player( nom, getGold(), lvlChevalier, lvlMage, lvlMoine, lvlRogue, lvlHealer, lvlFastTower,
                lvlSlowTower, lvlZoneTower, lvlVisionTower, lvlFontaine, getChemin(), getTowers(), lvlStage);
    }

    public Array<Integer> getChemin(){
        Array<Integer> chemin = new Array<Integer>();
        String[] tempo = this.chemin.split("::");
        for(int i=0 ; i<tempo.length ; i++){
            chemin.add(Integer.parseInt(tempo[i]));
        }
        return chemin;
    }

    public HashMap<Integer,Integer> getTowers(){
        HashMap<Integer,Integer> towers = new HashMap<Integer, Integer>();
        String[] tempo = this.posTowers.split("::");
        for(int i=0 ; i<tempo.length ; i++){
            String[] tempo2 = tempo[i].split(",,");
            towers.put(Integer.parseInt(tempo2[0]),Integer.parseInt(tempo2[1]));
        }
        return towers;
    }

    public int getGold(){
        int gold = (int) (200 * Math.pow(1.2,lvlStage-1));
        long diffDate = new Date().getTime() - date;
        float ratio = diffDate/TIME_MAX;
        if(ratio > 1){
            ratio = 1;
        }
        return (int)(gold/ratio);
    }
}
