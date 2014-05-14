package Modeles;

public class Eleve extends Utilisateur{

	private String classe;
	

	public Eleve(){super();}
	
	public Eleve(String prenom){
		super(prenom);
	}
	
	public Eleve(String prenom, String pass,String classe) {
		super(prenom, pass);
		this.classe=classe;
		// TODO Auto-generated constructor stub
	}
	//a jouter le constructeur par copy
	
	public Eleve (Eleve e){
		
		this.prenom=e.prenom;
		this.mot_de_pass=e.mot_de_pass;
		this.classe=e.classe;
	}

	
	public String getClasse(){
		return this.classe;
	}
	
	public void setClasse(String classe){
		this.classe=classe;
	}
	
	public String ToString(){
		String ch=super.ToString();
		ch+="\n classe:" +classe;
		return ch;
		
	}
	
}
