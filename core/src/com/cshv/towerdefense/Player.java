package com.cshv.towerdefense;

/**
 * Created by Barricade on 30/03/2018.
 */

public class Player {

    private String nom;
    private float vie, vieCombat, mana, manaCombat;

    public Player() {
        nom = "Brioche";
        vie = 100;
        mana = 100;

        resetStatsCombat();
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

    public void depenserMana(float mana) {
        if (mana > 0) {
            manaCombat -= mana;

            if (manaCombat < 0)
                manaCombat = 0;
        }
    }
}
