package com.example.assignmenteight;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private  static int playerHit;
    private  static int ballHit;
    private static int sizeHit;
    private static int ballthrow;
//Sound effects obtained from https://www.zapsplat.com

    public SoundPlayer(Context context){
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        playerHit = soundPool.load(context,R.raw.playerhit,1);
        ballHit = soundPool.load(context,R.raw.bombhit,1);
        sizeHit = soundPool.load(context,R.raw.powerup,1);
        ballthrow = soundPool.load(context,R.raw.throwball,1);
    }

    public void playPlayerHit(){
        soundPool.play(playerHit, 1.0f,1.0f,1,0,1.0f);
    }

    public void playBallHit(){
        soundPool.play(ballHit, 1.0f,1.0f,1,0,1.0f);
    }
    public void playSizeHit(){
        soundPool.play(sizeHit, 1.0f,1.0f,1,0,1.0f);
    }
    public void playBallThrow(){
        soundPool.play(ballthrow, 1.0f,1.0f,1,0,1.0f);
    }
}
