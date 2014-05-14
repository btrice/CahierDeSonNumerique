package Modeles;

public abstract class Utilisateur {
	
	protected String prenom;
	protected String mot_de_pass; //on modifira le type si le mot de passe doit être chiffré ou crypté
	
	public Utilisateur(String prenom,String pass){

		this.prenom=prenom;
		this.mot_de_pass=pass;
	}
	
	public Utilisateur(String prenom){
		this.prenom=prenom;
	}
	
	public Utilisateur(){}

	
	//a completer
	
	/*************************************************getters**************************************************/

	
	public String getPrenom(){
		return this.prenom;
	}
	
	//on verra par la suite si on chiffre le mot de passe 
	public String getMotDePasse(){
		return this.mot_de_pass;
		
	}
	
	
	/***************************************************setters*******************************************************/
	
	public void changeMotDePass(String mp){
		this.mot_de_pass=mp;
		
		//a completer
	}
	
	
	public void setPrenom(String prenom){
		this.prenom=prenom;
	}
	
	public String ToString(){
		String ch="";
		ch+="\nprenom:"+prenom;
		ch+="\n pass:" +mot_de_pass;
		return ch;
		
	}
	
}
