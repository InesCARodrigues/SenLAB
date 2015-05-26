import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;

public class Sensores implements Serializable{

    /**
     * Class Sensores
     *
     * Existem dois tipos de sensores: eletricos e nao eletricos
     * Ambos tem nome e um numero de leitura.
     *
     * Mas por terem também campos especificos (exemp: eletricos usam floats e os outros usam valores inteiros)
     * usa-se herança de classes para simplificar a resolução do problema.
     *
     */


    private int numLeitura;
    private String nomePaciente;                                        // Nome do Paciente a quem pretence a leitura do Sensor (esta variavel nao era obrigatoria mas vai dar jeito em vários pontos do projecto)
    private String nomeSensor;
    private Calendar data;



    //Construtores
    Sensores(int numLeitura,String nomePaciente, String nomeSensor,Calendar data){
        this.numLeitura = numLeitura;
        this.nomePaciente = nomePaciente;
        this.nomeSensor = nomeSensor;
        this.data = data;
    };


    //SETTERS

    public void setNumLeitura(int numLeitura) {
        this.numLeitura = numLeitura;
    }

    public void setNomeSensor(String nomeSensor) {
        this.nomeSensor = nomeSensor;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    //GETTERS

    public int getNumLeitura() {
        return numLeitura;
    }

    public String getNomeSensor() {
        return nomeSensor;
    }

    public Calendar getData() {
        return data;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    // TO STRING

    @Override
    public String toString() {
        return "\t"+ dataFormatada() +"\t|\t"+  numLeitura + "\t|\t"+  nomeSensor + "\t|\t";
    }


    /************* METODOS DA CLASS ******************/

    public String dataFormatada(){
        String aux;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy ");
        aux = sdf.format(this.data.getTime());
        return aux;
    }

}