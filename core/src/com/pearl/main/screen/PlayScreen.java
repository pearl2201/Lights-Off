package com.pearl.main.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pearl.main.Pearl;
import com.pearl.main.screen.stage.MenuStage;
import com.pearl.main.screen.stage.PlayStage;
import com.pearl.main.utils.Constants;

public class PlayScreen implements Screen {
	private Pearl game;
	private PlayStage stage;
	private int difficult;
	private boolean isPaused;
	private Texture texture;

	public PlayScreen(Pearl game, int n) {
		this.game = game;
		stage = new PlayStage(game, n);
		if (game.isAndroidPlaying()) {
			game.getHandler().showAds(true);
			System.out.println("show ad");
		} else {
			System.out.println("hide ad");
		}
		isPaused = false;
		texture = new Texture(Gdx.files.internal("image/opacity.png"));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		if (isPaused){
			stage.getSpriteBatch().begin();
			stage.getSpriteBatch().draw(texture, 0, 0, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
			stage.getSpriteBatch().end();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		stage.dispose();
		texture.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		isPaused = true;
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		isPaused = false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
