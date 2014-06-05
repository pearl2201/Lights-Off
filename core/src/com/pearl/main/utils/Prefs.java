package com.pearl.main.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
	public static Prefs instance = new Prefs();
	
	private static Preferences pref;
	public HashMap<Integer, Integer> levels;
	
	public Prefs()
	{
		levels = new HashMap<Integer, Integer>();
		pref = Gdx.app.getPreferences("pearlLightUp");
		
		if (!pref.contains("size4"))
		{
			pref.putInteger("size4", 1);
			pref.putInteger("size5", 1);
			pref.putInteger("size7", 1);
			pref.flush();
		}
		
		levels.put(4, pref.getInteger("size4", 1));
		levels.put(5, pref.getInteger("size5", 1));
		levels.put(7, pref.getInteger("size7", 1));
		
		
	}
	
	public void save(int size, int level)
	{
		levels.put(size, level);
		pref.putInteger("size" + size, level);
		pref.flush();
	}
}
