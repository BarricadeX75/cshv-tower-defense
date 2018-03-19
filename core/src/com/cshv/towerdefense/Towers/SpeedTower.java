package com.cshv.towerdefense.Towers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harri on 16/03/2018.
 */

public class SpeedTower extends Tower {

    private Animation<TextureRegion> actTower;

    public SpeedTower(Array<TextureRegion> towerAtc){

    }


    @Override
    public int getTarget() {
        return 0;
    }
}
