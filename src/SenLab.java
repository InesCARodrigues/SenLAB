import sun.management.Sensor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class SenLab implements Serializable {

    /**
     * Esta class "mãe"
     *
     * É na class SenLab que é feita toda a gestão das outras classes, ie, é aqui que ocorre o management de todos os pacientes,
     * os seus exames e todas as outras opções
     *
     *
     * É também nesta class que é efectuado o carregamento de ficheiros para a memoria
     *
     */


    private ArrayList <Paciente> pacientes = new ArrayList <Paciente>();                                 // Array List com todos os pacientes do sistema
    private ArrayList <SensorEletrico> eletricos = new ArrayList<SensorEletrico>();                      // Array List com todas as leituras de sensores eletricos do sistema
    private ArrayList <SensorAnalogico> analogicos  = new ArrayList<SensorAnalogico>();                  // Array List com todas as leituras de sensores nao eletricos do sistema


    //CONSTRUTORES
    SenLab(){};


    //SETTERS - WIP

    //GETTERS - WIP


    /************* METODOS DA CLASS ******************/

    // METODO PARA DEBUG - "mini base de dados"
    public void baseDados(SenLab gestor)
    {

        /** ISTO É TUDO APENAS PARA DEBUG! :) No final é para apagar **/

        // Adiciona paciente
        Paciente a = new Paciente("Joao",1,32,1,1,'M',true,true,1234);
        Paciente c = new Paciente("Maria",1,32,1,1,'M',true,true,1233);

        this.inserirPaciente(a);
        this.inserirPaciente(c);
     //   this.listaPacientes();

      //  this.numeroPacientes();

        // Adiciona sensores electricos
        float[] array = new float[]{1,2,3};
        Calendar data1 = new GregorianCalendar();
        SensorEletrico b = new SensorEletrico(1234,"Joao", "ECG",data1,array);
        SensorEletrico d = new SensorEletrico(1233,"Maria", "ECG",data1,array);
        this.inserirSensorEletrico(b);
        this.inserirSensorEletrico(d);

      //  this.listaSensoresEletricos();

    }

    /************* METODOS DA LISTA DE PACIENTES ******************/

    public void inserirPaciente(Paciente user)
    {
        pacientes.add(user);
    }

    public void eliminaEntradas(Paciente paciente){

        // Funcao que elimina todas as leituras de sensores de um dado paciente

        ArrayList<Integer> idExames = new ArrayList<Integer>();
        int i = 0,aux;

        idExames = paciente.getIdExames();

        for (i = 0; i < idExames.size(); i++){
            this.removeSensorAnalogico(idExames.get(i));
            this.removeSensorElectrico(idExames.get(i));
        }
    }


    public void apagarPaciente(String nome){

        // Ponto 5.6 do Projecto

        int i;

        Scanner in = new Scanner(System.in);
        String com;


        for(i = 0; i < pacientes.size(); i++){
            if(pacientes.get(i).getNome().equals(nome)){
                System.out.println("Apagar paciente "+nome+"? [S/N]");
                 com = comando(in);

                if(com.toUpperCase().equals("S")){

                    eliminaEntradas(pacientes.get(i));                                // Funçao para remover toda a informacao relacionada com este paciente (leituras dos sensores, etc)
                    pacientes.remove(i);                                              // Remove o paciente da lista de pacientes do sistema
                    System.out.println("Paciente apagado do sistema");
                    return;                                                           // Força saida da função (pois ja fez tudo o que tinha a fazer)

                }
                else{
                    System.out.println("Paciente não apagado");
                    return;                                                           // Força saida da função (pois ja fez tudo o que tinha a fazer)
                }

            }
        }

        System.out.println("Paciente não encontrado no sistema");                    // Penso que isto nao é requisitado no enunciado, mas fica aqui como extra ;)

    }



    public void numeroPacientes(){

        // Ponto 5.7 do Projecto

        int i, leituras = 0;

        for(i = 0; i < pacientes.size();i++){
            leituras += pacientes.get(i).getNumLeituras();

        }

        System.out.println("O sistema tem " + pacientes.size() + " pacientes introduzidos e um total de " + leituras + " leituras");
    }



    public void listaPacientes()
    {
        // Ponto 5.8 do Projecto

        int i;

        System.out.println("\nPacientes no sistema\n---------------------------------");

        for(i = 0; i < pacientes.size();i++)
        {
            System.out.println(pacientes.get(i).toString());
        }



        if(i == 0)
            System.out.println("Nao ha pacientes");

        System.out.println("---------------------------------");

    }



    public void listaLeituras(String nome){

        // Ponto 5.9 do Projecto

        ArrayList<Integer> idExames = new ArrayList<Integer>();
        int i,aux = 0;

        for(i = 0; i < this.pacientes.size();i++){
            if(this.pacientes.get(i).getNome().equals(nome)){
                idExames = pacientes.get(i).getIdExames();
                aux = 1;                                                // Flag para indicar que encontrou um Paciente válido
                System.out.println("---------------------------------\n+" +
                                    "Paciente: \n"+pacientes.get(i).toString2()+
                                    "\n---------------------------------");
                break;
            }

        }

        if(aux == 0){                                                   // Caso o Paciente nao tenha sido encontrado na lista
            System.out.println("Paciente inexistente");
            return;                                                     // Return pois nao ha mais nada a tratar nesta funcao
        }


        for (i = 0; i < idExames.size(); i++){
          //  this.imprimeSensorAnalogico(idExames.get(i));  - POR TERMINAR
            this.imprimeSensorElectrico(idExames.get(i));
        }
    }


    /*************************************************************/



    /************* METODOS DA LISTA DE SENSORES ELETRICOS ******************/

    public void inserirSensorEletrico(SensorEletrico sensor){
        eletricos.add(sensor);
    }

    public void imprimeSensorElectrico(int numLeitura){

        // Imprime todos os sensores electricos com o numero de leitura que recebe como argumento
        // Esta funcao é chamada no Ponto 5.9 do Projecto

        int i,leituras = 0;

        System.out.println("Exames:\n(Data,Codigo,Indica Problemas)");

        for(i = 0; i < this.eletricos.size();i++){
            if(this.eletricos.get(i).getNumLeitura() == numLeitura){
                System.out.println(this.eletricos.get(i).toString2());
                leituras++;
            }

        }

        System.out.println("\nTotal de leitura(s): "+leituras+"\n---------------------------------");
    }


    public void listaSensoresEletricos()
    {
        int i = 0;

        for(i = 0; i < eletricos.size();i++)
        {
            System.out.println(eletricos.get(i).toString());
        }

        if(i == 0)
            System.out.println("Nao ha sensores eletricos no sistema");
    }



    public void removeSensorElectrico(int idExame){

        int i;

        for(i = 0; i < eletricos.size();i++)
            if(eletricos.get(i).getNumLeitura() == idExame)
                eletricos.remove(i);
    }


    /*************************************************************/


    /************* METODOS DA LISTA DE SENSORES ANALOGICOS ******************/

    public void inserirSensorAnalogico(SensorAnalogico sensor){
        analogicos.add(sensor);
    }



    public void listaSensoresAnalogicos()
    {
        int i = 0;

        for(i = 0; i < analogicos.size();i++)
        {
            System.out.println(analogicos.get(i).toString());
        }

        if(i == 0)
            System.out.println("Nao ha sensores eletricos no sistema");
    }



    public void removeSensorAnalogico(int numLeitura){

        int i;

        for(i = 0; i < analogicos.size();i++)
            if(analogicos.get(i).getNumLeitura() == numLeitura)
                analogicos.remove(i);
    }


    /*************************************************************/



    /************* METODOS AUXILIARES ******************/



    public static String comando(Scanner in) {
        String input;
        System.out.print(">> ");
        input = in.next();
        return input;
    }


    public void removeSensorEspecifico(String nome, int numLeitura){

        // Ponto 5.4  (continuaçao)
       int i;

       if(nome.toUpperCase().equals("EOG") || nome.toUpperCase().equals("EMG") ||nome.toUpperCase().equals("ECG") ) {

           for( i = 0; i < this.eletricos.size(); i++){
               if(this.eletricos.get(i).getNumLeitura() == numLeitura){
                  this.eletricos.remove(i);
                  System.out.println("Apagado sensor "+nome);
                  return;
               }
           }

       }
       else {
           for (i = 0; i < this.eletricos.size(); i++) {
               if (this.analogicos.get(i).getNumLeitura() == numLeitura) {
                   this.analogicos.remove(i);
                   System.out.println("Apagado sensor " + nome);
                   return;
               }
           }
       }
    }


   public ArrayList<String> ponto5_4(int numLeitura, int chamadas){

       // Encontra a data e o utilizar que fez a leitura
       // Usado no ponto 5.4

       // Argumento chamadas - > 0 se nao apagou um sensor antes, 1 se foi apagado um sensor antes

       int i,aux = 1;
       ArrayList <String> nomes = new ArrayList<String>();

       System.out.println("Sensores disponiveis: ");

       for(i = 0; i < this.eletricos.size();i++) {

           if (this.eletricos.get(i).getNumLeitura() == numLeitura) {

               nomes.add(this.eletricos.get(i).getNomeSensor());

               if (aux != 1)
                   System.out.println("," + this.eletricos.get(i).getNomeSensor() + "(" + aux + ")");
               else
                   System.out.println(this.eletricos.get(i).getNomeSensor() + "(" + aux + ")");

           }

       }

        for(i = 0; i < this.analogicos.size();i++) {

            if (this.analogicos.get(i).getNumLeitura() == numLeitura) {

                nomes.add(this.analogicos.get(i).getNomeSensor());

                if(aux != 1)
                    System.out.println("," + this.analogicos.get(i).getNomeSensor() + "(" + aux + ")");
                else
                    System.out.println(this.analogicos.get(i).getNomeSensor() + "(" + aux + ")");
               aux++;
            }

        }

       if(chamadas == 0)
           System.out.println("Qual o numero do sensor que pretende apaga?");
       else
            System.out.println("Pretende apagar mais algum sensor?");

       System.out.println("(caso nenhum, introduzir 0)");

       return nomes;

   }



    public int ponto5_5(int numLeitura){

        // Encontra a data e o utilizar que fez a leitura
        // Usado no ponto 5.5

        int i;


        for(i = 0; i < this.eletricos.size();i++) {

            if (this.eletricos.get(i).getNumLeitura() == numLeitura) {
                System.out.println("Apagar leitura "+numLeitura+ " de "+this.eletricos.get(i).dataFormatada()+
                                    " pertencente a "+ this.eletricos.get(i).getNomePaciente() + "?[S/N]");
                 return 1;
            }

        }

         for(i = 0; i < this.analogicos.size();i++) {

             if (this.analogicos.get(i).getNumLeitura() == numLeitura) {
                 System.out.println("Apagar leitura "+numLeitura+ " de "+this.analogicos.get(i).dataFormatada()+
                                     " pertencente a "+ this.analogicos.get(i).getNomePaciente() + "?[S/N]");
                 return 1;
             }

         }

        System.out.println("Leitura inexistente no Sistema");
        return 0;

    }


    public void eliminaNumLeitura(int numLeitura){
        // Elimina todos os sensores com o num de leitura passado em argumento
        // Usado no ponto 5.5

        int i;
        String dataTemp = "";

        for(i = 0; i < this.eletricos.size();i++){

            if(this.eletricos.get(i).getNumLeitura() == numLeitura){
                dataTemp = this.eletricos.get(i).dataFormatada();
                this.eletricos.remove(i);
            }

        }

        for(i = 0; i < this.analogicos.size();i++){

            if(this.analogicos.get(i).getNumLeitura() == numLeitura){
                dataTemp = this.eletricos.get(i).dataFormatada();
                this.analogicos.remove(i);
            }

        }

        System.out.println("Leitura "+ numLeitura + " de "+ dataTemp + "apagado do sistema");
    }

    /**************************************************/




}
