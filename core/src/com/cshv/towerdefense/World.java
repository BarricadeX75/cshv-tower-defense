package com.cshv.towerdefense;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harri on 16/03/2018.
 */

public class World {

    public static final int NB_CASE_WIDTH = 10;
    public static final int NB_CASE_HEIGHT = 16;
    public static final int DEPART = 32;

    public Rectangle[] blocks;
    public boolean[] isSolid;
    private TextureRegion[] blockImg;
    //10*16
    final int arrayNum = 160;

    //Block Images
    private TextureRegion BLOCK_CHEMIN, BLOCK_SOL;
    private TextureRegion[] solMult = new TextureRegion[8];
    private TextureRegion[] cheminMult = new TextureRegion[8];
    private Array<Integer> _chemin;
    private int x, y;
    private int map;

    // Map navihation




    private void init() {




    }


    public World(int numMap,Array<Integer> chemin){
        _chemin = chemin;
        this.map = numMap;
        init();
        blocks = new Rectangle[arrayNum];
        blockImg = new TextureRegion[arrayNum];
        isSolid = new boolean[arrayNum];
        loadArrays();

    }

    public void loadArrays(){
        for (int i = 0; i < arrayNum; i++){
            if (x >= 320){
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
            batch.draw(blockImg[i],blocks[i].x, blocks[i].y,null);
        }

    }
}

