package com.cshv.towerdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;


public class TowerDefenseGame extends Game {

    public static final float WORLD_WIDTH = 1280;
    public static final float WORLD_HEIGHT = 800;

	private final AssetManager assetManager = new AssetManager();


	@Override
	public void create () {
		setScreen(new LoadingScreen(this));
	}

	public AssetManager getAssetManager() {
        return assetManager;
    }
}
