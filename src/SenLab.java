import sun.management.Sensor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;


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
    private Leituras lab = new Leituras();


    //CONSTRUTORES
    SenLab(){};


    //SETTERS
    public void setPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public void setEletricos(ArrayList<SensorEletrico> eletricos) {
        this.eletricos = eletricos;
    }

    public void setAnalogicos(ArrayList<SensorAnalogico> analogicos) {
        this.analogicos = analogicos;
    }


    //GETTERS
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public ArrayList<SensorEletrico> getEletricos() {
        return eletricos;
    }

    public ArrayList<SensorAnalogico> getAnalogicos() {
        return analogicos;
    }

    /************* METODOS DA CLASS ******************/

    public void carregaLab() throws IOException, ClassNotFoundException{


        ArrayList<Object> informacao = new ArrayList<Object>();

        if(lab.abreLeitura("src/lab.db")){                                                        // So tenta carergar se o ficheiro lab.db já existir
            informacao = lab.devolveInfo();

            this.setPacientes((ArrayList<Paciente>)informacao.get(0));
            this.setEletricos((ArrayList<SensorEletrico>) informacao.get(1));
            this.setAnalogicos((ArrayList<SensorAnalogico>) informacao.get(2));

            lab.fechaLeitura();                                                                  // Metodo para fechar o modo de leitura
        }
        else{
            System.out.println("DEBUG: nao existe o ficheiro lab.db");
            if(!(lab.criaLab()))                                                                // Tenta criar o ficheiro para o caso de este nao existir
                System.out.println("Atencao!!! Problema ao tentar criar o lab.bd ");            // Esta mensagem vai aparecer se o programa nao conseguir criar o lab.db


        }

        System.out.println("Carreguei os pacientes");


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
        System.out.println(idExames.toString());

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

           // System.out.println(pacientes.get(i).getNome()+" vs "+nome); --> DEBUG

            if(this.pacientes.get(i).getNome().equalsIgnoreCase(nome)){
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

        System.out.println("Exames:\n(Data,Codigo,Indica Problemas)");

       for (int j = 0; j < idExames.size(); j++){
            this.imprimeSensorAnalogico(idExames.get(j),0);
            this.imprimeSensorElectrico(idExames.get(j),0);
        }



        System.out.println("\nTotal de leitura(s): " + pacientes.get(i).getNumLeituras() + "\n---------------------------------");
    }


    /*************************************************************/
    /************* METODOS DA LISTA DE SENSORES ELETRICOS ******************/

    public void inserirSensorEletrico(SensorEletrico sensor){
        eletricos.add(sensor);
    }

    public void imprimeSensorElectrico(int numLeitura,int tipo){

        // Imprime todos os sensores electricos com o numero de leitura que recebe como argumento
        // Esta funcao é chamada no Ponto 5.9 do Projecto


        // Tipo indica o tipo de "toString" que vai ser chamado
        // Muitas vezes ao longo do projecto vamos ter que imprimir sensores. Mas o formato do output varia. Convem ter um toString para casa caso especifico :)

        int i,leituras = 0;

        for(i = 0; i < this.eletricos.size();i++){
            if(this.eletricos.get(i).getNumLeitura() == numLeitura){
                if(tipo == 0)
                    System.out.println(this.eletricos.get(i).toString2());
                else if(tipo == 1)
                    System.out.print(this.eletricos.get(i).getNomeSensor() + " ");
                leituras++;
            }

        }
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
            if(eletricos.get(i).getNumLeitura() == idExame){
                eletricos.remove(i);
                i--;
            }

    }


    /*************************************************************/


    /************* METODOS DA LISTA DE SENSORES ANALOGICOS ******************/

    public void inserirSensorAnalogico(SensorAnalogico sensor){
        analogicos.add(sensor);
    }


    public void imprimeSensorAnalogico(int numLeitura, int tipo){

        // Imprime todos os sensores electricos com o numero de leitura que recebe como argumento
        // Esta funcao é chamada no Ponto 5.9 do Projecto

        int i,leituras = 0;

        for(i = 0; i < this.analogicos.size();i++){
            if(this.analogicos.get(i).getNumLeitura() == numLeitura){
                if(tipo == 0)
                    System.out.println(this.analogicos.get(i).toString2());
                else if(tipo == 1)
                    System.out.print(this.analogicos.get(i).getNomeSensor() + " ");
                leituras++;
            }

        }


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

        System.out.println("Num leitura: " + numLeitura);

        for(i = 0; i < analogicos.size();i++){
            System.out.println("analogico: " + analogicos.get(i).getNumLeitura());
            if(analogicos.get(i).getNumLeitura() == numLeitura){
                analogicos.remove(i);
                i--;
            }


        }

    }


    /*************************************************************/




    /************ METODOS DE CARREGAMENTO DE FICHEIROS *********/


    public void carregaNovoPaciente(ArrayList<String> ficheiro,int numExame){


        /**
         *
         * O contrutor do Paciente é: public Paciente(String nome,int id,int idade,char sexo, boolean tomaMedicacao,boolean fuma,int idExames)
         *
         * Todos estes dados estão na Array List "ficheiro" (que acabamos de receber como argumento)
         */


        Boolean fuma;
        Boolean medicacao;
        int id, idade;
        char sexo;

        // As proximas linhas de codigo sao para converter as Strings carregadas do ficheiro para o formato correcto
        // exemplo: a idade nao deve ser uma String mas sim um int

        id = Integer.parseInt(ficheiro.get(0));
        idade = Integer.parseInt(ficheiro.get(1));
        sexo = ficheiro.get(3).charAt(0);
        if(ficheiro.get(4).equals('S'))
            fuma = true;
        else
            fuma = false;

        if(ficheiro.get(5).equals('S'))
            medicacao = true;
        else
            medicacao = false;



        Paciente novo = new Paciente(ficheiro.get(2),id,idade,sexo,medicacao,fuma,numExame);            // Cria o novo paciente
       // System.out.println(novo.toString());     --> DEBUG
        this.pacientes.add(novo);                                                                       // Adiciona o novo cliente à lista de clientes do sistema




        // Mensagem de confirmação
        System.out.println("Ficha de paciente "+ficheiro.get(2) +" introduzida no sistema\n"+
                            "ID: "+id+"\n"+
                            "Nome: "+ficheiro.get(2)+"\n"+
                            "Idade: "+idade+"\n"+
                            "Sexo: "+sexo+"\n"+
                            "Fuma: "+ficheiro.get(4)+"\n"+
                            "Toma medicamentos: "+ficheiro.get(5)+"\n\n"+
                            "Foi introduzida no sistema a leitura de "+ficheiro.get(6) + " com codigo "+numExame+"\n\n");

    }




    public void carregaPaciente(int index, ArrayList<String> ficheiro, int numExame){           // Recebe o index da posicao do Paciente e o numero do Exame em causa


        int numLeituras = pacientes.get(index).getNumLeituras();
        numLeituras++;
        pacientes.get(index).setNumLeituras(numLeituras);
        pacientes.get(index).adiconaIdExame(numExame);


        // Mensagem de confirmaçao
        System.out.println("Foi introduzido no sistema a aleitura de "+ficheiro.get(6) + " com codigo "+ numExame +
                            " na ficha do paciente "+ficheiro.get(2)+"\n");

    }



    public void carregaSensores(ArrayList<String> ficheiro,int numExame) {


        /**
         *
         *
         * O contrutor dos sensores electricos é: SensorEletrico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,float[] valor)
         * O contrutor dos sensores nao electricos é: SensorAnalogico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,int[] valor)
         *
         * Todos estes dados estão na Array List "ficheiro" (que acabamos de receber como argumento)
         *
         * 1º É preciso ver quantos sensores são no total
         * 2º É preciso ver que tipo de sensor cada um é
         * 3º É preciso carregar os valores correctos de cada um para um array
         *
         *
         */


        // Tratamento da data:
        String data = ficheiro.get(6);                                               // Sei que a data está na posicao 6 da lista que contem o ficheiro
        String dataSplit[] = data.split("/");                                        // Divido pelos "/" ficando com o dia, ano e mes isolados
        Calendar dataFinal = new GregorianCalendar();                                // Inicializa a variavel do tipo data
        dataFinal.set(Integer.parseInt(dataSplit[2]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[0]));        // Faz set à variavel do tipo data


        String nomeSensor;

        float[] valoresFloat = new float[120];
        int[] valoresInt = new int[120];

        int totalSensores = 0;
        int i = 7;                                                   // index da array list onde começam os sensores
        int skip;                                                   // Este int vai ser auxiliar quando for para guardar os valores registados pelos sensores
        char c;


        // Este ciclo for serve para contar o numero de sensores presentes no ficheiro carregado
        while(true)
        {
            c = ficheiro.get(i).charAt(0);                          // Carrega o primeiro index

            if (!(Character.isDigit(c))){                           // Ve se o char é digito ou nao
                i++;                                                //Se nao for digito é porque é um sensor
                totalSensores++;
            }
            else                                                    //Se for digito termina a contagem dos sensores
                break;
        }

       // System.out.println("Total de sensores: "+totalSensores); --> DEBUG

        for(i = 7; i < 7 + totalSensores; i++){

            nomeSensor = ficheiro.get(i);

            if(nomeSensor.equals("EMG") || nomeSensor.equals("ECG") || nomeSensor.equals("EOG")){
                // É electrico


                skip = i + totalSensores;
                for(int j = 0; j < 120; j++){               // carrega os valores do sensor

                   //System.out.println(ficheiro.get(skip));     --> DEBUG

                   valoresFloat[j] = Float.parseFloat(ficheiro.get(skip));
                   skip += totalSensores;
                }


                // Contrutor:  SensorEletrico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,float[] valor)
                SensorEletrico novo = new SensorEletrico(numExame,ficheiro.get(2),nomeSensor,dataFinal,valoresFloat);
                this.eletricos.add(novo);

              //  System.out.println(novo.toString());                  -- > DEBUG


            }
            else{
                //Nao é electrico

                if(nomeSensor.toUpperCase().startsWith("RESP"))                                 // Sensores analiticos, depois de carregados no sistema, ficam com problemas devido aos acentos
                                                                                                // "Respiratório" fica "RespiratÃ³rio"; É isso que vou corrigir aqui
                    nomeSensor = "Respiratorio";
                else
                    nomeSensor = "Acelerometro";


                skip = i + totalSensores;
                for(int j = 0; j < 120; j++){               // carrega os valores do sensor

                   //System.out.println(ficheiro.get(skip));     --> DEBUG

                   valoresInt[j] = Integer.parseInt(ficheiro.get(skip));
                   skip += totalSensores;
                }


                // Contrutor:  SensorEletrico(int numLeitura,String nomePaciente, String nomeSensor,Calendar data,float[] valor)
                SensorAnalogico novo = new SensorAnalogico(numExame,ficheiro.get(2),nomeSensor,dataFinal,valoresInt);
                this.analogicos.add(novo);

               // System.out.println(novo.toString());              -> DEBUG

            }
        }





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
           for (i = 0; i < this.analogicos.size(); i++) {
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
                   System.out.print(", " + this.eletricos.get(i).getNomeSensor() + "(" + aux + ")");
               else
                   System.out.print(this.eletricos.get(i).getNomeSensor() + "(" + aux + ")");

               aux++;

           }

       }

        for(i = 0; i < this.analogicos.size();i++) {

            if (this.analogicos.get(i).getNumLeitura() == numLeitura) {

                nomes.add(this.analogicos.get(i).getNomeSensor());

                if(aux != 1)
                    System.out.print(", " + this.analogicos.get(i).getNomeSensor() + "(" + aux + ")");
                else
                    System.out.print(this.analogicos.get(i).getNomeSensor() + "(" + aux + ")");
               aux++;
            }

        }

       if(chamadas == 0)
           System.out.println("\nQual o numero do sensor que pretende apaga?");
       else
            System.out.println("\nPretende apagar mais algum sensor?");

       System.out.println("(caso nenhum, introduzir 0)");

       return nomes;

   }




    public int ponto5_5(int numLeitura){

        // Encontra a data e o utilizar que fez a leitura
        // Usado no ponto 5.5

        int i;


        for(i = 0; i < this.eletricos.size();i++) {

            if (this.eletricos.get(i).getNumLeitura() == numLeitura) {
                System.out.println("Apagar leitura " + numLeitura + " de " + this.eletricos.get(i).dataFormatada() +
                        " pertencente a " + this.eletricos.get(i).getNomePaciente() + "?[S/N]");
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
        String nomePaciente ="";

        System.out.println("numLeitura: "+numLeitura);

        for(i = 0; i < eletricos.size();i++){
            if(eletricos.get(i).getNumLeitura() == numLeitura){

                dataTemp = eletricos.get(i).dataFormatada();
                //System.out.println("Vou remover o:" + eletricos.get(i).getNumLeitura()); --> DEBUG
                nomePaciente = eletricos.get(i).getNomePaciente();
                eletricos.remove(eletricos.get(i));

                i--;
            }
        }

        for(i = 0; i < this.analogicos.size();i++){
            if(analogicos.get(i).getNumLeitura() == numLeitura){

                dataTemp = analogicos.get(i).dataFormatada();
                //System.out.println("Vou remover o:" + analogicos.get(i).getNumLeitura()); --> DEBUG
                nomePaciente = analogicos.get(i).getNomePaciente();
                analogicos.remove(i);

                i--;
            }

        }



        // Remove a leitura do numero de leituras do paciente
        for(i = 0; i < pacientes.size();i++){
          //  System.out.println(pacientes.get(i).getNome() + " vs " + nomePaciente); --> DEBUG
            if(pacientes.get(i).getNome().toUpperCase().equals(nomePaciente.toUpperCase())) {
                System.out.println("Nome: "+nomePaciente);
                pacientes.get(i).removeIdExame(numLeitura);
                pacientes.get(i).removeLeitura();

                break;
            }
        }

        System.out.println("Leitura "+ numLeitura + " de "+ dataTemp + "apagado do sistema");
    }




    public void carregaNovaLeitura(String filename){

        // Metodo para o ponto 5.3 do projecto

        int sucesso;
        int numExame;
        int flag = 0;                                                           // Esta flag irá ser 0 se o Paciente for novo e 1 se ele já existir no sistema
        ArrayList<String> aux = new ArrayList<String>();

        Leituras teste = new Leituras();
        sucesso = teste.carregaExame(filename);

        if(sucesso == 1){

            numExame = teste.getNumExame();
            aux = teste.getListaExame();


            // Verifica se o nome do Paciente já existe
                // Se existir adiciona esta leitura na sua ficha
                // Se nao existir cria-o no sistema.


            for(int i = 0; i < pacientes.size();i++){
             // System.out.println(pacientes.get(i).getNome() + " vs "+ aux.get(2));                      --> DEBUG
                if(pacientes.get(i).getNome().equals(aux.get(2))){
                    carregaPaciente(i,aux,teste.getNumExame());
                    flag = 1;
                    break;
                }

            }

            if(flag == 0)
                carregaNovoPaciente(aux,teste.getNumExame());                       // Apenas carrega como NOVO paciente se a flag for 0, ie, o paciente nao tiver sido detetado no sistema

            carregaSensores(aux,teste.getNumExame());                               // Os sensores tem sempre que ser carregado, seja um paciente novo ou nao




        }

    }



    public void listaDoentes(){

        // Ponto 5.11 do Projecto

        int totalDoentes = 0;
        int flag = 0;           // Indica se foi detectada uma patologia em algum exame ou nao
        ArrayList <Integer> idExames = new ArrayList<Integer>();
        ArrayList <Integer> idPatologias = new ArrayList<Integer>();            // vai guardar os codigos dos exames onde foram detectadas patologias
        String estado = "";

        System.out.println("------------------------\n(ID,nome,Sexo,Idade,Fuma,Medicamentos,Codigos das leituras com indicares de problemas)\n------------------------");

        for(int i = 0; i < pacientes.size();i++){

            idPatologias.clear();
            flag = 0;

            idExames = pacientes.get(i).getIdExames();

            for(int j = 0; j < idExames.size();j++){
                if(estadoSaude(pacientes.get(i),idExames.get(j)).equals("Detectada patologia")){
                    flag = 1;           // Foi detectada uma doença num exame deste paciente!

                    idPatologias.add(idExames.get(j));          // Guarda o numero deste exame
                }
            }

            if(flag == 1){              // Se chegar até aqui com a flag a 1 é porque o paciente tem pelo menos uma patologia

                System.out.println(pacientes.get(i).getId() + "  |  " + pacientes.get(i).getNome() + "  |  " + pacientes.get(i).getSexo()+
                                    "  |  " + pacientes.get(i).getIdade() + "  |  " + pacientes.get(i).getFuma() + "  |  " + pacientes.get(i).getTomaMedicacao());


                for (int k = 0; k < idPatologias.size();k++){
                    System.out.print(" "+idPatologias.get(k));
                }

                System.out.println("\n");

                totalDoentes++;
            }
        }

        System.out.println("Total Doentes(s): "+totalDoentes);
        System.out.println("------------------------");


    }

    public void listaSaudaveis(){

        // ponto 5.10 do Projecto

        int totalSaudaveis = 0;
        int flag = 0;           // Indica se foi detectada uma patologia em algum exame ou nao
        ArrayList <Integer> idExames = new ArrayList<Integer>();
        String estado = "";

        System.out.println("------------------------\n(ID,nome,Sexo,Idade,Fuma,Medicamentos,Leituras");

        for(int i = 0; i < pacientes.size();i++){
            flag = 0;

            idExames = pacientes.get(i).getIdExames();

            for(int j = 0; j < idExames.size();j++){
                if(estadoSaude(pacientes.get(i),idExames.get(j)).equals("Detectada patologia")){
                    flag = 1;           // Foi detectada uma doença num exame deste paciente!
                    break;
                }
            }

            if(flag == 0){              // Se chegar até aqui com a flag a zero é porque é saudavel
                System.out.println(pacientes.get(i).getId() + "  |  " + pacientes.get(i).getNome() + "  |  " + pacientes.get(i).getSexo() +
                        "  |  " + pacientes.get(i).getIdade() + "  |  " + pacientes.get(i).getFuma() + "  |  " + pacientes.get(i).getTomaMedicacao() +
                        "  |  " + pacientes.get(i).getNumLeituras());

                totalSaudaveis++;
            }
        }

        System.out.println("\nTotal Saudaveis(s): "+totalSaudaveis);
        System.out.println("------------------------");

    }

    public String estadoSaude(Paciente paciente, int num){

        /*
            Função devolve o estado de saudade do Paciente.

            Se algum dos exames do paciente der sinal de patologia entao o paciente é considerado doente
            So se todos os exames derem sinal de Saudavel então é considerado saudavel :)

            Algoritmo:
                1) Procura o Id do Exame
                2) Procura todos os exames com esse ID e o seu valor
                3) Analisa os resultados

         */


        for(int j = 0; j < eletricos.size();j++){
            if(eletricos.get(j).getNumLeitura() == num){
                if(eletricos.get(j).saudavel().equals("Doente")){
                    return "Detectada patologia";
                }

            }
        }


        for(int j = 0; j < analogicos.size();j++){
            if(analogicos.get(j).getNumLeitura() == num){
                if(analogicos.get(j).saudavel().equals("Doente")){
                    return "Detectada patologia";
                }
            }
        }


        return "Saudavel";
    }


    public String getDataFormatada(int idExame){

        for(int k = 0; k < eletricos.size();k++){
            if(eletricos.get(k).getNumLeitura() == idExame){
                return eletricos.get(k).dataFormatada();
            }
        }

        for(int k = 0; k < analogicos.size();k++){
            if(analogicos.get(k).getNumLeitura() == idExame){
                return analogicos.get(k).dataFormatada();
            }
        }

        return "erro ao procurar data";

    }



    public void mostraDadosExames(PrintWriter relatorio,ArrayList<String> mostraExames, int dispoAnalogicos,int dispoElectricos,int accao){


        // esta funcao imprime tanto para o ecra como para o ficheiro txt
        // tudo depende da "accao"
            // Se accao = 0 -> imprime no ecra
            // Se accao = 1 -> imprime para o ficheiro

        int numExame,i;
        int aux = 0,temp;
        int totalElectricos = Integer.parseInt(mostraExames.get(8));
        int totalSensores = Integer.parseInt(mostraExames.get(7));
        int totalAnalogicos = totalSensores - totalElectricos;


        if(accao == 0)
            System.out.print("Time \\ Sensor ");
        else
            relatorio.print("Time \\ Sensor ");

        if(dispoAnalogicos == 1 && dispoElectricos == 1){   // É para mostrar os dispositivos electricos e os nao electricos

              // Imprime os nomes dos exames
              for(i = 0; i < totalSensores;i++){
                  if(accao == 0)
                    System.out.print(" "+mostraExames.get(9+i));
                  else
                      relatorio.print(" "+mostraExames.get(9+i));
              }



               if(accao == 0)
                   System.out.println("");
               else
                   relatorio.println("");




              for(i = 0; i < 120; i++){

                  temp  = 9 + totalSensores + i;
                  aux = 0;

                  if(accao == 0)
                    System.out.print(""+i);
                  else
                    relatorio.print(""+i);

                  for(int j = 0; j < totalSensores;j++){
                      if(accao== 0)
                          System.out.print("   "+mostraExames.get(temp+(aux * 120)));
                      else
                          relatorio.print("   "+mostraExames.get(temp+(aux * 120)));
                      aux++;
                  }

                  if(accao == 0)
                      System.out.println("");
                  else
                      relatorio.println("");
              }

        }
        else if(dispoElectricos == 1 && dispoAnalogicos == 0){                      // Apenas se pretende mostrar os dispositivos electricos


            // Imprime os nomes dos exames
            for(i = 0; i < totalElectricos;i++){
                System.out.print(" "+mostraExames.get(9+i));
            }

            if(accao == 0)
                System.out.println("");
            else
                relatorio.println("");


            for(i = 0; i < 120; i++){

                temp  = 9 + totalSensores + i;
                aux = 0;


                if(accao == 0)
                    System.out.print(""+i);
                else
                    relatorio.print(""+i);


                for(int j = 0; j < totalElectricos;j++){
                    if(accao == 0)
                        System.out.print("   "+mostraExames.get(temp+(aux * 120)));
                    else
                        relatorio.print("   "+mostraExames.get(temp+(aux * 120)));
                    aux++;
                }

                if(accao == 0)
                    System.out.println("");
                else
                    relatorio.println("");

            }
        }
        else if(dispoElectricos == 0 && dispoAnalogicos == 1){


              // Imprime os nomes dos exames
              for(i = 0; i < totalAnalogicos ;i++){
                  if(accao == 0)
                      System.out.print(" "+mostraExames.get(9+totalElectricos+i));
                  else
                      relatorio.print(" "+mostraExames.get(9+totalElectricos+i));
              }


            if(accao == 0)
                System.out.println("");
            else
                relatorio.println("");


              for(i = 0; i < 120; i++){
                  temp  = 9 + totalSensores + i + (totalElectricos * 120);
                  aux = 0;


                  if(accao == 0)
                      System.out.print(""+i);
                  else
                      relatorio.print(""+i);



                  for(int j = 0; j < totalAnalogicos;j++){
                      if(accao == 0)
                          System.out.print("   "+mostraExames.get(temp+(aux * 120)));
                      else
                          relatorio.print("   "+mostraExames.get(temp+(aux * 120)));
                      aux++;
                  }


                  if(accao == 0)
                      System.out.println("");
                  else
                      relatorio.println("");

              }

        }



    }


    public ArrayList<String> mostraExameRES(int numExame){

        // Esta função mostra os detalhes de um dado exame
        // Continuação da resolucao do ponto 5.13 do projecto

        // O objectivo deste metodo é colocar todos os dados que depois devem ser colocados no relatorio.txt numa arrayList


        ArrayList<String> retorno = new ArrayList<String>();
        ArrayList<String> nomesSensores = new ArrayList<String>();
        ArrayList<Float> valoresElectricos = new ArrayList<Float>();
        ArrayList<Integer> valoresAnalogicos = new ArrayList<Integer>();


        float[] temp = new float[120];
        int[] temp2 = new int[120];


        int i;
        String data="";
        String nomePaciente = "";

        int canaisMedidos = 0;              // Variavel para saber quantos sensores no TOTAL foram medidos
        int canaisElectricos = 0;           // Variavel para saber quantos sensores sao electricos

        int saudavel = 1;                   // 1 -> saudavel; 0 -> doente

        String aux;

        /*

        Como ver se é saudavel:
            a variavel saudavel começa como 1 (ie, paciente considerado saudavel).
            Se encontra um exame que dá saudavel

         */

        for(i = 0; i < eletricos.size();i++) {
            if (eletricos.get(i).getNumLeitura() == numExame) {
                canaisMedidos++;
                canaisElectricos++;
                data = getDataFormatada(numExame);
                nomePaciente =eletricos.get(i).getNomePaciente();


                temp = eletricos.get(i).getValor();


                for(int j = 0; j < 120; j++){
                    valoresElectricos.add(temp[j]);
                }

                nomesSensores.add(eletricos.get(i).getNomeSensor());

                if (eletricos.get(i).saudavel().equals("Doente")) {
                    saudavel = 0;
                }
            }
        }




         for(i = 0 ; i < analogicos.size();i++){
             if(analogicos.get(i).getNumLeitura() == numExame){

                 canaisMedidos++;
                 data = getDataFormatada(numExame);
                 nomePaciente = analogicos.get(i).getNomePaciente();

                 nomesSensores.add(analogicos.get(i).getNomeSensor());

                 temp2 = analogicos.get(i).getValor();

                 for(int j = 0; j < 120; j++){
                     valoresAnalogicos.add(temp2[j]);
                 }

                 if(analogicos.get(i).saudavel().equals("Doente")){
                     saudavel = 0;
                 }
             }
         }





        retorno.add(""+numExame);
        retorno.add(data);

        if(saudavel == 1)
            retorno.add("Saudavel");
        else
            retorno.add("Detectada patologia");


        // Encontra detalhes sobre o Paciente a quem pertence o exame
        for(i = 0; i < pacientes.size();i++){
            if(pacientes.get(i).getNome().toLowerCase().equals(nomePaciente.toLowerCase())){
                retorno.add(nomePaciente);
                retorno.add(""+pacientes.get(i).getId());
                retorno.add(""+pacientes.get(i).getIdade());
                retorno.add(""+pacientes.get(i).getSexo());
            }
        }

        retorno.add(""+canaisMedidos);                  // Agora sei que na posição 7 do array tenho o total de canais medidos
        retorno.add(""+canaisElectricos);               // Posicao 8 guarda a quantidade de sensores que sao electricos

        for(i = 0; i < nomesSensores.size();i++){
            retorno.add(nomesSensores.get(i));
        }


        // Este codigo vai colocar os valores dos sensores electricos todos na lista de retorno
        for(int j = 0;  j < valoresElectricos.size();j++){
           retorno.add(""+valoresElectricos.get(j));
        }

       // Este codigo vai colocar os valores dos sensores nao electricos todos na lista de retorno
       for(int j = 0;  j < valoresAnalogicos.size();j++){
          retorno.add(""+valoresAnalogicos.get(j));
       }

        return retorno;

    }



    public ArrayList<String> mostraExames(String nomePaciente){


        // Esta função mostra os detalhes dos exames de um dado paciente
        // Usada no ponto 5.12 do Projecto

        String estadoSaudade = "";                                        // Nesta variavel irá ser armazenado o estado de saudade do paciente
        ArrayList<Integer> aux = new ArrayList<Integer>();
        String data = "";
        ArrayList<String> retorno = new ArrayList<String>();

        System.out.println("Exames:\n(Data, Codigo, Indica Problemas )");

        for(int i = 0; i < pacientes.size();i++){
            if(pacientes.get(i).getNome().toUpperCase().equals(nomePaciente.toUpperCase())){        // Se for o paciente em causa
                int idade = pacientes.get(i).getIdade();
                aux = pacientes.get(i).getIdExames();

                for(int j = 0; j < aux.size();j++) {
                    estadoSaudade = estadoSaude(pacientes.get(i), aux.get(j));                    // Optem o estado de saude (saudavel ou com patologias)
                    data = getDataFormatada(aux.get(j));                                         // Optem a data
                    System.out.println("\n(" + (j + 1) + ") " + data + " " + aux.get(j) + " " + estadoSaudade + "\ncanais medidos: ");


                    // Guarda valores na Array List que vai devolver depois e que vai ser usada para fazer o "relatorio.txt"
                    retorno.add("" + aux.get(j));
                    retorno.add("" + estadoSaudade);
                    retorno.add(""+data);
                    retorno.add(""+idade);


                    // Procura quais os sensores electricos usados
                    for(int k = 0; k < eletricos.size(); k++){
                        if(eletricos.get(k).getNumLeitura() == aux.get(j)){
                            retorno.add(eletricos.get(k).getNomeSensor());
                            System.out.print(eletricos.get(k).getNomeSensor()+ " ");
                        }
                    }

                    // Procura quais os sensores analogicos usados
                    for(int k = 0; k < analogicos.size(); k++){
                        if(analogicos.get(k).getNumLeitura() == aux.get(j)){
                            retorno.add(analogicos.get(k).getNomeSensor());
                            System.out.print(analogicos.get(k).getNomeSensor()+ " ");
                        }
                    }

                }
            }
        }



        return retorno;


    }


    /*********************************************************************/


    /***************   GRAVAÇAO DO PROJECTO NO FICHEIRO LAB.BD ***********/


    public void gravaProjecto(SenLab gestor) throws IOException, ClassNotFoundException{

        ArrayList<Object> informacao = new ArrayList<Object>();
        informacao.add(this.getPacientes());
        informacao.add(this.getEletricos());
        informacao.add(this.getAnalogicos());

        lab.guardaInfo(informacao);

    }

    /*********************************************************************/




}
