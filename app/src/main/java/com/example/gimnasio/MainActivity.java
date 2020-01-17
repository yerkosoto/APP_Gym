package com.example.gimnasio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout txtUsuario, txtPassword;
    Button btnIngreso;
    TextView txtRegistro;
    String estaturaUsuario, idUsuario, nombreUsuario, apellidoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // REFERENCIAS

        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnIngreso = findViewById(R.id.btnIngreso);
        txtRegistro = findViewById(R.id.txtRegistro);


        // BOTON DELOGUEO
        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = txtUsuario.getEditText().getText().toString();
                String password = txtPassword.getEditText().getText().toString();
                login(usuario,password);

            }
        });

        // BOTON REGISTRO
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegistroUsuario.class);
                startActivity(intent);
            }
        });
    }

    public void login(String user, String pass) {
        DBhelper dBhelper = new DBhelper(this,"gimnasio",null,1);
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        if(db != null) {
            Cursor cur = db.rawQuery("SELECT * FROM  tbl_usuarios where usuario = '"+user+"' and clave = '"+pass+"' ",null);
            int cantidad = cur.getCount();
            if(cantidad == 1){
                if(cur.moveToFirst()){
                    do {
                        idUsuario = cur.getString(0);
                        nombreUsuario = cur.getString(2);
                        apellidoUsuario = cur.getString(3);
                        estaturaUsuario = cur.getString(5);
                    }while (cur.moveToNext());
                }
                Intent intent =  new Intent(this,IngresoOK.class);
                intent.putExtra("estaturaUsuario",estaturaUsuario);
                intent.putExtra("idUsuario",idUsuario);
                intent.putExtra("nombreUsuario", nombreUsuario);
                intent.putExtra("apellidoUsuario",apellidoUsuario);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
