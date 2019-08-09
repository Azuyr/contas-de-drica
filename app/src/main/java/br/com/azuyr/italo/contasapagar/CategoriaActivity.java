package br.com.azuyr.italo.contasapagar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import br.com.azuyr.italo.contasapagar.dao.ContasAPagarDAO;
import br.com.azuyr.italo.contasapagar.models.Categoria;
import br.com.azuyr.italo.contasapagar.models.CategoriaVencimento;
import br.com.azuyr.italo.contasapagar.utils.MaskEditUtil;
import br.com.azuyr.italo.contasapagar.utils.Util;

public class CategoriaActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private ListView listaCategoriasVencimentos;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        helper = new FormularioHelper(this);
        util = new Util();

        Intent intent = getIntent();
        Categoria categoria = (Categoria) intent.getSerializableExtra("conta");

        if(categoria != null){
            helper.preencheFormulario(categoria);
        }

        Button buttonAdicionarVencimento = findViewById(R.id.categoria_add_vencimentos);
        buttonAdicionarVencimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Categoria categoria = helper.getCategoria();

                    ContasAPagarDAO dao = new ContasAPagarDAO(CategoriaActivity.this);

                    CategoriaVencimento categoriaVencimento = new CategoriaVencimento();

                    //Date data = new Date(System.currentTimeMillis());

                    categoriaVencimento.setValor(50.00);
                    categoriaVencimento.setId_categoria(categoria.getId());
                    categoriaVencimento.setData_vencimento(System.currentTimeMillis());
                    categoriaVencimento.setResumo("Teste Resumo");
                    categoriaVencimento.setTitulo("1");

                    dao.insere(categoriaVencimento);
                    Toast.makeText(CategoriaActivity.this, "Vencimento '" + categoriaVencimento.getResumo() + "' Salva com Sucesso!", Toast.LENGTH_SHORT).show();

                    carregaLista(categoria.getId());

                    dao.close();
                }catch (Exception ex){
                    Toast.makeText(CategoriaActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                };

            }
        });

    }

    private void carregaLista(Long idCategoria) {
        try {
            ContasAPagarDAO dao = new ContasAPagarDAO(this);
            List<CategoriaVencimento> categoriasVencimentos = dao.buscaCategoriasVencimentos(idCategoria);
            dao.close();

            util.msgBoxCustomer(this, "ID: " + idCategoria.toString() + " Quantidade: " + categoriasVencimentos.size());

            //ArrayAdapter<CategoriaVencimento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriasVencimentos);
            //listaCategoriasVencimentos.setAdapter(adapter);

        }catch (Exception ex){
            util.msgBoxCustomer(this, ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_categoria, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_categoria_salvar:
                Categoria categoria = helper.getCategoria();

                ContasAPagarDAO dao = new ContasAPagarDAO(this);

                if(categoria.getId() != null){
                    dao.altera(categoria);
                    Toast.makeText(CategoriaActivity.this, "Categoria '" + categoria.getNome() + "' Alterada com Sucesso!", Toast.LENGTH_SHORT).show();
                }else {
                    dao.insere(categoria);
                    Toast.makeText(CategoriaActivity.this, "Categoria '" + categoria.getNome() + "' Salva com Sucesso!", Toast.LENGTH_SHORT).show();
                }

                dao.close();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
