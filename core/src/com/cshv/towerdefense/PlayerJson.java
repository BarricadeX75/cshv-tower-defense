package com.cshv.towerdefense;


import java.util.HashMap;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harri on 16/04/2018.
 */

public class PlayerJson {
    private String nom;
    private int lvlStage, gold, lvlFastTower, lvlZoneTower, lvlVisionTower, lvlSlowTower;
    private int lvlChevalier, lvlHealer, lvlMage, lvlRogue, lvlMoine, lvlFontaine;
    private String chemin;
    private String posTower;

    public PlayerJson() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getLvlStage() {
        return lvlStage;
    }

    public void setLvlStage(int lvlStage) {
        this.lvlStage = lvlStage;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLvlFastTower() {
        return lvlFastTower;
    }

    public void setLvlFastTower(int lvlFastTower) {
        this.lvlFastTower = lvlFastTower;
    }

    public int getLvlZoneTower() {
        return lvlZoneTower;
    }

    public void setLvlZoneTower(int lvlZoneTower) {
        this.lvlZoneTower = lvlZoneTower;
    }

    public int getLvlVisionTower() {
        return lvlVisionTower;
    }

    public void setLvlVisionTower(int lvlVisionTower) {
        this.lvlVisionTower = lvlVisionTower;
    }

    public int getLvlSlowTower() {
        return lvlSlowTower;
    }

    public void setLvlSlowTower(int lvlSlowTower) {
        this.lvlSlowTower = lvlSlowTower;
    }

    public int getLvlChevalier() {
        return lvlChevalier;
    }

    public void setLvlChevalier(int lvlChevalier) {
        this.lvlChevalier = lvlChevalier;
    }

    public int getLvlHealer() {
        return lvlHealer;
    }

    public void setLvlHealer(int lvlHealer) {
        this.lvlHealer = lvlHealer;
    }

    public int getLvlMage() {
        return lvlMage;
    }

    public void setLvlMage(int lvlMage) {
        this.lvlMage = lvlMage;
    }

    public int getLvlRogue() {
        return lvlRogue;
    }

    public void setLvlRogue(int lvlRogue) {
        this.lvlRogue = lvlRogue;
    }

    public int getLvlMoine() {
        return lvlMoine;
    }

    public void setLvlMoine(int lvlMoine) {
        this.lvlMoine = lvlMoine;
    }

    public int getLvlFontaine() {
        return lvlFontaine;
    }

    public void setLvlFontaine(int lvlFontaine) {
        this.lvlFontaine = lvlFontaine;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin( Array<Integer> _chemin) {
        chemin = "";
        for(Integer integer : _chemin){
            chemin += integer+"::";
        }
    }

    public String getPosTower() {
        return posTower;
    }

    public void setPosTower(HashMap<Integer,Integer> posTowers) {
        String tower = "";
        for(Integer pos: posTowers.keySet()){
            Integer type = posTowers.get(pos);
            tower += pos+",,"+type+"::";
        }
        posTower = tower;
    }
}
