package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class RegistroUsuario extends AppCompatActivity {

    TextInputLayout tilNombreUsuario, tilNombre, tilApellido, tilFechaNacimiento, tilEstatura, tilClave;
    Button btnCalendario, btnRegistrarUsuario, btnVolver;
    String usuario, nombre, apellido, fechaNacimiento, estatura, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        // REFERENCIAS

        tilNombreUsuario = findViewById(R.id.tilNombreUsuario);
        tilNombre = findViewById(R.id.tilNombre);
        tilApellido = findViewById(R.id.tilApellido);
        tilFechaNacimiento = findViewById(R.id.tilFechaNacimiento);
        tilEstatura = findViewById(R.id.tilEstatura);

        tilClave = findViewById(R.id.tilClave);
        btnCalendario = findViewById(R.id.btnCalendario);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = tilNombreUsuario.getEditText().getText().toString();
                nombre = tilNombre.getEditText().getText().toString();
                apellido = tilApellido.getEditText().getText().toString();
                fechaNacimiento = tilFechaNacimiento.getEditText().getText().toString();
                estatura = tilEstatura.getEditText().getText().toString();
                clave = tilClave.getEditText().getText().toString();

                if (usuario.length() == 0) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa el nombre de usuario", Toast.LENGTH_SHORT).show();
                    tilNombreUsuario.setError("Campo requerido");
                } else if (nombre.length() == 0) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa el nombre", Toast.LENGTH_SHORT).show();
                    tilNombre.setError("Campo requerido");
                } else if (apellido.length() == 0) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa el apellido", Toast.LENGTH_SHORT).show();
                    tilApellido.setError("Campo requerido");
                } else if (fechaNacimiento.length() == 0) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa una fecha", Toast.LENGTH_SHORT).show();
                    tilFechaNacimiento.setError("Campo requerido");
                } else if (estatura.length() == 0) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa una estatura", Toast.LENGTH_SHORT).show();
                    tilEstatura.setError("Campo requerido");
                } else if (clave.length() == 0){
                    Toast.makeText(RegistroUsuario.this, "Ingresa una clave", Toast.LENGTH_SHORT).show();
                    tilClave.setError("Campo requerido");
                } else {
                    insertarUsuario(usuario, nombre, apellido, fechaNacimiento, estatura, clave);
                }
            }
        });


        Calendar calendario = Calendar.getInstance();
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
                        tilFechaNacimiento.getEditText().setText(fecha);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
         });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void insertarUsuario(String usuario, String nombre, String apellido, String fechaNacimiento, String estatura, String clave) {
        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (db != null){


            Cursor cur = db.rawQuery("select usuario from tbl_usuarios where usuario = '" + usuario +"'",null);

            if (cur.moveToFirst() == true){
                Toast.makeText(this, "Usuario duplicado", Toast.LENGTH_SHORT).show();
            }else{

                ContentValues contentValues = new ContentValues();
                contentValues.put("usuario", usuario);
                contentValues.put("nombre", nombre);
                contentValues.put("apellido", apellido);
                contentValues.put("fechaNacimiento", fechaNacimiento);
                contentValues.put("estatura", estatura);
                contentValues.put("clave", clave);
                long nFila = db.insert("tbl_usuarios", null, contentValues);

                if (nFila > 0) {
                    Toast.makeText(this, "Registro Ok", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Fallo al registrar", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}


