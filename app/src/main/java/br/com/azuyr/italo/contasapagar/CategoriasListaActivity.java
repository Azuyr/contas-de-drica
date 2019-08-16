package br.com.azuyr.italo.contasapagar;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.azuyr.italo.contasapagar.dao.ContasAPagarDAO;
import br.com.azuyr.italo.contasapagar.models.Categoria;
import br.com.azuyr.italo.contasapagar.models.ContaVencimento;
import br.com.azuyr.italo.contasapagar.models.Identificacao;
import br.com.azuyr.italo.contasapagar.utils.Util;

public class CategoriasListaActivity extends AppCompatActivity {

    private ListView listaCategorias;
    private FormularioHelper helper;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_lista);

        listaCategorias = findViewById(R.id.categorias_lista);

        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ContaVencimento contaVencimento = (ContaVencimento) listaCategorias.getItemAtPosition(i);

            Intent intentIrParaForm = new Intent(CategoriasListaActivity.this,CategoriaActivity.class);
            intentIrParaForm.putExtra("conta",contaVencimento);
            startActivity(intentIrParaForm);

            Toast.makeText(CategoriasListaActivity.this,"Editando Conta '" + contaVencimento.getResumo() + "'",Toast.LENGTH_SHORT).show();
            }
        });

        Button novaConta = findViewById(R.id.categorias_botao_novo);
        novaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intentIrParaFormulario = new Intent(CategoriasListaActivity.this,CategoriaActivity.class);
            startActivity(intentIrParaFormulario);
            }
        });

        registerForContextMenu(listaCategorias);

        helper = new FormularioHelper(this);
        util = new Util();

        Intent intent = getIntent();
        ContasAPagarDAO dao = new ContasAPagarDAO(this);

        Identificacao identificacao = dao.getIdentificacao();

        if(identificacao != null){
            helper.preencheFormulario(identificacao);
        }
    }

    private void carregaLista() {
        ContasAPagarDAO dao = new ContasAPagarDAO(this);
        //List<Categoria> categorias = dao.buscaCategorias();
        List<ContaVencimento> contas = dao.buscaContasVencimentos();
        dao.close();

        //ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, categorias);
        ArrayAdapter<ContaVencimento> adapter = new ArrayAdapter<ContaVencimento>(this, android.R.layout.simple_list_item_1, contas);
        listaCategorias.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                ContaVencimento contaVencimento = (ContaVencimento) listaCategorias.getItemAtPosition(info.position);

                ContasAPagarDAO dao = new ContasAPagarDAO(CategoriasListaActivity.this);
                dao.deleta(contaVencimento);
                dao.close();

                carregaLista();

                Toast.makeText(CategoriasListaActivity.this,"Conta '" + contaVencimento.getResumo() + "' Deletada com Sucesso!",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_categorias_lista, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_identificacao_salvar:
                Identificacao identificacao = helper.getIdentificacao();

                ContasAPagarDAO dao = new ContasAPagarDAO(this);

                if(identificacao.getId() != null){
                    dao.altera(identificacao);
                    Toast.makeText(CategoriasListaActivity.this, identificacao.getNome() + "' Alterado(a) com Sucesso!", Toast.LENGTH_SHORT).show();
                }else {
                    dao.insere(identificacao);
                    Toast.makeText(CategoriasListaActivity.this, identificacao.getNome() + "' Salvo(a) com Sucesso!", Toast.LENGTH_SHORT).show();
                }

                dao.close();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
