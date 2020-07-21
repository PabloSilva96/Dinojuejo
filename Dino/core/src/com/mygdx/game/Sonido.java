package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sonido {

    static Sound scoreSFX;
    static Sound jumpSFX;
    static Sound hitSFX;
    static Music songSFX;

    public static void loadSFX() {
        scoreSFX = Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));
        jumpSFX = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
        hitSFX = Gdx.audio.newSound(Gdx.files.internal("oof.mp3"));
        songSFX = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));
    }

    public static void play(SFX sfx) {
        switch (sfx) {
            case SCORE:
                scoreSFX.play();
                break;
            case HIT:
                hitSFX.play();
                break;
            case JUMP:
                jumpSFX.play();
                break;
            case SONG:
                songSFX.play();
                songSFX.setLooping(true);
                break;
            case STOP:
                songSFX.stop();
                break;
        }
    }
}