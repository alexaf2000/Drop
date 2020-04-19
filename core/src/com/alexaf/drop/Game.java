package com.alexaf.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class Game extends com.badlogic.gdx.Game {
	public SpriteBatch batch;
	public BitmapFont font;



	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.


		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("BrnDmge.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 28;
		font = generator.generateFont(parameter);


		// Let's start the screen MainMenu
		this.setScreen(new MainMenu(this)); // Sending as a param the this Game object
	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		batch.dispose();
	}
}
