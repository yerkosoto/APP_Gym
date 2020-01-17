package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class RegistroEvaluaciones extends AppCompatActivity {

    TextInputLayout tilFecha, tilPeso;
    Button btnCalendario, btnVolver, btnRegistrarActividad;
    String estaturaUsuario, idUsuario, nombreUsuario, apellidoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_evaluaciones);

        //RESCATO DATOS
        estaturaUsuario = getIntent().getStringExtra("estaturaUsuario");
        idUsuario = getIntent().getStringExtra("idUsuario");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        apellidoUsuario = getIntent().getStringExtra("apellidoUsuario");

        // REFERENCIAS

        tilFecha = findViewById(R.id.tilFecha);
        tilPeso = findViewById(R.id.tilPeso);
        btnCalendario = findViewById(R.id.btnCalendario);
        btnVolver = findViewById(R.id.btnVolver);
        btnRegistrarActividad = findViewById(R.id.btnRegistrarActividad);




        btnRegistrarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = tilFecha.getEditText().getText().toString();
                String peso = tilPeso.getEditText().getText().toString();

                insertarRegistro(fecha, peso, idUsuario);
                Intent intent = new Intent(v.getContext(),IngresoOK.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IngresoOK.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
            }
        });

        final Calendar calendario = Calendar.getInstance();
        final int dia = calendario.get(Calendar.DAY_OF_MONTH);
        final int mes = calendario.get(Calendar.MONTH);
        final int anio = calendario.get(Calendar.YEAR);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        @SuppressLint("DefaultLocale") String fecha = String.format("%02d-%02d-%d", dayOfMonth,(month +1), year);
                        tilFecha.getEditText().setText(fecha);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    public void insertarRegistro(String fecha, String peso, String idUsuario) {
        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (db != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("fecha", fecha);
            contentValues.put("peso",peso);
            contentValues.put("id_usuarios",idUsuario);
            long nFila = db.insert("tbl_evaluaciones",null,contentValues);
            if (nFila > 0) {
                Toast.makeText(this, "Registro Ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Fallo al registrar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
