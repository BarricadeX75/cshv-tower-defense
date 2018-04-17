package com.cshv.towerdefense.Units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.GameScreen;
import com.cshv.towerdefense.Player;
import com.cshv.towerdefense.World;

/**
 * Created by harri on 16/04/2018.
 */

public class Fontaine extends Unit {
    TextureRegion spriteFontaine;
    Player _player;

    public Fontaine(Array<TextureRegion> fontaine, GameScreen jeu, int lvlFontaine, Player player){
        int numSprite = lvlFontaine/10;
        if(numSprite>3){
            numSprite = 3;
        }
        spriteFontaine = fontaine.get(numSprite);
        parent = jeu;
        chemin = parent.getChemin();
        currentCase = 0;
        _player = player;
        setPosition(chemin[currentCase].getX() , chemin[currentCase].getY());
    }

    @Override
    public void setCarrac(int lvlStage) {
        vie = 0;
    }

    public void update(float delta) {

    }

    public void setDegats(int degats) {
        _player.recevoirDegats(degats);
    }

    public boolean draw(SpriteBatch batch) {
        batch.draw( spriteFontaine, _x-1, _y);
        return dead;
    }
}
