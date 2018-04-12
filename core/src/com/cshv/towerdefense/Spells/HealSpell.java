package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Units.Unit;

/**
 * Created by harri on 03/04/2018.
 */

public class HealSpell extends Spell {
    private static final float FRAME_DURATION = 0.2F;
    private Animation<TextureRegion> healSpell;
    private TextureRegion fin;
    private Unit _target;
    private Unit _caster;
    private float animationTimer = 0;


    public HealSpell (Array<TextureRegion> heal, Unit caster, Unit target){
        healSpell = new Animation<TextureRegion>(FRAME_DURATION,heal);
        healSpell.setPlayMode(Animation.PlayMode.NORMAL);
        fin = heal.get(heal.size-1);
        _target = target;
        _caster = caster;

    }

    private void setHeal(){
        _target.setHeal(_caster.getDegats());
    }


    @Override
    public void update(float delta) {
        animationTimer += delta;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion spell = healSpell.getKeyFrame(animationTimer);
        float x = _target.getX()-(spell.getRegionWidth()/4);
        float y = _target.getY()-(spell.getRegionHeight()/4);
        batch.draw(spell,x,y);

        if(spell == fin){
            setHeal();
            return true;
        }

        return false;
    }
}
