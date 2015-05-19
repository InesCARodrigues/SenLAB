import java.util.Arrays;
import java.util.Calendar;

public class SensorAnalogico extends Sensores{

    private static final int MAX = 120;
    private int[] valor = new int[MAX];


    // Construtor
    SensorAnalogico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,int[] valor) {
        super(numLeitura,nomePaciente, nomeSensor,data);
        this.valor = valor;
    }

    // SETTERS

    public void setValor(int[] valor) {
        this.valor = valor;
    }

    // GETTERS

    public int[] getValor() {
        return valor;
    }

    // TO STRING

    @Override
    public String toString() {
        return  super.toString() +
                "SensorAnalogico{" +
                "valor=" + Arrays.toString(valor) +
                '}';
    }


    /************* METODOS DA CLASS ******************/

    public boolean saudavel(){

        /**
         *
         *  Cada sensor calcula de forma diferente se o Paciente é saudavel ou não. Mas todos são sensores nao electricos
         *  Assim sendo foram criadas 2 funções distintas: um algoritmo de resolução para cada sensor
         *
         *  Mais eficiente do que ter 2 classes distintas (uma por sensor)
         *
         */

        if(this.getNomeSensor().toUpperCase().equals("ACELEROMETRO"))
            return algoritmoRespi();

        else if(this.getNomeSensor().toUpperCase().equals("RESPIRATORIO"))
            return algoritmoAcel();

        else{
            System.out.println("ERRO : Nome do sensor nao reconhecido");
            return false;
        }

    }


    public boolean algoritmoRespi()
    {
        // WIP
        return false;

    }

    public boolean algoritmoAcel()
    {
        int aux = 0;
        int dif2val = 0;

        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                if (valor[i] < valor[j]) {
                    aux = valor[i];
                    valor[i] = valor[j];
                    valor[j] = aux;
                }
            }
        }
        for (int k = 0; k < MAX; k++) {
            dif2val = valor[k+1]-valor[k];
        }

        return dif2val <= 5;

    }




}