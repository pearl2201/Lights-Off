package com.pearl.main;

import com.badlogic.gdx.Game;
import com.pearl.main.game.IActivityRequestHandler;
import com.pearl.main.screen.LoadingScreen;

public class Pearl extends Game {
	private IActivityRequestHandler handler;
	private boolean androidPlaying;
	private boolean loadingAssetComplete;

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
