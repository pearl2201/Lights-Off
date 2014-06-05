package com.pearl.main.screen.stage;

import com.badlogic.gdx.Game;
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
import com.pearl.main.utils.Constants;

public class MenuStage extends Stage {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureRegion hard;
	private TextureRegion medium;
	private TextureRegion easy;
	private TextureRegion header;
	private TextureRegion footer;
	private TextureRegion exit;
	private TextureRegion rate;

	private Pearl game;

	public MenuStage(Pearl game) {
		// TODO Auto-generated constructor stub
		super(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2, 0);
		camera.update();
		batch = new SpriteBatch();
		addButtonToStage();
		addEventToButton();
		Gdx.input.setInputProcessor(this);
		this.game = game;
	}

	private void addButtonToStage() {
		// TODO Auto-generated method stub
		header = Assets.instance.header;
		footer = Assets.instance.footerL;
		rate = Assets.instance.rate;
		exit = Assets.instance.exitB;
		easy = Assets.instance.easy;
		hard = Assets.instance.hard;
		medium = Assets.instance.medium;

	}

	private void addEventToButton() {
		{
			Actor actor = new Actor();
			actor.setBounds(0, 350, Assets.instance.medium.getRegionWidth(), Assets.instance.medium.getRegionHeight());
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
			Actor actor = new Actor();
			actor.setBounds(0, 530, Assets.instance.easy.getRegionWidth(), Assets.instance.easy.getRegionHeight());
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
			Actor actor = new Actor();
			actor.setBounds(0, 170, Assets.instance.hard.getRegionWidth(), Assets.instance.hard.getRegionHeight());
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
			Actor actor = new Actor();
			actor.setBounds(240, 72, Assets.instance.exitB.getRegionWidth(), Assets.instance.exitB.getRegionHeight());
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
			Actor actor = new Actor();
			actor.setBounds(0, 72, Assets.instance.exitB.getRegionWidth(), Assets.instance.exitB.getRegionHeight());
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
		super.draw();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(header, 0, 700);
		batch.draw(footer, 0, 0);
		batch.draw(rate, 0, 72);
		batch.draw(exit, 240, 72);
		batch.draw(hard, 0, 170);
		batch.draw(medium, 0, 350);
		batch.draw(easy, 0, 530);

		batch.end();
	}
}
