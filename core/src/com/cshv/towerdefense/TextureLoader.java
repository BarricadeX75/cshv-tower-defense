package com.cshv.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Barricade on 14/04/2018.
 */

public class TextureLoader {

    private TextureAtlas textureAtlas;
    private Array<TextureRegion> sol;
    private Array<TextureRegion> chemin;
    private Array<TextureRegion> towerSpeed;
    private Array<TextureRegion> mobSlimeUp[] = new Array[8];
    private Array<TextureRegion> mobSlimeLeft[] = new Array[8];
    private Array<TextureRegion> mobSlimeRight[] = new Array[8];
    private Array<TextureRegion> mobSlimeDown[] = new Array[8];
    private Array<TextureRegion> mobChienSqueletteUp;
    private Array<TextureRegion> mobChienSqueletteLeft;
    private Array<TextureRegion> mobChienSqueletteRight;
    private Array<TextureRegion> mobChienSqueletteDown;
    private Array<TextureRegion> mobGriffonUp;
    private Array<TextureRegion> mobGriffonLeft;
    private Array<TextureRegion> mobGriffonRight;
    private Array<TextureRegion> mobGriffonDown;
    private Array<TextureRegion> mobLamiaUp[] = new Array[8];
    private Array<TextureRegion> mobLamiaLeft[] = new Array[8];
    private Array<TextureRegion> mobLamiaRight[] = new Array[8];
    private Array<TextureRegion> mobLamiaDown[] = new Array[8];
    private Array<TextureRegion> mobLoupGarouUp[] = new Array[8];
    private Array<TextureRegion> mobLoupGarouLeft[] = new Array[8];
    private Array<TextureRegion> mobLoupGarouRight[] = new Array[8];
    private Array<TextureRegion> mobLoupGarouDown[] = new Array[8];
    private Array<TextureRegion> mobOrcUp[] = new Array[4];
    private Array<TextureRegion> mobOrcLeft[] = new Array[4];
    private Array<TextureRegion> mobOrcRight[] = new Array[4];
    private Array<TextureRegion> mobOrcDown[] = new Array[4];
    private Array<TextureRegion> mobCentaureUp[] = new Array[8];
    private Array<TextureRegion> mobCentaureLeft[] = new Array[8];
    private Array<TextureRegion> mobCentaureRight[] = new Array[8];
    private Array<TextureRegion> mobCentaureDown[] = new Array[8];
    private Array<TextureRegion> mobGolemUp[] = new Array[8];
    private Array<TextureRegion> mobGolemLeft[] = new Array[8];
    private Array<TextureRegion> mobGolemRight[] = new Array[8];
    private Array<TextureRegion> mobGolemDown[] = new Array[8];
    private Array<TextureRegion> mobMushroomUp[] = new Array[5];
    private Array<TextureRegion> mobMushroomLeft[] = new Array[5];
    private Array<TextureRegion> mobMushroomRight[] = new Array[5];
    private Array<TextureRegion> mobMushroomDown[] = new Array[5];
    private Array<TextureRegion> mobBatUp[] = new Array[8];
    private Array<TextureRegion> mobBatLeft[] = new Array[8];
    private Array<TextureRegion> mobBatRight[] = new Array[8];
    private Array<TextureRegion> mobBatDown[] = new Array[8];
    private Array<TextureRegion> mobDragonUp[] = new Array[2];
    private Array<TextureRegion> mobDragonLeft[] = new Array[2];
    private Array<TextureRegion> mobDragonRight[] = new Array[2];
    private Array<TextureRegion> mobDragonDown[] = new Array[2];
    private Array<TextureRegion> unitChevalierUp;
    private Array<TextureRegion> unitChevalierLeft;
    private Array<TextureRegion> unitChevalierRight;
    private Array<TextureRegion> unitChevalierDown;
    private Array<TextureRegion> unitChevalierAtkUp;
    private Array<TextureRegion> unitChevalierAtkLeft;
    private Array<TextureRegion> unitChevalierAtkRight;
    private Array<TextureRegion> unitChevalierAtkDown;
    private Array<TextureRegion> unitHealerUp;
    private Array<TextureRegion> unitHealerLeft;
    private Array<TextureRegion> unitHealerRight;
    private Array<TextureRegion> unitHealerDown;
    private Array<TextureRegion> unitHealerAtkUp;
    private Array<TextureRegion> unitHealerAtkLeft;
    private Array<TextureRegion> unitHealerAtkRight;
    private Array<TextureRegion> unitHealerAtkDown;
    private Array<TextureRegion> unitMoineUp;
    private Array<TextureRegion> unitMoineLeft;
    private Array<TextureRegion> unitMoineRight;
    private Array<TextureRegion> unitMoineDown;
    private Array<TextureRegion> unitMoineAtkUp;
    private Array<TextureRegion> unitMoineAtkLeft;
    private Array<TextureRegion> unitMoineAtkRight;
    private Array<TextureRegion> unitMoineAtkDown;
    private Array<TextureRegion> unitRogueUp;
    private Array<TextureRegion> unitRogueLeft;
    private Array<TextureRegion> unitRogueRight;
    private Array<TextureRegion> unitRogueDown;
    private Array<TextureRegion> unitRogueAtkUp;
    private Array<TextureRegion> unitRogueAtkLeft;
    private Array<TextureRegion> unitRogueAtkRight;
    private Array<TextureRegion> unitRogueAtkDown;
    private Array<TextureRegion> unitMageUp;
    private Array<TextureRegion> unitMageLeft;
    private Array<TextureRegion> unitMageRight;
    private Array<TextureRegion> unitMageDown;
    private Array<TextureRegion> unitMageAtkUp;
    private Array<TextureRegion> unitMageAtkLeft;
    private Array<TextureRegion> unitMageAtkRight;
    private Array<TextureRegion> unitMageAtkDown;
    private Array<TextureRegion> atkChevalierLeft;
    private Array<TextureRegion> atkChevalierRight;
    private Array<TextureRegion> atkMage;
    private Array<TextureRegion> atkMoineLeft;
    private Array<TextureRegion> atkMoineRight;
    private Array<TextureRegion> atkRogueLeft;
    private Array<TextureRegion> atkRogueRight;
    private Array<TextureRegion> spellHealLeft;
    private Array<TextureRegion> spellHealRight;
    private Array<TextureRegion> spellWater;
    private Array<TextureRegion> spellFire;
    private Array<TextureRegion> atkCacMobLeft;
    private Array<TextureRegion> atkCacMobRight;
    private Array<TextureRegion> projectile;
    private Array<TextureRegion> towerZoneAtkUp;
    private Array<TextureRegion> towerZoneAtkLeft;
    private Array<TextureRegion> towerZoneAtkRight;
    private Array<TextureRegion> towerZoneAtkDown;
    private Array<TextureRegion> spellSlowTower;
    private Array<TextureRegion> projectileTower[] = new Array[4];
    private Array<TextureRegion> projectileMob;
    private Array<TextureRegion> exploMushroom;
    private Array<TextureRegion> spriteTowerFast;
    private Array<TextureRegion> spriteTowerSlow;
    private Array<TextureRegion> spriteTowerVision;
    private Array<TextureRegion> spriteTowerZone;
    private Array<TextureRegion> spriteFontaine;
    private Array<TextureRegion> flecheTexture;
    private Array<TextureRegion> controleTexture;
    private Array<TextureRegion> bigControleTexture;
    private Array<TextureRegion> bagroundTexture;




    private TextureRegion caret, dialogBackground;
    private TextureRegion buttonUp, buttonDown;
    private TextureRegion barBack, barRed, barBlue;


    public TextureLoader(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;

        initTextures();
        initSpritesUnits();
        initSpritesMobs();
        initSpritesTowers();
        initSpritesSpells();
    }

    public Array<TextureRegion> getSol() {
        return sol;
    }

    public Array<TextureRegion> getChemin() {
        return chemin;
    }

    public Array<TextureRegion> getTowerSpeed() {
        return towerSpeed;
    }

    public Array<TextureRegion>[] getMobSlimeUp() {
        return mobSlimeUp;
    }

    public Array<TextureRegion>[] getMobSlimeLeft() {
        return mobSlimeLeft;
    }

    public Array<TextureRegion>[] getMobSlimeRight() {
        return mobSlimeRight;
    }

    public Array<TextureRegion>[] getMobSlimeDown() {
        return mobSlimeDown;
    }

    public Array<TextureRegion> getMobChienSqueletteUp() {
        return mobChienSqueletteUp;
    }

    public Array<TextureRegion> getMobChienSqueletteLeft() {
        return mobChienSqueletteLeft;
    }

    public Array<TextureRegion> getMobChienSqueletteRight() {
        return mobChienSqueletteRight;
    }

    public Array<TextureRegion> getMobChienSqueletteDown() {
        return mobChienSqueletteDown;
    }

    public Array<TextureRegion> getMobGriffonUp() {
        return mobGriffonUp;
    }

    public Array<TextureRegion> getMobGriffonLeft() {
        return mobGriffonLeft;
    }

    public Array<TextureRegion> getMobGriffonRight() {
        return mobGriffonRight;
    }

    public Array<TextureRegion> getMobGriffonDown() {
        return mobGriffonDown;
    }

    public Array<TextureRegion>[] getMobLamiaUp() {
        return mobLamiaUp;
    }

    public Array<TextureRegion>[] getMobLamiaLeft() {
        return mobLamiaLeft;
    }

    public Array<TextureRegion>[] getMobLamiaRight() {
        return mobLamiaRight;
    }

    public Array<TextureRegion>[] getMobLamiaDown() {
        return mobLamiaDown;
    }

    public Array<TextureRegion>[] getMobLoupGarouUp() {
        return mobLoupGarouUp;
    }

    public Array<TextureRegion>[] getMobLoupGarouLeft() {
        return mobLoupGarouLeft;
    }

    public Array<TextureRegion>[] getMobLoupGarouRight() {
        return mobLoupGarouRight;
    }

    public Array<TextureRegion>[] getMobLoupGarouDown() {
        return mobLoupGarouDown;
    }

    public Array<TextureRegion>[] getMobOrcUp() {
        return mobOrcUp;
    }

    public Array<TextureRegion>[] getMobOrcLeft() {
        return mobOrcLeft;
    }

    public Array<TextureRegion>[] getMobOrcRight() {
        return mobOrcRight;
    }

    public Array<TextureRegion>[] getMobOrcDown() {
        return mobOrcDown;
    }

    public Array<TextureRegion>[] getMobCentaureUp() {
        return mobCentaureUp;
    }

    public Array<TextureRegion>[] getMobCentaureLeft() {
        return mobCentaureLeft;
    }

    public Array<TextureRegion>[] getMobCentaureRight() {
        return mobCentaureRight;
    }

    public Array<TextureRegion>[] getMobCentaureDown() {
        return mobCentaureDown;
    }

    public Array<TextureRegion>[] getMobGolemUp() {
        return mobGolemUp;
    }

    public Array<TextureRegion>[] getMobGolemLeft() {
        return mobGolemLeft;
    }

    public Array<TextureRegion>[] getMobGolemRight() {
        return mobGolemRight;
    }

    public Array<TextureRegion>[] getMobGolemDown() {
        return mobGolemDown;
    }

    public Array<TextureRegion>[] getMobMushroomUp() {
        return mobMushroomUp;
    }

    public Array<TextureRegion>[] getMobMushroomLeft() {
        return mobMushroomLeft;
    }

    public Array<TextureRegion>[] getMobMushroomRight() {
        return mobMushroomRight;
    }

    public Array<TextureRegion>[] getMobMushroomDown() {
        return mobMushroomDown;
    }

    public Array<TextureRegion>[] getMobBatUp() {
        return mobBatUp;
    }

    public Array<TextureRegion>[] getMobBatLeft() {
        return mobBatLeft;
    }

    public Array<TextureRegion>[] getMobBatRight() {
        return mobBatRight;
    }

    public Array<TextureRegion>[] getMobBatDown() {
        return mobBatDown;
    }

    public Array<TextureRegion>[] getMobDragonUp() {
        return mobDragonUp;
    }

    public Array<TextureRegion>[] getMobDragonLeft() {
        return mobDragonLeft;
    }

    public Array<TextureRegion>[] getMobDragonRight() {
        return mobDragonRight;
    }

    public Array<TextureRegion>[] getMobDragonDown() {
        return mobDragonDown;
    }

    public Array<TextureRegion> getUnitChevalierUp() {
        return unitChevalierUp;
    }

    public Array<TextureRegion> getUnitChevalierLeft() {
        return unitChevalierLeft;
    }

    public Array<TextureRegion> getUnitChevalierRight() {
        return unitChevalierRight;
    }

    public Array<TextureRegion> getUnitChevalierDown() {
        return unitChevalierDown;
    }

    public Array<TextureRegion> getUnitChevalierAtkUp() {
        return unitChevalierAtkUp;
    }

    public Array<TextureRegion> getUnitChevalierAtkLeft() {
        return unitChevalierAtkLeft;
    }

    public Array<TextureRegion> getUnitChevalierAtkRight() {
        return unitChevalierAtkRight;
    }

    public Array<TextureRegion> getUnitChevalierAtkDown() {
        return unitChevalierAtkDown;
    }

    public Array<TextureRegion> getUnitHealerUp() {
        return unitHealerUp;
    }

    public Array<TextureRegion> getUnitHealerLeft() {
        return unitHealerLeft;
    }

    public Array<TextureRegion> getUnitHealerRight() {
        return unitHealerRight;
    }

    public Array<TextureRegion> getUnitHealerDown() {
        return unitHealerDown;
    }

    public Array<TextureRegion> getUnitHealerAtkUp() {
        return unitHealerAtkUp;
    }

    public Array<TextureRegion> getUnitHealerAtkLeft() {
        return unitHealerAtkLeft;
    }

    public Array<TextureRegion> getUnitHealerAtkRight() {
        return unitHealerAtkRight;
    }

    public Array<TextureRegion> getUnitHealerAtkDown() {
        return unitHealerAtkDown;
    }

    public Array<TextureRegion> getUnitMoineUp() {
        return unitMoineUp;
    }

    public Array<TextureRegion> getUnitMoineLeft() {
        return unitMoineLeft;
    }

    public Array<TextureRegion> getUnitMoineRight() {
        return unitMoineRight;
    }

    public Array<TextureRegion> getUnitMoineDown() {
        return unitMoineDown;
    }

    public Array<TextureRegion> getUnitMoineAtkUp() {
        return unitMoineAtkUp;
    }

    public Array<TextureRegion> getUnitMoineAtkLeft() {
        return unitMoineAtkLeft;
    }

    public Array<TextureRegion> getUnitMoineAtkRight() {
        return unitMoineAtkRight;
    }

    public Array<TextureRegion> getUnitMoineAtkDown() {
        return unitMoineAtkDown;
    }

    public Array<TextureRegion> getUnitRogueUp() {
        return unitRogueUp;
    }

    public Array<TextureRegion> getUnitRogueLeft() {
        return unitRogueLeft;
    }

    public Array<TextureRegion> getUnitRogueRight() {
        return unitRogueRight;
    }

    public Array<TextureRegion> getUnitRogueDown() {
        return unitRogueDown;
    }

    public Array<TextureRegion> getUnitRogueAtkUp() {
        return unitRogueAtkUp;
    }

    public Array<TextureRegion> getUnitRogueAtkLeft() {
        return unitRogueAtkLeft;
    }

    public Array<TextureRegion> getUnitRogueAtkRight() {
        return unitRogueAtkRight;
    }

    public Array<TextureRegion> getUnitRogueAtkDown() {
        return unitRogueAtkDown;
    }

    public Array<TextureRegion> getUnitMageUp() {
        return unitMageUp;
    }

    public Array<TextureRegion> getUnitMageLeft() {
        return unitMageLeft;
    }

    public Array<TextureRegion> getUnitMageRight() {
        return unitMageRight;
    }

    public Array<TextureRegion> getUnitMageDown() {
        return unitMageDown;
    }

    public Array<TextureRegion> getUnitMageAtkUp() {
        return unitMageAtkUp;
    }

    public Array<TextureRegion> getUnitMageAtkLeft() {
        return unitMageAtkLeft;
    }

    public Array<TextureRegion> getUnitMageAtkRight() {
        return unitMageAtkRight;
    }

    public Array<TextureRegion> getUnitMageAtkDown() {
        return unitMageAtkDown;
    }

    public Array<TextureRegion> getAtkChevalierLeft() {
        return atkChevalierLeft;
    }

    public Array<TextureRegion> getAtkChevalierRight() {
        return atkChevalierRight;
    }

    public Array<TextureRegion> getAtkMage() {
        return atkMage;
    }

    public Array<TextureRegion> getAtkMoineLeft() {
        return atkMoineLeft;
    }

    public Array<TextureRegion> getAtkMoineRight() {
        return atkMoineRight;
    }

    public Array<TextureRegion> getAtkRogueLeft() {
        return atkRogueLeft;
    }

    public Array<TextureRegion> getAtkRogueRight() {
        return atkRogueRight;
    }

    public Array<TextureRegion> getSpellHealLeft() {
        return spellHealLeft;
    }

    public Array<TextureRegion> getSpellHealRight() {
        return spellHealRight;
    }

    public Array<TextureRegion> getSpellWater() {
        return spellWater;
    }

    public Array<TextureRegion> getSpellFire() {
        return spellFire;
    }

    public Array<TextureRegion> getAtkCacMobLeft() {
        return atkCacMobLeft;
    }

    public Array<TextureRegion> getAtkCacMobRight() {
        return atkCacMobRight;
    }

    public Array<TextureRegion> getProjectile() {
        return projectile;
    }

    public Array<TextureRegion>[] getProjectileTower() {
        return projectileTower;
    }

    public Array<TextureRegion> getSpellZoneTowerUp() {
        return towerZoneAtkUp;
    }

    public Array<TextureRegion> getSpellZoneTowerLeft() {
        return towerZoneAtkLeft;
    }

    public Array<TextureRegion> getSpellZoneTowerRight() {
        return towerZoneAtkRight;
    }

    public Array<TextureRegion> getSpellZoneTowerDown() {
        return towerZoneAtkDown;
    }

    public Array<TextureRegion> getSpellSlowTower() {
        return spellSlowTower;
    }

    public Array<TextureRegion> getProjectileMob() {
        return projectileMob;
    }

    public Array<TextureRegion> getExploMushroom() {
        return exploMushroom;
    }

    public TextureRegion getButtonUp() {
        return buttonUp;
    }

    public TextureRegion getButtonDown() {
        return buttonDown;
    }

    public TextureRegion getBarBack() {
        return barBack;
    }

    public TextureRegion getBarRed() {
        return barRed;
    }

    public TextureRegion getBarBlue() {
        return barBlue;
    }

    public TextureRegion getCaret() {
        return caret;
    }

    public TextureRegion getDialogBackground() {
        return dialogBackground;
    }

    private void initTextures() {
        sol = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            sol.add(textureAtlas.findRegion("herbe"));
        }
        chemin = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            chemin.add(textureAtlas.findRegion("sol"));
        }
        towerSpeed = new Array<TextureRegion>();
        for(int i=0 ; i<1 ; i++){
            towerSpeed.add(textureAtlas.findRegion("tour"));
        }
        projectile = new Array<TextureRegion>();
        for(int i=1; i<2 ; i++){
            projectile.add(textureAtlas.findRegion("projectile"+i));
        }

        //caret = textureAtlas.findRegion("caret");
        //dialogBackground = textureAtlas.findRegion("dialogBackground");

        buttonUp = textureAtlas.findRegion("buttonUp");
        buttonDown = textureAtlas.findRegion("buttonDown");

        barBack = textureAtlas.findRegion("barBack");
        barRed = textureAtlas.findRegion("barRed");
        barBlue = textureAtlas.findRegion("barBlue");
    }

    private void initSpritesUnits() {
        unitChevalierDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitChevalierDown.add(textureAtlas.findRegion("chevalier_down ("+i+")"));
        }
        unitChevalierUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitChevalierUp.add(textureAtlas.findRegion("chevalier_up ("+i+")"));
        }
        unitChevalierLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitChevalierLeft.add(textureAtlas.findRegion("chevalier_left ("+i+")"));
        }
        unitChevalierRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitChevalierRight.add(textureAtlas.findRegion("chevalier_right ("+i+")"));
        }
        unitChevalierAtkDown = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            unitChevalierAtkDown.add(textureAtlas.findRegion("chevalier_atc_down ("+i+")"));
        }
        unitChevalierAtkUp = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            unitChevalierAtkUp.add(textureAtlas.findRegion("chevalier_atc_up ("+i+")"));
        }
        unitChevalierAtkLeft = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            unitChevalierAtkLeft.add(textureAtlas.findRegion("chevalier_atc_left ("+i+")"));
        }
        unitChevalierAtkRight = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            unitChevalierAtkRight.add(textureAtlas.findRegion("chevalier_atc_right ("+i+")"));
        }
        unitMageDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMageDown.add(textureAtlas.findRegion("mage_down ("+i+")"));
        }
        unitMageUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMageUp.add(textureAtlas.findRegion("mage_up ("+i+")"));
        }
        unitMageLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMageLeft.add(textureAtlas.findRegion("mage_left ("+i+")"));
        }
        unitMageRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMageRight.add(textureAtlas.findRegion("mage_right ("+i+")"));
        }
        unitMageAtkDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMageAtkDown.add(textureAtlas.findRegion("mage_atc_down ("+i+")"));
        }
        unitMageAtkUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMageAtkUp.add(textureAtlas.findRegion("mage_atc_up ("+i+")"));
        }
        unitMageAtkLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMageAtkLeft.add(textureAtlas.findRegion("mage_atc_left ("+i+")"));
        }
        unitMageAtkRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMageAtkRight.add(textureAtlas.findRegion("mage_atc_right ("+i+")"));
        }
        unitHealerDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitHealerDown.add(textureAtlas.findRegion("healer_down ("+i+")"));
        }
        unitHealerUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitHealerUp.add(textureAtlas.findRegion("healer_up ("+i+")"));
        }
        unitHealerLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitHealerLeft.add(textureAtlas.findRegion("healer_left ("+i+")"));
        }
        unitHealerRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitHealerRight.add(textureAtlas.findRegion("healer_right ("+i+")"));
        }
        unitHealerAtkDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitHealerAtkDown.add(textureAtlas.findRegion("healer_atc_down ("+i+")"));
        }
        unitHealerAtkUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitHealerAtkUp.add(textureAtlas.findRegion("healer_atc_up ("+i+")"));
        }
        unitHealerAtkLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitHealerAtkLeft.add(textureAtlas.findRegion("healer_atc_left ("+i+")"));
        }
        unitHealerAtkRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitHealerAtkRight.add(textureAtlas.findRegion("healer_atc_right ("+i+")"));
        }
        unitMoineDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMoineDown.add(textureAtlas.findRegion("moine_down"+i));
        }
        unitMoineUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMoineUp.add(textureAtlas.findRegion("moine_up"+i));
        }
        unitMoineLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMoineLeft.add(textureAtlas.findRegion("moine_left"+i));
        }
        unitMoineRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitMoineRight.add(textureAtlas.findRegion("moine_right"+i));
        }
        unitMoineAtkDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMoineAtkDown.add(textureAtlas.findRegion("moine_atc_down ("+i+")"));
        }
        unitMoineAtkUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMoineAtkUp.add(textureAtlas.findRegion("moine_atc_up ("+i+")"));
        }
        unitMoineAtkLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMoineAtkLeft.add(textureAtlas.findRegion("moine_atc_left ("+i+")"));
        }
        unitMoineAtkRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitMoineAtkRight.add(textureAtlas.findRegion("moine_atc_right ("+i+")"));
        }
        unitRogueDown = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitRogueDown.add(textureAtlas.findRegion("rogue_down ("+i+")"));
        }
        unitRogueUp = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitRogueUp.add(textureAtlas.findRegion("rogue_up ("+i+")"));
        }
        unitRogueLeft = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitRogueLeft.add(textureAtlas.findRegion("rogue_left ("+i+")"));
        }
        unitRogueRight = new Array<TextureRegion>();
        for(int i=1 ; i<7 ; i++){
            unitRogueRight.add(textureAtlas.findRegion("rogue_right ("+i+")"));
        }
        unitRogueAtkDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitRogueAtkDown.add(textureAtlas.findRegion("rogue_atc_down ("+i+")"));
        }
        unitRogueAtkUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitRogueAtkUp.add(textureAtlas.findRegion("rogue_atc_up ("+i+")"));
        }
        unitRogueAtkLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitRogueAtkLeft.add(textureAtlas.findRegion("rogue_atc_left ("+i+")"));
        }
        unitRogueAtkRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            unitRogueAtkRight.add(textureAtlas.findRegion("rogue_atc_right ("+i+")"));
        }
    }

    public Array<TextureRegion> getSpriteTowerFast() {
        return spriteTowerFast;
    }

    public Array<TextureRegion> getSpriteTowerSlow() {
        return spriteTowerSlow;
    }

    public Array<TextureRegion> getSpriteTowerVision() {
        return spriteTowerVision;
    }

    public Array<TextureRegion> getSpriteTowerZone() {
        return spriteTowerZone;
    }

    public Array<TextureRegion> getSpriteFontaine() {
        return spriteFontaine;
    }

    public Array<TextureRegion> getFlecheTexture() {
        return flecheTexture;
    }

    public Array<TextureRegion> getControleTexture() {
        return controleTexture;
    }

    public Array<TextureRegion> getBigControleTexture(){
        return  bigControleTexture;
    }

    public Array<TextureRegion> getBagroundTexture() {
        return bagroundTexture;
    }

    private void initSpritesMobs() {
        for(int j=1 ; j<9 ; j++){
            mobSlimeUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobSlimeUp[j-1].add(textureAtlas.findRegion("mobSlime"+j+"_vertical"+i));
            }
            mobSlimeLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobSlimeLeft[j-1].add(textureAtlas.findRegion("mobSlime"+j+"_left"+i));
            }
            mobSlimeRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobSlimeRight[j-1].add(textureAtlas.findRegion("mobSlime"+j+"_right"+i));
            }
            mobSlimeDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobSlimeDown[j-1].add(textureAtlas.findRegion("mobSlime"+j+"_vertical"+i));
            }
        }

        for(int j=1 ; j<5 ; j++){
            mobOrcUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobOrcUp[j-1].add(textureAtlas.findRegion("mobOrc"+j+"_up"+i));
            }
            mobOrcLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobOrcLeft[j-1].add(textureAtlas.findRegion("mobOrc"+j+"_left"+i));
            }
            mobOrcRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobOrcRight[j-1].add(textureAtlas.findRegion("mobOrc"+j+"_right"+i));
            }
            mobOrcDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobOrcDown[j-1].add(textureAtlas.findRegion("mobOrc"+j+"_down"+i));
            }
        }

        for(int j=1 ; j<9 ; j++){
            mobLoupGarouUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLoupGarouUp[j-1].add(textureAtlas.findRegion("mobWerewolf"+j+"_up"+i));
            }
            mobLoupGarouLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLoupGarouLeft[j-1].add(textureAtlas.findRegion("mobWerewolf"+j+"_left"+i));
            }
            mobLoupGarouRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLoupGarouRight[j-1].add(textureAtlas.findRegion("mobWerewolf"+j+"_right"+i));
            }
            mobLoupGarouDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLoupGarouDown[j-1].add(textureAtlas.findRegion("mobWerewolf"+j+"_down"+i));
            }
        }

        for(int j=1 ; j<9 ; j++){
            mobGolemUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobGolemUp[j-1].add(textureAtlas.findRegion("mobGolem"+j+"_up"+i));
            }
            mobGolemLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobGolemLeft[j-1].add(textureAtlas.findRegion("mobGolem"+j+"_left"+i));
            }
            mobGolemRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobGolemRight[j-1].add(textureAtlas.findRegion("mobGolem"+j+"_right"+i));
            }
            mobGolemDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobGolemDown[j-1].add(textureAtlas.findRegion("mobGolem"+j+"_down"+i));
            }
        }

        for(int j=1 ; j<9 ; j++){
            mobBatUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobBatUp[j-1].add(textureAtlas.findRegion("mobBat"+j+"_up"+i));
            }
            mobBatLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobBatLeft[j-1].add(textureAtlas.findRegion("mobBat"+j+"_left"+i));
            }
            mobBatRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobBatRight[j-1].add(textureAtlas.findRegion("mobBat"+j+"_right"+i));
            }
            mobBatDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobBatDown[j-1].add(textureAtlas.findRegion("mobBat"+j+"_down"+i));
            }
        }

        for(int j=1 ; j<9 ; j++){
            mobCentaureUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobCentaureUp[j-1].add(textureAtlas.findRegion("mobCentaure"+j+"_up"+i));
            }
            mobCentaureLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobCentaureLeft[j-1].add(textureAtlas.findRegion("mobCentaure"+j+"_left"+i));
            }
            mobCentaureRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobCentaureRight[j-1].add(textureAtlas.findRegion("mobCentaure"+j+"_right"+i));
            }
            mobCentaureDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobCentaureDown[j-1].add(textureAtlas.findRegion("mobCentaure"+j+"_down"+i));
            }
        }

        for(int j=1 ; j<9 ; j++){
            mobLamiaUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLamiaUp[j-1].add(textureAtlas.findRegion("mobLamia"+j+"_up"+i));
            }
            mobLamiaLeft[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLamiaLeft[j-1].add(textureAtlas.findRegion("mobLamia"+j+"_left"+i));
            }
            mobLamiaRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLamiaRight[j-1].add(textureAtlas.findRegion("mobLamia"+j+"_right"+i));
            }
            mobLamiaDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobLamiaDown[j-1].add(textureAtlas.findRegion("mobLamia"+j+"_down"+i));
            }
        }

        for(int j=4 ; j<9 ; j++){
            mobMushroomUp[j-4] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobMushroomUp[j-4].add(textureAtlas.findRegion("mobMushroom"+j+"_up"+i));
            }
            mobMushroomLeft[j-4] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobMushroomLeft[j-4].add(textureAtlas.findRegion("mobMushroom"+j+"_left"+i));
            }
            mobMushroomRight[j-4] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobMushroomRight[j-4].add(textureAtlas.findRegion("mobMushroom"+j+"_right"+i));
            }
            mobMushroomDown[j-4] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobMushroomDown[j-4].add(textureAtlas.findRegion("mobMushroom"+j+"_down"+i));
            }
        }

        for(int j=1 ; j<3 ; j++){
            mobDragonUp[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobDragonUp[j-1].add(textureAtlas.findRegion("mobDragon"+j+"_up"+i));
            }
            mobDragonLeft[j-1] = new Array<TextureRegion>();

            for(int i=1 ; i<4 ; i++){
                mobDragonLeft[j-1].add(textureAtlas.findRegion("mobDragon"+j+"_left"+i));
            }
            mobDragonRight[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobDragonRight[j-1].add(textureAtlas.findRegion("mobDragon"+j+"_right"+i));
            }
            mobDragonDown[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<4 ; i++){
                mobDragonDown[j-1].add(textureAtlas.findRegion("mobDragon"+j+"_down"+i));
            }
        }

        mobChienSqueletteUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobChienSqueletteUp.add(textureAtlas.findRegion("mobDevilHound_up"+i));
        }
        mobChienSqueletteLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobChienSqueletteLeft.add(textureAtlas.findRegion("mobDevilHound_left"+i));
        }
        mobChienSqueletteRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobChienSqueletteRight.add(textureAtlas.findRegion("mobDevilHound_right"+i));
        }
        mobChienSqueletteDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobChienSqueletteDown.add(textureAtlas.findRegion("mobDevilHound_down"+i));
        }

        mobGriffonUp = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobGriffonUp.add(textureAtlas.findRegion("mobGriffon_up"+i));
        }
        mobGriffonLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobGriffonLeft.add(textureAtlas.findRegion("mobGriffon_left"+i));
        }
        mobGriffonRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobGriffonRight.add(textureAtlas.findRegion("mobGriffon_right"+i));
        }
        mobGriffonDown = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            mobGriffonDown.add(textureAtlas.findRegion("mobGriffon_down"+i));
        }
    }

    private void initSpritesTowers() {
        towerZoneAtkDown = new Array<TextureRegion>();
        for(int i=0 ; i<23 ; i++){
            towerZoneAtkDown.add(textureAtlas.findRegion("sort_Tower1_down ("+i+")"));
        }
        towerZoneAtkUp = new Array<TextureRegion>();
        for(int i=0 ; i<23 ; i++){
            towerZoneAtkUp.add(textureAtlas.findRegion("sort_Tower1_up ("+i+")"));
        }
        towerZoneAtkLeft = new Array<TextureRegion>();
        for(int i=0 ; i<23 ; i++){
            towerZoneAtkLeft.add(textureAtlas.findRegion("sort_Tower1_left ("+i+")"));
        }
        towerZoneAtkRight = new Array<TextureRegion>();
        for(int i=0 ; i<23 ; i++){
            towerZoneAtkRight.add(textureAtlas.findRegion("sort_Tower1_right ("+i+")"));
        }

        for(int j=1 ; j<5 ; j++){
            projectileTower[j-1] = new Array<TextureRegion>();
            for(int i=1 ; i<11 ; i++){
                projectileTower[j-1].add(textureAtlas.findRegion("projectile_tower"+j+"_ ("+i+")"));
            }
        }

        spellSlowTower = new Array<TextureRegion>();
        for( int i=1 ; i<6 ; i++){
            spellSlowTower.add(textureAtlas.findRegion("sort_Tower2_ ("+i+")"));
        }

        spriteTowerFast = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spriteTowerFast.add(textureAtlas.findRegion("fastTower ("+i+")"));
        }

        spriteTowerSlow = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spriteTowerSlow.add(textureAtlas.findRegion("slowTower ("+i+")"));
        }

        spriteTowerZone = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spriteTowerZone.add(textureAtlas.findRegion("towerZone ("+i+")"));
        }

        spriteTowerVision = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spriteTowerVision.add(textureAtlas.findRegion("visionTower ("+i+")"));
        }

        spriteFontaine = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spriteFontaine.add(textureAtlas.findRegion("fontaine ("+i+")"));
        }
    }

    private void initSpritesSpells() {
        atkChevalierLeft = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atkChevalierLeft.add(textureAtlas.findRegion("slash_left ("+i+")"));
        }
        atkChevalierRight = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atkChevalierRight.add(textureAtlas.findRegion("slash_right ("+i+")"));
        }
        atkMage = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atkMage.add(textureAtlas.findRegion("atk_mage ("+i+")"));
        }
        atkMoineLeft = new Array<TextureRegion>();
        for(int i=1 ; i<10 ; i++){
            atkMoineLeft.add(textureAtlas.findRegion("atk_moine_left ("+i+")"));
        }
        atkMoineRight = new Array<TextureRegion>();
        for(int i=1 ; i<10 ; i++){
            atkMoineRight.add(textureAtlas.findRegion("atk_moine_right ("+i+")"));
        }
        atkRogueLeft = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            atkRogueLeft.add(textureAtlas.findRegion("atk_rogue_left ("+i+")"));
        }
        atkRogueRight = new Array<TextureRegion>();
        for(int i=1 ; i<4 ; i++){
            atkRogueRight.add(textureAtlas.findRegion("atk_rogue_right ("+i+")"));
        }
        atkCacMobLeft = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atkCacMobLeft.add(textureAtlas.findRegion("atk_cac_mob_left ("+i+")"));
        }
        atkCacMobRight = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            atkCacMobRight.add(textureAtlas.findRegion("atk_cac_mob_right ("+i+")"));
        }
        spellHealLeft = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spellHealLeft.add(textureAtlas.findRegion("heale_left ("+i+")"));
        }
        spellHealRight = new Array<TextureRegion>();
        for(int i=1 ; i<5 ; i++){
            spellHealRight.add(textureAtlas.findRegion("heale_right ("+i+")"));
        }
        spellWater = new Array<TextureRegion>();
        for(int i=1 ; i<9 ; i++){
            spellWater.add(textureAtlas.findRegion("sort_eau ("+i+")"));
        }
        spellFire = new Array<TextureRegion>();
        for(int i=1 ; i<8 ; i++){
            spellFire.add(textureAtlas.findRegion("sort_feu ("+i+")"));
        }
        projectileMob = new Array<TextureRegion>();
        for(int i=1; i<7 ; i++){
            projectileMob.add(textureAtlas.findRegion("projectile_mob"+i));
        }
        exploMushroom = new Array<TextureRegion>();
        for(int i=1; i<11 ; i++){
            exploMushroom.add(textureAtlas.findRegion("atc_Boom_Mushroom ("+i+")"));
        }
        flecheTexture = new Array<TextureRegion>();
        for(int i=1; i<3 ; i++){
            flecheTexture.add(textureAtlas.findRegion("fleche ("+i+")"));
        }
        controleTexture = new Array<TextureRegion>();
        for(int i=1; i<3 ; i++){
            controleTexture.add(textureAtlas.findRegion("valid ("+i+")"));
        }
        bigControleTexture = new Array<TextureRegion>();
        for(int i=1 ; i<3 ; i++){
            bigControleTexture.add(textureAtlas.findRegion("confirmation ("+i+")"));
        }
        bagroundTexture = new Array<TextureRegion>();
        bagroundTexture.add(textureAtlas.findRegion("Titre"));
        bagroundTexture.add(textureAtlas.findRegion("Tittre_fond"));
        //bigControleTexture.add(new TextureRegion(new Texture(Gdx.files.internal("confirmation (1).png"))));
        //bigControleTexture.add(new TextureRegion(new Texture(Gdx.files.internal("confirmation (2).png"))));

    }
}
