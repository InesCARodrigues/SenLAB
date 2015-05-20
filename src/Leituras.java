import java.io.*;
import java.util.*;



public class Leituras {

    /**
     *  Nesta class serão feitas todas as leituras de ficheiros binários (.lab)
     */

    private ArrayList<String> listaExame = new ArrayList<String>();
    private String exame;

    //Construtores
    public Leituras(){};


    //SETTERS

    public void setListaExame(ArrayList<String> listaExame) {
        this.listaExame = listaExame;
    }

    public void setExame(String exame) {
        this.exame = exame;
    }


    //GETTERS

    public ArrayList<String> getListaExame() {
        return listaExame;
    }

    public String getExame() {
        return exame;
    }


    // METODOS

    public void carregaExame(String filename){

        String path = "src/";
        path = path + filename;                                                      // Coloca o caminho correcto para carregar o ficheiro binário


        StringBuilder builder = new StringBuilder();                                 // Cria novo String Builder para depois passar os dados carregados para String
        int ch;

        File a_file = new File(path);

        try
        {
            FileInputStream fis = new FileInputStream(path);                        // Carrega os dados binários


            while((ch = fis.read()) != -1){                                         //Carrega os dados para o String builder
                builder.append((char)ch);
            }

            fis.close();

            this.exame = builder.toString();                                             //Guarda o conteudo na String "exame"
            this.converteEmLista(this.exame);


           // System.out.println("Leitura efectuada com sucesso\n");



        }
        catch(IOException e) {                                                      // Esta excpetion ocorre se nao for possivel carregar o ficheiro binario
            System.out.println("Problema ao carregar o ficheiro");
            e.printStackTrace();

        }
    }



    public void converteEmLista(String exame){


        exame = exame.replace("\n", "").replace("\r", "");

        String arr[] = exame.split("\t");

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
        }

        */



    }





}
