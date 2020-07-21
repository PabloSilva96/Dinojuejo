package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.Interpolation.circle;

public class Juejo extends ApplicationAdapter implements DinoListener, ItemListener {

    SpriteBatch batch;

    JuejoEstado juejoEstado;

    int score;

    Dino dino;


    Parallax[] backgrounds = new Parallax[4];
    JuejoItems[] items = new JuejoItems[JuejoItems.numberOfItems];

    Texto scoreText;
    Texto screenText;

    @Override
    public void create() {

        batch = new SpriteBatch();

        Sonido.loadSFX();

        dino = new Dino(this);


        for (int i = 0; i < Parallax.backgroundFiles.length; i++) {
            backgrounds[i] = new Parallax(Parallax.backgroundFiles[i], i * 1.1f + .25f);
        }

        for (int i = 0; i < JuejoItems.numberOfItems; i++) {
            items[i] = new JuejoItems(this, i);
        }

        scoreText = new Texto(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 100);
        screenText = new Texto(Gdx.graphics.getWidth() / 2, 300);

        start();

    }

    public void start() {

        juejoEstado = JuejoEstado.TAP_TO_PLAY;

        dino.start();
        for (int i = 0; i < JuejoItems.numberOfItems; i++) {
            items[i].start();
        }

        for (JuejoItems item : items) {
            item.start();
        }

        updateScore(0);
    }

    private void updateScore(int newScore) {

        score = newScore;
        scoreText.setText(Integer.toString(score));
    }

    private void update() {

        switch (juejoEstado) {

            case PLAY:

                updateGameElements();
                break;

            case TAP_TO_PLAY:
                Sonido.play(SFX.STOP);
                Sonido.play(SFX.SONG);

                if (Gdx.input.justTouched()) {

                    juejoEstado = JuejoEstado.PLAY;
                    Sonido.play(SFX.JUMP);
                }
                break;

            case GAME_OVER:

                if (Gdx.input.justTouched()) {
                    start();
                    juejoEstado = JuejoEstado.TAP_TO_PLAY;
                }
                break;

        }
    }

    private void updateGameElements() {

        if (Gdx.input.justTouched()) {
            dino.applyImpulse();
            Sonido.play(SFX.JUMP);
        }

        dino.update();

        for (JuejoItems item : items) {
            item.update();
        }

        for (Parallax background : backgrounds) {
            background.update();
        }
    }

    private void draw() {

        batch.begin();

        for (Parallax background : backgrounds) {
            background.draw(batch);
        }

        dino.draw(batch);

        for (JuejoItems item : items) {
            item.draw(batch);
        }

        drawScreenText();

        batch.end();

    }

    private void drawScreenText() {

        switch (juejoEstado) {

            case TAP_TO_PLAY:

                screenText.setText("TAP TO PLAY");
                screenText.draw(batch);

                break;

            case PLAY:

                scoreText.draw(batch);

                break;

            case GAME_OVER:

                screenText.setText("GAME OVER");
                screenText.draw(batch);

                scoreText.draw(batch);

                break;
        }
    }

    @Override
    public void render() {

        if (juejoEstado == JuejoEstado.PLAY) {
            checkCollisions();
        }

        update();
        draw();
    }

    private void checkCollisions() {
        Circle dinoCircle = dino.getCollisionCircle();
        for (JuejoItems item : items) {
            Rectangle[] itemRectangles = item.collisionRectangles();
            if (Intersector.overlaps(dinoCircle, itemRectangles[1])) {

                die();
                return;
            }

        }

    }


    @Override
    public void die() {

        juejoEstado = JuejoEstado.GAME_OVER;
        Sonido.play(SFX.HIT);
    }

    @Override
    public void incScore() {

        Sonido.play(SFX.SCORE);
        updateScore(score + 1);
    }

    @Override
    public void dispose() {

        batch.dispose();
    }
}
