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

public class ZoneTowerSpell extends Spell {
    private static final float FRAME_DURATION = 0.1F;
    private Animation<TextureRegion> spellTower;
    private TextureRegion fin;
    private int _direction;
    private Tower _tower;
    private Cell _cells[];
    private Array<Integer> _caseCible;
    private float animationTimer = 0;


    public ZoneTowerSpell ( Array<TextureRegion> atcLeft, Array<TextureRegion> atcRight, Array<TextureRegion> atcDown, Array<TextureRegion> atcUp, Tower tower, Cell[] cells, Array<Integer> caseCible, int direction){
        switch (direction){
            case 1:
                spellTower = new Animation<TextureRegion>(FRAME_DURATION,atcLeft);
                spellTower.setPlayMode(Animation.PlayMode.NORMAL);
                fin = atcLeft.get(atcLeft.size-1);
                break;
            case 2:
                spellTower = new Animation<TextureRegion>(FRAME_DURATION,atcRight);
                spellTower.setPlayMode(Animation.PlayMode.NORMAL);
                fin = atcRight.get(atcRight.size-1);
                break;
            case 3:
                spellTower = new Animation<TextureRegion>(FRAME_DURATION,atcDown);
                spellTower.setPlayMode(Animation.PlayMode.NORMAL);
                fin = atcDown.get(atcDown.size-1);
                break;
            case 4:
                spellTower = new Animation<TextureRegion>(FRAME_DURATION,atcUp);
                spellTower.setPlayMode(Animation.PlayMode.NORMAL);
                fin = atcUp.get(atcUp.size-1);
                break;
        }
        _direction = direction;
        _caseCible = caseCible;
        _tower = tower;
        _cells = cells;
    }

    private void setDegat(){
        int domage = _tower.getAttaque()*3;
        for(Integer integer : _caseCible){
            _cells[integer].degatZone(domage,1);
        }
    }


    @Override
    public void update(float delta) {
        animationTimer += delta;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion spell = spellTower.getKeyFrame(animationTimer);
        float x,y;
        x = _tower.getX();
        y = _tower.getY();
        if(_direction == 1){
            x -= spell.getRegionWidth();
        }else if( _direction == 3){
            y -= spell.getRegionHeight();
        }


        batch.draw(spell,x,y);

        if(spell == fin){
            setDegat();
            return true;
        }

        return false;
    }
}