package com.example.matheus.meugerenciadorfinanceiro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaLancamentosActivity extends AppCompatActivity {

    //ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>(); //array
    public static final String EXTRA_KEY = "posicao";
    public static final String EXTRA_RESULTADO = "result";
    private ListView listView;  //outlet para listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>();
        setContentView(R.layout.activity_lista_lancamentos);
        listView = (ListView) findViewById(R.id.listView);
        LancamentoAdapter adapter = new LancamentoAdapter(this, Lancamento.lancamentos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(view.getContext(), MainActivity.class);
                it.putExtra(EXTRA_RESULTADO, position);
                setResult(RESULT_OK, it);
                finish();
            /* ESSE ARRAY ESTA VAZIO -> lancamentos.get(position)
            // intent.putExtra("chave1", lancamentos.get(position));

            startActivity(intent);

                /*
                startActivity(intent);*/

                //Toast.makeText(getApplicationContext(),  "Esta vazio ?? "+   lancamentos.isEmpty() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
