package com.cshv.towerdefense.Spells;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cshv.towerdefense.Cell;
import com.cshv.towerdefense.Towers.Tower;

/**
 * Created by harri on 31/03/2018.
 */

public class ProjectileTower extends Spell{

    private TextureRegion projectile;
    private Tower tower;
    private Cell cell;
    private int typeTower;
    private float x;
    private float y;
    private float xTarget;
    private float yTarget;
    private float vitesseX;
    private float vitesseY;

    public ProjectileTower(TextureRegion projectile, Tower tower,Cell cell, int type){
        this.projectile = projectile;
        this.tower = tower;
        this.cell = cell;
        typeTower = type;
        x = tower.getX();
        y = tower.getY();
        xTarget = cell.getMob().getX();
        yTarget = cell.getMob().getY();
        vitesseX = (x-xTarget)/5;
        vitesseY = (y-yTarget)/5;

    }

    public void setDegat(){
        int dmg = tower.getAttaque();
        switch(typeTower){
            case 1: cell.getMob().setDegats(dmg);
                break;
            case 2: cell.getMob().setDegats(dmg);
                cell.getMob().addMalus(tower.getMalus(),4000);
                break;
            case 3: cell.degatZone(dmg);
                break;
        }
    }

    @Override
    public void move() {
        x+= vitesseX;
        y+= vitesseY;
    }

    @Override
    public void update(float delta) {
        move();
    }

    @Override
    public boolean draw(SpriteBatch batch) {
        batch.draw(projectile,x,y);
        if(x == xTarget && y == yTarget){
            return true;
        }
        return false;
    }
}
