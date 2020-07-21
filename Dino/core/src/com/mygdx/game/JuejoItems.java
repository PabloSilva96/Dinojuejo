package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class JuejoItems implements JuejoElementos, Dibujar {

    static final float vx = 15;
    float distanceBetween = 800;
    static final int numberOfItems = 2;

    static int scoringTube = 0;

    Texture coin;
    Sprite coins;
    Texture bomb;
    Sprite bombs;


    float x;


    int index;

    ItemListener listener;

    Rectangle CoinCollisionRectangle;
    Rectangle BombCollisionRectangle;

    public JuejoItems(ItemListener listener, int index) {

        this.listener = listener;
        this.index = index;


        coin = new Texture("coin.png");
        coins = new Sprite(coin);
        bomb = new Texture("bomb.png");
        bombs = new Sprite(bomb);


        CoinCollisionRectangle = new Rectangle();
        BombCollisionRectangle = new Rectangle();

    }

    @Override
    public void start() {

        scoringTube = 0;
        setX(Gdx.graphics.getWidth() + index * distanceBetween);


    }

    public void recycle() {

        setX(x + numberOfItems * distanceBetween);

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(coin, coins.getX(), Gdx.graphics.getWidth() / 2);
        batch.draw(bomb, bombs.getX(), 0);
    }

    @Override
    public void update() {

        if (x + bomb.getWidth() / 2 < Gdx.graphics.getWidth() / 2 && scoringTube % numberOfItems == index) {
            listener.incScore();
            scoringTube += 1;
        }

        if (x + bomb.getWidth() < 0) {
            recycle();
        }

        setX(x - vx);

    }


    public void setX(float x) {
        this.x = x;
        coins.setX(x + distanceBetween);
        bombs.setX(x);
    }

    public Rectangle[] collisionRectangles() {

        Rectangle[] rectangles = new Rectangle[2];

        rectangles[0] = CoinCollisionRectangle.set(coins.getX(), Gdx.graphics.getWidth() / 2, coin.getWidth(), coin.getHeight());
        rectangles[1] = BombCollisionRectangle.set(bombs.getX(), 0, bomb.getWidth() / 4, bomb.getHeight() / 4);

        return rectangles;


    }


}
