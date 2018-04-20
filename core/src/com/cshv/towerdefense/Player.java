package com.cshv.towerdefense;

import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Towers.Tower;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Barricade on 30/03/2018.
 */

public class Player {

    private String nom;
    private float vie, vieCombat, mana, manaCombat;
    private int gold, lvlStage, lvlFastTower, lvlZoneTower, lvlVisionTower, lvlSlowTower;
    private int lvlChevalier, lvlHealer, lvlMage, lvlRogue, lvlMoine, lvlFontaine;
    private float regenMana;
    private Array<Integer> chemin;
    private HashMap<Integer, Integer> towers;

    //Constructeur pour un joueur existant
    public Player(String nom, int gold, int lvlChevalier, int lvlMage, int lvlMoine, int lvlRogue,
                  int lvlHealer, int lvlFastTower, int lvlSlowTower, int lvlZoneTower, int lvlVisionTower, int lvlFontaine, Array<Integer> chemin, HashMap<Integer, Integer> towers, int lvlStage){
        this.nom = nom;
        this.gold = gold;
        this.lvlChevalier = lvlChevalier;
        this.lvlMage = lvlMage;
        this.lvlMoine = lvlMoine;
        this.lvlRogue = lvlRogue;
        this.lvlHealer = lvlHealer;
        this.lvlFastTower = lvlFastTower;
        this.lvlSlowTower = lvlSlowTower;
        this.lvlZoneTower = lvlZoneTower;
        this.lvlVisionTower = lvlVisionTower;
        this.chemin = chemin;
        this.towers = towers;
        this.lvlFontaine = lvlFontaine;
        this.lvlStage = lvlStage;
    }
    //Constructeur par défaut
    public Player() {
        nom = "Brioche";
        vie = 300;
        mana = 160;
        gold = 0;
        lvlChevalier = 1;
        lvlRogue = 1;
        lvlMoine = 1;
        lvlHealer = 1;
        lvlMage = 1;
        lvlStage = 1;
        lvlFastTower = 1;
        lvlSlowTower = 1;
        lvlZoneTower = 1;
        lvlVisionTower = 1;
        lvlFontaine = 1;

        chemin = new Array<Integer>();
        for(int i=5 ; i<176 ; i+=11){
            chemin.add(i);
        }

        towers = new HashMap<Integer, Integer>();
        towers.put(81, Tower.FAST_TOWER);
        towers.put(83, Tower.SLOW_TOWER);
        towers.put(70, Tower.ZONE_TOWER);
        towers.put(72, Tower.VISION_TOWER);

        resetStatsCombat();
    }

    //Constructeur pour un nouveau joueur
    public Player(String nom) {
        this();
        this.nom = nom;
    }

    public Array<Integer> getChemin() {
        return chemin;
    }

    public void setChemin(Array<Integer> chemin) {
        this.chemin = chemin;
    }

    public HashMap<Integer, Integer> getTowers() {
        return towers;
    }

    public void setTowers(HashMap<Integer, Integer> towers) {
        this.towers = towers;
    }

    public void setLvlFontaine(int lvl){
        lvlFontaine = lvl;
    }

    public int getLvlFontaine(){
        return lvlFontaine;
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

    public void addGold(int gold) {
        this.gold += gold;
    }

    public void removeGold(int gold) {
        this.gold -= gold;
    }

    public String getCheminString(){
        String _chemin = "";
        for(Integer integer : chemin){
            _chemin += integer+"::";
        }
        return _chemin;
    }

    public long getDate() {
        return new Date().getTime();
    }

    public String getTowersString() {
        String tower = "";
        for(Integer pos: towers.keySet()){
            Integer type = towers.get(pos);
            tower += pos+",,"+type+"::";
        }
        return tower;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setVie(float vie) {
        this.vie = vie;
    }

    public int getLvlStage(){
        return lvlStage;
    }

    public String getNom() {
        return nom;
    }

    public float getVie() {
        return vie;
    }

    public float getVieCombat() {
        return vieCombat;
    }

    public void setVieCombat(float vieCombat) {
        this.vieCombat = vieCombat;
    }

    public float getMana() {
        return mana;
    }

    public float getManaCombat() {
        return manaCombat;
    }

    public void setManaCombat(float manaCombat) {
        this.manaCombat = manaCombat;
    }

    public void resetStatsCombat() {
        vie = 300 + 15 * (lvlFontaine-1);
        mana = 160 + 5 * (lvlFontaine-1);
        regenMana = 0.02f + (0.001f * lvlFontaine);
        vieCombat = vie;
        manaCombat = mana;
    }

    public void updateRegenMana(){
        manaCombat += regenMana;
        if(manaCombat> mana){
            manaCombat = mana;
        }
    }

    public void recevoirDegats(float degats) {
        if (degats > 0) {
            vieCombat -= degats;

            if (vieCombat < 0)
                vieCombat = 0;
        }
    }

    public void bonusMana(float manaBonus){
        manaCombat += manaBonus;
        if(manaCombat > mana){
            manaCombat = mana;
        }
    }

    public void bonusVie(float vieBonus){
        vieCombat += vieBonus;
        if(vieCombat > vie){
            vieCombat = vie;
        }
    }

    public void depenserMana(float mana) {
        if (mana > 0) {
            manaCombat -= mana;

            if (manaCombat < 0)
                manaCombat = 0;
        }
    }

    public void addStat(int lvlSupp, int cible){
        switch (cible){
            case 0: lvlChevalier += lvlSupp;
                break;
            case 1: lvlMage += lvlSupp;
                break;
            case 2: lvlMoine += lvlSupp;
                break;
            case 3: lvlRogue += lvlSupp;
                break;
            case 4: lvlHealer += lvlSupp;
                break;
            case 5: lvlFastTower += lvlSupp;
                break;
            case 6: lvlSlowTower += lvlSupp;
                break;
            case 7: lvlZoneTower += lvlSupp;
                break;
            case 8: lvlVisionTower += lvlSupp;
                break;
            case 9: lvlFontaine += lvlSupp;
                break;
        }

        resetStatsCombat();
    }


}
