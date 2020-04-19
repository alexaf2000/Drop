package com.alexaf.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class Game extends com.badlogic.gdx.Game {
	SpriteBatch batch;
	Texture dropImage, bucketImage;
	Array<Sound> dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int ScreenWidth, ScreenHeight, puntuation;
	BitmapFont Marker;

	@Override
	public void create () {
		batch = new SpriteBatch();
		dropImage = new Texture("drop.png");
		bucketImage = new Texture("bucket.png");
		dropSound = new Array<Sound>();
		dropSound.add(Gdx.audio.newSound(Gdx.files.internal("drop_0.mp3")));
		dropSound.add(Gdx.audio.newSound(Gdx.files.internal("drop_1.mp3")));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		Marker = new BitmapFont();

		rainMusic.play();
		rainMusic.setLooping(true);
		// Let's create the camera
		camera = new OrthographicCamera();

		// Let's set a Screen size
		ScreenHeight = 480;
		ScreenWidth = 800;

		camera.setToOrtho(false, ScreenWidth, ScreenHeight);

		// Let's create a Rectangle that will represent the bucket
		bucket = new Rectangle();
		bucket.x = ScreenWidth/2 - 64 / 2; // Center the bucket horizonally
		bucket.y = 20; // vertically 20px of height
		bucket.width = 64;
		bucket.height = 64;

		// Let's create the rain drops inside an array
		raindrops = new Array<Rectangle>();
		spawnRaindrop();


	}

	private void spawnRaindrop(){
	Rectangle raindrop = new Rectangle();
	// Generates random position where will spawn the drop
	raindrop.x = MathUtils.random(0, ScreenWidth-64);
	// Set the heigh as the higher (top of the screen)
	raindrop.y = ScreenHeight;
	raindrop.width = 64;
	raindrop.height = 64;

	// Add the drop to the array
	raindrops.add(raindrop);
	lastDropTime = TimeUtils.nanoTime();
	}

	// While executing...
	@Override
	public void render () {
		// Set screen color to blue
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update the camera
		camera.update();

		// Lets load the spriteBatch with the camera
		batch.setProjectionMatrix(camera.combined);

		// Let's start the game
		batch.begin();

		// Draw the bucket in the correct position
		batch.draw(bucketImage, bucket.x, bucket.y);

		// While executing, if screen is touched...
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			// Will get the position
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touchPos);
			// And set it to the bucket
			bucket.x = touchPos.x - 64/2; // width divided by two (the middle of the bucket)
		}
		// In case that a Key left or right was pressed will move 200
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// Checks if is inside the screen
		if(bucket.x < 0)
			bucket.x = 0;
		if(bucket.x > ScreenWidth - 64)
			bucket.x = ScreenWidth - 64;


		// If after a longtime a drop is not spawned let's create some
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();

		// This will be the physics and will check if is inside the bucket
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -= MathUtils.random(200,400) * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0){
				iter.remove();
			}
			// If player well done
			if(raindrop.overlaps(bucket)){
				puntuation++;
				// Let's play a random drop sound...
				dropSound.random().play();
				iter.remove();
			}
		}

		// Foreach existent raindrop will assign its image, and position
		for (Rectangle raindrop : raindrops){
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}

		Marker.draw(batch,"Captured drops: "+puntuation,0, ScreenHeight);

		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
