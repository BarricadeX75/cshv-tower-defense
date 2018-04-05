package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Cell;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Units.Unit;

/**
 * Created by harri on 03/04/2018.
 */

public class MagicSpell extends Spell {
    private static final float FRAME_DURATION = 0.1F;
    private Animation<TextureRegion> magicSpell;
    private TextureRegion fin;
    private Mob _mob;
    private Unit _unit;
    private Cell _cells[];
    private int _type;
    private float animationTimer = 0;


    public MagicSpell (Array<TextureRegion> magie, Unit unit, Mob mob, Cell[] cells, int type){
        magicSpell = new Animation<TextureRegion>(FRAME_DURATION,magie);
        magicSpell.setPlayMode(Animation.PlayMode.NORMAL);
        fin = magie.get(magie.size-1);
        _mob = mob;
        _unit = unit;
        _type = type;
        _cells = cells;
    }

    private void setDegat(){
        if(_type == 1){
            Cell cell = _cells[_mob.getCurrentCase()];
            cell.degatZone(_unit.getDegats(),1);
        }else{
            Cell cell = _cells[_unit.getCurrentCase()];
            cell.degatZone(_mob.getDegats(),1);
        }
    }


    @Override
    public void update(float delta) {
        animationTimer += delta;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion spell = magicSpell.getKeyFrame(animationTimer);
        float x,y;
        if(_type == 1) {
            x = _mob.getX();
            y = _mob.getY();
        }else{
            x = _unit.getX();
            y = _unit.getY();
        }
        batch.draw(spell,x,y);

        if(spell == fin){
            setDegat();
            return true;
        }

        return false;
    }
}