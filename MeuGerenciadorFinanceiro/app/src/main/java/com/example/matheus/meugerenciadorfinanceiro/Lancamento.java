package com.example.matheus.meugerenciadorfinanceiro;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aluno on 22/09/2016.
 */
public class Lancamento implements Parcelable{


    public static ArrayList<Lancamento> lancamentos = new ArrayList<Lancamento>();
    private int codigo;
    private String descricao;
    private Date data;
    private float valor;
    private int parcelas;
    private String tipo;
    private String categoria;

    public static final Creator <Lancamento> CREATOR = new Creator<Lancamento>() {
        @Override
        public Lancamento createFromParcel(Parcel parcel) {
            return new Lancamento(parcel);
        }
        @Override
        public Lancamento[] newArray(int i) {
            return new Lancamento[i];
        }
    };
    public Lancamento (int controle, String descricao, Date data, float valor, int parcelas, String tipo, String categoria) {
        this.codigo = controle;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.parcelas = parcelas;
        this.tipo = tipo;
        this.categoria = categoria;
    }
    private Lancamento(Parcel parcel) {
        this.codigo = parcel.readInt();
        this.descricao = parcel.readString();
        this.data = (java.util.Date) parcel.readSerializable();
        this.valor = parcel.readFloat();
        this.parcelas = parcel.readInt();
        this.tipo = parcel.readString();
        this.categoria = parcel.readString();
    }
    public int describeContents() {return 0;}
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeInt(this.codigo);
        dest.writeString(this.descricao);
        dest.writeSerializable(this.data);
        dest.writeFloat(this.valor);
        dest.writeInt(this.parcelas);
        dest.writeString(this.tipo);
        dest.writeString(this.categoria);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data){
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /*public static Date formataData(String data) throws Exception {
        if (data == null || data.equals(""))
            return null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            date = (java.util.Date)formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }*/
}
