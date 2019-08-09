package br.com.azuyr.italo.contasapagar;

import android.widget.EditText;

import br.com.azuyr.italo.contasapagar.models.Categoria;
import br.com.azuyr.italo.contasapagar.models.CategoriaVencimento;

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoObservacao;

    private Categoria categoria;

    public FormularioHelper(CategoriaActivity activity){
        campoNome = activity.findViewById(R.id.categoria_nome);
        campoObservacao = activity.findViewById(R.id.categoria_observacao);
        categoria = new Categoria();
    }

    public Categoria getCategoria() {

        categoria.setNome(campoNome.getText().toString());
        categoria.setObservacao(campoObservacao.getText().toString());

        return categoria;
    }

    public void preencheFormulario(Categoria categoria) {
        campoNome.setText(categoria.getNome());
        campoObservacao.setText(categoria.getObservacao());

        this.categoria = categoria;
    }

}
