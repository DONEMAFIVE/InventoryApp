package ru.anosov.inventoryapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import ru.anosov.inventoryapp.R;
import static ru.anosov.inventoryapp.activity.StartScanActivity.NAME;
import static ru.anosov.inventoryapp.activity.StartScanActivity.NUMBER;
import static ru.anosov.inventoryapp.activity.StartScanActivity.STATUS;

public class EditActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch aSwitch;
    private MaterialEditText met;
    private TextView numberObject;
    private TextView statusObject;
    private TextView nameObject;

    private String translate(String str){ //Вынести в отдельный класс
        switch (str){
            case "Moved":
                return "Перемещно";
            case "EmptyNumber":
                return "Номер пуст";
            case "EmptyStatus": //Поправить в на сервере
                return "Не найдено";
            case "None":
                return "Не найдено";
            case "Ok":
                return "Всё найдено";
        }
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("ИНВЕНТАРИЗАЦИЯ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aSwitch = findViewById(R.id.switcher);
        aSwitch.setOnCheckedChangeListener(this);
        aSwitch.setChecked(false);
        met = findViewById(R.id.met);
        met.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();

        String name = intent.getStringExtra(NAME);
        String number = intent.getStringExtra(NUMBER);
        String status = intent.getStringExtra(STATUS);

        nameObject = findViewById(R.id.name_object);
        numberObject = findViewById(R.id.number_object);
        statusObject = findViewById(R.id.status_object);

        nameObject.setText(name);
        numberObject.setText("Инвентарный номер: "+ translate(number));
        statusObject.setText("Статус: "+translate(status));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (aSwitch.isChecked()) {
            met.setVisibility(View.VISIBLE);
        }else {
            met.setVisibility(View.INVISIBLE);
        }
    }
}
