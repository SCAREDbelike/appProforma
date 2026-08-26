package com.jijijija.appproforma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Desactiva el modo oscuro para evitar cajas oscuras o texto ilegible
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        // Carga tu diseño de la proforma
        setContentView(R.layout.activity_main);
    }
}