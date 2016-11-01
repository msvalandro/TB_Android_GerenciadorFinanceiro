package com.example.matheus.meugerenciadorfinanceiro;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * Created by Matheus on 22/09/2016.
 */
public class LancamentoAdapter extends BaseAdapter {

    private List<Lancamento> lancamentos;
    private Activity activity;

    public LancamentoAdapter(Activity activity, List<Lancamento> lancamentos) {
        this.activity = activity;
        this.lancamentos = lancamentos;
    }

    @Override
    public int getCount() {
        return lancamentos.size();
    }

    @Override
    public Object getItem(int posicao) {
        return lancamentos.get(posicao);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicao, View v, ViewGroup parent){
        //Criar uma view apartir do arquivo de layout activity_lista_lancamentos
        View view = this.activity.getLayoutInflater().inflate(R.layout.cell_lista_lancamentos, null);

        //Obter o objeto lancamento a partir da posicao
        Lancamento lancamento = lancamentos.get(posicao);

        //Exibir o nome do lancamento
        TextView descricao = (TextView) view.findViewById(R.id.textViewLancamento);
        descricao.setText(lancamento.getDescricao());

        TextView data = (TextView) view.findViewById(R.id.textViewRecebeData);
        data.setText(new SimpleDateFormat("dd").format(lancamento.getData()).toString());

        TextView valor = (TextView) view.findViewById(R.id.textViewRecebeValor);
        valor.setText(String.valueOf(lancamento.getValor()));

        return view;
    }
}
