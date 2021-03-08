package com.example.baskenttahminoyunu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

   public void aktiviteGeçiş(View v) {
        int viewId = v.getId();
        if(viewId == R.id.button_normal_oyun) {
           Intent intent = new Intent(this, NormalOyunActivity.class);
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
