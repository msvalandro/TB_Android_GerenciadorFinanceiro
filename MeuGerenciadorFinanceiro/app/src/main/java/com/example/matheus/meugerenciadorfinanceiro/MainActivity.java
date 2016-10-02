package com.example.matheus.meugerenciadorfinanceiro;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int cachorroquente, posicaoAux = -1, controle = 1;
    private static final int REQUEST_CAT = 1;
    private static final int REQUEST_POS = 300;
    private static final String STATE_CAT = "categoria";
    private String categoria;

    private Button btnSelCat, btnSlv, btnList, btnExc;
    EditText editTextDescricao, editTextData, editTextValor;

    private RadioGroup radioGp;

    public void clear() {
        editTextDescricao.setText("");
        editTextData.setText("");
        editTextValor.setText("");
        //zerar os outros botoes tambem
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lancamento.lancamentos = new ArrayList<Lancamento>();
        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        editTextData = (EditText) findViewById(R.id.editTextData);
        editTextValor = (EditText) findViewById(R.id.editTextValor);
        btnSelCat = (Button) findViewById(R.id.btnSelCat);
        btnSlv = (Button) findViewById(R.id.btnSlv);
        btnList = (Button) findViewById(R.id.btnList);
        btnExc = (Button) findViewById(R.id.btnExc);
        radioGp = (RadioGroup) findViewById(R.id.radioGroup);
        btnSelCat.setOnClickListener(this);
        btnSlv.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnExc.setOnClickListener(this);
        radioGp.setOnClickListener(this);
                //check(radio1.getChildAt(index).getId());
    }


    @Override
    public void onClick(View v){
        /*Intent intent = new Intent(getBaseContext(), ListaLancamentosActivity.class);
        int posicao = intent.getIntExtra("posicao", 0);
        editTextDescricao.setText(Lancamento.lancamentos.get(posicao).getDescricao().toString());*/

        switch (v.getId()){

            case R.id.btnSelCat:
                Intent it = new Intent(this, SelecaoCategoriaActivity.class);
                it.putExtra(SelecaoCategoriaActivity.EXTRA_CAT, categoria);
                startActivityForResult(it, REQUEST_CAT);
                break;
            case R.id.btnSlv:
                if (!editTextDescricao.getText().toString().isEmpty() &&
                        !editTextData.getText().toString().isEmpty() && !editTextValor.getText().toString().isEmpty() &&
                        !getReponse().equals("-1") ){
                    if(posicaoAux > -1) {
                        for (cachorroquente = 0; cachorroquente < Lancamento.lancamentos.size(); cachorroquente++) {
                            if (Lancamento.lancamentos.get(posicaoAux).getCodigo() == Lancamento.lancamentos.get(cachorroquente).getCodigo()) {
                                Lancamento.lancamentos.get(posicaoAux).setDescricao(editTextDescricao.getText().toString());
                                Lancamento.lancamentos.get(cachorroquente).setData(Integer.parseInt(editTextData.getText().toString()));
                                Lancamento.lancamentos.get(cachorroquente).setValor(Float.parseFloat(editTextValor.getText().toString()));
                                clear();
                                Toast.makeText(this, "Editado.", Toast.LENGTH_SHORT).show();
                                posicaoAux = -1;
                                break;
                            }
                        }
                        break;
                    }
                    String situacao = "";
                    if (getReponse().equals("2131492954")){
                        situacao = "Receita";
                    }

                    if (getReponse().equals("2131492955")){
                        situacao = "Despesa";
                    }

                    Lancamento lancamento = new Lancamento(controle, editTextDescricao.getText().toString(),Integer.parseInt(editTextData.getText().toString()), Float.parseFloat(editTextValor.getText().toString()), situacao );
                    Lancamento.lancamentos.add(lancamento);
                    controle = controle + 1;
                    clear();
                    Toast.makeText(this, "Adicionado.", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(this, "Radio id  " + getReponse() , Toast.LENGTH_SHORT).show();
                    //break;
                }else{
                        Toast.makeText(this, "Complete os campos.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnList:
                Intent intent = new Intent(this, ListaLancamentosActivity.class);
                intent.putExtra(ListaLancamentosActivity.EXTRA_KEY, categoria);
                startActivityForResult(intent, REQUEST_POS);
                break;
            case R.id.btnExc:
                if(Lancamento.lancamentos.isEmpty()){
                    Toast.makeText(this, "Não existem lançamentos.", Toast.LENGTH_LONG).show();
                    break;
                }
                if (posicaoAux > -1){
                    Lancamento.lancamentos.remove(posicaoAux);
                    posicaoAux = -1;
                    clear();
                    Toast.makeText(this, "Lançamento removido.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Selecione um lançamento.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public String getReponse(){
       //  String.valueOf(radioGp.getCheckedRadioButtonId());
        //int = radioGp.getCheckedRadioButtonId();
        return  String.valueOf(radioGp.getCheckedRadioButtonId());

    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CAT){
            categoria = data.getStringExtra(SelecaoCategoriaActivity.EXTRA_RESULTADO);
            if(categoria != null){
                btnSelCat.setText(categoria);
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_POS){
            posicaoAux = data.getIntExtra(ListaLancamentosActivity.EXTRA_RESULTADO, 0);
            editTextDescricao.setText(Lancamento.lancamentos.get(posicaoAux).getDescricao());
            editTextData.setText(Integer.valueOf(Lancamento.lancamentos.get(posicaoAux).getData()).toString());
            editTextValor.setText(Float.valueOf(Lancamento.lancamentos.get(posicaoAux).getValor()).toString());
        }
    }
}
