package com.pearl.main.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.pearl.main.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static Assets instance = new Assets();

	public TextureRegion againB;
	public TextureRegion arrowLeft;
	public TextureRegion arrowRight;
	public TextureRegion dark4;
	public TextureRegion dark5;
	public TextureRegion dark7;
	public TextureRegion light4;
	public TextureRegion light5;
	public TextureRegion light7;
	public TextureRegion solve4;
	public TextureRegion solve5;
	public TextureRegion solve7;
	public TextureRegion easy;
	public TextureRegion exitB;
	public TextureRegion exitL;
	public TextureRegion footerL;
	public TextureRegion hard;
	public TextureRegion header;
	public TextureRegion logo;
	public TextureRegion medium;
	public TextureRegion menuB;
	public TextureRegion playBg;
	public TextureRegion rate;
	public TextureRegion reset;
	public TextureRegion solve;
	public TextureRegion win;
	public TextureRegion next;
	public BitmapFont font;

	private AssetManager assetM;
	public Music bgMusicM;
	public Sound buttonS;
	public Sound cellS;
	public Sound winS;

	public void load(AssetManager assetM) {
		this.assetM = assetM;
		assetM.load(Constants.TEXTUREATLAS_FILE_PATH, TextureAtlas.class);
		assetM.load("image/font.fnt", BitmapFont.class);
		assetM.load("music/bgmusic.ogg", Music.class);
		assetM.load("sound/button.ogg", Sound.class);
		assetM.load("sound/win.ogg", Sound.class);
		assetM.load("sound/cell.ogg", Sound.class);

	}

	public void getData() {
		assetM.finishLoading();
		font = assetM.get("image/font.fnt");
		TextureAtlas atlas = assetM.get(Constants.TEXTUREATLAS_FILE_PATH);

		againB = atlas.findRegion("again");
		arrowLeft = atlas.findRegion("arrow left");
		arrowRight = atlas.findRegion("arrow right");
		dark4 = atlas.findRegion("dark4");
		dark5 = atlas.findRegion("dark5");
		dark7 = atlas.findRegion("dark7");
		light4 = atlas.findRegion("light4");
		light5 = atlas.findRegion("light5");
		light7 = atlas.findRegion("light7");
		solve4 = atlas.findRegion("solve4");
		solve5 = atlas.findRegion("solve5");
		solve7 = atlas.findRegion("solve7");
		easy = atlas.findRegion("easy");
		exitB = atlas.findRegion("exit");
		footerL = atlas.findRegion("footer");
		hard = atlas.findRegion("hard");
		header = atlas.findRegion("header");
		logo = atlas.findRegion("logo");
		medium = atlas.findRegion("medium");
		menuB = atlas.findRegion("menu");
		playBg = atlas.findRegion("play bg");
		rate = atlas.findRegion("rate");
		reset = atlas.findRegion("reset");
		solve = atlas.findRegion("solve");
		win = atlas.findRegion("win");
		exitL = atlas.findRegion("exitL");
		next = atlas.findRegion("next");

		cellS = assetM.get("sound/cell.ogg", Sound.class);
		winS = assetM.get("sound/win.ogg", Sound.class);
		buttonS = assetM.get("sound/button.ogg", Sound.class);
		bgMusicM = assetM.get("music/bgmusic.ogg", Music.class);

	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		Gdx.app.log("assets", asset.toString());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		cellS.dispose();
		winS.dispose();
		buttonS.dispose();
		bgMusicM.dispose();
		assetM.dispose();
		font.dispose();
	}

	public boolean isFinishLoading() {
		return assetM.update();
	}
}
