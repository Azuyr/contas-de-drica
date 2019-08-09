package br.com.azuyr.italo.contasapagar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.azuyr.italo.contasapagar.CategoriaActivity;
import br.com.azuyr.italo.contasapagar.models.Categoria;
import br.com.azuyr.italo.contasapagar.models.CategoriaVencimento;
import br.com.azuyr.italo.contasapagar.utils.Util;

public class ContasAPagarDAO extends SQLiteOpenHelper{

    public ContasAPagarDAO(Context context) {
        super(context, "ContasAPagar",null, 2);
    }
    Util util;

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Categorias (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, observacao TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE Categorias_Vencimentos (id INTEGER PRIMARY KEY, id_categoria INTEGER, data_vencimento DATE NOT NULL, valor DOUBLE, titulo STRING, resumo STRING)";
        db.execSQL(sql);

        util = new Util();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS Categorias";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS Categorias_Vencimentos";
        db.execSQL(sql);

        onCreate(db);
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

   public void insere(Categoria categoria) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosCategoria(categoria);

        db.insert("Categorias",null, dados);
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

    private ContentValues getDadosCategoriaVencimento(CategoriaVencimento categoriaVencimento) {
        ContentValues dados = new ContentValues();
        dados.put("id_categoria",categoriaVencimento.getId_categoria());
        dados.put("data_vencimento",categoriaVencimento.getData_vencimento());
        dados.put("titulo",categoriaVencimento.getTitulo());
        dados.put("resumo",categoriaVencimento.getResumo());
        dados.put("valor",categoriaVencimento.getValor());
        return dados;
    }

    public List<CategoriaVencimento> buscaCategoriasVencimentos(long idCategoria) {
        String sql = "SELECT * FROM [Categorias_Vencimentos] WHERE [id_categoria]=" + idCategoria;
        SQLiteDatabase db = getReadableDatabase();

        sql = "SELECT * FROM [Categorias_Vencimentos]";
        Cursor c = db.rawQuery(sql, null);

        List<CategoriaVencimento> categoriaVencimentos = new ArrayList<CategoriaVencimento>();

        c.moveToFirst();

        while (c.moveToNext()){
            CategoriaVencimento categoriaVencimento = new CategoriaVencimento();
            categoriaVencimento.setId(c.getLong(c.getColumnIndex("id")));
            categoriaVencimento.setData_vencimento(c.getLong(c.getColumnIndex("data_vencimento")));
            categoriaVencimento.setValor(c.getDouble(c.getColumnIndex("valor")));
            categoriaVencimento.setResumo(c.getString(c.getColumnIndex("resumo")));
            categoriaVencimento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            categoriaVencimento.setId_categoria(c.getLong(c.getColumnIndex("id_categoria")));

            categoriaVencimentos.add(categoriaVencimento);
        }

        c.close();

        return categoriaVencimentos;
    }

    public void insere(CategoriaVencimento categoriaVencimento) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosCategoriaVencimento(categoriaVencimento);

        db.insert("Categorias_Vencimentos",null, dados);
    }

    public void deleta(CategoriaVencimento categoriaVencimento) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {categoriaVencimento.getId().toString()};

        db.delete("Categorias_Vencimentos","id = ?",params);
    }

    public void altera(CategoriaVencimento categoriaVencimento) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosCategoriaVencimento(categoriaVencimento);

        String[] params = {categoriaVencimento.getId().toString()};

        db.update("Categorias_Vencimentos",dados,"id = ?", params);
    }
}
