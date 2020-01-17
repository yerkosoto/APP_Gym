package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IngresoOK extends AppCompatActivity {

    TextView txtBienvenido;
    Button btnRegistroActividad, btnConsultar, btnVolver;
    String estaturaUsuario, idUsuario, nombreUsuario, apellidoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_ok);

        //RESCATO DATOS
        estaturaUsuario = getIntent().getStringExtra("estaturaUsuario");
        idUsuario = getIntent().getStringExtra("idUsuario");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        apellidoUsuario = getIntent().getStringExtra("apellidoUsuario");

        // REFERENCIAS
        txtBienvenido = findViewById(R.id.txtBienvenido);
        btnRegistroActividad = findViewById(R.id.btnRegistroActividad);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnVolver = findViewById(R.id.btnVolver);

        txtBienvenido.setText("Bienvenido " +nombreUsuario);



        btnRegistroActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),RegistroEvaluaciones.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConsultarEvaluaciones.class);
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
                Intent intent =  new Intent(v.getContext(),MainActivity.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
            }
        });
    }
}
