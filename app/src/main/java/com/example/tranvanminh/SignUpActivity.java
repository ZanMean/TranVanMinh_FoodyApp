package com.example.tranvanminh;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class SignUpActivity extends AppCompatActivity {

    Button btn_login;
    TextView textView, tvDeSau;
    EditText login_email;

    AppBarConfiguration appBarConfiguration;
    NavController navController;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.nav_signin);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fullNameFragment, R.id.addressFragment, R.id.usernamePasswordFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController( toolbar,navController);
    }
}

