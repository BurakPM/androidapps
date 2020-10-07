package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SoundPool diceSound;
    private int diceSound_id;

    private ImageView imageViewDice;
    private Random rng = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imageViewDice = findViewById(R.id.image_view_dice);

        imageViewDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                diceSound.play(diceSound_id, 1, 1, 0, 0, 1);

                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        rollDice();
                    }
                }.start();

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes aa = new AudioAttributes.Builder().
                    setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).
                    setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).
                    build();

            diceSound = new SoundPool.Builder().
                    setAudioAttributes(aa).build();

        } else {
            diceSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        diceSound_id = diceSound.load(this, R.raw.rollingdicesound, 1);

    }


    private void rollDice() {
        int randomNum = rng.nextInt(6) + 1;

        switch (randomNum) {
            case 1:
                imageViewDice.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageViewDice.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageViewDice.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageViewDice.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageViewDice.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageViewDice.setImageResource(R.drawable.dice6);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diceSound.release();
        diceSound = null;
    }

}