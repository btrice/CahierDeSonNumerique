package BD;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.cahierdeson.MainActivity;

import Modeles.Module;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ModulesBDD {
	
	//pas d'id c'est au to increment dans la bd
	//a determiner manipulation d'un module a travers la bd (comme ElevesBDD)
	
	private static final int OLD_BDD=3;  //la version ancienne de la bd
	private static final int VERSION_BDD = 7; //la version actuelle de la bdd
	private static final String NOM_BDD = "cahierdeson";

	//nom des champs de la table eleves
	private static final String TABLE_MODULES = "Modules";
	
	
	private static final String COL_GRAPHEME = "grapheme";
	private static final int NUM_COL_GRAPHEME =0; 
	
	private static final String COL_LIEN_IMAGE_BOREL="lien_image_borel";
	private static final int NUM_COL_LIEN_IMAGE_BOREL=1;
	
	private static final String COL_LIEN_IMAGE_REFERENTE="lien_image_referente";
	private static final int NUM_COL_LIEN_IMAGE_REFERENTE=2;
	
	private static final String COL_LIEN_SON="lien_son";
	private static final int NUM_COL_LIEN_SON=3;
	
	private static final String COL_LIEN_VIDEO="lien_video";
	private static final int NUM_COL_LIEN_VIDEO=4;
	
	private static final String COL_DATE_CREATION="date_creation";
	private static final int NUM_COL_DATE_CREATION=5;
	

	private static final String COL_ELEVE = "id_eleve";
	private static final int NUM_COL_ELEVE = 6;
 
	private SQLiteDatabase bdd;
 
	private BDCahierDeSon maBaseSQLite;
 
	public ModulesBDD(Context context){
		//On créer la BDD et sa table
		maBaseSQLite = new BDCahierDeSon(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public boolean insertModule(Module module){
		
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		//values.put(COL_ID, eleve.getId());
		values.put(COL_GRAPHEME ,module.getGrapheme().toUpperCase());
		values.put(COL_LIEN_IMAGE_BOREL, module.getLienImageBorel());
		values.put(COL_LIEN_IMAGE_REFERENTE, module.getLienImageReferente());
		values.put(COL_LIEN_SON, module.getLienSon());
		values.put(COL_LIEN_VIDEO, module.getLienVideo());
		values.put(COL_DATE_CREATION, module.getDateCreation());
		values.put(COL_ELEVE,module.getIdEleve());
		
		
		//on insère l'objet dans la BDD via le ContentValues
		
		if( bdd.insert(TABLE_MODULES, null, values)!=0)
			{Log.i("bdd cahierdeson","insertion de module"+module.getGrapheme()+ " réussite+ idEleve:"+module.getIdEleve());
				return true;
			}
		else {
			Log.i("bdd cahierdeson"," erreur ! insertion de module"+module.getGrapheme()+ "non exécutée");
			return false;
			}
		    
		
	}
	
	/**
	 * modifier un module par les données dans le module passé en parametres
	 * @param id
	 * @param module
	 * @return
	 */
 
	public boolean updateModules(String grapheme,String idE, Module module){
		//La mise à jour d'un module dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple préciser quel module on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_GRAPHEME ,module.getGrapheme());
		values.put(COL_LIEN_IMAGE_BOREL, module.getLienImageBorel());
		values.put(COL_LIEN_IMAGE_REFERENTE, module.getLienImageReferente());
		values.put(COL_LIEN_SON, module.getLienSon());
		values.put(COL_LIEN_VIDEO, module.getLienVideo());
		values.put(COL_DATE_CREATION, module.getDateCreation());
		values.put(COL_ELEVE,module.getIdEleve());
		
		if(bdd.update(TABLE_MODULES, values, COL_ELEVE + " like '" + idE+"' and "+COL_GRAPHEME+" like '"+grapheme+"'", null)>0)
			{
			Log.i("update eleve","update exécuté");
			return true;
			}
		else{
			Log.i("update eleve","erreur update non exécutée");
			return false;
		}
		
			
			
	}
 
	public boolean removeModuleWithID(String grapheme){
		//Suppression d'un module de la BDD grâce à l'ID
		if(bdd.delete(TABLE_MODULES, COL_GRAPHEME+ " = " +grapheme, null)>0)
			{
			Log.i("suppression","ok, module dont l'id est"+ grapheme+"est supprimé");
			return true;
			}
		Log.i("suppression","erreur, module dont l'id est"+ grapheme+" n'est pas supprimé");
		return false;
	}
 
	
	/***
	 * ici on sélectionne le module  dont le grapheme et l'id eleve sont fourni en paramétres
	 * 
	 * @param 
	 * grapheme
	 * IdeEleve
	 * @return
	 */
	public Module getModuleWith(String grapheme,String idEleve){
		//Récupère dans un Cursor les valeur correspondant à un module contenu dans la BDD (ici on sélectionne le module  dont le nom de champs COL correspond à la valeur value)
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME,COL_LIEN_IMAGE_BOREL,COL_LIEN_IMAGE_REFERENTE,COL_LIEN_SON,COL_LIEN_VIDEO,COL_DATE_CREATION,COL_ELEVE}, COL_GRAPHEME + " LIKE \"" + grapheme +"\" and "+ COL_ELEVE +" like \""+idEleve+"\"", null, null, null, null);
		if(c.getCount()==0)
			{Log.i("get graphème  with","affichage exécuté, résultat  null");
			return null;
			}
		else{ Log.i("get graphème with","affichage exécuté, résultat non null !");
		c.moveToFirst();
		return cursorToModule(c);
		}
	}
 
	//Cette méthode permet de convertir un cursor en un module
	private Module cursorToModule(Cursor c){

		//on est sur que c n'est pas vide
		// se place sur le premier élément
		//c.moveToFirst();
		//On créé un module
		 Module m= new Module();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		m.setGrapheme(c.getString(NUM_COL_GRAPHEME));
		m.setLienImageBorel(c.getString(NUM_COL_LIEN_IMAGE_BOREL));
		m.setLienImageReferente(c.getString(NUM_COL_LIEN_IMAGE_REFERENTE));
		m.setLienSon(c.getString(NUM_COL_LIEN_SON));
		m.setLienVideo(c.getString(NUM_COL_LIEN_VIDEO));
		m.setDateCreation(c.getString(NUM_COL_DATE_CREATION));
		m.setIdEleve(c.getString(NUM_COL_ELEVE));

		//eleve.setLienImage(c.getString(NUM_COL_PHOTO_PROFIL));
		//On ferme le cursor
		//c.close();
 
		
		return m;
	}
	
	
	/**
	 * renvoi un map qui contient la liste des graphemes qui n'ont pas été crée par l'eleve
	 * autrement dit les graphemes proposés par l'application 
	 *
	 */
	
	public Map getGraphemeWith(String COL,String value){
		//
		
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME}, COL + " LIKE \"" + value +"\" and id_eleve = 0 ", null, null, null, null);
		if(c.getCount()==0)
			{Log.i("get graphème with","affichage exécuté, résultat  null");
			return null;
			}
		else Log.i("get graphème with","affichage exécuté, résultat non null !");
		return cursorToMap(c);
	}
	
	//Cette méthode permet de convertir un cursor en une hashmap qui contiendra les graphèmes dans le curseur
	private Map<Integer,String>cursorToMap(Cursor c){
		//on se place sur le premier élément
		Map<Integer,String> map=new HashMap<Integer,String>();
		Boolean move=c.moveToFirst();
		int key=0;
		while(move){

		//on récupère la grapheme
		
		String grapheme=c.getString(0); 
		//on l'insere dans le map
		map.put(key, grapheme);
		key++;
		//on passe à l'element suivant de curseur
		move=c.moveToNext();
		
		}
		//On ferme le cursor
		c.close();
 
		//On retourne le livre
		return map;
	}
	
/* get grapheme dont l id de l eleve est null c a dire si le grapheme est dans la BD par defaut */
	
	public Map getGraphemeDefaut(String COL,String value){
		//Récupère dans un Cursor les valeur correspondant à un module contenu dans la BDD (ici on sélectionne le module  dont le nom de champs COL correspond à la valeur value)
		
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME}, COL + " LIKE \"" + value +"\" and "+ COL_ELEVE +" = 0", null, null, null, null);
		if(c.getCount()==0)
			{Log.i("get graphème par defaut","affichage exécuté, résultat  null");
			return null;
			}
		else Log.i("get graphème par defaut","affichage exécuté, résultat non null !");
		return cursorToMap(c);
	}
	
	/*
	 * permet de vérifier si le module dont la garapheme=grapheme est déjà crée par l'eleve dont l'id=idEleve
	 * 
	 * */
	public boolean ExistModule(String idEleve,String grapheme){
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME}, COL_GRAPHEME+ " LIKE \"" + grapheme +"\" and "+ COL_ELEVE +" like '"+ idEleve+"'", null, null, null, null);
		if(c.getCount()==0)
			{Log.i("le module n'existe pas "," résultat  null, ok");
			return true;
			}
		else{
			Log.i("le module existe deja"," l eleve a deja crée ce module, résultat non null!");
			return false;
		}
		
	}

	
	/**
	 * Récupérer le lien de l'image d'un module dont l'id de l'eleve est null et le lien de l'image n'est pas vide 
	 *et le grapheme egale au grapheme passé en parametre
	 * s'il renvoi un lien vide on considère que le module n'est pas livré avec l'application
	 */
	public String getLienImageBD(String grapheme){
		Cursor c = bdd.query(TABLE_MODULES, new String[] {this.COL_LIEN_IMAGE_BOREL}, COL_GRAPHEME+ " LIKE \"" + grapheme +"\" and "+ COL_ELEVE +" like  \" dictionnaire \"", null, null, null, null);
		if(c.getCount()==0)
			{Log.i("le module n'existe pas dans le dictionnaire "," résultat  null, ok");
			return null;
			}
		else{
			Log.i("le module existe dans le dictionnaire"," lien trouvé!");
			c.moveToFirst();
			String lienImg=c.getString(0);
			
			return lienImg;
		}
		
	}
	
	
	
	/**
	 * Récupérer des liens des images disponibles livrées avec l'application 
	 * On a choisi l'id eleve par defaut est dictionnaire
	 * renvoi un tableau de string
	 */
	public ArrayList<String> getLiensImageBD(String grapheme){
		Cursor c = bdd.query(TABLE_MODULES, new String[] {this.COL_LIEN_IMAGE_BOREL}, COL_GRAPHEME+ " not LIKE \"" + grapheme.toUpperCase() +"\" and "+ COL_ELEVE +" like \" dictionnaire \"", null, null,null, null);
		ArrayList<String> result=new ArrayList<String>();
		if(c.getCount()>0)
			{
			Log.i("get liens images"," plusieurs liens trouvés : "+c.getCount());
			boolean next=c.moveToFirst();
			while(next){
				result.add(c.getString(0));
				next=c.moveToNext();
			}
			
		return result;
			}
		else{
			Log.i("get liens images  "," résultat  null");
			return null;
			}

		
	}
	
	
	
	public void SelectAllModules(){
		
		//Récupère dans un Cursor les valeur correspondant à un eleve contenu dans la BDD (ici on sélectionne l'eleve dont le nom de champs COL correspond à la valeur value)
		Cursor c = bdd.query(this.TABLE_MODULES, new String[] {COL_GRAPHEME,COL_LIEN_IMAGE_BOREL,COL_LIEN_IMAGE_REFERENTE,COL_LIEN_SON,COL_LIEN_VIDEO,COL_DATE_CREATION,COL_ELEVE},null, null, null, null, null);
		if(c.getCount()==0){
			Log.i("select * de module"," resultat null");
		}
		else{
			Boolean move=c.moveToFirst();
			Module e;// e= cursorToModule(c);
			String ch;//=e.ToString();
			//Log.i("0", ch);
			int i=0;
			while(move){

		
				e= cursorToModule(c);
				ch=e.ToString();
				Log.i(""+i, ch);
				move=c.moveToNext();
				i++;
		
					}
			}
		
		//On ferme le cursor
		c.close();	
	
	}

/**
 * 
 * permet de mettre a jour le module en lui ajoutant le lien de l'image passé en parametre
 * @param idEleve
 * @param lienImageBorel
 * @param grapheme
 * @return
 */
	
	public boolean AddLienImageBorel(String idEleve,String lienImageBorel,String grapheme){
		//La mise à jour d'un module dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple préciser quel module on doit mettre à jour grâce au grapheme et l'identifiant de l'eleve
		//on est sur que cela est unique car au moment de l'insertion d'un module pour un eleve on oblige que le grapheme soit unique
		ContentValues values = new ContentValues();
		
		values.put(COL_LIEN_IMAGE_BOREL, lienImageBorel);
		//try{
		//bdd.execSQL("update "+ TABLE_MODULES+ " set lien_image_borel =\""+lienImageBorel+"\" and "+COL_ELEVE+"="+idEleve);

		Log.i(" avant update module ","");
		try{
		
		if(bdd.update(TABLE_MODULES, values, COL_ELEVE + " = " +idEleve+" and "+COL_GRAPHEME+" like \""+grapheme.toUpperCase()+"\"",null)>0)
			{
			Log.i("update module "+ grapheme+" nume eleve:  "+ idEleve,"update exécuté");
			return true;
			}
		/*else{
			Log.i(" update module "+ grapheme+" nume eleve:  "+ idEleve,"erreur update non exécutée");
			return false;
		}*/
	
		
		}catch (Exception ex) {
	          ex.printStackTrace();
	          Log.i("update lien image", "update échouée");
	          return false;
	        }
		//return true;
		return false;
	}
	
	
	
	
	/**
	 * renvoi un map qui contient la liste des graphemes qui ont été créés par l'eleve
	 * et qui commencent/contiennet le mot passé en parametre
	 * @pram
	 * idEleve
	 * grapheme
	 *
	 */
	
	public Map getGraphemesEleve(String mot,String idEleve){
		
		
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME}, COL_GRAPHEME + " LIKE \"" + mot +"\" and id_eleve like '"+idEleve+"'", null, null, null, null);
		if(c.getCount()==0)
			{Log.i("get graphèmes eleve","affichage exécuté, résultat  null");
			return null;
			}
		else Log.i("get graphème with","affichage exécuté, résultat non null !");
		return cursorToMap(c);
	}
	
/**
 * renvoi un Map qui contient la liste de tous les grapheme crée par l'eleve dont 
 * l'id est passé en parametres
 * @param idEleve
 * @return
 */
	public ArrayList<String> getListeModulesEleve(String idEleve){
		//Récupère dans un Cursor les valeur correspondant à un module contenu dans la BDD (ici on sélectionne le module  dont le nom de champs COL correspond à la valeur value)
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME}, COL_ELEVE +" like \""+idEleve+"\"", null, null, null, null);
		if(c.getCount()==0)
			{
			//Log.i("get graphème  with","affichage exécuté, résultat  null");
			return null;
			}
		else{ 
			Log.i("get graphème with","affichage exécuté, résultat non null !");
			c.moveToFirst();
			ArrayList<String>  liste=new ArrayList<String> ();
			Boolean move=c.moveToFirst();
			int key=0;
			while(move){
				//on récupère la grapheme
				String grapheme=c.getString(0); 
				//on l'insere dans le map
				liste.add(grapheme);
				key++;
				//on passe à l'element suivant de curseur
				move=c.moveToNext();
		
			}
			//On ferme le cursor
			c.close();
	 
		//On retourne la liste
		return liste;
		}
	}
 	
	
/**
 * renvoie le module dont le garpheme et l'id de l'eleve sont passé en parametre de fonction	
 * @param mot
 * @param idEleve
 * @return
 */
public Module getModuleEleve(String mot,String idEleve){
		
		
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME}, COL_GRAPHEME + " LIKE \"" + mot +"\" and id_eleve like '"+idEleve+"' order by "+ COL_DATE_CREATION+"Desc", null, null, null, null);
		if(c.getCount()==0)
			{
			Log.i("get Module eleve","affichage exécuté, résultat  null");
			return null;
			}
		else Log.i("get module eleve","affichage exécuté, résultat non null !");
		return cursorToModule(c);
	}	
//renvoi une liste de tous lesmodules crées par l'eleve passé en prametre
	public ArrayList<Module> getAllModulesEleve(String idEleve){
		//Récupère dans un Cursor les valeur correspondant à un module contenu dans la BDD (ici on sélectionne le module  dont le nom de champs COL correspond à la valeur value)
		Cursor c = bdd.query(TABLE_MODULES, new String[] {COL_GRAPHEME,COL_LIEN_IMAGE_BOREL,COL_LIEN_IMAGE_REFERENTE,COL_LIEN_SON,COL_LIEN_VIDEO,COL_DATE_CREATION}, COL_ELEVE +" like \""+idEleve+"\"", null, null, null, null);
		if(c.getCount()==0)
			{
			//Log.i("get graphème  with","affichage exécuté, résultat  null");
			return null;
			}
		else{ 
			Log.i("get graphème with","affichage exécuté, résultat non null !");
			c.moveToFirst();
			ArrayList<Module>  liste=new ArrayList<Module> ();
			Boolean move=c.moveToFirst();
			int key=0;
			while(move){
				//on récupère la grapheme
				String grapheme=c.getString(0); 
				String lien_borel=c.getString(1);
				String lien_image_referente=c.getString(2);
				String lien_son=c.getString(3);
				String lien_video=c.getString(4);
				String date_creation=c.getString(5);
				//on l'insere dans le map
				Module module =new Module(grapheme,lien_borel,lien_image_referente,lien_son,lien_video,date_creation,idEleve);
				liste.add(module);
				key++;
				//on passe à l'element suivant de curseur
				move=c.moveToNext();
		
			}
			//On ferme le cursor
			c.close();
	 
		//On retourne la liste
		return liste;
		}
	}
}
