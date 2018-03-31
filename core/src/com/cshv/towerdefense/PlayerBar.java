package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Barricade on 28/03/2018.
 */


public class PlayerBar extends Actor {

    private static final float UNIT_WIDTH = 9f;
    private static final float BAR_WIDTH = 158f;
    private static final float BAR_HEIGHT = 9f;

    private Player player;
    private TextureRegion barBackMid, barBackLeft, barBackRight;
    private TextureRegion barLifeMid, barLifeLeft, barLifeRight;
    private TextureRegion barManaMid, barManaLeft, barManaRight;
    private float leftEdge, rightEdge;

    private Label lifeLabel, manaLabel;


    public PlayerBar(Player player, float leftEdge, float rightEdge, float y, BitmapFont bitmapFont, float fontScale) {
        this.player = player;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        setBounds(leftEdge, y, rightEdge - leftEdge, BAR_HEIGHT * (1 + fontScale));

        barBackMid = new TextureRegion(new Texture(Gdx.files.internal("barBackMid.png")));
        barBackLeft = new TextureRegion(new Texture(Gdx.files.internal("barBackLeft.png")));
        barBackRight = new TextureRegion(new Texture(Gdx.files.internal("barBackRight.png")));
        
        barLifeMid = new TextureRegion(new Texture(Gdx.files.internal("barRedMid.png")));
        barLifeLeft = new TextureRegion(new Texture(Gdx.files.internal("barRedLeft.png")));
        barLifeRight = new TextureRegion(new Texture(Gdx.files.internal("barRedRight.png")));

        barManaMid = new TextureRegion(new Texture(Gdx.files.internal("barBlueMid.png")));
        barManaLeft = new TextureRegion(new Texture(Gdx.files.internal("barBlueLeft.png")));
        barManaRight = new TextureRegion(new Texture(Gdx.files.internal("barBlueRight.png")));

        Label.LabelStyle labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);

        lifeLabel = new Label("Vie", labelStyle);
        lifeLabel.setFontScale(fontScale);
        lifeLabel.setPosition(
                leftEdge,
                getY() + getHeight(),
                Align.left
        );

        manaLabel = new Label("Mana", labelStyle);
        manaLabel.setFontScale(fontScale);
        manaLabel.setPosition(
                rightEdge - (manaLabel.getWidth() * fontScale),
                getY() + getHeight(),
                Align.left
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        lifeLabel.draw(batch, parentAlpha);
        manaLabel.draw(batch, parentAlpha);

        batch.draw(
                barBackLeft,
                leftEdge,
                getY() - BAR_HEIGHT,
                UNIT_WIDTH,
                BAR_HEIGHT
        );
        if (player.getVieCombat() > 0)
            batch.draw(
                    barLifeLeft,
                    leftEdge,
                    getY() - BAR_HEIGHT,
                    UNIT_WIDTH,
                    BAR_HEIGHT
            );
        batch.draw(
                barBackMid,
                leftEdge + UNIT_WIDTH,
                getY() - BAR_HEIGHT,
                BAR_WIDTH - UNIT_WIDTH*2,
                BAR_HEIGHT
        );
        batch.draw(
                barLifeMid,
                leftEdge + UNIT_WIDTH,
                getY() - BAR_HEIGHT,
                (BAR_WIDTH - UNIT_WIDTH*2) * (player.getVieCombat() / player.getVie()),
                BAR_HEIGHT
        );
        batch.draw(
                barBackRight,
                leftEdge + BAR_WIDTH - UNIT_WIDTH,
                getY() - BAR_HEIGHT,
                UNIT_WIDTH,
                BAR_HEIGHT
        );
        if (player.getVieCombat() == player.getVie())
            batch.draw(
                    barLifeRight,
                    leftEdge + BAR_WIDTH - UNIT_WIDTH,
                    getY() - BAR_HEIGHT,
                    UNIT_WIDTH,
                    BAR_HEIGHT
            );

        batch.draw(
                barBackLeft,
                rightEdge - BAR_WIDTH,
                getY() - BAR_HEIGHT,
                UNIT_WIDTH,
                BAR_HEIGHT
        );
        if (player.getManaCombat() == player.getMana())
            batch.draw(
                    barManaLeft,
                    rightEdge - BAR_WIDTH,
                    getY() - BAR_HEIGHT,
                    UNIT_WIDTH,
                    BAR_HEIGHT
            );
        batch.draw(
                barBackMid,
                rightEdge - BAR_WIDTH + UNIT_WIDTH,
                getY() - BAR_HEIGHT,
                BAR_WIDTH - UNIT_WIDTH*2,
                BAR_HEIGHT
        );
        batch.draw(
                barManaMid,
                (rightEdge - UNIT_WIDTH - (BAR_WIDTH - UNIT_WIDTH*2) * (player.getManaCombat() / player.getMana())),
                getY() - BAR_HEIGHT,
                (BAR_WIDTH - UNIT_WIDTH*2) * (player.getManaCombat() / player.getMana()),
                BAR_HEIGHT
        );
        batch.draw(
                barBackRight,
                rightEdge - UNIT_WIDTH,
                getY() - BAR_HEIGHT,
                UNIT_WIDTH,
                BAR_HEIGHT
        );
        if (player.getManaCombat() > 0)
            batch.draw(
                    barManaRight,
                    rightEdge - UNIT_WIDTH,
                    getY() - BAR_HEIGHT,
                    UNIT_WIDTH,
                    BAR_HEIGHT
            );
    }
}
