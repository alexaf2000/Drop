package com.alexaf.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class MainMenu implements Screen {

    Game gameProcess; // The parent game
    OrthographicCamera camera;


    public MainMenu(Game gameProcess) {
        this.gameProcess = gameProcess;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        gameProcess.batch.setProjectionMatrix(camera.combined);

        gameProcess.batch.begin();
        gameProcess.font.draw(gameProcess.batch, "Welcome to Drop game!!! ", 220, 220);
        gameProcess.font.draw(gameProcess.batch, "Tap anywhere to begin!", 220, 180);
        gameProcess.batch.end();

        if (Gdx.input.isTouched()) {
            gameProcess.setScreen(new MainGame(this.gameProcess));
            dispose();
        }



    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
