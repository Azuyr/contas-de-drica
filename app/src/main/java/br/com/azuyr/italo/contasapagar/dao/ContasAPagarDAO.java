package br.com.azuyr.italo.contasapagar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.azuyr.italo.contasapagar.models.Categoria;
import br.com.azuyr.italo.contasapagar.models.CategoriaVencimento;

public class ContasAPagarDAO extends SQLiteOpenHelper{

    public ContasAPagarDAO(Context context) {
        super(context, "ContasAPagar",null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Categorias (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, observacao TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE Categorias_Vencimentos (id INTEGER PRIMARY KEY, id_categoria INTEGER, data_Vencimento DATE NOT NULL, pago BIT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Categorias";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS Categorias_Vencimentos";
        db.execSQL(sql);

        onCreate(db);
    }

    public void insere(Categoria categoria) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosCategoria(categoria);

        db.insert("Categorias",null, dados);
    }

    @NonNull
    private ContentValues getDadosCategoria(Categoria categoria) {
        ContentValues dados = new ContentValues();
        dados.put("nome",categoria.getNome());
        dados.put("observacao",categoria.getObservacao());
        return dados;
    }

    public List<Categoria> buscaCategorias() {
        String sql = "SELECT * FROM [Categorias]";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Categoria> categorias = new ArrayList<Categoria>();

        while (c.moveToNext()){
            Categoria categoria = new Categoria();
            categoria.setId(c.getLong(c.getColumnIndex("id")));
            categoria.setNome(c.getString(c.getColumnIndex("nome")));
            categoria.setObservacao(c.getString(c.getColumnIndex("observacao")));

            categorias.add(categoria);
        }

        c.close();

        return categorias;
    }

    public List<CategoriaVencimento> buscaCategoriasVencimentos(long idCategoria) {
        String sql = "SELECT * FROM [CategoriasVencimentos] WHERE [id_categoria]=" + idCategoria;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<CategoriaVencimento> categoriaVencimentos = new ArrayList<CategoriaVencimento>();

        while (c.moveToNext()){
            CategoriaVencimento categoriaVencimento = new CategoriaVencimento();
            categoriaVencimento.setId(c.getLong(c.getColumnIndex("id")));
            //categoriaVencimento.setData_vencimento(c.get(c.getColumnIndex("data_vencimento")));
            //categoria.setObservacao(c.getString(c.getColumnIndex("pago")));

            categoriaVencimentos.add(categoriaVencimento);
        }

        c.close();

        return categoriaVencimentos;
    }

    public void deleta(Categoria categoria) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {categoria.getId().toString()};

        db.delete("Categorias","id = ?",params);
    }

    public void altera(Categoria categoria) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosCategoria(categoria);

        String[] params = {categoria.getId().toString()};

        db.update("Categorias",dados,"id = ?", params);
    }
}
