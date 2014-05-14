package vues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.example.cahierdeson.MainActivity;
import com.example.cahierdeson.Profil;
import com.example.cahierdeson.R;
import BD.ModulesBDD;
import Modeles.AlertFalse;
import Modeles.Eleve;
import Modeles.Module;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Consulter grapheme est une activité qui permet de saisir le grapheme à chercher
 * le clique sur le bouton cherhcer ouvre le module correspondant au graphéme saisi 
 * @author Amel Krouma
 *
 */
public class Consulter_Grapheme extends Activity implements TextWatcher{
	
	protected AutoCompleteTextView grapheme;

	protected ImageButton boutonChercher;
	protected Map<Integer,String> MapGraphemes;
	protected ListView listeModuleView; //conteindra la liste des module 
	
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.consulter_grapheme);
        //on récupère le champs grapheme du fichier xml (son identifiant est bien grapheme)
        grapheme=(AutoCompleteTextView)findViewById(R.id.grapheme);
        this.boutonChercher=(ImageButton)findViewById(R.id.buttonChercherGrapheme);
        //on active le bouton dans une fonction a part car il ya trop de traitement
        this.activerBoutonChercher();  
        listeModuleView =(ListView) findViewById(R.id.listModule);
        this.remplirListeModules();
        this.MapGraphemes=new HashMap<Integer,String>();
        //on inscrit l'activity actuelle comme écouteur de changement de texte de champs grapheme
        grapheme.addTextChangedListener(this);
        
        //label eleve couran
        TextView labelEleveCourant=(TextView)findViewById(R.id.labelEleveConnecte);
        labelEleveCourant.setText(MainActivity.eleveConnecte.getPrenom());
        //bouton de la barre de menu
        actionnerBoutonMenu();
        
        	 
        	 
   
        
    }
    
public void onBackPressed(){
		
		finish();
		Intent i;
        i= new Intent(this,Accueil.class);
        startActivity(i);
	}

    /**
     * remplirListe
     * Permet de remplire la liste a gauche par les modules dejà crée par l'eleve
     * les modules sont cliquable 
     * le clic sur chaque bouton ridirige vers la vue consultation de module correspondant
     * On utilise un ListCustomAdapter créé a la fin de la classe 
     */
    
	private void remplirListeModules() {
		// TODO Auto-generated method stub
		 	
			ModulesBDD modulebdd = new ModulesBDD(this);
	        modulebdd.open();
	        //on remplie une liste par la liste des graphemes de l'eleve
	        ArrayList<String> listeGraphemeModule=modulebdd.getListeModulesEleve(MainActivity.eleveConnecte.getPrenom());
	        if(listeGraphemeModule!=null){
	        ListCustomAdapter listModuleAdapter  = new ListCustomAdapter(Consulter_Grapheme.this, R.layout.element_list_bouton, listeGraphemeModule);
	        listeModuleView =(ListView) findViewById(R.id.listModule);
	        listeModuleView.setItemsCanFocus(false);
	        listeModuleView.setAdapter(listModuleAdapter);
	        }
	        modulebdd.close();
	        }	


	/**
     * au changement de texte 
     * on consulte la base de données si le grapheme existe on lui propose une liste de qui commence par le même lettre 
     */
	
	public void afterTextChanged(Editable arg0) {
		//on va accéder à la base de données et chercher la liste de graphèmes qui commencent par le mot écrit
		
		
		String achercher=this.grapheme.getText().toString();
		
		//Création d'une instance de ma classe ModuleBDD
        ModulesBDD modulebdd = new ModulesBDD(this);
        modulebdd.open();
       
        //on cherche si le grapheme existe dans la bd
        this.MapGraphemes=modulebdd.getGraphemesEleve(achercher.toUpperCase()+"%",MainActivity.eleveConnecte.getPrenom() );
       
        //on actualise la liste de graphemes proposé
        //this.listeGraphemes.removeAllViews();
        //on crée une liste qui  contient les graphemes
        ArrayList<String> ListString=new ArrayList<String>();
        //on va ajouter la liste des graphèmes récupérée de la BD dans la liste déroulante
        
         if(! (this.MapGraphemes==null) && this.MapGraphemes.size()>=1){
        		//Toast.makeText(this,""+MapGraphemes.size()+"", Toast.LENGTH_LONG).show();
	        	for(int key=0;key<this.MapGraphemes.size();key++){
	        		String graphemElement=this.MapGraphemes.get(key);
	        		ListString.add(graphemElement);
	        	}
	        		ArrayAdapter <String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ListString);
	        		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
	        		grapheme.setThreshold(1);
	        		this.grapheme.setAdapter(adapter);
	        
        }	
        
         modulebdd.close();
	}

		
	

	
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
		// TODO Auto-generated method stub
		
		
	}

	
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
			
	}

	//on ajoute un listener au bouton et par la suite o
	private void activerBoutonChercher() {
		// TODO Auto-generated method stub
		this.boutonChercher.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mot=Consulter_Grapheme.this.grapheme.getText().toString();
				if(mot.compareTo("")==0)
					 {
						AlertFalse alert=new AlertFalse(Consulter_Grapheme.this,"graphème vide !");//Enregistrer_Photo.this);
						alert.show();
			
					 }
				else{
					
					//il faut vérifier si celui ci existe dans la bd avec le même id d'eleve
					//s'il existe on charge le module courant par celui correspondant au garpheme saisi
					//et on redirige vers la vue de consultation de tout le module
					
					 ModulesBDD modulebdd = new ModulesBDD(Consulter_Grapheme.this);
				     modulebdd.open();
				     String idEleve=MainActivity.eleveConnecte.getPrenom();
					 //on recupère le module correspondant au grapheme saisi s'il en existe
				     Module moduleTrouver=modulebdd.getModuleWith(mot.toUpperCase(), idEleve);
				     modulebdd.close();
				     if(moduleTrouver==null)
						     {
						    	 AlertFalse alert=new AlertFalse(Consulter_Grapheme.this,"module introuvable !");
						    	 alert.show();
						     }
				     else{
				    	//on charge le module courant par celui récupéré de la BD
							
							MainActivity.moduleCourant=moduleTrouver;
							//Toast.makeText(Consulter_Grapheme.this,""+MainActivity.moduleCourant.ToString()+"", Toast.LENGTH_LONG).show();
							//on ridirige vers l'activity de consultation module
							Intent i;
				            i= new Intent(Consulter_Grapheme.this,ConsultationActivity.class);
				            startActivity(i);
							
							
							}
					
				     }

				
				}
			
			
				
			});
	
	
		}
	
/**
 * classe pour remplir la liste des graphemes
 * liste qui contient des bouton cliquable , chaque bouton contient un grapheme
 * le clic sur le bouton dirige vers la vue de consultation de module correspondant au grapheme 	
 * @author Ammoula
 *
 */

	public class ListCustomAdapter extends ArrayAdapter<String> {
		 private Context context;
		 int layoutResourceId;
		 String graphemeModule;
		 ArrayList<String> data = new ArrayList<String>();//liste de graphemes

		 public ListCustomAdapter(Context context, int layoutResourceId,
		   ArrayList<String> data) {
		  super(context, layoutResourceId, data);
		  this.layoutResourceId = layoutResourceId;
		  this.context = context;
		  this.data = data;
		 }

		 @Override
		 public View getView(int position, View convertView, ViewGroup parent) {
		  View row = convertView;
		  ButtonHolder holder = null;

		  if (row == null) {
		   LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		   row = inflater.inflate(layoutResourceId, parent, false);
		   holder = new  ButtonHolder();
		   holder.butonGrapheme = (Button) row.findViewById(R.id.button1);
		  
		   row.setTag(holder);
		  } else {
		   holder = (ButtonHolder) row.getTag();
		  }
		  graphemeModule = data.get(position);
		  holder.butonGrapheme.setText(graphemeModule);
		 //on actionne les boutons 
		  holder.butonGrapheme.setOnClickListener(new View.OnClickListener() {

		   @Override
		   public void onClick(View v) {
		    // TODO Auto-generated method stub
			 //on recupère le text de grapheme
			   
			   //il faut vérifier si celui ci existe dans la bd avec le même id d'eleve
			   //s'il existe on charge le module courant par celui correspondant au garpheme saisi
			   //et on redirige vers la vue de consultation de tout le module
			   Button source=(Button)v;
			   String mot=(String)source.getText(); //le texte de bouton cliqué (le grapheme)
			//Toast.makeText(Consulter_Grapheme.this,""+mot+"", Toast.LENGTH_LONG).show();
				ModulesBDD modulebdd = new ModulesBDD(Consulter_Grapheme.this);
			    modulebdd.open();
			    String idEleve=MainActivity.eleveConnecte.getPrenom();
				//on recupère le module correspondant au grapheme saisi s'il en existe
			    Module moduleTrouver=modulebdd.getModuleWith(mot.toUpperCase(), idEleve);
			    modulebdd.close();
			    if(moduleTrouver==null)
					     {
					    	 AlertFalse alert=new AlertFalse(Consulter_Grapheme.this,"probleme d'affichage !");
					    	 alert.show();
					     }
			     else{
			    	//on charge le module courant par celui récupéré de la BD
						
						MainActivity.moduleCourant=moduleTrouver;
						//Toast.makeText(Consulter_Grapheme.this,""+MainActivity.moduleCourant.ToString()+"", Toast.LENGTH_LONG).show();
						//on ridirige vers l'activity de consultation module
						Intent i;
			            i= new Intent(Consulter_Grapheme.this,ConsultationActivity.class);
			            startActivity(i);
						
						
						}
				
			     }
		   
		  });
		 

		  return row;

		 }

	}
	
	
	static class  ButtonHolder{
		Button butonGrapheme;
	}

	
	public void actionnerBoutonMenu(){
		
		ImageButton menu=(ImageButton)findViewById(R.id.boutonHome);
		menu.setOnClickListener(new BoutonMenuListener (Accueil.class));
		ImageButton profil=(ImageButton)findViewById(R.id.buttonProfil);
		profil.setOnClickListener(new BoutonMenuListener (Profil.class));
		/* ImageButton btnImage=(ImageButton) findViewById(R.id.boutonImage);
         ImageButton btnVideo=(ImageButton) findViewById(R.id.boutonVideo);
         ImageButton btnImageReferente=(ImageButton) findViewById(R.id.boutonReferente);
         ImageButton btnGrapheme=(ImageButton) findViewById(R.id.boutonGrapheme);
         btnImage.setOnClickListener(new BoutonMenuListener(Enregistrer_Photo.class));
         btnVideo.setOnClickListener(new BoutonMenuListener(Enregistrer_Video.class));
         btnImageReferente.setOnClickListener(new BoutonMenuListener(Enregistrer_Image_Referente.class));
         btnGrapheme.setOnClickListener(new BoutonMenuListener(Enregistrer_Grapheme.class));
         */
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
	    	Intent i;
            i= new Intent(Consulter_Grapheme.this,C);
            startActivity(i);
	    }
			
		
		}
	
}



	

