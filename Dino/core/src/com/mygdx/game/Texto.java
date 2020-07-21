package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Texto implements Dibujar {

    BitmapFont font;
    GlyphLayout glyphLayout;
    String text;

    float x;
    float y;


    public Texto(float x, float y) {

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

        glyphLayout = new GlyphLayout();

        this.x = x;
        this.y = y;

        text = "";
    }

    public void setText(String text) {

        glyphLayout.setText(font, text);
        this.text = text;
    }

    @Override
    public void draw(SpriteBatch batch) {

        font.draw(batch, text, x - glyphLayout.width / 2, y - glyphLayout.height / 2);
    }
}