package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Barricade on 28/03/2018.
 */


public class PlayerBar extends Actor {

    private static final float UNIT_WIDTH = 9f;
    private static final float BARS_WIDTH = 158f;
    private static final float BARS_HEIGHT = 9f;

    private Player player;
    private TextureRegion barBackMid, barBackLeft, barBackRight;
    private TextureRegion barLifeMid, barLifeLeft, barLifeRight;
    private TextureRegion barManaMid, barManaLeft, barManaRight;
    private float leftEdge, rightEdge;


    public PlayerBar(Player player, float leftEdge, float rightEdge, float y) {
        this.player = player;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        setPosition(leftEdge, y);

        barBackMid = new TextureRegion(new Texture(Gdx.files.internal("barBackMid.png")));
        barBackLeft = new TextureRegion(new Texture(Gdx.files.internal("barBackLeft.png")));
        barBackRight = new TextureRegion(new Texture(Gdx.files.internal("barBackRight.png")));
        
        barLifeMid = new TextureRegion(new Texture(Gdx.files.internal("barRedMid.png")));
        barLifeLeft = new TextureRegion(new Texture(Gdx.files.internal("barRedLeft.png")));
        barLifeRight = new TextureRegion(new Texture(Gdx.files.internal("barRedRight.png")));

        barManaMid = new TextureRegion(new Texture(Gdx.files.internal("barBlueMid.png")));
        barManaLeft = new TextureRegion(new Texture(Gdx.files.internal("barBlueLeft.png")));
        barManaRight = new TextureRegion(new Texture(Gdx.files.internal("barBlueRight.png")));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
        TextureRegion lifeLeftTexture, lifeRightTexture;
        TextureRegion manaLeftTexture, manaRightTexture;
        
        if (player.getVieCombat() > 0)
            lifeLeftTexture = barLifeLeft;
        else
            lifeLeftTexture = barBackLeft;
        
        if (player.getVieCombat() == player.getVie())
            lifeRightTexture = barLifeRight;
        else
            lifeRightTexture = barBackRight;
        
        if (player.getManaCombat() > 0)
            manaRightTexture = barManaRight;
        else
            manaRightTexture = barBackRight;
        
        if (player.getManaCombat() == player.getMana())
            manaLeftTexture = barManaLeft;
        else
            manaLeftTexture = barBackLeft;

        batch.draw(
                lifeLeftTexture,
                leftEdge,
                getY() - BARS_HEIGHT,
                UNIT_WIDTH,
                BARS_HEIGHT
        );
        batch.draw(
                barBackMid,
                leftEdge + UNIT_WIDTH,
                getY() - BARS_HEIGHT,
                BARS_WIDTH - UNIT_WIDTH*2,
                BARS_HEIGHT
        );
        batch.draw(
                barLifeMid,
                leftEdge + UNIT_WIDTH,
                getY() - BARS_HEIGHT,
                (BARS_WIDTH - UNIT_WIDTH*2) * (player.getVieCombat() / player.getVie()),
                BARS_HEIGHT
        );
        batch.draw(
                lifeRightTexture,
                leftEdge + BARS_WIDTH - UNIT_WIDTH,
                getY() - BARS_HEIGHT,
                UNIT_WIDTH,
                BARS_HEIGHT
        );

        batch.draw(
                manaLeftTexture,
                rightEdge - BARS_WIDTH,
                getY() - BARS_HEIGHT,
                UNIT_WIDTH,
                BARS_HEIGHT
        );
        batch.draw(
                barBackMid,
                rightEdge - BARS_WIDTH + UNIT_WIDTH,
                getY() - BARS_HEIGHT,
                BARS_WIDTH - UNIT_WIDTH*2,
                BARS_HEIGHT
        );
        batch.draw(
                barManaMid,
                (rightEdge - UNIT_WIDTH - (BARS_WIDTH - UNIT_WIDTH*2) * (player.getManaCombat() / player.getMana())),
                getY() - BARS_HEIGHT,
                (BARS_WIDTH - UNIT_WIDTH*2) * (player.getManaCombat() / player.getMana()),
                BARS_HEIGHT
        );
        batch.draw(
                manaRightTexture,
                rightEdge - UNIT_WIDTH,
                getY() - BARS_HEIGHT,
                UNIT_WIDTH,
                BARS_HEIGHT
        );
    }
}
