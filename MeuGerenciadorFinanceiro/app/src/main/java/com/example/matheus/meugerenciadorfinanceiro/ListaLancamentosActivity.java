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
    ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>(); //array
    private ListView listView;  //outlet para listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lancamentos);


        listView = (ListView) findViewById(R.id.listView);

        LancamentoAdapter adapter = new LancamentoAdapter(this, Lancamento.lancamentos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("chave1", lancamentos.get(position));
                startActivity(intent);


                /*TextView textViewLancamento = ((TextView) view.findViewById(R.id.textViewLancamento));
                TextView textViewData = ((TextView) view.findViewById(R.id.textViewRecebeData));
                //TextView textViewValor = ((TextView) view.findViewById(R.id.textViewRecebeValor));

                // get the clicked item name
                String listItemDescricao = textViewLancamento.getText().toString();

                // get the clicked item ID
                String listItemData = textViewData.getTag().toString();
                //String listItemValor = textViewValor.ge

                // just toast it
                Toast.makeText(context, "Item: " + textViewLancamento + ", Item ID: " + textViewData, Toast.LENGTH_SHORT).show();
                */
            }
        });


        /*listaLancamentos.setOnClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao,long id){
                Intent intent = new Intent(ListaLancamentosActivity.this, Lancamento.class);

                intent.putExtra("key1", lancamentos.get(posicao));

                startActivity(intent);
            }
        });

        /*


        LinearLayout linear = (LinearLayout) findViewById(R.id.layourVertical);*/
    }
}
