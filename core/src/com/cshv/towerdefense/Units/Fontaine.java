package com.cshv.towerdefense.Units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.NormalMode;
import com.cshv.towerdefense.Player;

/**
 * Created by harri on 16/04/2018.
 */

public class Fontaine extends Unit {
    TextureRegion spriteFontaine;
    Player _player;
    private  int lvlFontaine;

    public Fontaine(Array<TextureRegion> fontaine, GameScreen jeu, int lvlFontaine, Player player){
        int numSprite = lvlFontaine/10;
        if(numSprite>3){
            numSprite = 3;
        }
        this.lvlFontaine = lvlFontaine;
        spriteFontaine = fontaine.get(numSprite);
        parent = jeu;
        chemin = parent.getChemin();
        currentCase = 0;
        _player = player;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY());
    }

    @Override
    public void setCarrac(int lvlStage) {
        vie = 1;
        vieMax = 1;
    }

    public void update(float delta) {

    }

    public float getVita() {
        return 1;
    }

    public void setDegats(int degats) {
        _player.recevoirDegats(degats);
    }

    public boolean draw(SpriteBatch batch) {
        if(lvlFontaine>=10){
            batch.draw(spriteFontaine,_x-5,_y);
        }else {
            batch.draw(spriteFontaine, _x - 1, _y);
        }
        return dead;
    }
}
