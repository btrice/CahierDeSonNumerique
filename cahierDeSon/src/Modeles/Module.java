package Modeles;

public class Module {
	
	//private String word_module;
	private String grapheme;
	private String lien_image_borel;
	//private String lien_image_word;
	private String lien_image_referente;
	private String lien_son;
	private String lien_video;
	private String date_creation;
	private String id_eleve;
	
	public Module(){}
	
	public Module(String grapheme, String lien_image_borel,String lien_image_referente,String lien_son,String lien_video,String date_creation,String id_eleve)
	{
		//this.word_module=word_module;
		this.grapheme=grapheme;
		this.lien_image_borel=lien_image_borel;
		this.lien_image_referente=lien_image_referente;
		this.lien_son=lien_son;
		this.lien_video=lien_video;
		this.date_creation=date_creation;
		this.id_eleve=id_eleve;
		/*this.lien_image_borel)lien_image_borel;*/
	}

	
	/****************************************getters**********************************************/
	

	
	//public String getWord(){return this.word_module;}
	
	public String getGrapheme(){return this.grapheme;}
	
	public String getLienImageBorel(){ return this.lien_image_borel;}
	public String getLienImageReferente(){ return this.lien_image_referente;}
	
	public String getLienSon(){return this.lien_son;}
	
	public String getLienVideo(){return this.lien_video;}
	
	public String getDateCreation(){return this.date_creation;}
	
	public String getIdEleve(){return this.id_eleve;}
	/*public String getLienImageBorel(){return this.lien_image_borel}
	 * 
	 * public String getLienImageWord(){return this.lein_image_word;}*/
	
	/****************************************setters*********************************************/

	public void setGrapheme(String g){this.grapheme=g;} 	

   
  // public void setWord(String world){ this.word_module=world;}
   
   public void setLienImageBorel(String lien){ this.lien_image_borel=lien;}
   
   public void setLienImageReferente(String lien){ this.lien_image_referente=lien;}
   public void setLienSon(String lien){ this.lien_son=lien;}
   
   public void setLienVideo(String lien){ this.lien_video=lien;}
   
   public void setDateCreation(String date){ this.date_creation=date;}
   
   public void setIdEleve(String id){ this.id_eleve=id;}
   
   /*public void setLienImageBorel(String lien){this.lien_image_borel=lien;}
    * 
    * public void setLienImageWord(String lien){this.lien_image_word=lien;}*/
   
   public String ToString(){
	   String ch=" Module:";
	   ch+="\n grapheme: "+grapheme;
	   ch+="\n id_Eleve:"+this.id_eleve;
	   ch+="\n lien_image_borel:"+lien_image_borel;
	   ch+="\n lien_image_referente:"+lien_image_referente;
	   ch+="\n lien_son:"+lien_son;
	   ch+="\n lien_video:"+lien_video;
	   ch+="\n date_creation:"+date_creation;
	
	   return ch;
   }
   
   

}
