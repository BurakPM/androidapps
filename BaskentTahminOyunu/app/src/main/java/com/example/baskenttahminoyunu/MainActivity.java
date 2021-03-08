package com.example.baskenttahminoyunu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

   public void aktiviteGeçiş(View v) {
        //switch is broken here (Android resource IDs suddenly not final)

        int viewId = v.getId();
        if(viewId == R.id.button_normal_oyun) {
            intent = new Intent(this, NormalOyunActivity.class);
            startActivity(intent);
        }else if(viewId == R.id.button_açıklama) {
            showInfoDialog();

        }

    }


    private void showInfoDialog() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(getSupportFragmentManager(), "custom dialog");
    }
}