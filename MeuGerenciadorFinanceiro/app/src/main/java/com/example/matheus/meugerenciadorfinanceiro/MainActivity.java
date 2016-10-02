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

    private static final int REQUEST_CAT = 1;
    private static final String STATE_CAT = "categoria";
    private String categoria;

    private Button btnSelCat, btnSlv, btnList;
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
        radioGp = (RadioGroup) findViewById(R.id.radioGroup);
        btnSelCat.setOnClickListener(this);
        btnSlv.setOnClickListener(this);
        btnList.setOnClickListener(this);
        radioGp.setOnClickListener(this);
                //check(radio1.getChildAt(index).getId());

    }


    @Override
    public void onClick(View v){
        Intent intent = new Intent(getBaseContext(), ListaLancamentosActivity.class);
        switch (v.getId()){

            case R.id.btnSelCat:
                Intent it = new Intent(this, SelecaoCategoriaActivity.class);
                it.putExtra(SelecaoCategoriaActivity.EXTRA_CAT, categoria);
                startActivityForResult(it, REQUEST_CAT);


                break;
            case R.id.btnSlv:
                    if (!editTextDescricao.getText().toString().isEmpty() && !editTextData.getText().toString().isEmpty() && !editTextValor.getText().toString().isEmpty() && !getReponse().equals("-1") ){
                        String situacao = "";
                        if (getReponse().equals("2131492954")){
                            situacao = "Receita";
                        }

                        if (getReponse().equals("2131492955")){
                            situacao = "Despesa";
                        }

                        Lancamento lancamento = new Lancamento(editTextDescricao.getText().toString(),Integer.parseInt(editTextData.getText().toString()), Float.parseFloat(editTextValor.getText().toString()), situacao );

                        Lancamento.lancamentos.add(lancamento);
                        clear();
                        Toast.makeText(this, "Adicionado.", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(this, "Radio id  " + getReponse() , Toast.LENGTH_SHORT).show();
            }else{
                        Toast.makeText(this, "Complete os campos.", Toast.LENGTH_SHORT).show();
                }



                break;
            case R.id.btnList:
                intent = new Intent(getBaseContext(), ListaLancamentosActivity.class);
                startActivity(intent);
                break;

        }
    }

    public String getReponse(){
       //  String.valueOf(radioGp.getCheckedRadioButtonId());
        //int = radioGp.getCheckedRadioButtonId();
        return  String.valueOf(radioGp.getCheckedRadioButtonId());

    }

    @Override
    protected void onResume() {
        super.onResume();
/*
        Intent intent = getIntent();
        Lancamento lancamentoBk = intent.getParcelableExtra("chave1");
        editTextDescricao.setText(lancamentoBk.getDescricao());
        editTextData.setText(String.valueOf(lancamentoBk.getData()));
        editTextValor.setText(String.valueOf(lancamentoBk.getValor()));*/
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
    }
}
