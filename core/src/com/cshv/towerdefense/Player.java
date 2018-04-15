package com.cshv.towerdefense;

/**
 * Created by Barricade on 30/03/2018.
 */

public class Player {

    private String nom;
    private float vie, vieCombat, mana, manaCombat;
    private int lvlStage, gold, lvlFastTower, lvlZoneTower, lvlVisionTower, lvlSlowTower;
    private int lvlChevalier, lvlHealer, lvlMage, lvlRogue, lvlMoine;


    public Player() {
        nom = "Brioche";
        vie = 300;
        mana = 160;
        lvlChevalier = 1;
        lvlRogue = 1;
        lvlMoine = 1;
        lvlHealer = 1;
        lvlMage = 1;
        lvlStage = 1;
        gold = 0;
        lvlZoneTower = 1;
        lvlVisionTower = 1;

        lvlSlowTower = 1;
        lvlFastTower = 1;

        resetStatsCombat();
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
        vieCombat = vie;
        manaCombat = mana;
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
}
