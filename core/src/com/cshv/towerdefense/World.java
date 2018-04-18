package com.cshv.towerdefense;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.cshv.towerdefense.Towers.Tower;

/**
 * Created by harri on 16/03/2018.
 */

public class World {

    public static final int NB_CASE_WIDTH = 11;
    public static final int NB_CASE_HEIGHT = 16;
    public static final int DEPART = 32;

    public Rectangle[] blocks;
    public boolean[] isSolid;
    private TextureRegion[] blockImg;
    private TextureRegion[] blockImgTower;
    //10*16
    private final int arrayNum = 176;

    //Block Images
    private TextureRegion BLOCK_CHEMIN, BLOCK_SOL, BLOCK_TOWER_ZONE, BLOCK_TOWER_FAST, BLOCK_TOWER_SLOW, BLOCK_TOWER_VISION;
    private TextureRegion[] solMult = new TextureRegion[8];
    private TextureRegion[] cheminMult = new TextureRegion[8];
    private Array<Integer> _chemin;
    private int x, y;


    // Map navigation




    private void initTabTower() {
        blockImgTower = new TextureRegion[176];
        for(int i=0 ; i<176 ; i++){
            blockImgTower[i] = null;
        }
    }


    public World( Array<TextureRegion> sols, Array<TextureRegion> chemins, Array<Integer> chemin){
        _chemin = chemin;
        y=88;
        //init();
        BLOCK_SOL = sols.get(0);
        BLOCK_CHEMIN = chemins.get(0);
        blocks = new Rectangle[arrayNum];
        blockImg = new TextureRegion[arrayNum];
        isSolid = new boolean[arrayNum];
        loadArrays();

    }

    public World( Array<TextureRegion> sols, Array<TextureRegion> chemins, Array<Integer> chemin, TextureRegion towerFast, TextureRegion towerSlow, TextureRegion towerZone, TextureRegion towerVision){
        _chemin = chemin;
        y=88;
        //init();
        BLOCK_SOL = sols.get(0);
        BLOCK_CHEMIN = chemins.get(0);
        blocks = new Rectangle[arrayNum];
        blockImg = new TextureRegion[arrayNum];
        isSolid = new boolean[arrayNum];
        BLOCK_TOWER_FAST = towerFast;
        BLOCK_TOWER_SLOW = towerSlow;
        BLOCK_TOWER_ZONE = towerZone;
        BLOCK_TOWER_VISION = towerVision;
        initTabTower();
        loadArrays();

    }

    public void loadArrays(){
        for (int i = 0; i < arrayNum; i++){
            if (x >= 352){
                x = 0;
                y += 32;
            }
                blockImg[i] = BLOCK_SOL;
                isSolid[i] = true;
                blocks[i] = new Rectangle(x, y, 32, 32);

            x += 32;
        }
        for(int i=0 ; i<_chemin.size ; i++){
            int numCase = _chemin.get(i);
            blockImg[numCase] = BLOCK_CHEMIN;
            isSolid[numCase] = false;
        }

    }

    public void cheminEditor(int cell){
        blockImg[cell] = BLOCK_CHEMIN;
    }

    public void setChemin(Array<Integer> chemin){
        _chemin = chemin;
    }

    public void suppCellCheminEditor(int cell){
        blockImg[cell] = BLOCK_SOL;
    }

    public void towerEditor(int cell, int type){
        switch (type){
            case Tower.FAST_TOWER:blockImgTower[cell] = BLOCK_TOWER_FAST;
                break;
            case Tower.SLOW_TOWER:blockImgTower[cell] = BLOCK_TOWER_SLOW;
                break;
            case Tower.ZONE_TOWER:blockImgTower[cell] = BLOCK_TOWER_ZONE;
                break;
            case Tower.VISION_TOWER:blockImgTower[cell] = BLOCK_TOWER_VISION;
                break;
        }
    }

    public void removeTower(int cell){

        blockImgTower[cell] = null;
    }

    public float getXcase(int numCase){
        return blocks[numCase].getX();
    }

    public float getYcase(int numCase){
        return blocks[numCase].getY();
    }

    public Rectangle[] getChemin(){
        Rectangle chemin[] = new  Rectangle[_chemin.size];
        for(int i=0 ; i< _chemin.size ; i++){
            chemin[i] = blocks[_chemin.get(i)];
        }
        return chemin;
    }

    public void draw(SpriteBatch batch){
        for (int i=0; i<arrayNum; i++){
            batch.draw(blockImg[i],blocks[i].x, blocks[i].y);
            /*if(blockImgChemin[i] != null){
                batch.draw(blockImgChemin[i],blocks[i].x, blocks[i].y);
            }*/
        }
    }

    public void drawEditor(SpriteBatch batch){
        for (int i=arrayNum-1 ; i>=0; i--){
            batch.draw(blockImg[i],blocks[i].x, blocks[i].y);
            if(blockImgTower[i] != null){
                batch.draw(blockImgTower[i],blocks[i].x+1, blocks[i].y);
            }
        }
    }
}

