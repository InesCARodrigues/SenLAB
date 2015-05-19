import java.util.*;

public class Paciente {


	/** CLASS PACIENTE CORRECTAMENTE IMPLEMENTADA
	 *
	 * 	- Todas as variaveis estão como private
	 *  - Contrutor implementado
	 *  - Variaveis apenas acediveis por Getters e modificaveis por Setters
	 *
	 *  - Metodos implementados no final do codigo
	 *
	 */



     private String nome;
	 //private static int numPaciente;												// Variavel estática permite incremetar o numero de pacientes de forma unica
	 private int id;
     private int idade;
	 private int leituras;
	 private int contadorExames;
	 private int numLeituras;														// Numero de leituas
	 private ArrayList <Integer> idExames = new ArrayList<Integer>();					// Array com os Numeros de Leituras dos Sensores
     private char sexo;
     private boolean tomaMedicacao;
     private boolean fuma;



	//Construtores
	public Paciente(String nome,int id,int idade, int leituras, int contadorExames,char sexo, boolean tomaMedicacao,boolean fuma,int idExames)
	{
		this.id = id;																// id = identificador unico do utilizador
		this.nome = nome;
		this.idade = idade;
		this.leituras = leituras;
		this.contadorExames = contadorExames;
		this.sexo = sexo;
		this.tomaMedicacao = tomaMedicacao;
		this.fuma = fuma;
		this.numLeituras = 1;														// Quando o paciente é criado tem apenas uma leitura de exame associada
		this.idExames.add(idExames);											    // ID dos exames efetuados a este paciente

	}


	// SETTERS

	public void setNome(String nome){
		this.nome = nome;
	}

	public void setIdade(int idade){
		this.idade = idade;
	}

	public void setLeituras(int leituras) {
		this.leituras = leituras;
	}

	public void setContadorExames(int contadorExames) {
		this.contadorExames = contadorExames;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public void setTomaMedicacao(boolean tomaMedicacao) {
		this.tomaMedicacao = tomaMedicacao;
	}

	public void setFuma(boolean fuma) {
		this.fuma = fuma;
	}

    public void setId(int id) {
        this.id = id;
    }

    public void setNumLeituras(int numLeituras) {
        this.numLeituras = numLeituras;
    }

    public void setIdExames(ArrayList<Integer> idExames) {
        this.idExames = idExames;
    }





    //GETTERS
	public String getNome() {
		return nome;
	}

	public int getId() {
		return id;
	}

	public int getIdade() {
		return idade;
	}

	public int getLeituras() {
		return leituras;
	}

	public int getContadorExames() {
		return contadorExames;
	}

	public char getSexo() {
		return sexo;
	}

	public Character getTomaMedicacao() {
        if(tomaMedicacao)
            return 'S';
        else
            return 'N';
	}

	public Character getFuma() {
		if(fuma)
            return 'S';
        else
            return 'N';
	}

    public int getNumLeituras() {
        return numLeituras;
    }

    public ArrayList<Integer> getIdExames() {
        return idExames;
    }


    //TO STRING         - NOTA: Vou fazer vários toString uma vez que o enunciado pede o output das classes em vários formatos
	@Override
	public String toString() {
		return 	"id: " + id +
				"\nNome: " + nome +
				"\nIdade: " + idade +
				"\nSexo: " + sexo +
				"\nleitura(s): " + numLeituras + "\n";
	}


    public String toString2(){
        return "(ID,Nome,Sexo,Idade,Fuma,Medicamentos,Leituras)\n"+
                "\t"+id + "\t|\t"+ nome + "\t|\t"+ sexo + "\t|\t" + idade + "\t|\t"+ getFuma() + "\t|\t"+ getTomaMedicacao() + "\t|\t" + leituras + "\n" ;
    }


	/************* METODOS DA CLASS ******************/


}
