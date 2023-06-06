package com.example.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {
    private Button btnBorrarDatos;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.reunion2);
        btnBorrarDatos = findViewById(R.id.btn_borrar_datos);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Button btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnBorrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borrar la información de SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("usuario");
                editor.remove("password");
                editor.apply();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnMostrarDatos = findViewById(R.id.btn_mostrar_datos);
        btnMostrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatosRegistrados();
            }
        });
    }
    private void mostrarDatosRegistrados() {
        try {
            File f = new File(getExternalFilesDir(null), "datos3.txt");
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }

            br.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Datos Registrados")
                    .setMessage(sb.toString())
                    .setPositiveButton("Cerrar", null)
                    .show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}