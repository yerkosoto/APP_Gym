package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class ConsultarEvaluaciones extends AppCompatActivity {

    Button btnVolver;
    ListView lvLista;
    String [] lista;
    String estaturaUsuario, idUsuario, nombreUsuario, apellidoUsuario;
    Button btnCalendario, btnCalendarioDos,btnBuscarFecha;
    TextInputLayout tilCalendario, tilCalendarioDos;
    String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_evaluaciones);

        //RESCATO DATOS
        estaturaUsuario = getIntent().getStringExtra("estaturaUsuario");
        idUsuario = getIntent().getStringExtra("idUsuario");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        apellidoUsuario = getIntent().getStringExtra("apellidoUsuario");

        // REFERENCIAS
        lvLista = findViewById(R.id.lvLista);
        btnVolver = findViewById(R.id.btnVolver);
        btnCalendario = findViewById(R.id.btnCalendario);
        btnCalendarioDos = findViewById(R.id.btnCalendarioDos);
        tilCalendario = findViewById(R.id.tilCalendario);
        tilCalendarioDos = findViewById(R.id.tilCalendarioDos);
        btnBuscarFecha = findViewById(R.id.btnBuscarFecha);


        // CARGO LISTA CON DATOS

        lista = consultarRegistros();
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista);
        lvLista.setAdapter(adapter);

        // BUSCAR POR FECHAS
        btnBuscarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha =tilCalendario.getEditText().getText().toString();
                String fechaFin =tilCalendarioDos.getEditText().getText().toString();
                datos = buscarEvaluacion(fecha,fechaFin, idUsuario);
                ArrayAdapter<String> adapter;
                adapter= new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,datos);
                lvLista.setAdapter(adapter);
            }
        });





        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EliminarEvaluaciones.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                intent.putExtra("id", lista[position].split(";")[0]);
                intent.putExtra("fecha", lista[position].split(";")[1]);
                intent.putExtra("peso", lista[position].split(";")[2]);
                startActivity(intent);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),IngresoOK.class);
                intent.putExtra("estaturaUsuario", estaturaUsuario);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("apellidoUsuario", apellidoUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
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
                        tilCalendario.getEditText().setText(fecha);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        Calendar calendarioDos = Calendar.getInstance();
        final int diaDos = calendarioDos.get(Calendar.DAY_OF_MONTH);
        final int mesDos = calendarioDos.get(Calendar.MONTH);
        final int anioDos = calendarioDos.get(Calendar.YEAR);

        btnCalendarioDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        @SuppressLint("DefaultLocale") String fechaDos = String.format("%02d-%02d-%d", dayOfMonth,(month +1), year);
                        tilCalendarioDos.getEditText().setText(fechaDos);
                    }
                }, anioDos, mesDos, diaDos);
                datePickerDialog.show();
            }
        });

    }


    public String[] consultarRegistros() {
        String[] lista = new String[0];
        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        if (db != null){
            Cursor cur = db.rawQuery("select * from tbl_evaluaciones where id_usuarios = "+idUsuario,null);

            int cantidad = cur.getCount();
            int i = 0;
            lista = new String[cantidad];
            if(cur.moveToFirst()){
                do{
                    String fila =  cur.getString(0) + ";Fecha: " + cur.getString(1) + ";Peso: " +cur.getString(2);
                    lista[i] = fila;
                    i++;
                }while (cur.moveToNext());
            }
        }return lista;
    }

    public String[] buscarEvaluacion(String fechIni,String fechFin, String id) {

        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        String[] datos = new String[0];
        if(db != null) {
            Cursor cur = db.rawQuery("SELECT * FROM  tbl_evaluaciones where id_usuarios = '" + id + "' AND fecha BETWEEN '"+fechIni+"' AND '"+fechFin+"'",null);
            int cantidad = cur.getCount();
            datos = new String[cantidad];
            int aux=0;
            if(cur.moveToFirst()){
                do {
                    datos[aux] = cur.getString(0) + ";Fecha: " + cur.getString(1) + ";Peso: " +cur.getString(2);
                    aux = aux + 1;
                }while (cur.moveToNext());
            }

        }
        return datos;
    }
}
