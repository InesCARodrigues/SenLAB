
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main {

    public static final String SAIR = "S";
    public static final String AJUDA = "A";
    public static final String NOVA_LEITURA = "PNE";
    public static final String APAGAR_SENSOR = "AS";
    public static final String APAGAR_LEITURA = "AL";
    public static final String APAGAR_PACIENTE = "AP";
    public static final String NUMERO_PACIENTES = "NP";
    public static final String LISTAR_PACIENTES = "LP";
    public static final String LISTAR_LEITURAS_PACIENTE = "LE";
    public static final String LISTAR_PACIENTES_SAUDAVEIS = "LS";
    public static final String LISTAR_PACIENTES_PATOLOGIAS = "LD";
    public static final String RELATORIO_HISTORICO = "REA";
    public static final String NOME = "Nome do utente: ";
    public static final String COMANDO_INVALIDO = "Comando invalido";
    public static final String SESSAO_TERMINADA = "Sessao terminada. Ate a proxima!";


    public static String comando(Scanner in) {
        String input;
        System.out.print(">> ");
        input = in.nextLine();
        return input;
    }


    public static void ajuda() {    //Menu ajuda para ver o menu


        // Nota: Impressão do Menu com apenas um chamada do System é mais eficiente :)

        System.out.println( "\n********************************\n"+
                            "* Ajuda\n" +
                            "********************************\n" +
                            "S      - Sair do programa\n" +
                            "G      - Gravar\n\n" +
                            "CNL - Carregar Novas Leituras (nome)\n\n" +
                            "AS - Apagar sensor por numero de leitura\n"+
                            "AL - Apagar Leitura\n"+
                            "AP - Apagar Paciente\n\n"+
                            "NP - Numero de Pacientes\n"+
                            "LP - Listar Pacientes\n"+
                            "LE - Listar Leituras feitas a um determinado paciente\n"+
                            "LS - Listar pacientes saudaveis\n"+
                            "LD - Listar pacientes com patologias\n\n"+
                            "REA - Produzir um relatorio do historico do paciente por datas\n"+
                            "RES - produzir um relatorio simples do paciente por datas" );


    }


    public static void relatorioExame(SenLab gestor){

        // Ponto 5.13 do Projecto

        /*

            Atenção
            -------

                Os pontos 5.12 e 5.13 (geração de relatorios txt) são provavelmente os pontos mais complexos do projecto!
                Recomendo uma leitura cuidada desta parte.

                Vão aparecer imensas linhas do tipo "mostraExames.get(X)"

                O mostraExames é um arrayList onde estão guardadas todas as informacoes necessarias a geracao do relatorio.
                E criado na funcao "mostraExameRES" da class SenLab.

                Este codigo apenas funciona para este projecto em especifico, E UM CODIGO ESTATICO! Ie. esta preparado para um e so um output possivel.
                Dai usar .get(CONSTANTE) -> porque sei exactamente o que vai la estar.

                Para facilitar a leitura do codigo irei deixar aqui um "Menu" do que esta em cada indice da arrayList "mostraExames"

                    .get(0) - codigo do Exame                                   .get(3) - nome do Paciente que fez o exame
                    .get(1) - data do Exame                                     .get(4) - id do Paciente que fez o exame
                    .get(2) - Saudavel/Detectada patologia                      .get(5) - idade do Paciente que fez o exame

                    .get(6) - Sexo do Paciente que fez o exame
                    .get(7) - Numero de sensores usados no exame
                    .get(8) - Numero de sensores ELECTRICOS usados no exame

                    Dai em diante todos os index da ArrayList sao os nomes dos sensores ("EMG" etc..)
                    Depois dos nomes sao guardados os valores de cada sensor.


                Percebendo isto o codigo que se segue deve ficar mais facil de entender :)

                                                                          Nota: O ponto 5.12 segue exactamente a mesma logica.


         */

        Scanner in = new Scanner(System.in);
        String codigo;
        String aux;
        String sumarioTec = "";
        String codigoCedula = "";
        String nomeFunc = "";
        ArrayList<String> mostraExames = new ArrayList<String>();

        int temp;                                                                               // variavel temporaria, so para auxiliar
        int dispoElectricos = 0;
        int dispoAnalogicos = 0;



        try
        {

            PrintWriter relatorio = new PrintWriter("src/relatorio.txt");



            System.out.println("Qual o codigo do exame? ");
            codigo = comando(in);

            System.out.println("--------------------------\nExames:");
            System.out.println("(Data, codigo, indica problemas)");

            mostraExames =  gestor.mostraExameRES(Integer.parseInt(codigo));

            System.out.println(mostraExames.get(1)+ " "+ codigo + " "+ mostraExames.get(2));
            System.out.print("\tcanais medidos: ");

            temp = Integer.parseInt(mostraExames.get(7));

            for(int i = 0; i < temp; i++){
                System.out.print(" "+mostraExames.get(9+i));
            }


            System.out.println("\nDisponibilizar resultados das leituras dos sensores electricos?[s/n]");

            aux = comando(in);
            if(aux.toLowerCase().equals("S".toLowerCase())){
                dispoElectricos = 1;
            }

            System.out.println("Disponibilizar resultados das leituras dos sensores nao electricos?[s/n]");

            aux = comando(in);
            if(aux.toLowerCase().equals("S".toLowerCase())){
                dispoAnalogicos = 1;
            }

            // Mostra os detalhes do paciente que estao guardados na array list "mostraExames"
            System.out.println("\n\nID " + mostraExames.get(4) + " Idade " + mostraExames.get(5) + " Nome " + mostraExames.get(3) + " Sexo " + mostraExames.get(6));


            gestor.mostraDadosExames(relatorio, mostraExames, dispoAnalogicos, dispoElectricos, 0);

            System.out.println("Introduza sumario tecnico: ");
            sumarioTec = comando(in);

            System.out.println("Qual o codigo da sua cedula profissional: ");
            codigoCedula = comando(in);

            System.out.println("Qual o seu nome: ");
            nomeFunc = comando(in);

            // Produz o relatorio

            relatorio.println("\nExame: "+mostraExames.get(0)+"\n"+
                                "Data: "+mostraExames.get(1)+"\n"+
                                "Nome Paciente: "+mostraExames.get(3)+"\n"+
                                "Idade: "+mostraExames.get(5)+"\n\n"+
                                "------------------------------\n"+
                                "Nome Tecnico: "+nomeFunc+"\n"+
                                "Cedula profissional: "+codigoCedula+"\n\n"+
                                "Relatorio base:\n"+mostraExames.get(2)+"\n\n"+
                                "Resumo tecnico: \n"+sumarioTec+"\n"+
                                 "------------------------------\n");


            gestor.mostraDadosExames(relatorio, mostraExames, dispoAnalogicos, dispoElectricos, 1);


            relatorio.close();

           System.out.println("Relatorio produzido");


        }
        catch ( java.io.IOException e) {
            System.out.println("ERRO ao tentar produzir o relatorio.txt");
        }


    }


    public static void relatorioHistorico(SenLab gestor){

        // Ponto 5.12 do Projecto
        int i,temp = 1,numSensores = 0;
        Scanner in = new Scanner(System.in);
        String aux = "";
        String nome = "";
        String sumarioTec = "";
        String codigoCedula ="";
        String exame = "";
        ArrayList<String> mostraExames = new ArrayList<String>();

        System.out.println("Qual o nome do Paciente");
        aux = comando(in);


        mostraExames =  gestor.mostraExames(aux);


        // Reconhe input do tecnico que está a usar o Programa
        System.out.println("\nIntroduza sumário técnico (uma frase): ");
        sumarioTec = comando(in);
        System.out.println("Qual o codigo da sua cedula profissional?");
        codigoCedula = comando(in);
        System.out.println("Qual o seu nome?");
        nome = comando(in);

        // Produz o relatorio

        try
        {
            PrintWriter relatorio = new PrintWriter("src/relatorio.txt");


            relatorio.println(" Nome do paciente: "+aux +"\n"+
                                "Idade: "+mostraExames.get(3)+"\n"+
                                "---------------------------\n"+
                                "Nome Tecnico: "+nome+"\n"+
                                "Cedula Profissional: "+codigoCedula+"\n\n"+
                                "Relatorio base:\n"+sumarioTec+"\n"+
                                "---------------------------\n"+
                                "Exames:\n(Data,Codigo,Indica Problemas)\n");

            i = 0;

            while((i+5) < mostraExames.size()){
                relatorio.println(mostraExames.get(i + 2) + " " + mostraExames.get(i + 1) + " " + mostraExames.get(i + 2));
                relatorio.print("\tcanais medidos: ");
                i = i + 5;
                temp = temp +1;
                while(true){
                    if(i >= mostraExames.size() || mostraExames.get(i).equals(temp))
                        break;

                    relatorio.print(mostraExames.get(i) + " ");
                    i++;
                }

                relatorio.print("\n");
            }

            relatorio.close();

            System.out.println("Relatorio produzido");

        }
        catch ( java.io.IOException e) {
            System.out.println("ERRO ao tentar produzir o relatorio.txt");
        }
    }



    public static void apagarSensorPorNumero(SenLab gestor){

        // Ponto 5.4 (XXL)

        Scanner in = new Scanner(System.in);
        int temp;
        int chamadas = 0;                                                   // Numero de vezes que a funcao "ponto5_4" já foi chamda
        String com;

        ArrayList<String> nomes = new ArrayList<String>();

        System.out.println("Qual o codigo da leitura?");
        com = comando(in);
        temp = Integer.parseInt(com);                                       // Cuidado, se nao for um inteiro vai dar erro! Nao está protegido

        do{
            nomes = gestor.ponto5_4(temp,chamadas);

            com = comando(in);

            if(Integer.parseInt(com) > nomes.size() ||  Integer.parseInt(com) <= 0 ){               // Casos que dao problemas
                if( Integer.parseInt(com) != 0)
                    System.out.println("Nao existe esse sensor");

                chamadas = 0;
            }
            else{
                gestor.removeSensorEspecifico(nomes.get(Integer.parseInt(com)-1),temp);             // Recebe o nome do sensor e o numero de leitura
                chamadas = 1;
            }




        }while(Integer.parseInt(com) != 0 );




    }


    public static void apagarLeituras(SenLab gestor){

        // Ponto 5.5

        Scanner in = new Scanner(System.in);
        int temp,aux = 0;
        String com;

        System.out.println("Qual o codigo da leitura?");
        com = comando(in);
        temp = Integer.parseInt(com);                                       // Cuidado, se nao for um inteiro vai dar erro! Nao está protegido

        aux = gestor.ponto5_5(temp);

        if(aux == 1)
            com = comando(in);


        if(com.toUpperCase().equals("S")){
            gestor.eliminaNumLeitura(temp);                                 // Elimina todas as entradas
        }
        else if(com.toUpperCase().equals("N"))
            System.out.println("Exame nao apagado");

    }





    public static void main(String[] args) throws IOException, ClassNotFoundException{


        SenLab gestor = new SenLab();                                               // Inicialização da class SenLab
        gestor.carregaLab();                                                        // Carrega os dados gravados desde a ultima utilizacao do programa

        System.out.println("Clique A/a para ver o menu");

        Scanner in = new Scanner(System.in);
        String com = comando(in);
        String aux;

        System.out.println(com);


        while (!com.toUpperCase().equals(SAIR)) { //enquanto o utilizador n�o fizer "S", isto �, enquanto n�o disser que quer sair do programa, este ir� continuar, ficando em ciclo infinito

            switch (com.toUpperCase()) {

                case "A":
                    ajuda();
                    break;
                case "PNE":
                    //PNE(); ----falta o ficheiro nos ()
                    System.out.println("PNE");
                    break;
                case "CNL":                                                                     // Ponto 5.3 do Projecto
                    System.out.println("Qual o nome do ficheiro");
                    aux = comando(in);
                    gestor.carregaNovaLeitura(aux);
                    break;
                case "AS":
                    apagarSensorPorNumero(gestor);                                              //Ponto 5.4 do Projecto
                    break;
                case "AL":
                    apagarLeituras(gestor);                                                     //Ponto 5.5 do Projecto
                    break;
                case "AP":
                    System.out.println("Qual o nome do paciente a apagar?");
                    aux = comando(in);
                    gestor.apagarPaciente(aux);
                    break;
                case "NP":
                    gestor.numeroPacientes();                                                    // Ponto 5.7 do Projecto
                    break;
                case "LP":
                    gestor.listaPacientes();                                                     // Ponto 5.8 do Projecto
                    break;
                case "LE":
                    System.out.println("Qual o paciente?");
                    aux = comando(in);
                    gestor.listaLeituras(aux);                                                  // Ponto 5.9 do Projecto
                    break;
                case "LS":
                    gestor.listaSaudaveis();                                                    // Ponto 5.10 do Projecto
                    break;
                case "LD":
                    gestor.listaDoentes();                                                      // Ponto 5.11 do Projecto
                    break;
                case "REA":
                    relatorioHistorico(gestor);                                                 // Ponto 5.12 do Projecto
                    break;
                case "RES":
                    relatorioExame(gestor);                                                  // Ponto 5.13 do Projecto
                    break;
                case "G":
                    gestor.gravaProjecto(gestor);
                    break;

                default:
                    System.out.println(COMANDO_INVALIDO);
                    break;


            }

            System.out.println("");
            System.out.println("");
            com = comando(in);
        }


        System.out.println(SESSAO_TERMINADA);
        in.close();
    }
}





