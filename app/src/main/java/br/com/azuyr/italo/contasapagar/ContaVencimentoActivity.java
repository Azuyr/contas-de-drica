package br.com.azuyr.italo.contasapagar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.azuyr.italo.contasapagar.models.ContaVencimento;
import br.com.azuyr.italo.contasapagar.utils.Util;

public class ContaVencimentoActivity  extends AppCompatActivity  {

    private ContaVencimentoFormularioHelper helper;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas_vencimentos);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        helper = new ContaVencimentoFormularioHelper(this);
        util = new Util();

        Intent intent = getIntent();
        ContaVencimento contaVencimento = (ContaVencimento) intent.getSerializableExtra("conta");

        if(contaVencimento != null){
            helper.preencheFormulario(contaVencimento);
        }


    }

}
