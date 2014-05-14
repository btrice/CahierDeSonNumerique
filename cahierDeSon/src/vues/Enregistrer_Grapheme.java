package vues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import BD.ModulesBDD;
import Modeles.AlertFalse;
import Modeles.AlertTrue;
import Modeles.Eleve;
import Modeles.Module;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cahierdeson.MainActivity;
import com.example.cahierdeson.R;

public class Enregistrer_Grapheme extends Activity{
	
	protected AutoCompleteTextView grapheme;

	protected Button boutonValider;
	protected ImageButton imageActivity;
	protected Map<Integer,String> MapGraphemes;
	//protected Eleve eleveConnecte;
	protected ImageButton boutonvideo;
	protected ImageButton boutonSon;
	protected ImageButton boutonImageReferente;
	protected ImageButton boutonImageBorel;
	protected ImageButton menu;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrer_grapheme);
        boutonvideo=(ImageButton)findViewById(R.id.boutonVideo);
        boutonSon=(ImageButton)findViewById(R.id.boutonSon);
        boutonImageReferente=(ImageButton)findViewById(R.id.boutonReferente);
        boutonImageBorel=(ImageButton)findViewById(R.id.boutonImage);
        menu=(ImageButton)findViewById(R.id.boutonHome);
        boutonvideo.setEnabled(false);
        boutonSon.setEnabled(false);
        boutonImageReferente.setEnabled(false);
        boutonImageBorel.setEnabled(false);
        //on récupère le champs grapheme du fichier xml (son identifiant est bien grapheme)
        grapheme=(AutoCompleteTextView)findViewById(R.id.grapheme);
        
        //si le graphème a été déjà crée on interdi de le modifier 
        //car le grapheme est l'identifiant de module
        if(MainActivity.moduleCourant!=null)
        	{
        	String graphemeCourant=MainActivity.moduleCourant.getGrapheme();
	        	if(!graphemeCourant.equals("")){ 
				        	grapheme.setText(graphemeCourant);
				        	grapheme.setFocusable(false);
				            boutonvideo.setEnabled(true);
				            boutonSon.setEnabled(true);
				            boutonImageReferente.setEnabled(true);
				            boutonImageBorel.setEnabled(true);
	        	}//fin if
        	
        	}else {grapheme.setText("");
		 	boutonvideo.setVisibility(View.INVISIBLE); 
	        boutonSon.setVisibility(View.INVISIBLE);
	        boutonImageReferente.setVisibility(View.INVISIBLE);
	        boutonImageBorel.setVisibility(View.INVISIBLE);
        	}
        this.boutonValider=(Button)findViewById(R.id.buttonValiderGrapheme);
        //this.eleveConnecte=new Eleve (MainActivity.eleveConnecte);
        //on active le bouton dans une fonction a part car il ya trop de traitement
        this.activerBoutonValider();
       
        this.MapGraphemes=new HashMap<Integer,String>();
        //on inscrit l'activity actuelle comme écouteur de changement de texte de champs grapheme
       // grapheme.addTextChangedListener(this);
        this.actionnerBoutonMenu();
        //label eleve couran
        TextView labelEleveCourant=(TextView)findViewById(R.id.labelEleveConnecte);
        System.out.println("passé1");
        labelEleveCourant.setText(MainActivity.eleveConnecte.getPrenom());
        System.out.println("passé2");
    }

    
	//on ajoute un listener au bouton ajouter grapheme
	private void activerBoutonValider() {
		// TODO Auto-generated method stub
		this.boutonValider.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//on recupère le grapheme saisi
				String mot=Enregistrer_Grapheme.this.grapheme.getText().toString();
				//s'il est vide on affiche une alerte pour informer
				if(mot.compareTo("")==0)
					 {
					
					//Toast.makeText(Enregistrer_Grapheme.this,"saissez le graphème !", Toast.LENGTH_LONG).show(); 
					AlertFalse alert=new AlertFalse(Enregistrer_Grapheme.this,"graphème vide !");//Enregistrer_Photo.this);
					alert.show();
					 }
				else{
					
					//il faut vérifier si celui ci existe avec le même id d'eleve
					//s'il existe on ne l insére pas et on affiche une alert qui il existe deja dans le compte de l'élève	
					
					 ModulesBDD modulebdd = new ModulesBDD(Enregistrer_Grapheme.this);
				     modulebdd.open();
				     String id=MainActivity.eleveConnecte.getPrenom();
				     if(!modulebdd.ExistModule(id,mot.toUpperCase()))
						     {
						    	 //Toast.makeText(Enregistrer_Grapheme.this,"ce grapheme existe déjà !", Toast.LENGTH_LONG).show();
				    	       AlertFalse alert=new AlertFalse(Enregistrer_Grapheme.this,"graphème existe !");//Enregistrer_Photo.this);
							   alert.show();
						     }
				     //si le module n'existe pas
				     else{
							
					     //si le module n'existe pas on crée un  nouveau module avec la date courante 
					     //on attribu le module à l'eleve connecté
				    	//on l'ajoute à la BD  avec l'id de l eleve connecté et la date de création
							Date date=new Date();
							String d=new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(date);
							Module module= new Module(mot,"","","","",d,id);
							if(modulebdd.insertModule(module)){
								//Toast.makeText(Enregistrer_Grapheme.this,"félicitation, vous venez de créer un nouveau module !", Toast.LENGTH_LONG).show();
								AlertTrue alert=new AlertTrue(Enregistrer_Grapheme.this);//Enregistrer_Photo.this);
								alert.show();
								//on active les autres boutons pour que le 'eleve puisse créer le reste du module
								 	boutonvideo.setEnabled(true);
								 	boutonvideo.setVisibility(View.VISIBLE);
							        boutonSon.setEnabled(true);
							        boutonSon.setVisibility(View.VISIBLE);
							        boutonImageReferente.setEnabled(true);
							        boutonImageReferente.setVisibility(View.VISIBLE);
							        boutonImageBorel.setEnabled(true);
							        boutonImageBorel.setVisibility(View.VISIBLE);
							        grapheme.setFocusable(false);
							        grapheme.setEnabled(false);
							//on change le module courant par celui ajouté
							MainActivity.moduleCourant=module;
							
							}
							//si l'insertion n'est pas effectuée
							else{
								 AlertFalse alert=new AlertFalse(Enregistrer_Grapheme.this,"erreur");//Enregistrer_Photo.this);
								 alert.show();
							}
					modulebdd.close();
					}//fin else
					
				}//fin else
				}	//fin onclick
				
			});//fin listener
		
	}
public void onBackPressed(){
		
		finish();
		Intent i;
        i= new Intent(this,Accueil.class);
        startActivity(i);
	}

public void actionnerBoutonMenu(){
		
		ImageButton menu=(ImageButton)findViewById(R.id.boutonHome);
		menu.setOnClickListener(new BoutonMenuListener (Accueil.class));
        boutonImageBorel.setOnClickListener(new BoutonMenuListener(Enregistrer_Photo.class));
        boutonvideo.setOnClickListener(new BoutonMenuListener(Enregistrer_Video.class));
        boutonImageReferente.setOnClickListener(new BoutonMenuListener(Enregistrer_Image_Referente.class));
        boutonSon.setOnClickListener(new BoutonMenuListener(Enregistrer_Son.class));
 
	}
	
	
	private class BoutonMenuListener implements View.OnClickListener{
		private Class<?> C;
		public BoutonMenuListener(Class<?> c){
			super();
			C=c;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// it was the 1st button
			finish();
	    	Intent i;
            i= new Intent(Enregistrer_Grapheme.this,C);
            startActivity(i);
	    }
			
		
		}
	
    
}
