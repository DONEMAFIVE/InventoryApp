package ru.anosov.inventoryapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import ru.anosov.inventoryapp.R;

public class AuthActivity extends AppCompatActivity {
    private Button btSignIn;
    private MaterialEditText metLogin;
    private MaterialEditText metPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("АВТОРИЗАЦИЯ");

        btSignIn = findViewById(R.id.bt_sign_in);
        metLogin = findViewById(R.id.et_login);
        metPassword = findViewById(R.id.et_password);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(metLogin.getText());
                String password = String.valueOf(metPassword.getText());
                if (login.equals("Администратор") && password.equals("1")) {
                    goToMainActivity();
                } else {
                    Toast errLoPnA = Toast.makeText(getApplicationContext(), "Не правильный логин или пароль ", Toast.LENGTH_SHORT);
                    errLoPnA.show();
                }
            }
        });
    }
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
