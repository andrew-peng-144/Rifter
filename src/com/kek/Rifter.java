package com.kek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Rifter extends Game {
	public SpriteBatch batch;

	public AssetManager assets;
	public void create () {
		batch = new SpriteBatch();
		assets = new AssetManager();
//		assets.load("badlogic.jpg",Texture.class);
//		assets.load("debug.jpg",Texture.class);
//		assets.load("Tone.png",Texture.class);
		assets.load("win.png", Texture.class);
		assets.load("rip.png", Texture.class);
		assets.load("ML CSP.mp3", Sound.class);

		assets.load("uiskin.atlas",TextureAtlas.class);

		assets.finishLoading();
	
		Preferences prefs = Gdx.app.getPreferences("My Preferences kek");
		prefs.putString("name", "Donald Duck");
		String name = prefs.getString("name", "No name stored");

		prefs.putBoolean("soundOn", true);
		prefs.putInteger("highscore", 10);
		prefs.flush();
		
		setScreen(new MenuScreen(this));
		//this.setScreen(new PlayScreen(this));
	}


	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}
