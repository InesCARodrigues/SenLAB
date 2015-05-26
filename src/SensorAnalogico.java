import java.util.Arrays;
import java.util.Calendar;

public class SensorAnalogico extends Sensores{

    private static final int MAX = 120;
    private int[] valor = new int[MAX];


    // Construtor
    SensorAnalogico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,int[] valor) {
        super(numLeitura,nomePaciente, nomeSensor,data);

        for(int i = 0; i < 120; i++){
            this.valor[i] = valor[i];
        }
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


    public String toString2(){
        return super.toString() + saudavel();
    }

    /************* METODOS DA CLASS ******************/

    public String saudavel(){

        /**
         *
         *  Cada sensor calcula de forma diferente se o Paciente é saudavel ou não. Mas todos são sensores nao electricos
         *  Assim sendo foram criadas 2 funções distintas: um algoritmo de resolução para cada sensor
         *
         *  Mais eficiente do que ter 2 classes distintas (uma por sensor)
         *
         */


        if(this.getNomeSensor().toUpperCase().equals("ACELEROMETRO"))
            if(algoritmoRespi())
                return "Saudavel";
            else
                return "Doente";

        else if(this.getNomeSensor().toUpperCase().equals("RESPIRATORIO"))
            if(algoritmoAcel())
                return "Saudavel";
            else
                return "Doente";

        else{
            System.out.println("ERRO : Nome do sensor nao reconhecido");
            return "Doente";
        }

    }


    public boolean algoritmoRespi()
    {
        // Um ciclo respiratorio:
            // Irei considerar como ciclo uma sequencia de 1's seguida de 0's

            // 111111110000000: 8 uns e 7 zeros -> ciclo completo de 15 segundos
            // Em media, a cada 20 segundos, não pode ter mais de 2 ciclos ou menos que 0,7 ciclos.

            // Algoritmo:
                // Conta o numero de periodos de 20 segundos.
                // Conta o numero de ciclos
                // Vê se, em media, nao ha mais de 2 ciclos ou menos de 0.7 ciclos por cada 20 segundos




        int nPeriodos;                        // Guarda o numero de ciclos de 20 segundos
        int nCiclos = 0;                          // Guarda o numero de periodos (1's seguidos de 0's..)
        int flag = 0;                         // Esta flag vai ajudar a detectar onde começa e onde termina cada ciclo
        float valorMedioFinal;                 // Guarda o que será o valor medio final (numero de ciclos a dividir por numero de periodos de 20 segundos)


        nPeriodos = valor.length / 20;


        for( int i = 0; i < valor.length;i++){  // Este ciclo serve para contar o numero de ciclos presentes nos dados detectados pelo sensor respiratorio

            if(flag == 0 && valor[i] == 1){
                flag = 1;                       // Detectou o inicio de um ciclo
                nCiclos++;                      // Adiciona esse ciclo ao numero de ciclos total
            }
            else if(flag == 1 && valor[i] == 0){
                flag = 0;                       // Prepara a flag para detectar o inicio do proximo ciclo
            }

        }


        valorMedioFinal = nCiclos / nPeriodos;

        if(valorMedioFinal > 2 || valorMedioFinal < 0.7){
            return false;                                   // está doente
        }
        else
            return true;                                   // está saudavel

    }




    public boolean algoritmoAcel()
    {
        int val1, val2;
        Arrays.sort(valor);

        for(int i = 1; i < valor.length;i++){

            val1 = valor[i-1];
            val2 = valor[i];

            if(Math.abs(val2 - val1) > 5){              //Math.abs() devolve o modulo do numero -> para assegurar que é sempre positivo
                return true;                            // Se a diferença entre 2 valores for maior que 5 é porque está doente
            }

        }

        return false;                                  // Se chegou até aqui sem retornar nada é porque está saudavel

    }




}