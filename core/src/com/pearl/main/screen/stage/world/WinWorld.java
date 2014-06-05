package com.pearl.main.screen.stage.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pearl.main.game.Assets;
import com.pearl.main.screen.stage.PlayStage;
import com.pearl.main.screen.stage.world.helper.SimpleAbstractActor;

public class WinWorld extends Group {

	private PlayStage stage;
	private String text;

	public WinWorld(PlayStage stage) {
		this.stage = stage;
		init();
	}

	private void init() {
		text = "YOU HAVE USED " + stage.getStepCount() + " STEPS";
		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.next, 120, 320);
			addActor(actor);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					super.clicked(event, x, y);
					Assets.instance.buttonS.play();
					stage.nextLevel();

				}
			});
		}
		
		{
			SimpleAbstractActor actor = new SimpleAbstractActor(Assets.instance.exitL, 280, 320);
			addActor(actor);
			actor.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					Assets.instance.buttonS.play();
					Gdx.app.exit();

				}
			});
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		batch.draw(Assets.instance.win, 0, 0);

		Assets.instance.font.setScale(0.6f);
		Assets.instance.font.draw(batch, text, 240 - Assets.instance.font.getBounds(text).width / 2, 388 + Assets.instance.font.getLineHeight());
		Assets.instance.font.setScale(1f);
		super.draw(batch, parentAlpha);
	}

}
