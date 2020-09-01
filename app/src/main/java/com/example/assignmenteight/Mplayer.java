package com.example.assignmenteight;

import android.content.Context;
import android.media.MediaPlayer;

public class Mplayer {

    public MediaPlayer player;
    boolean isPlaying;
    public Mplayer(Context context, int media) {


        player = MediaPlayer.create(context, media);
        player.setLooping(true);
        player.start();
    }


    public void stopMusic()
    {
        player.pause();
    }
    public void startMusic()
    {
        player.start();
    }
}