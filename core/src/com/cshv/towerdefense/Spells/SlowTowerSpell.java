package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Cell;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Towers.Tower;

/**
 * Created by harri on 13/04/2018.
 */

public class SlowTowerSpell extends Spell {
    private static final float FRAME_DURATION = 0.1F;
    private Animation<TextureRegion> magicSpell;
    private TextureRegion fin;
    private Mob _mob;
    private Tower _tower;
    private Cell _cells[];
    private int _type;
    private float animationTimer = 0;


    public SlowTowerSpell (Array<TextureRegion> magie, Tower tower, Mob mob, Cell[] cells){
        magicSpell = new Animation<TextureRegion>(FRAME_DURATION,magie);
        magicSpell.setPlayMode(Animation.PlayMode.NORMAL);
        fin = magie.get(magie.size-1);
        _mob = mob;
        _tower = tower;
        _cells = cells;
    }

    private void setDegat(){
        Cell cell = _cells[_mob.getCurrentCase()];
        cell.degatZone(_tower.getAttaque()*4,1);
        cell.addMalusAll(_tower.getMalus());
    }


    @Override
    public void update(float delta) {
        animationTimer += delta;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion spell = magicSpell.getKeyFrame(animationTimer);
        float x,y;
        x = _mob.getX();
        y = _mob.getY();

        batch.draw(spell,x,y);

        if(spell == fin){
            setDegat();
            return true;
        }

        return false;
    }
}