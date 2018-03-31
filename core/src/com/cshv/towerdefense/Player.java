package com.cshv.towerdefense;

/**
 * Created by Barricade on 30/03/2018.
 */

public class Player {

    private int vie, vieCombat, mana, manaCombat;

    public Player() {
        vie = 100;
        mana = 100;
    }

    public int getVie() {
        return vie;
    }

    public int getVieCombat() {
        return vieCombat;
    }

    public int getMana() {
        return mana;
    }

    public int getManaCombat() {
        return manaCombat;
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
}
