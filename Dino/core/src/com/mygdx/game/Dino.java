package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Dino implements JuejoElementos, Dibujar {

    static final float impulse = -50;
    static final float gravity = 2;

    float y;
    float x;

    float vy;

    private int saltar = 2;
    private int cambiar = 0;

    DinoListener listener;

    Texture[] sprites;

    Circle collisionCircle;

    public Dino(DinoListener listener) {

        this.listener = listener;

        sprites = new Texture[4];
        sprites[0] = new Texture("frame-1.png");
        sprites[1] = new Texture("frame-2.png");
        sprites[2] = new Texture("frame-3.png");
        sprites[3] = new Texture("frame-4.png");

        collisionCircle = new Circle();
    }

    public void applyImpulse() {

        if (Gdx.input.justTouched() && saltar > 0) {
            vy = Dino.impulse;
            saltar--;
        } else {
            vy = vy + gravity;
        }
    }

    @Override
    public void start() {

        y = 0;
        x = Gdx.graphics.getWidth() / 2 - sprites[2].getWidth() / 2;

    }

    @Override
    public void draw(SpriteBatch batch) {

        if (cambiar < 3) {
            batch.draw(sprites[cambiar], x, y);
            cambiar++;
        } else if (cambiar == 3) {
            batch.draw(sprites[cambiar], x, y);
            cambiar = 0;
        }

    }


    @Override
    public void update() {

        vy = vy + gravity;
        y -= vy;

        if (y < 0) {
            y = 0;
            saltar = 2;
        }

        collisionCircle.set(x + sprites[0].getWidth() / 2, y + sprites[0].getHeight() / 2, sprites[0].getHeight() / 2);
    }

    public Circle getCollisionCircle() {

        return collisionCircle;
    }
}
