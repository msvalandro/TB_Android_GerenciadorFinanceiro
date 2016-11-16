package com.example.matheus.meugerenciadorfinanceiro;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class TopFiveActivity extends AppCompatActivity {

    private int batatafrita, aux2;
    private int[] categoria = new int[6];
    private float totalValor = 0, aux;
    private float[] valorCategoria = new float[6];
    private TextView textViewMes, textViewNome1, textViewNome2, textViewNome3, textViewNome4, textViewNome5, textViewValor1, textViewValor2, textViewValor3, textViewValor4, textViewValor5, textViewPorcentagem1, textViewPorcentagem2, textViewPorcentagem3, textViewPorcentagem4, textViewPorcentagem5;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_five);

        textViewMes = (TextView) findViewById(R.id.textViewMes);
        textViewMes.setText(defineMes(c.get(Calendar.MONTH)));
        textViewNome1 = (TextView) findViewById(R.id.textViewNome1);
        textViewNome2 = (TextView) findViewById(R.id.textViewNome2);
        textViewNome3 = (TextView) findViewById(R.id.textViewNome3);
        textViewNome4 = (TextView) findViewById(R.id.textViewNome4);
        textViewNome5 = (TextView) findViewById(R.id.textViewNome5);
        textViewValor1 = (TextView) findViewById(R.id.textViewValor1);
        textViewValor2 = (TextView) findViewById(R.id.textViewValor2);
        textViewValor3 = (TextView) findViewById(R.id.textViewValor3);
        textViewValor4 = (TextView) findViewById(R.id.textViewValor4);
        textViewValor5 = (TextView) findViewById(R.id.textViewValor5);
        textViewPorcentagem1 = (TextView) findViewById(R.id.textViewPorcentagem1);
        textViewPorcentagem2 = (TextView) findViewById(R.id.textViewPorcentagem2);
        textViewPorcentagem3 = (TextView) findViewById(R.id.textViewPorcentagem3);
        textViewPorcentagem4 = (TextView) findViewById(R.id.textViewPorcentagem4);
        textViewPorcentagem5 = (TextView) findViewById(R.id.textViewPorcentagem5);

        zeraVetor();
        atualizaTopFive();
    }

    public void zeraVetor() {
        for(batatafrita = 0; batatafrita < valorCategoria.length; batatafrita++) {
            valorCategoria[batatafrita] = 0;
            categoria[batatafrita] = 0;
        }
    }

    public void atualizaTopFive() {
        /*for (batatafrita = 0; batatafrita < Lancamento.lancamentos.size(); batatafrita++) {
            if(Lancamento.lancamentos.get(batatafrita).equals("Lazer"))
                categoria[batatafrita]++;
            if(Lancamento.lancamentos.get(batatafrita).equals("Veículo"))
                categoria[batatafrita]++;
            if(Lancamento.lancamentos.get(batatafrita).equals("Casa"))
                categoria[batatafrita]++;
            if(Lancamento.lancamentos.get(batatafrita).equals("Escola"))
                categoria[batatafrita]++;
            if(Lancamento.lancamentos.get(batatafrita).equals("Saúde"))
                categoria[batatafrita]++;
            if(Lancamento.lancamentos.get(batatafrita).equals("Rituais Satânicos"))
                categoria[batatafrita]++;
        }*/
        for (batatafrita = 0; batatafrita < Lancamento.lancamentos.size(); batatafrita++) {
            if(Lancamento.lancamentos.get(batatafrita).getTipo().equals("Despesa")) {
                if (Lancamento.lancamentos.get(batatafrita).getCategoria().equals("Lazer"))
                    valorCategoria[0] = valorCategoria[0] + Lancamento.lancamentos.get(batatafrita).getValor();
                if (Lancamento.lancamentos.get(batatafrita).getCategoria().equals("Veículo"))
                    valorCategoria[1] = valorCategoria[1] + Lancamento.lancamentos.get(batatafrita).getValor();
                if (Lancamento.lancamentos.get(batatafrita).getCategoria().equals("Casa"))
                    valorCategoria[2] = valorCategoria[2] + Lancamento.lancamentos.get(batatafrita).getValor();
                if (Lancamento.lancamentos.get(batatafrita).getCategoria().equals("Escola"))
                    valorCategoria[3] = valorCategoria[3] + Lancamento.lancamentos.get(batatafrita).getValor();
                if (Lancamento.lancamentos.get(batatafrita).getCategoria().equals("Saúde"))
                    valorCategoria[4] = valorCategoria[4] + Lancamento.lancamentos.get(batatafrita).getValor();
                if (Lancamento.lancamentos.get(batatafrita).getCategoria().equals("Rituais Satânicos"))
                    valorCategoria[5] = valorCategoria[5] + Lancamento.lancamentos.get(batatafrita).getValor();
                totalValor = totalValor + Lancamento.lancamentos.get(batatafrita).getValor();
            }
        }
        textViewValor1.setText(String.valueOf(maiorValor()));
        textViewNome1.setText(maiorCategoria(aux2));
        textViewPorcentagem1.setText(formataString((Float.parseFloat(textViewValor1.getText().toString()) / totalValor) * 100) + "%");
        textViewValor2.setText(String.valueOf(maiorValor()));
        textViewNome2.setText(maiorCategoria(aux2));
        textViewPorcentagem2.setText(formataString((Float.parseFloat(textViewValor2.getText().toString()) / totalValor) * 100) + "%");
        textViewValor3.setText(String.valueOf(maiorValor()));
        textViewNome3.setText(maiorCategoria(aux2));
        textViewPorcentagem3.setText(formataString((Float.parseFloat(textViewValor3.getText().toString()) / totalValor) * 100) + "%");
        textViewValor4.setText(String.valueOf(maiorValor()));
        textViewNome4.setText(maiorCategoria(aux2));
        textViewPorcentagem4.setText(formataString((Float.parseFloat(textViewValor4.getText().toString()) / totalValor) * 100) + "%");
        textViewValor5.setText(String.valueOf(maiorValor()));
        textViewNome5.setText(maiorCategoria(aux2));
        textViewPorcentagem5.setText(formataString((Float.parseFloat(textViewValor5.getText().toString()) / totalValor) * 100) + "%");
    }
    public float maiorValor() {
        aux = 0;
        for (batatafrita = 0; batatafrita < valorCategoria.length; batatafrita++) {
            if (aux < valorCategoria[batatafrita]) {
                aux = valorCategoria[batatafrita];
                aux2 = batatafrita;
            }
        }
        if(aux == 0) {
            for (batatafrita = 0; batatafrita < valorCategoria.length; batatafrita++) {
                if (0 == valorCategoria[batatafrita]) {
                    aux2 = batatafrita;
                    valorCategoria[batatafrita] = -1;
                    return aux;
                }
            }
        }
        valorCategoria[aux2] = -1;
        return aux;
    }
    public String maiorCategoria(int pipoca) {
        if(pipoca == 0)
            return "Lazer";
        if(pipoca == 1)
            return "Veículo";
        if(pipoca == 2)
            return "Casa";
        if(pipoca == 3)
            return "Escola";
        if(pipoca == 4)
            return "Saúde";
        if(pipoca == 5)
            return "Rituais Satânicos";
        return "Categoria";
    }
    public String formataString (float a) {
        return String.format("%.2f", a);
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
