package com.alexaf.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dropImage, bucketImage;
	Sound dropSound;
	Music rainMusic;

	@Override
	public void create () {
		batch = new SpriteBatch();
		dropImage = new Texture("drop.png");
		bucketImage = new Texture("bucket.png");
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(dropImage, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		dropImage.dispose();
	}
}
