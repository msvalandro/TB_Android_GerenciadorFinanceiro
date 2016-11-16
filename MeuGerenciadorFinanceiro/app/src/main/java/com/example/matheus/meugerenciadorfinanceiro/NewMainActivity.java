package com.example.matheus.meugerenciadorfinanceiro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.MenuItemHoverListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NewMainActivity extends AppCompatActivity {

    private int posicao;
    private float saldo;
    private String tipo;
    private TextView textViewSaldo, textViewMes;
    private Calendar c = Calendar.getInstance();
    private static final int REQUEST_POS = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        textViewMes = (TextView) findViewById(R.id.textViewMes);
        textViewSaldo = (TextView) findViewById(R.id.textViewSaldo);

        textViewMes.setText(defineMes(c.get(Calendar.MONTH)));
        atualizaSaldo();
    }
    @Override
    public void onResume() {
        super.onResume();
        atualizaSaldo();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.acao_receita:
                Intent receita = new Intent(this, MainActivity.class);
                receita.putExtra("tipo", "Receita");
                startActivity(receita);
                break;
            case R.id.acao_despesa:
                Intent despesa = new Intent(this, MainActivity.class);
                despesa.putExtra("tipo", "Despesa");
                startActivity(despesa);
                break;
            case R.id.acao_listar:
                if (Lancamento.lancamentos.isEmpty()) {
                    Toast.makeText(this, "Não existem lançamentos", Toast.LENGTH_LONG).show();
                } else {
                    Intent lista = new Intent(this, ListaLancamentosActivity.class);
                    startActivityForResult(lista, REQUEST_POS);
                }
                break;
            case R.id.acao_top:
                Intent top = new Intent(this, TopFiveActivity.class);
                startActivity(top);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void atualizaSaldo() {
        this.saldo = 0;
        for (int cachorroquente = 0; cachorroquente < Lancamento.lancamentos.size(); cachorroquente++) {
            if (Lancamento.lancamentos.get(cachorroquente).getTipo().equals("Receita")) {
                saldo = saldo + Lancamento.lancamentos.get(cachorroquente).getValor();
            } else if (Lancamento.lancamentos.get(cachorroquente).getTipo().equals("Despesa")) {
                saldo = saldo - Lancamento.lancamentos.get(cachorroquente).getValor();
            } else {
                Toast.makeText(this, "Deu merda!", Toast.LENGTH_SHORT).show();
            }
        }
        textViewSaldo.setText(String.valueOf(this.saldo));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_POS) {
            posicao = data.getIntExtra(ListaLancamentosActivity.EXTRA_RESULTADO, 0);
        }
        for (int i = 0; i < Lancamento.lancamentos.size(); i++)
            if (posicao == Lancamento.lancamentos.get(i).getCodigo()) {
                tipo = Lancamento.lancamentos.get(i).getTipo();
            }
        Intent lancamento = new Intent(this, MainActivity.class);
        lancamento.putExtra("posicao", posicao);
        lancamento.putExtra("tipo", tipo);
        startActivity(lancamento);
    }
}
