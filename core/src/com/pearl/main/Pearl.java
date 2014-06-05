package com.pearl.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pearl.main.game.Assets;
import com.pearl.main.game.IActivityRequestHandler;
import com.pearl.main.screen.LoadingScreen;
import com.pearl.main.screen.MenuScreen;

public class Pearl extends Game {
	private IActivityRequestHandler handler;
	private boolean androidPlaying;
	private boolean loadingAssetComplete;
	private boolean countDownAdTime;

	public Pearl() {
		androidPlaying = false;
		loadingAssetComplete = false;
	}

	public Pearl(IActivityRequestHandler handler) {
		this.handler = handler;
		androidPlaying = true;
		loadingAssetComplete = false;
		handler.showAds(true);
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

		setScreen(new LoadingScreen(this));

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		

	}

	public void setHandler(IActivityRequestHandler handler) {
		this.handler = handler;
	}

	public IActivityRequestHandler getHandler() {
		return handler;
	}

	public boolean isAndroidPlaying() {
		return androidPlaying;
	}

	public boolean isLoadingAssetComplete() {
		return loadingAssetComplete;
	}
	
	public void setLoadingAssetComplete(boolean loadingAssetComplete) {
		this.loadingAssetComplete = loadingAssetComplete;
	}
}
