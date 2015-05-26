import java.io.*;
import java.util.*;



public class Leituras {

    /**
     *  Nesta class serão feitas todas as leituras de ficheiros binários (.lab)
     */



    private ArrayList<String> listaExame = new ArrayList<String>();
    private int numExame;
    private ObjectInputStream iS; // iS -> input Stream
    private ObjectOutputStream oS; // oS -> output Stream
    private Writer writer;


    //Construtores
    public Leituras(){};


    //SETTERS

    public void setListaExame(ArrayList<String> listaExame) {
        this.listaExame = listaExame;
    }

    public void setNumExame(int numExame) {
        this.numExame = numExame;
    }



    //GETTERS

    public ArrayList<String> getListaExame() {
        return listaExame;
    }

    public int getNumExame() {
        return numExame;
    }


    // METODOS


    public boolean criaLab(){

        try{
            oS = new ObjectOutputStream(new FileOutputStream("src/lab.db"));
            oS.close();
            return true;
        } catch (IOException e){
            return false;
        }
    }


    //Método para abrir um ficheiro para leitura
    //Recebe o nome do ficheiro
    public boolean abreLeitura(String nomeDoFicheiro) {

        try{
            iS = new ObjectInputStream(new FileInputStream(nomeDoFicheiro));
            System.out.println("Abri para leitura");
            return true;
        } catch (IOException e){
            return false;
        }
    }

    //Método para abrir um ficheiro para escrita
    //Recebe o nome do ficheiro
    public void abreEscrita(String nomeDoFicheiro) throws IOException
    {
        oS = new ObjectOutputStream(new FileOutputStream(nomeDoFicheiro));
    }



    //Método para fechar um ficheiro aberto em modo leitura
    public void fechaLeitura() throws IOException
    {
        iS.close();
    }

    public void fechaEscrita() throws IOException{
        oS.close();
    }



    // Metodo para guardar toda a Informacao - Clientes e Sensores

    public void guardaInfo(ArrayList<Object> informacao){

        try {
            this.abreEscrita("src/lab.db");
            oS.writeObject(informacao);
            oS.close();
            System.out.println("Clientes guardados com sucesso");
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro ao guardar os pacientes "+ex);
        }
    }



    public ArrayList <Object> devolveInfo() throws IOException, ClassNotFoundException{
        this.abreLeitura("src/lab.db");
        ArrayList <Object> readers = (ArrayList <Object>)iS.readObject();
        this.fechaLeitura();
        return readers;
    }



    public int carregaExame(String filename){

        String exame;


        String path = "src/";
        path = path + filename;                                                      // Coloca o caminho correcto para carregar o ficheiro binário


        StringBuilder builder = new StringBuilder();                                 // Cria novo String Builder para depois passar os dados carregados para String
        int ch;

        File a_file = new File(path);

        try
        {
            FileInputStream fis = new FileInputStream(path);                        // Carrega os dados binários

            String aux[] = filename.split("-");
            String aux2[] = aux[1].split("\\.");
            numExame = Integer.parseInt(aux2[0]);                                        //As duas linhas de codigo a cima servem para isolar o numero do exame

            System.out.println("EXAME NUM: "+numExame);


            while((ch = fis.read()) != -1){                                         //Carrega os dados para o String builder
                builder.append((char)ch);
            }

            fis.close();

            exame = builder.toString();                                             //Guarda o conteudo na String "exame"
            this.converteEmLista(exame);

            return 1;                                                               // este 1 indica que tudo correu como esperado


           // System.out.println("Leitura efectuada com sucesso\n");



        }
        catch(IOException e) {                                                      // Esta excpetion ocorre se nao for possivel carregar o ficheiro binario
            System.out.println("Ficheiro inexistente");
            return 0;                                                               // Este 0 indica que ocorreu um problema ao carregar o ficheiro

        }
    }



    public void converteEmLista(String exame){



        String arr[] = exame.split("\n|\\t");



        if(arr != null && arr.length > 0)
        {
            for(String value: arr)
            {
                if(!(value.equals(" ") || value.equals("")))
                    this.listaExame.add(value);


            }
        }




        /*

        DEBUG

       for(int i = 0; i < listaExame.size();i++){
            System.out.println("item["+i+"] = "+listaExame.get(i));
        }*/





    }





}
