package com.example.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Locale;

public class Crear_cuenta extends AppCompatActivity {
    private EditText nombre;
    private EditText apellido;
    private EditText cedula;
    private EditText telefono;
    private Spinner spnNacionalidad;
    private Spinner spnGenero;
    private RadioGroup radioGroup;
    private EditText editTextDate;
    private Button registrarButton;
    private Button cancelarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        Spinner nacionalidades = findViewById(R.id.spn_nacionalidad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.labels_nacionalidad,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nacionalidades.setAdapter(adapter);

        Spinner genero = findViewById(R.id.spn_genero);
        ArrayAdapter<CharSequence> adapter_genero = ArrayAdapter.createFromResource(this, R.array.labels_genero,
                android.R.layout.simple_spinner_item);
        adapter_genero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adapter_genero);

        ImageView button = findViewById(R.id.imageView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        nombre = findViewById(R.id.txt_nombre);
        apellido = findViewById(R.id.txt_apellido);
        cedula = findViewById(R.id.txt_cedula);
        telefono = findViewById(R.id.txt_telefono);
        spnNacionalidad = findViewById(R.id.spn_nacionalidad);
        spnGenero = findViewById(R.id.spn_genero);
        radioGroup = findViewById(R.id.radioGroup);
        editTextDate = findViewById(R.id.editTextDate);
        registrarButton = findViewById(R.id.btn_registrar);
        Button borrarButton = findViewById(R.id.btn_borrar);
        cancelarButton = findViewById(R.id.btn_cancelar);

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Crear_cuenta.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                guardarDatosRegistro(v);
            }
        });

        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setText("");
                apellido.setText("");
                cedula.setText("");
                telefono.setText("");
            }
        });
    }
    public void cancelar(View view) {
        finish();
    }
    public void showDatePicker(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year);

                EditText etFecha = findViewById(R.id.editTextDate);
                etFecha.setText(selectedDate);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    public void guardarDatosRegistro(View v) {

        int statusSD = verificarStatusSD();
        String info;

        if (statusSD == 0) {

            try {
                File f = new File(getExternalFilesDir(null), "datos3.txt");
                OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f, true));

                String nombreTexto = nombre.getText().toString();
                String apellidoTexto = apellido.getText().toString();
                String cedulaTexto = cedula.getText().toString();
                String telefonoTexto = telefono.getText().toString();
                String nacionalidadTexto = spnNacionalidad.getSelectedItem().toString();
                String generoTexto = spnGenero.getSelectedItem().toString();
                String estadoCivilTexto = obtenerEstadoCivilSeleccionado();
                String fechaNacimientoTexto = editTextDate.getText().toString();

                info = nombreTexto + "; " + apellidoTexto + "; " + cedulaTexto + "; " + telefonoTexto + "; "
                        + nacionalidadTexto + "; " + generoTexto + "; " + estadoCivilTexto + "; " + fechaNacimientoTexto + "\n";

                fout.write(info);
                fout.close();
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e("Ficheros", "Error");
            }
        } else {
            Toast.makeText(getApplicationContext(), "No guardado", Toast.LENGTH_SHORT).show();
        }
    }

    private int verificarStatusSD() {

        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "SD montada", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            Toast.makeText(getApplicationContext(), "Montado solo lectura", Toast.LENGTH_SHORT).show();
            return 1;
        } else {
            Toast.makeText(getApplicationContext(), "No esta montado", Toast.LENGTH_SHORT).show();
            return 2;
        }
    }
    private String obtenerEstadoCivilSeleccionado() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }
}