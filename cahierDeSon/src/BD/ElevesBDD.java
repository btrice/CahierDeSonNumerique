package BD;

import Modeles.Eleve;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class ElevesBDD {

	private static final int VERSION_BDD = 7;
	private static final String NOM_BDD = "cahierdeson";

	//nom des champs de la table eleves
	private static final String TABLE_ELEVES = "Eleves";

	private static final String COL_PRENOM = "prenom";
	private static final int NUM_COL_PRENOM = 0;
	
	private static final String COL_PASS = "mot_de_passe";
	private static final int NUM_COL_PASS = 1;
	private static final String COL_CLASSE = "classe";
	private static final int NUM_COL_CLASSE = 2;

	
 
	private SQLiteDatabase bdd;
 
	private BDCahierDeSon maBaseSQLite;
 
	public ElevesBDD(Context context){
		//On cr�er la BDD et sa table
		maBaseSQLite = new BDCahierDeSon(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close(){
		//on ferme l'acc�s � la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public long insertEleve(Eleve eleve){
		
		//Si le pr�nom existe on relance 
		if(getEleveWith("prenom",eleve.getPrenom().toString()) != null){
			Log.i("Isertion Eleve", "Pr�nom existant !");
			return 0;
			
		}
		//Cr�ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associ� � une cl� (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		//values.put(COL_ID, eleve.getId());
		values.put(COL_PRENOM, eleve.getPrenom());
		values.put(COL_CLASSE, eleve.getClasse());
		values.put(COL_PASS, eleve.getMotDePasse());
	
		
		
		//on ins�re l'objet dans la BDD via le ContentValues
		long res=bdd.insert(TABLE_ELEVES, null, values);
		if( res >0)
				{
			Log.i("bdd cahierdeson","insertion d'un �l�ve r�ussie");
			return 1;
				}
		else
			return 0;
		
	}
 
	public boolean updateEleves(String prenom, Eleve eleve){
		//La mise � jour d'un ELEVE dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simple pr�ciser quel ELEVE on doit mettre � jour gr�ce � l'ID
		ContentValues values = new ContentValues();
		values.put(COL_PRENOM, eleve.getPrenom());
		values.put(COL_CLASSE, eleve.getClasse());
		values.put(COL_PASS, eleve.getMotDePasse());
	
		//si l'insertion est bien faite on affiche un message
		if( bdd.update(TABLE_ELEVES, values, COL_PRENOM+ " like \"" +prenom+ "\"", null)>0)
			{
				Log.i("update eleve","update ex�cut�");
				return true; 
			}
		else {
			Log.i("update eleve","erreur ! update non ex�cut�");
			return false;
			}
			
			
	}
 
	public boolean removeEleveWithID(String prenom){
		//Suppression d'un eleve de la BDD gr�ce � l'ID
		
		if( bdd.delete(TABLE_ELEVES, COL_PRENOM + " = " +prenom, null)>0)
		{
			Log.i("suppression","eleve dont le prenom est"+ prenom);
			return true;
		}
		else{
			Log.i("suppression","erreur! eleve dont le prenom est"+ prenom+" n'est pas supprim�");
			return false;
		  	}
	}
 
	public Eleve getEleveWith(String COL,String value){
		//R�cup�re dans un Cursor les valeur correspondant � un eleve contenu dans la BDD (ici on s�lectionne l'eleve dont le nom de champs COL correspond � la valeur value)
		Cursor c = bdd.query(TABLE_ELEVES, new String[] {COL_PRENOM,COL_PASS,COL_CLASSE}, COL + " = ? " ,new String[] {value}, null, null, null);
		if(c.getCount()!=0)
			{Log.i("afficher prenom","selection selon "+COL+ "ex�cut�e, resultat non null");
			c.moveToFirst();
			System.out.println("probl�me cursorToEleve");
			return cursorToEleve(c);
			}
		else {
			Log.i("afficher prenom", "resultat null");
			return null;
			}
		}
	
 
	//Cette m�thode permet de convertir un cursor en un eleve
	private Eleve cursorToEleve(Cursor c){
		//si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
		if (c.getCount() == 0){
			System.out.println("CursorToEleve: curseur vide");
			return null;
		}
			
 
		//Sinon on se place sur le premier �l�ment
		//c.moveToFirst();
		//On cr�� un eleve
		Eleve eleve = new Eleve();
		//on lui affecte toutes les infos gr�ce aux infos contenues dans le Cursor
		eleve.setPrenom(c.getString(NUM_COL_PRENOM));
		System.out.println("CursorToEleve: passe setPrenom");
		eleve.changeMotDePass(c.getString(NUM_COL_PASS));
		System.out.println("CursorToEleve: passe setMDP");
		eleve.setClasse(c.getString(NUM_COL_CLASSE));
		System.out.println("CursorToEleve: passe setClasse");
		//On ferme le cursor
		c.close();
 
		//On retourne l eleve
		return eleve;
	}
	
	/**/
	
	//afficher dans le log la liste des eleve se trouvnat dans la bd
	public void SelectAllEleve(){
		
			//R�cup�re dans un Cursor les valeur correspondant � un eleve contenu dans la BDD (ici on s�lectionne l'eleve dont le nom de champs COL correspond � la valeur value)
			Cursor c = bdd.query(TABLE_ELEVES, new String[] { COL_PRENOM,COL_PASS,COL_CLASSE},null, null, null, null, null);
			if(c.getCount()!=0)
				Log.i("select * de Eleves"," resultat non null");
			
			String ch="";
			Eleve e;
			Boolean move=c.moveToFirst();
			
			int i=1;
			while(move){

			
				e= cursorToEleve(c);
				ch=e.ToString();
				Log.i(""+i, ch);
				i++;
				move=c.moveToNext();
		
			}
			//On ferme le cursor
			c.close();	
		
		}
	
}
