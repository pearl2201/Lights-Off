package com.pearl.main.screen.stage.world.helper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SimpleAbstractActor extends Actor{
	
	private TextureRegion texture;
	public SimpleAbstractActor(TextureRegion texture, int x, int y)
	{
		super();
		this.texture = texture;
		setBounds(x, y, texture.getRegionWidth(), texture.getRegionHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		batch.draw(texture, getX(), getY());
	}

}
