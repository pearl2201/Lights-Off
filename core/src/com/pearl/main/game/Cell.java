package com.pearl.main.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pearl.main.utils.ScreenBoardCoorConvert;

public class Cell extends Actor {

	private CellType type;
	private boolean isToggle;
	private boolean light;
	private boolean solve;

	public Cell(int x, int y, boolean light, CellType type) {
		super();
		Vector2 pos = ScreenBoardCoorConvert.convertBoardToScreen(x, y, type);
		setBounds(pos.x, pos.y, type.getSize(), type.getSize());
		isToggle = false;
		light = light;
		this.type = type;
	}

	public void isToggle() {
		light = light ? false : true;
		if (solve)
			solve = false;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!solve) {
			if (light)
				batch.draw(type.getLightTextureRegion(), getX(), getY());
			else if (!light)
				batch.draw(type.getDarkTextureRegion(), getX(), getY());
		} else {
			batch.draw(type.getSolve(), getX(), getY());
		}
	}

	public void setLight(boolean light) {
		this.light = light;
	}
	
	public void setSolve(boolean solve)
	{
		this.solve = solve;
	}
}
