package com.example.mediaplayerusingapi;

import static android.widget.SeekBar.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button play, pause, stop;
    private MediaPlayer mediaPlayer;
    SeekBar seekBar;

    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setSubtitle("Developed by Mayank Virole");
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        mediaPlayer = MediaPlayer.create(this,R.raw.again);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setMax(mediaPlayer.getDuration());
        play.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"Playing the song",Toast.LENGTH_SHORT).show();
            mediaPlayer.start();
            handler.postDelayed(moveSeekBarThread, 0);
        });

        pause.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"Song paused!", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            handler.removeCallbacks(moveSeekBarThread);
        });

        stop.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this,"Media player stopped!!",Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b)mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private Runnable moveSeekBarThread = new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer.isPlaying()){
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                handler.postDelayed(this, 50); //Looping the thread after 0.1 second
                // seconds
            }
        }
    };
}