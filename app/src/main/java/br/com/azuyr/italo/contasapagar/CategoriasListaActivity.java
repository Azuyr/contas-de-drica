package br.com.azuyr.italo.contasapagar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
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

public class CategoriasListaActivity extends AppCompatActivity {

    private ListView listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_lista);

        listaCategorias = findViewById(R.id.categorias_lista);

        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Categoria categoria = (Categoria) listaCategorias.getItemAtPosition(i);

                Intent intentIrParaForm = new Intent(CategoriasListaActivity.this,CategoriaActivity.class);
                intentIrParaForm.putExtra("conta",categoria);
                startActivity(intentIrParaForm);

                Toast.makeText(CategoriasListaActivity.this,"Editando Conta '" + categoria.getNome() + "'",Toast.LENGTH_SHORT).show();
            }
        });

        Button novaCategoria = findViewById(R.id.categorias_botao_novo);
        novaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentIrParaFormulario = new Intent(CategoriasListaActivity.this,CategoriaActivity.class);
                startActivity(intentIrParaFormulario);
            }
        });

        registerForContextMenu(listaCategorias);
    }

    private void carregaLista() {
        ContasAPagarDAO dao = new ContasAPagarDAO(this);
        List<Categoria> categorias = dao.buscaCategorias();
        dao.close();

        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, categorias);
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

                Categoria categoria = (Categoria) listaCategorias.getItemAtPosition(info.position);

                ContasAPagarDAO dao = new ContasAPagarDAO(CategoriasListaActivity.this);
                dao.deleta(categoria);
                dao.close();

                carregaLista();

                Toast.makeText(CategoriasListaActivity.this,"Conta '" + categoria.getNome() + "' Deletada com Sucesso!",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
}
