package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Units.Unit;

/**
 * Created by harri on 03/04/2018.
 */

public class SlashSpell extends Spell {
    private static final float FRAME_DURATION = 0.1F;
    private Animation<TextureRegion> atcAnim;
    private TextureRegion fin;
    private Unit _unit;
    private Mob _mob;
    private int _type;
    private int _direction;
    private float animationTimer = 0;

    public SlashSpell (Array<TextureRegion> atcLeft, Array<TextureRegion> atcRight, Unit unit, Mob mob, int direction, int type){
        if(direction == 2 || direction == 3) {
            atcAnim = new Animation<TextureRegion>(FRAME_DURATION, atcRight);
            atcAnim.setPlayMode(Animation.PlayMode.NORMAL);
            fin = atcRight.get(atcRight.size-1);
        }else {
            atcAnim = new Animation<TextureRegion>(FRAME_DURATION, atcLeft);
            atcAnim.setPlayMode(Animation.PlayMode.NORMAL);
            fin = atcLeft.get(atcLeft.size-1);
        }
        _type = type;
        _direction = direction;
        _unit = unit;
        _mob = mob;
    }

    @Override
    public void update(float delta) {
        animationTimer += delta;
    }

    public void setDegat() {
        if(_type == 1) {
            int dmg = _mob.getDegats();
            _unit.setDegats(dmg);
        }else{
            int dmg = _unit.getDegats();
            _mob.setDegats(dmg);
        }
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        TextureRegion spell = atcAnim.getKeyFrame(animationTimer);
        System.out.print("");
        float x,y;
        x = spell.getRegionWidth()/3;
        y = spell.getRegionHeight();
        if(_type == 1) {
            switch (_direction){
                case 1: batch.draw(spell,_unit.getX(),_unit.getY()-10);
                    break;
                case 2: batch.draw(spell, _mob.getX(), _mob.getY()-10);
                    break;
                case 3: batch.draw(spell, _unit.getX()-x, _unit.getY()+y+5,0, 0, spell.getRegionWidth(), spell.getRegionHeight(), 1f,1f,-90f);
                    break;
                case 4: batch.draw(spell, _mob.getX()-x, _mob.getY()+y,0, 0, spell.getRegionWidth(), spell.getRegionHeight(), 1f,1f,-90f);
                    break;
            }
        }else{
            switch (_direction){
                case 1: batch.draw(spell, _mob.getX(), _mob.getY()-10);
                    break;
                case 2: batch.draw(spell, _unit.getX(), _unit.getY()-10);
                    break;
                case 3: batch.draw(spell, _mob.getX()-x, _mob.getY()+y+5, 0, 0, spell.getRegionWidth(), spell.getRegionHeight(), 1f,1f,-90f);
                    break;
                case 4: batch.draw(spell, _unit.getX()-x, _unit.getY()+y, 0, 0, spell.getRegionWidth(), spell.getRegionHeight(), 1f,1f,-90f);
                    break;
            }
        }

        if(spell == fin){
            setDegat();
            return true;
        }

        return false;
    }
}
