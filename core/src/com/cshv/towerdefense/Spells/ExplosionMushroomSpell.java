package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Cell;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Towers.Tower;

/**
 * Created by harri on 15/04/2018.
 */

public class ExplosionMushroomSpell extends Spell {
    private static final float FRAME_DURATION = 0.1F;
    private Animation<TextureRegion> explosion;
    private TextureRegion fin;
    private float x,y;
    private Cell _cell;
    private float animationTimer = 0;


    public ExplosionMushroomSpell (Array<TextureRegion> exploTexture, float xCell, float yCell, Cell cell){
        explosion = new Animation<TextureRegion>(FRAME_DURATION,exploTexture);
        explosion.setPlayMode(Animation.PlayMode.NORMAL);
        fin = exploTexture.get(exploTexture.size-1);
        x = xCell;
        y = yCell;
        _cell = cell;
    }

    private void setDegat(){
        _cell.allDead();
    }


    @Override
    public void update(float delta) {
        animationTimer += delta;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion spell = explosion.getKeyFrame(animationTimer);


        batch.draw(spell,x-20,y-20);

        if(spell == fin){
            setDegat();
            return true;
        }

        return false;
    }
}