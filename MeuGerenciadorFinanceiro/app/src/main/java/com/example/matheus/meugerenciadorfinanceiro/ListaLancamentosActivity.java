package com.example.matheus.meugerenciadorfinanceiro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListaLancamentosActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_KEY = "posicao";
    public static final String EXTRA_RESULTADO = "result";
    private ListView listView;  //outlet para listview
    private Button btnPrev, btnNext;
    private TextView textViewMes, textViewAno;
    private static final int REQUEST_MES = 1;
    private Calendar c = Calendar.getInstance();
    private DateFormat ano = new SimpleDateFormat("yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lancamentos);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        textViewMes = (TextView) findViewById(R.id.textViewMes);
        textViewAno = (TextView) findViewById(R.id.textViewAno);

        textViewMes.setText(defineMes(c.get(Calendar.MONTH)));
        textViewAno.setText(String.valueOf(c.get(Calendar.YEAR)));
        btnPrev.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>();
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
            }
        });
    }

    @Override
    public void onClick(View vanilla) {
        switch (vanilla.getId()) {
            case R.id.btnPrev:
                atualizaMes(-1);
                break;
            case R.id.btnNext:
                atualizaMes(+1);
                break;
        }
    }
    public void atualizaMes(int pizza) {
        c.add(Calendar.MONTH, pizza);
        textViewMes.setText(defineMes(c.get(Calendar.MONTH)));
        textViewAno.setText(String.valueOf(c.get(Calendar.YEAR)));
    }
    public String defineMes(int sorvete) {
        switch (sorvete) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
        }
        return "error";
    }
}
