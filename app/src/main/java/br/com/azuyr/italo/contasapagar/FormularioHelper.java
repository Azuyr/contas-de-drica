package br.com.azuyr.italo.contasapagar;

import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import br.com.azuyr.italo.contasapagar.models.ContaVencimento;
import br.com.azuyr.italo.contasapagar.models.Identificacao;
import br.com.azuyr.italo.contasapagar.utils.Util;

public class FormularioHelper {
    private EditText campoTitulo;
    private EditText campoResumo;
    private EditText campoVencimento;
    private EditText campoValor;

    private EditText campoIdentificacaoNome;
    private EditText campoIdentificacaoEmail;
    private EditText campoIdentificacaoTelefone;

    private ContaVencimento contaVencimento;
    private Identificacao identificacao;

    public FormularioHelper(CategoriaActivity activity){
        campoTitulo = activity.findViewById(R.id.contas_vencimentos_titulo);
        campoResumo = activity.findViewById(R.id.contas_vencimentos_resumo);
        campoVencimento = activity.findViewById(R.id.contas_vencimentos_vencimento);
        campoValor = activity.findViewById(R.id.contas_vencimentos_valor);

        contaVencimento = new ContaVencimento();
    }

    public FormularioHelper(CategoriasListaActivity activity){
        campoIdentificacaoNome = activity.findViewById(R.id.contas_vencimentos_titulo);
        campoIdentificacaoEmail = activity.findViewById(R.id.contas_vencimentos_resumo);
        campoIdentificacaoTelefone = activity.findViewById(R.id.contas_vencimentos_vencimento);

        identificacao = new Identificacao();
    }

    public ContaVencimento getContaVencimento() {

        contaVencimento.setTitulo(campoTitulo.getText().toString());
        contaVencimento.setResumo(campoResumo.getText().toString());
        contaVencimento.setVencimento(new Date(campoVencimento.getText().toString()).getTime());
        contaVencimento.setValor(Double.parseDouble(campoValor.getText().toString()));

        return contaVencimento;
    }

    public Identificacao getIdentificacao() {

        identificacao.setNome(campoIdentificacaoNome.getText().toString());
        identificacao.setEmail(campoIdentificacaoEmail.getText().toString());
        identificacao.setTelefone(campoIdentificacaoTelefone.getText().toString());

        return identificacao;
    }

    public void preencheFormulario(ContaVencimento contaVencimento) {

        campoTitulo.setText(contaVencimento.getTitulo());
        campoResumo.setText(contaVencimento.getResumo());
        campoVencimento.setText(Util.setLongGetDate(contaVencimento.getVencimento()));
        campoValor.setText(contaVencimento.getValor().toString());

        this.contaVencimento = contaVencimento;

    }

    public void preencheFormulario(Identificacao identificacao) {

        campoIdentificacaoNome.setText(identificacao.getNome());
        campoIdentificacaoEmail.setText(identificacao.getEmail());
        campoIdentificacaoTelefone.setText(identificacao.getTelefone());

        this.identificacao = identificacao;

    }
}
