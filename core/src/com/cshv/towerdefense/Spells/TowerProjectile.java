package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshv.towerdefense.Cell;
import com.cshv.towerdefense.Mobs.Mob;
import com.cshv.towerdefense.Towers.Tower;

/**
 * Created by harri on 31/03/2018.
 */

public class TowerProjectile extends Spell{

    private TextureRegion projectile;
    private Tower tower;
    private Cell cell[];
    private Mob mob;
    private int typeTower;
    private float x;
    private float y;
    private float xTarget;
    private float yTarget;
    private float vitesseX;
    private float vitesseY;
    private int conter;

    public TowerProjectile(TextureRegion projectile, Tower tower, Cell[] cell, Mob mob, int type){
        this.projectile = projectile;
        this.tower = tower;
        this.cell = cell;
        typeTower = type;
        x = tower.getX();
        y = tower.getY();
        this.mob = mob;
        xTarget = mob.getX();
        yTarget = mob.getY();
        vitesseX = (x-xTarget)/10;
        vitesseY = (y-yTarget)/10;
        conter = 0;

    }

    public void setDegat(){
        int dmg = tower.getAttaque();
        switch(typeTower){
            case 1: mob.setDegats(dmg);
                Gdx.app.log("degat", "degat");
                break;
            case 2: mob.setDegats(dmg);
                mob.addMalus(tower.getMalus(),4000);
                break;
            case 3: cell[mob.getCurrentCase()].degatZone(dmg);
                break;
        }
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
        batch.draw(projectile,x,y);

        if(conter ==10){
            setDegat();
            return true;
        }

        return  false;
    }
}
