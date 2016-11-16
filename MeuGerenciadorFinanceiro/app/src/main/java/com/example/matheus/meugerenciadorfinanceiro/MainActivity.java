package com.example.matheus.meugerenciadorfinanceiro;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //A gambiarra a gente aceita, o que a gente não aceita é a derrota.
    private int cachorroquente, posicaoAux = -1, controle = 1;
    private static final int REQUEST_CAT = 1;
    private static final int REQUEST_POS = 300;
    private static final String STATE_CAT = "categoria";
    private String categoria, tipo;
    private float saldo;
    Lancamento lancamento;
    private java.util.Date data;
    SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");

    private Button btnSelCat, btnSlv, btnList, btnExc;
    private RadioButton rdbReceita, rdbDespesa;
    private EditText editTextDescricao, editTextData, editTextValor, editTextParcelas;
    private TextView textViewSaldo;
    private RadioGroup radioGp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Lancamento.lancamentos = new ArrayList<Lancamento>();
        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        editTextData = (EditText) findViewById(R.id.editTextData);
        editTextValor = (EditText) findViewById(R.id.editTextValor);
        editTextParcelas = (EditText) findViewById(R.id.editTextParcelas);
        textViewSaldo = (TextView) findViewById(R.id.textViewSaldo);
        btnSelCat = (Button) findViewById(R.id.btnSelCat);
        btnSlv = (Button) findViewById(R.id.btnSlv);
        btnList = (Button) findViewById(R.id.btnList);
        btnExc = (Button) findViewById(R.id.btnExc);
        radioGp = (RadioGroup) findViewById(R.id.radioGroup);
        rdbReceita = (RadioButton) findViewById(R.id.rdbReceita);
        rdbDespesa = (RadioButton) findViewById(R.id.rdbDespesa);
        out.setLenient(false);
        atualizaSaldo();
        Intent it = getIntent();
        posicaoAux = refreshArray(it.getIntExtra("posicao", -33));
        tipo = it.getStringExtra("tipo");
        retornaActivity();
        if (tipo.equals("Receita")){
            rdbReceita.toggle();
            rdbDespesa.setEnabled(false);
        }else if (tipo.equals("Despesa")){
            rdbDespesa.toggle();
            rdbReceita.setEnabled(false);
        }

        editTextData.addTextChangedListener(Mask.insert("##/##/####", editTextData));

        btnSelCat.setOnClickListener(this);
        btnSlv.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnExc.setOnClickListener(this);
        radioGp.setOnClickListener(this);
        rdbReceita.setOnClickListener(this);
        rdbDespesa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSelCat:
                Intent it = new Intent(this, SelecaoCategoriaActivity.class);
                it.putExtra(SelecaoCategoriaActivity.EXTRA_CAT, categoria);
                startActivityForResult(it, REQUEST_CAT);
                break;
            case R.id.btnSlv:
                try {
                    data = formataData(editTextData.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(this, "Data inválida!", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (!editTextDescricao.getText().toString().isEmpty() &&
                        !editTextData.getText().toString().isEmpty() && !editTextValor.getText().toString().isEmpty() &&
                        /*!getReponse().equals("-1") && */ !btnSelCat.getText().equals("Categoria") && (retornaParcela() == 0 || (!(retornaParcela() < 2) && !(retornaParcela() > 12)))) {
                    if (posicaoAux > -1) {
                        for (cachorroquente = 0; cachorroquente < Lancamento.lancamentos.size(); cachorroquente++) {
                            if (Lancamento.lancamentos.get(posicaoAux).getCodigo() == Lancamento.lancamentos.get(cachorroquente).getCodigo()) {
                                Lancamento.lancamentos.get(posicaoAux).setDescricao(editTextDescricao.getText().toString());
                                Lancamento.lancamentos.get(cachorroquente).setData(data);
                                Lancamento.lancamentos.get(cachorroquente).setValor(Float.parseFloat(editTextValor.getText().toString()));
                                if (retornaParcela() > 1 && retornaParcela() < 13){
                                    Lancamento lancamento = null;
                                    for (int i = 0; i < Integer.parseInt(editTextParcelas.getText().toString()); i++){
                                        lancamento = new Lancamento(controle, editTextDescricao.getText().toString(), addMes(data, i), (Float.parseFloat(editTextValor.getText().toString()) / Integer.parseInt(editTextParcelas.getText().toString())), 0, tipo, categoria);
                                        Lancamento.lancamentos.add(lancamento);
                                        controle = controle + 1;
                                    }
                                    Lancamento.lancamentos.remove(posicaoAux);
                                    posicaoAux = -1;
                                    clear();
                                    atualizaSaldo();
                                    Toast.makeText(this, "Editado.", Toast.LENGTH_SHORT).show();
                                    break;
                                }

                                //Lancamento.lancamentos.get(cachorroquente).setParcelas(Integer.parseInt(editTextParcelas.getText().toString()));
                                /*tipo = "";
                                if (getReponse().equals("2131492961")) {
                                    tipo = "Receita";
                                }

                                if (getReponse().equals("2131492962")) {
                                    tipo = "Despesa";
                                }*/
                                //Lancamento.lancamentos.get(cachorroquente).setTipo(tipo);
                                Lancamento.lancamentos.get(cachorroquente).setCategoria(categoria);
                                clear();
                                atualizaSaldo();
                                Toast.makeText(this, "Editado.", Toast.LENGTH_SHORT).show();
                                posicaoAux = -1;
                                break;
                            }
                        }
                        break;
                    }
                    /*tipo = "";
                    if (getReponse().equals("2131492961")) {
                        tipo = "Receita";
                    }

                    if (getReponse().equals("2131492962")) {
                        tipo = "Despesa";
                    }*/
                    if (retornaParcela() > 1 && retornaParcela() < 13){
                        for (int i = 0; i < Integer.parseInt(editTextParcelas.getText().toString()); i++){
                            lancamento = new Lancamento(controle, editTextDescricao.getText().toString(), addMes(data, i), (Float.parseFloat(editTextValor.getText().toString()) / Integer.parseInt(editTextParcelas.getText().toString())), 0, tipo, categoria);
                            Lancamento.lancamentos.add(lancamento);
                            controle = controle + 1;
                        }
                        clear();
                        atualizaSaldo();
                        Toast.makeText(this, "Adicionado.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    lancamento = new Lancamento(controle, editTextDescricao.getText().toString(), data, Float.parseFloat(editTextValor.getText().toString()), retornaParcela(), tipo, categoria);
                    Lancamento.lancamentos.add(lancamento);
                    controle = controle + 1;
                    clear();
                    atualizaSaldo();
                    Toast.makeText(this, "Adicionado.", Toast.LENGTH_SHORT).show();
                } else {
                    /*if (getReponse().equals("-1")) {
                        Toast.makeText(this, "Informe se o lançamento é Receita ou Despesa.", Toast.LENGTH_SHORT).show();
                        break;
                    }*/
                    if (btnSelCat.getText().equals("Categoria")) {
                        Toast.makeText(this, "Informe uma categoria.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (editTextDescricao.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Informe uma descrição.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (editTextData.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Informe uma data.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (editTextValor.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Informe um valor.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (Integer.parseInt(editTextParcelas.getText().toString()) < 2 || Integer.parseInt(editTextParcelas.getText().toString()) > 12){
                        Toast.makeText(this, "O número de parcelas deve estar entre 2 e 12.", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btnList:
                if (Lancamento.lancamentos.isEmpty()) {
                    Toast.makeText(this, "Não existem lançamentos.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, ListaLancamentosActivity.class);
                    intent.putExtra(ListaLancamentosActivity.EXTRA_KEY, categoria);
                    startActivityForResult(intent, REQUEST_POS);
                }
                break;
            case R.id.btnExc:
                if (Lancamento.lancamentos.isEmpty()) {
                    Toast.makeText(this, "Não existem lançamentos.", Toast.LENGTH_LONG).show();
                    break;
                }
                if (posicaoAux > -1) {
                    Lancamento.lancamentos.remove(posicaoAux);
                    posicaoAux = -1;
                    clear();
                    atualizaSaldo();
                    Toast.makeText(this, "Lançamento removido.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Selecione um lançamento.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public String getReponse() {return String.valueOf(radioGp.getCheckedRadioButtonId());}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CAT) {
            categoria = data.getStringExtra(SelecaoCategoriaActivity.EXTRA_RESULTADO);
            if (categoria != null) {
                btnSelCat.setText(categoria);
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_POS) {
            posicaoAux = refreshArray(data.getIntExtra(ListaLancamentosActivity.EXTRA_RESULTADO, 0)); //pega o id e subtrai 1 para obter pos no vetor
            retornaActivity();
        }
    }

    public void clear() {
        editTextDescricao.setText("");
        editTextData.setText("");
        editTextValor.setText("");
        editTextParcelas.setText("");
        btnSelCat.setText("Categoria");
        //radioGp.clearCheck();
    }

    public void atualizaSaldo() {
        this.saldo = 0;
        for (cachorroquente = 0; cachorroquente < Lancamento.lancamentos.size(); cachorroquente++) {
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
    public java.util.Date formataData(String data) throws Exception {
        if (data == null || data.equals(""))
            return null;
        java.util.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }
    public static java.util.Date addMes(java.util.Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, qtd);
        return cal.getTime();
    }
    public int retornaParcela(){
        if (editTextParcelas.getText().toString().isEmpty()) {
            return 0;
        }else {
            return Integer.parseInt(editTextParcelas.getText().toString());
        }
    }
    public int refreshArray(int codigo){
        for(int i = 0; i < Lancamento.lancamentos.size(); i++){
            if(codigo == Lancamento.lancamentos.get(i).getCodigo())
                return i;
        }
        return -33;
    }
    public void retornaActivity() {
        if(posicaoAux != -33) {
            tipo = Lancamento.lancamentos.get(posicaoAux).getTipo();
            categoria = Lancamento.lancamentos.get(posicaoAux).getCategoria();
            editTextDescricao.setText(Lancamento.lancamentos.get(posicaoAux).getDescricao());
            editTextData.setText(out.format(Lancamento.lancamentos.get(posicaoAux).getData()));
            editTextValor.setText(Float.valueOf(Lancamento.lancamentos.get(posicaoAux).getValor()).toString());
            if (Lancamento.lancamentos.get(posicaoAux).getParcelas() == 0)
                editTextParcelas.setText("");
            else
                editTextParcelas.setText(Integer.valueOf(Lancamento.lancamentos.get(posicaoAux).getParcelas()).toString());
            btnSelCat.setText(categoria);
            if (tipo.equals("Receita")) {
                rdbReceita.toggle();
            }
            if (tipo.equals("Despesa")) {
                rdbDespesa.toggle();
            }
        }
    }
}