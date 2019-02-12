package ru.anosov.inventoryapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import ru.anosov.inventoryapp.Config;
import ru.anosov.inventoryapp.R;

public class MainActivity extends AppCompatActivity {
    public static Config cfg = new Config();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        goSetting();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("ИНВЕНТАРИЗАЦИЯ");

        Button btEditListDB = findViewById(R.id.btEditListDB);
        Button btContinueInvent = findViewById(R.id.btContinueInvent);
        Button btStartInvent = findViewById(R.id.btStartInvent);

        View.OnClickListener onClickListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditListDB();
            }
        };
        View.OnClickListener onClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goContinueInvent();
            }
        };
        View.OnClickListener onClickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStartInvent();
            }
        };

        btEditListDB.setOnClickListener(onClickListener1);
        btContinueInvent.setOnClickListener(onClickListener2);
        btStartInvent.setOnClickListener(onClickListener3);

        readConfig();
    }

    public void goEditListDB() {
        Intent intent = new Intent(this, EditListDbActivity.class);
        startActivity(intent);
    }

    public void goStartInvent() {
        Intent intent = new Intent(this, StartInventActivity.class);
        startActivity(intent);
    }

    public void goContinueInvent() {
        Intent intent = new Intent(this, ContinueInventActivity.class);
        startActivity(intent);
    }

    public void goSetting() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void readConfig() {
        String fullPath = getFilesDir() + "/config.cfg";
        String filename = "config.cfg";
        File file = new File(fullPath);

        Context context = getApplicationContext();
        if (file.exists()) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast workComplete = Toast.makeText(getApplicationContext(), "Установите IP-адрес ", Toast.LENGTH_SHORT);
            workComplete.show();
            goToSetting();
        }
    }

    public void goToSetting() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}