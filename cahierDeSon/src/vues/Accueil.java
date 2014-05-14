package vues;

import com.example.cahierdeson.MainActivity;
import com.example.cahierdeson.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Accueil extends Activity {
	private ImageButton consulter;
	private ImageButton enregistrer;
	private ImageButton  recherche;
	private ImageButton  profil;
	private ImageButton deconnexion;
	private ImageButton quizz;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        //on r�cup�re les 6 bouton et leur attribuer des listeners
        
        MainActivity.moduleCourant=null;
        this.consulter=(ImageButton)findViewById(R.id.consultation);
        this.enregistrer=(ImageButton)findViewById(R.id.enregistrement);
        this.recherche=(ImageButton)findViewById(R.id.recherche);
        this.profil=(ImageButton)findViewById(R.id.profil);
        this.deconnexion=(ImageButton)findViewById(R.id.deconnexion);
        this.quizz=(ImageButton)findViewById(R.id.quizz);
        this.quizz.setOnClickListener(new BoutonListener(Recherche_Quizz.class));
        this.consulter.setOnClickListener(new BoutonListener(Consulter_Grapheme.class));
        this.enregistrer.setOnClickListener(new BoutonListener(Enregistrer_Grapheme.class));
        this.recherche.setOnClickListener(new BoutonListener(Consulter_Grapheme.class));
        this.profil.setOnClickListener(new BoutonListener(Profil.class));
        this.deconnexion.setOnClickListener(new BoutonListener(MainActivity.class));
        //label eleve couran
        TextView labelEleveCourant= (TextView)findViewById(R.id.labelEleveConnecte);
        labelEleveCourant.setText(MainActivity.eleveConnecte.getPrenom());
        
       
        
    }
	
	private class BoutonListener implements View.OnClickListener{
		private Class<?> C;
		public BoutonListener(Class<?> c){
			super();
			C=c;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// it was the 1st button
			if(C.equals(MainActivity.class)){
				MainActivity.eleveConnecte = null;
				MainActivity.moduleCourant = null;
				Log.i("deconnexion eleve","Buffer Vid� !");
				
			}
			finish();
	    	Intent i;
            i= new Intent(Accueil.this,C);
            startActivity(i);
	    }
			
		
		}
	
	

}
