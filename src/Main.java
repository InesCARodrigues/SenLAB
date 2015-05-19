
import java.util.ArrayList;
import java.util.Scanner;

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
        input = in.next();
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
                            "AS - Apagar sensor por n�mero de leitura\n"+
                            "AL - Apagar Leitura\n"+
                            "AP - Apagar Paciente\n\n"+
                            "NP - Numero de Pacientes\n"+
                            "LP - Listar Pacientes\n"+
                            "LE - Listar Leituras feitas a um determinado paciente\n"+
                            "LS - Listar pacientes saudaveis\n"+
                            "LD - Listar pacientes com patologias\n\n"+
                            "REA - Produzir um relat�rio do historico do paciente por datas\n"+
                            "RES - produzir um relatorio simples do paciente por datas" );


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





    public static void main(String[] args) {


        SenLab gestor = new SenLab();                                               // Inicialização da class SenLab
        gestor.baseDados(gestor);                                                   // Carrega dados;  SO PARA DEBUG  !!!



        /**
         * POR IMPLEMENTAR
         * gestor.carregaFicheiros();
         *
         *
         */





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
                    System.out.println("LS");
                    break;
                case "LD":
                    System.out.println("LD");
                    break;
                case "REA":
                    System.out.println("REA");
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





