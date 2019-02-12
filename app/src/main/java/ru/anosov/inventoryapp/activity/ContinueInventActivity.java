package ru.anosov.inventoryapp.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.anosov.inventoryapp.R;

public class ContinueInventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_invent);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("ИНВЕНТАРИЗАЦИЯ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
