import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SensorEletrico extends Sensores {

    private static final int MAX = 120;
    private float[] valor = new float[MAX];                         // Os valores dos sensores Eletricos sao decimais



    SensorEletrico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,float[] valor) {
        super(numLeitura,nomePaciente, nomeSensor,data);
        for(int i = 0; i < 120; i++){
            this.valor[i] = valor[i];
        }

    }



    //SETTERS
    public void setValor(float[] valor) {
        this.valor = valor;
    }

    // GETTERS
    public float[] getValor() {
        return valor;
    }



    //TO STRING - NOTA: Vou fazer vários toString uma vez que o enunciado pede o output das classes em vários formatos

    @Override
    public String toString() {
        return  super.toString() +
                "SensorEletrico{" +
                "valor=" + Arrays.toString(valor) +
                '}';
    }

    public String toString2(){
            return super.toString() + saudavel();
    }


    /************* METODOS DA CLASS ******************/

    public String saudavel(){

        /**
         *
         *  Cada sensor calcula de forma diferente se o Paciente é saudavel ou não. Mas todos são sensores electricos
         *  Assim sendo foram criadas 3 funções distintas: um algoritmo de resolução para cada sensor
         *
         *  Mais eficiente do que ter 3 classes distintas (uma por sensor) :)
         *
         */

        if(this.getNomeSensor().toUpperCase().equals("ECG"))
            if(algoritmoECG())
                return "Saudavel";
            else
                return "Doente";

        else if (this.getNomeSensor().toUpperCase().equals("EOG"))
            if(algoritmoEOG())
                return "Saudavel";
            else
                return "Doente";

        else if(this.getNomeSensor().toUpperCase().equals("EMG")){
            if(algoritmoEMG())
                return "Saudavel";
            else
                return "Doente";
        }


        else{
            System.out.println("ERRO : Nome do sensor nao reconhecido");
            return "";
        }

    }


    public boolean algoritmoECG()
    {
        float soma = 0;

        for (int i = 0; i < valor.length; i++) // Usou-se um for each loop.
            soma+= valor[i];

        if((soma/valor.length) >= 120 && (soma/valor.length) <= 200){
            return true;
        }

        else{
            return false;
        }


    }



    public boolean algoritmoEOG()
    {
       float difaoquad = 0;
        float soma = 0;

        for (int i = 0; i < MAX; i++){
            soma+= valor[i];
        }
        float media = soma/MAX;
        for (int f = 0; f < 120; f++){
            difaoquad += (valor[f] - media)*(valor[f] -  media);
        }
        float desviopadrao = difaoquad/(MAX - 1);
        return desviopadrao <= 5;
    }



    public boolean algoritmoEMG()
    {
        float soma = 0;

        for (int i = 0; i < MAX; i++)
            soma+= valor[i];
        return (soma/MAX) >= 22.5 && (soma/MAX) <= 27;
    }


}
