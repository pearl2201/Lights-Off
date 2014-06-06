package com.pearl.main.screen.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pearl.main.Pearl;
import com.pearl.main.game.Assets;
import com.pearl.main.screen.PlayScreen;
import com.pearl.main.screen.stage.world.helper.SimpleAbstractActor;
import com.pearl.main.utils.Constants;

public class MenuStage extends Stage {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureRegion header;
	private TextureRegion footer;

	private Pearl game;

	public MenuStage(Pearl game) {
		// TODO Auto-generated constructor stub
		super(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
		camera.update();
		
		batch = new SpriteBatch();
				
		header = Assets.instance.header;
		footer = Assets.instance.footerL;
		addEventToButton();
		
	}

	

	private void addEventToButton() {
		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.medium, 0, 350);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					Assets.instance.buttonS.play();
					game.setScreen(new PlayScreen(game, 5));

					super.clicked(event, x, y);
				}
			});
			addActor(actor);
		}

		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.easy, 0, 530);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					Assets.instance.buttonS.play();
					game.setScreen(new PlayScreen(game, 4));

					super.clicked(event, x, y);
				}
			});
			addActor(actor);
		}

		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.hard, 0, 170);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					Assets.instance.buttonS.play();
					game.setScreen(new PlayScreen(game, 7));

					super.clicked(event, x, y);
				}
			});
			addActor(actor);
		}

		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.exitB, 240, 72);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					Assets.instance.buttonS.play();
					Gdx.app.exit();
				}
			});
			addActor(actor);
		}

		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.rate, 0, 72);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					if (game.isAndroidPlaying())
						Assets.instance.buttonS.play();
					game.getHandler().showRateApp();
				}
			});
			addActor(actor);
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(header, 0, 700);
		batch.draw(footer, 0, 0);

		batch.end();

		super.draw();
	}
}
