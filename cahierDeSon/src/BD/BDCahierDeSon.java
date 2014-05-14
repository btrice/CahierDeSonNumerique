package BD;
//import java.sql.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BDCahierDeSon extends SQLiteOpenHelper {

	private static final String TABLE_ELEVES="Eleves";
	private static final String TABLE_MODULES="Modules";
	
	//requete pour creer la table eleve
	private static final String  QUERY_CREATE_ELEVES="create table Eleves (" +

			"prenom TEXT primary key,"+
			"mot_de_passe TEXT,"+
			"classe text"+	
			
			")";
	
	//requete pour cr�er la table module
	
	private static final String QUERY_CREATE_MODULES=" create table "+TABLE_MODULES+"(" +
			
			"grapheme text not NULL," + /* lien_image_borel text, lien_image_word text */
			"lien_image_borel TEXT," +
			"lien_image_referente TEXT," +
			"lien_son TEXT," +
			"lien_video TEXT," +
			"date_creation DATE," +
			"id_eleve TEXT references Eleves not null,"+
			"primary key(grapheme,id_eleve)"
			+
			")";
	
    //constructeur , creation de la base de donn�es
	public BDCahierDeSon(Context c, String name, CursorFactory factory, int version) {
        super(c, name, factory, version);
         

                 
    }
     
    // � la cr�ation de tables de la base de donn�es
  
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		try {

              db.execSQL(this.QUERY_CREATE_ELEVES);   //execution des requ�tes creation eleves
              db.execSQL(this.QUERY_CREATE_MODULES); //  ---      ------     ------     modules
             // System.out.println("BDD sqlite");
              
            Log.i("BDD sqlite", "Cr�ation r�ussi");
            //Log.i("strucure", msg)
             
        } catch (Exception ex) {
          ex.printStackTrace();
          Log.i("BDD sqlite", "Cr�ation �chou�e");
        }	
		
	}//fin oncreate

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//On peut fait ce qu'on veut ici moi j'ai d�cid� de supprimer la table et de la recr�er
		//comme �a lorsque je change la version les id repartent de 0
				 db.execSQL("DROP TABLE " + TABLE_ELEVES + ";");
				//db.execSQL("DROP TABLE " + TABLE_MODULES + ";");
				onCreate(db);
	}
	
}
