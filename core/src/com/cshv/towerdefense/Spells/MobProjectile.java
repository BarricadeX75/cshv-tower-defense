package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshv.towerdefense.Cell;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Towers.Tower;
import com.cshv.towerdefense.Units.Unit;

/**
 * Created by harri on 01/04/2018.
 */

public class MobProjectile extends Spell {

    private TextureRegion projectile;
    private Mob mob;
    private Unit unit;
    private float x;
    private float y;
    private float xTarget;
    private float yTarget;
    private float vitesseX;
    private float vitesseY;
    private int conter;

    public MobProjectile(TextureRegion projectile, Mob mob, Unit unit) {
        this.projectile = projectile;
        this.mob = mob;
        x = mob.getX();
        y = mob.getY();
        this.unit = unit;
        xTarget = unit.getX();
        yTarget = unit.getY();
        vitesseX = (x - xTarget) / 10;
        vitesseY = (y - yTarget) / 10;
        conter = 0;

    }

    public void setDegat() {
        int dmg = mob.getDegats();
        unit.setDegats(dmg);
    }

    @Override
    public void move() {
        x -= vitesseX;
        y -= vitesseY;
    }

    @Override
    public void update(float delta) {
        move();
        conter++;
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        batch.draw(projectile, x, y);

        if (conter == 10) {
            setDegat();
            return true;
        }

        return false;
    }
}
