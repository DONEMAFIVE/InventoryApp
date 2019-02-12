package ru.anosov.inventoryapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import ru.anosov.inventoryapp.Config;
import ru.anosov.inventoryapp.R;

public class SettingsActivity extends AppCompatActivity {
    private EditText ipAddress;
    public static Config cfg = new Config();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Настройки");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         ipAddress = findViewById(R.id.ipAddress);
         String fullPath = getFilesDir() + "/config.cfg";
         File file = new File(fullPath);

         if (file.exists()){
             read();
         }

         ipAddress.setText(cfg.getIP());
    }

    public void write(View v){
        String IP = ipAddress.getText().toString();
        String filename = "config.cfg";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(IP.getBytes());
            outputStream.close();

            Toast workComplete = Toast.makeText(getApplicationContext(), "Ip saved as: "+IP , Toast.LENGTH_SHORT);
            workComplete.show();
            Intent backOnMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backOnMainActivity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read(){
        String fullPath = getFilesDir() + "/config.cfg";
        String filename = "config.cfg";
        File file = new File(fullPath);

        Context context = getApplicationContext();
        if (file.exists()){
            try {
                FileInputStream fis = context.openFileInput(filename);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                cfg.setIP(String.valueOf(sb));
            }catch (Exception e){
             e.printStackTrace();
            }
        } else {
           Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }
  }

