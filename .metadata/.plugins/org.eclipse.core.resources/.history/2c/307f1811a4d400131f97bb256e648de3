
	package vues;

	import java.util.ArrayList;
import java.util.Random;


	import com.example.cahierdeson.MainActivity;
import com.example.cahierdeson.R;

	import BD.ModulesBDD;
import Modeles.AlertFalse;
import Modeles.AlertTrue;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Quizz extends Activity{

	/**
	 * cette vue permet jouer avec les images correspondant à un graphème
	 * on tape le grapheme puis on choisi les image correspondante, si c'est vrai alors ok si non il doit rejouer
	 * si le grapheme existe dans la base de données on propose 2 images et l'élève doit en choisir une 
	 * si le grapheme n'existe pas on propose à l'éleve de selectionner ou capturer une photo
	 * @author Ammoula
	 *
	 */

		
		private ImageView imgBorel1;
		private ImageView imgBorel2;
		protected String graphemeCourant=MainActivity.moduleCourant.getGrapheme();

		private String idEleveCourant=MainActivity.eleveConnecte.getPrenom();
		int partieImageFausse;
		private ModulesBDD modulebdd ;
		private ImageButton menuActivity;
		private ImageButton profil;
		private ImageButton rechercheQuizz;
		private ImageButton boutonAjouterImage;
		private String LienBorelTrue;
		
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enregistrer_photo);
		TextView labelEleveCourant=(TextView)findViewById(R.id.labelEleveConnecte);
		TextView labelGraphemeCourant=(TextView)findViewById(R.id.graphemeCourant);
		if(MainActivity.moduleCourant!=null) labelGraphemeCourant.setText(MainActivity.moduleCourant.getGrapheme());
	    if(MainActivity.eleveConnecte!=null)labelEleveCourant.setText(MainActivity.eleveConnecte.getPrenom());
		actionnerBoutons();
		        //si le module courant a été livré avec l'application 
		        //autrement dit au moment de la création de grapheme l'eleve à choisi un grapheme qui été dans la liste de proposition
		        //alors on recupère le lien de l'imageet le propose à l'élève
		        
		       //Création d'une instance de ma classe ModuleBDD
		         modulebdd = new ModulesBDD(this);
		         modulebdd.open();
		        
		        
		        LienBorelTrue=modulebdd.getLienImageBD(MainActivity.moduleCourant.getGrapheme());
		        //Toast.makeText(this,"grapheme courant"+ LienBorelTrue+"", Toast.LENGTH_LONG).show();
		        //Toast.makeText(this,"lien borel"+ LienBorelTrue+"", Toast.LENGTH_LONG).show();
		        //si le lien existe dans la bd 
		        if(!LienBorelTrue.equals(null)){
		        	
		        	//Toast.makeText(this,"lien borel not vide " +LienBorelTrue+"", Toast.LENGTH_LONG).show();
			        //La vue contient u debut 2 images, on va changer ces image par celles proposées
			    	//on recupère les images
		        	imgBorel1=(ImageView)findViewById(R.id.imageBorel1);
			    	        
			    	imgBorel2=(ImageView)findViewById(R.id.ImageBorel2);
			    	        		        
			        //on crée un drowable avec le lien de l'image récupére de la bd  

			    	int imgId = getResources().getIdentifier(LienBorelTrue, "drawable", getPackageName());

		    	    //on insere l'image ah hazard dans l'ativité
		    	    Random rand=new Random();
		    	    int i=rand.nextInt(10); 
		    	     // partieImageFausse; sert à determiner dans quelle partie sera la deuxième image fausse
		    	    //si i est impaire alors on ajoute l'image correcte dans la partie1 a coté de bouton de menu
		    	    //et l image fausse de l'autre coté, si nom ça sera l'inverse
		    	    if(i%2!=0)
		    	    	{
		    	    	imgBorel1.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));
		    	    	//imgBorel1.setImageURI(imgUri);
		    	    	partieImageFausse=2;
		    	    	}
		    	    else{
		    	    	imgBorel2.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));
		    	    	//imgBorel2.setImageURI(imgUri);
		    	    	 partieImageFausse=1;
		    	       }
		    	    	
			    	placerImageFausse(partieImageFausse);
			    	
			    	
			    	
	     
			        //on ajoute au hazard une autre image et l'inserer dans la 2ème proposition
		        }//fin if
		        modulebdd.close();
		 }//fin void
		

		/**
		 * permet de placer la deuxième image qui est fausse dans la partie mis en parametre
		 * @param partie
		 */
		
	private void placerImageFausse(int partie){
		modulebdd = new ModulesBDD(this);
		//on recupère quelques liens des images dont le grapheme est différent au grapheme courant 
		ArrayList<String>liensimg=modulebdd.getLiensImageBD(graphemeCourant);
		//on choisi un lien au hazard 
		//Toast.makeText(Enregistrer_Photo.this,"lienimg"+liensimg.size(), Toast.LENGTH_LONG).show();
		if(!liensimg.equals(null) && liensimg.size()>0 )
			 {
			 String lienBorelFaut="";
			 Random rand=new Random();
			 while(lienBorelFaut.equals("")){
			    int i=rand.nextInt(liensimg.size()-1); 
			    lienBorelFaut=liensimg.get(i);
			 }
		 
		
		//on crée l'image correspondante 
		 int imgId = getResources().getIdentifier(lienBorelFaut.toLowerCase(), "drawable", getPackageName());
	 	 Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgId);	 
		 //on l'insére dans la partie correspondante
	 	 if(partie==1) imgBorel1.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));
	 	 else imgBorel2.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));
	 	 	
			 }
		modulebdd.close();

	}

	/**
	 * Actionner les bouron
	 */

	private void actionnerBoutons(){
		
		menuActivity= (ImageButton)findViewById(R.id.boutonHome);
		menuActivity.setOnClickListener(new MenuListener(1));
		
		profil=(ImageButton)findViewById(R.id.boutonProfil);
		//profil.setOnClickListener(new MenuListener(2));
		
		rechercheQuizz=(ImageButton)findViewById(R.id.rechercheQuizz);
		//rechercheQuizz.setOnClickListener(new MenuListener(3));
		
		boutonAjouterImage=(ImageButton)findViewById(R.id.boutonAjouterImage);
		//boutonAjouterImage.setOnClickListener(new MenuListener(0));
		
	}
public void onBackPressed(){
		
		finish();
		Intent i;
        i= new Intent(this,Accueil.class);
        startActivity(i);
	}
	/***
	 * on crée un écouteur pour chaque bouton dans la barre de menu a droite
	 * 
	 */
	public class MenuListener implements View.OnClickListener{
	    int destination;
	    
	    public MenuListener(int des){
	    	super();
	    	this.destination=des;
	    }
	    
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		   //on actionne enregistrer _photo bouton numero 4
			switch(destination){
			//bouton ajouter image
			case 0:{ 
					//verifier que l'enfant à selectionné la bonne image
				    RadioButton radio1=(RadioButton)findViewById(R.id.radio1);
				    RadioButton radio2=(RadioButton)findViewById(R.id.radio2);
				    if((radio1.isChecked() && partieImageFausse==1) || (radio2.isChecked() && partieImageFausse==2))
				    {//Toast.makeText(Enregistrer_Photo.this,"Image fausse :( ", Toast.LENGTH_LONG).show();
				    	AlertFalse alert=new AlertFalse(Quizz.this,"faux !");//Enregistrer_Photo.this);
						alert.show();
				    }
				    else {
				    	Toast.makeText(Quizz.this,"Image vraie ", Toast.LENGTH_LONG).show();
				    
				    	AlertTrue alert=new AlertTrue(Quizz.this);//Enregistrer_Photo.this);
						alert.show();
				    	
				    }
				    	
				    }break;
	
			//bouton qui redirige vers la vue accueil
			case 1: {
					Intent i;
					i= new Intent(Quizz.this,Accueil.class);
					finish();
					startActivity(i);} break;
					
			case 2: {
				Intent i;
				i= new Intent(Quizz.this,Modif_profil.class);
				finish();
				startActivity(i);} break;
			case 3: {
				Intent i;
				i= new Intent(Quizz.this,Recherche_Quizz.class);
				finish();
				startActivity(i);} break;
			}
		}
		
	}
		


	

	
	
}
