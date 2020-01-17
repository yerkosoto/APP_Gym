package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class EliminarEvaluaciones extends AppCompatActivity {

    TextInputLayout tilFecha, tilPeso, tilAltura, tilIMC;
    Button btnBorrar, btnActualizar, btnVolver;
    String estaturaUsuario, idUsuario, nombreUsuario, apellidoUsuario;
    String fecha, peso, id;

    Double imc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_evaluaciones);

        //RESCATO DATOS
        estaturaUsuario = getIntent().getStringExtra("estaturaUsuario");
        idUsuario = getIntent().getStringExtra("idUsuario");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        apellidoUsuario = getIntent().getStringExtra("apellidoUsuario");

        // REFERENCIAS
        tilFecha = findViewById(R.id.tilFecha);
        tilPeso = findViewById(R.id.tilPeso);
        tilIMC = findViewById(R.id.tilIMC);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnActualizar = findViewById(R.id.btnActualizar);
        tilAltura = findViewById(R.id.tilAltura);
        btnVolver = findViewById(R.id.btnVolver);

        id = getIntent().getStringExtra("id");
        String datoFecha = getIntent().getStringExtra("fecha");
        String datoPeso = getIntent().getStringExtra("peso");


        // SETEO LOS BLOQUES
        fecha = datoFecha.split(" ")[1];
        tilFecha.getEditText().setText(fecha);

        peso = datoPeso.split(" ")[1];
        tilPeso.getEditText().setText(peso);


        tilAltura.getEditText().setText(estaturaUsuario);


        Consultas consultas = new Consultas();

        imc = consultas.calculaImc(peso,estaturaUsuario);

        tilIMC.getEditText().setText(imc.toString());


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



        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ConsultarEvaluaciones.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                borrarRegistro(id);
                Toast.makeText(EliminarEvaluaciones.this, "Registro borrado con éxito", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecha = tilFecha.getEditText().getText().toString();
                peso = tilPeso.getEditText().getText().toString();
                Intent intent = new Intent(v.getContext(),ConsultarEvaluaciones.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                Toast.makeText(EliminarEvaluaciones.this, "Registro actualizado correctamente", Toast.LENGTH_SHORT).show();
                actualizarRegistro(id, peso,fecha, imc);
                startActivity(intent);
            }
        });
    }


    public void borrarRegistro(String id) {
        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (db!= null){
            int exect = db.delete("tbl_evaluaciones","id ="+id,null );
            if (exect > 0){
                Intent intent = new Intent(this,ConsultarEvaluaciones.class);
                Toast.makeText(this, "Registro borrado exitosamente", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }
    }

    public void actualizarRegistro(String id, String peso, String fecha, Double imc) {
        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        if (db!= null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("fecha",fecha);
            contentValues.put("peso",peso);
            contentValues.put("imc",imc);
            db.update("tbl_evaluaciones",contentValues,"id ="+id, null);

        }
    }
}
