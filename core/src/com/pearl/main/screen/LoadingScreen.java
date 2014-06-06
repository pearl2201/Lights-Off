package com.pearl.main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pearl.main.Pearl;
import com.pearl.main.game.Assets;
import com.pearl.main.screen.stage.world.helper.SimpleAbstractActor;
import com.pearl.main.utils.Constants;

public class LoadingScreen implements Screen {
	private Pearl game;
	private Stage stage;
	
	private Texture backgroundLoading;
	private Texture textLoading;
	
	private boolean displayText;
	private SimpleAbstractActor text ;
	
	private boolean loadingAssetComplete;
	
	public LoadingScreen(Pearl game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
		
		backgroundLoading = new Texture(Gdx.files.internal("image/background.png"));
		textLoading = new Texture(Gdx.files.internal("image/loadingText.png"));
		
		SimpleAbstractActor background = new SimpleAbstractActor(new TextureRegion(backgroundLoading), 0, 0);
		stage.addActor(background);
		text = new SimpleAbstractActor(new TextureRegion(textLoading), 240 - textLoading.getWidth()/2, 300);
		stage.addActor(text);
		displayText = true;
		
		Assets.instance.load(new AssetManager());
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.draw();
		displayText = displayText? false:true;
		text.setVisible(displayText);
		if (!loadingAssetComplete) {
			if (Assets.instance.isFinishLoading()) {

				Assets.instance.getData();
				loadingAssetComplete = true;
				if (game.isAndroidPlaying())
					game.getHandler().showAds(false);
				game.setLoadingAssetComplete(true);
				Assets.instance.bgMusicM.setLooping(true);
				Assets.instance.bgMusicM.play();
				game.setScreen(new MenuScreen(game));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		stage.dispose();
		backgroundLoading.dispose();
		textLoading.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
