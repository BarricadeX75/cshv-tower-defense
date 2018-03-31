package com.cshv.towerdefense;

/**
 * Created by Barricade on 30/03/2018.
 */

public class Player {

    private String nom;
    private int vie, vieCombat, mana, manaCombat;

    public Player() {
        vie = 100;
        mana = 100;
    }

    public String getNom() {
        return nom;
    }
    public int getVie() {
        return vie;
    }

    public int getVieCombat() {
        return vieCombat;
    }

    public void setVieCombat(int vieCombat) {
        this.vieCombat = vieCombat;
    }

    public int getMana() {
        return mana;
    }

    public int getManaCombat() {
        return manaCombat;
    }

    public void setManaCombat(int manaCombat) {
        this.manaCombat = manaCombat;
    }

    public void resetStatsCombat() {
        vieCombat = vie;
        manaCombat = mana;
    }

    public void recevoirDegats(int degats) {
        if (degats > 0) {
            vieCombat -= degats;

            if (vieCombat < 0)
                vieCombat = 0;
        }
    }

    public void depenserMana(int mana) {
        if (mana > 0) {
            manaCombat -= mana;

            if (manaCombat < 0)
                manaCombat = 0;
        }
    }
}
