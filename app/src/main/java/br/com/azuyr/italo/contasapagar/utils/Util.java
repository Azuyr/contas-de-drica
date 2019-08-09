package br.com.azuyr.italo.contasapagar.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import br.com.azuyr.italo.contasapagar.CategoriaActivity;

public class Util {
    //atributo da classe.
    private AlertDialog alerta;

    public void msgBoxCustomer(Context context, String msg) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //define o titulo
        builder.setTitle("Titulo");
        //define a mensagem
        builder.setMessage(msg);
        //define um botão como positivo
        builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(CategoriaActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(CategoriaActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
