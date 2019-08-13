package br.com.azuyr.italo.contasapagar;

import android.widget.EditText;

import java.sql.Date;

import br.com.azuyr.italo.contasapagar.models.ContaVencimento;

public class ContaVencimentoFormularioHelper {
    private ContaVencimento contaVencimento;

    private final EditText campoTitulo;
    private final EditText campoResumo;
    private final EditText campoValor;
    private final EditText campoVencimento;


    public ContaVencimentoFormularioHelper(ContaVencimentoActivity activity){
        campoTitulo = activity.findViewById(R.id.contas_vencimentos_titulo);
        campoResumo = activity.findViewById(R.id.categoria_observacao);
        campoValor = activity.findViewById(R.id.categoria_observacao);
        campoVencimento = activity.findViewById(R.id.categoria_observacao);
        contaVencimento = new ContaVencimento();
    }

    public ContaVencimento getContasVencimentos() {

        contaVencimento.setTitulo(campoTitulo.getText().toString());
        contaVencimento.setResumo(campoTitulo.getText().toString());
        contaVencimento.setValor((Double.parseDouble(campoValor.getText().toString())));
        contaVencimento.setVencimento(Long.parseLong(campoVencimento.getText().toString()));

        return contaVencimento;
    }

    public void preencheFormulario(ContaVencimento contaVencimento) {
        campoTitulo.setText(contaVencimento.getTitulo());
        campoResumo.setText(contaVencimento.getResumo());
        campoValor.setText(contaVencimento.getValor().toString());
        campoTitulo.setText(Date.valueOf(contaVencimento.getVencimento().toString()).toString());


        this.contaVencimento = contaVencimento;
    }
}
