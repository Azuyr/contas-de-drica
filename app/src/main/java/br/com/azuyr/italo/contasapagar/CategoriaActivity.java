package br.com.azuyr.italo.contasapagar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import br.com.azuyr.italo.contasapagar.dao.ContasAPagarDAO;
import br.com.azuyr.italo.contasapagar.models.Categoria;
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
