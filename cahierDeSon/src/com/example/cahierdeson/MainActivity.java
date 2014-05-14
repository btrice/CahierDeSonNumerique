package com.example.cahierdeson;


import java.util.ArrayList;
import java.util.Locale;

import BD.ElevesBDD;
import BD.ModulesBDD;
import Modeles.CircleView;
import Modeles.Eleve;
import Modeles.Module;

import com.example.cahierdeson.R;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;

import android.view.Menu;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import vues.Accueil;
import vues.Enregistrer_Grapheme;
import vues.Subscribe;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements View.OnClickListener {
	private TextToSpeech ttobj;
	private int cpt =3; // Compteur d'erreur de mot de passe
	AutoCompleteTextView autoText = null;// instance du text autoComplete
	TextView txt = null;//Instance du Labeltext
	protected StringBuilder pwd = new StringBuilder("");//Mot de passe en clair
	protected ArrayList<CircleView> c = new ArrayList<CircleView>();// nos objets-dessins
	protected boolean[] colored = new boolean[5];//tableau de verification : si tel objet est coloré 
	
	public static Eleve eleveConnecte=null;//l'eleve connecte
	public static Module moduleCourant=null;//le module courant
	public static boolean IsvueConsulter=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*ModulesBDD M= new ModulesBDD(this);
		M.open();
		M.SelectAllModules();
		M.close();*/
		
		//exporter();
		/* Eleve E =new Eleve ("dictionnaire", "pass","ce1");
	      Module M1=new Module("O", "borel_O.jpg","","","","","dictionnaire");
	      Module M2=new Module("B", "borel_B.jpg","","","","","dictionnaire");
	      Module M3=new Module("A", "borel_A.jpg","","","","","dictionnaire");
	      Module M4=new Module("CH", "borel_CH.jpg","","","","","dictionnaire");
	      Module M5=new Module("F", "borel_F.jpg","","","","","dictionnaire");
	      Module M6=new Module("I", "borel_I.jpg","","","","","dictionnaire");
	      ModulesBDD bdd=new ModulesBDD(this);
	      bdd.open();
	      bdd.insertModule(M1);
	      bdd.insertModule(M2);
	      bdd.insertModule(M3);
	      bdd.insertModule(M4);
	      bdd.insertModule(M5);
	      bdd.insertModule(M6);
	      bdd.close();
	      ElevesBDD bddE=new ElevesBDD(this);
	      bddE.open();
	      bddE.insertEleve(E);
	      bddE.close();
	      
	      
	      
	        //bouton enregistrer grapheme
		
		*/
		//============On instancie les contenaires
		RelativeLayout face  =(RelativeLayout) findViewById(R.id.ln); 
		LinearLayout r_eye  =(LinearLayout) findViewById(R.id.lay_r_eye); 
		LinearLayout l_eye  =(LinearLayout) findViewById(R.id.lay_l_eye); 
		LinearLayout noise  =(LinearLayout) findViewById(R.id.lay_noise); 
		LinearLayout mouth  =(LinearLayout) findViewById(R.id.lay_mouth);
		//==================================
	
		//=============On instancie les boutons
		Button btn_red = (Button) findViewById(R.id.ButtonRed);
		Button btn_org = (Button) findViewById(R.id.ButtonOrange);
		Button btn_yel = (Button) findViewById(R.id.ButtonYellow);
		Button btn_grn = (Button) findViewById(R.id.ButtonGreen);
		Button btn_blu = (Button) findViewById(R.id.ButtonBlue);
		Button btn_gry = (Button) findViewById(R.id.ButtonGrey);
		Button btn_wht = (Button) findViewById(R.id.buttonWhite);
		ImageButton btn_nxt =  (ImageButton) findViewById(R.id.BoutonNxt);
		ImageButton btn_sub= (ImageButton) findViewById(R.id.BoutonInsc);
		//================================================
		
		//==========On instancie les Zones de texte
		txt = (TextView) findViewById(R.id.textView1);
		autoText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		//=====================================================
	
		//=====================On dessine 
		c.add(new CircleView(this,1,Color.BLACK));
		c.add(new CircleView(this,1,Color.BLACK));
					
		c.add(new CircleView(this,3,Color.BLACK));
		 
		c.add(new CircleView(this,4,Color.BLACK));
		 
		c.add(new CircleView(this,5,Color.BLACK));
	
		//====================================================
		
		//====================On ajoute les dessins aux vues
		
		r_eye.addView(c.get(1));
		l_eye.addView(c.get(0));
		noise.addView(c.get(2));
		mouth.addView(c.get(3));
		face.addView(c.get(4));
		//=========================================================
		
		
		//=============On place les écouteurs
		
		btn_red.setOnClickListener(this);
		btn_org.setOnClickListener(this);
		btn_yel.setOnClickListener(this);
		btn_grn.setOnClickListener(this);
		btn_blu.setOnClickListener(this);
		btn_gry.setOnClickListener(this);
		btn_wht.setOnClickListener(this);
		btn_nxt.setOnClickListener(this);
		btn_sub.setOnClickListener(this);
		//======================================
		
		//============== Appel sub
		
		
	}

	@Override
	public void onClick(View v){
		
		//zero sera l'indice de la forme qui n'est pas encore colorié
		int zero = find_zero();
		
		//
		
		if(v.getId()== R.id.BoutonInsc) {
			Intent intentSub = new Intent(this,Subscribe.class);
			startActivity(intentSub);
			
		}
		if(v.getId()== R.id.BoutonNxt) {
			valider();
			
		}
		//Si tous les objets-dessins ont été coloré, c'est pas la peine de tester
		if(zero==-1){
			System.out.println("On a fini de colorier");
			// (CECI EST UN TEST) On print le pwd pour voir
			System.out.println(pwd.toString());
			return;
		}
		
		switch(v.getId()){
		
		case R.id.ButtonRed:
			pwd.append("RD");
			this.get_c(zero).setColor(Color.RED);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			break;
		case R.id.ButtonOrange:
			
			this.get_c(zero).setColor(Color.MAGENTA);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			pwd.append("OG");
		
			break;
		case R.id.ButtonYellow:
			
			this.get_c(zero).setColor(Color.YELLOW);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			pwd.append("YL");
			break;
		case R.id.ButtonGreen:
			
			this.get_c(zero).setColor(Color.GREEN);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			pwd.append("GN");
			break;
		case R.id.ButtonBlue:
			
			this.get_c(zero).setColor(Color.BLUE);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			pwd.append("BE");
			break;
		case R.id.ButtonGrey:
			
			this.get_c(zero).setColor(Color.LTGRAY);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			pwd.append("GR");
			break;
		case R.id.buttonWhite:
			
			this.get_c(zero).setColor(Color.WHITE);
			this.get_c(zero).invalidate();
			this.set_colored(true,zero);
			pwd.append("WT");
			break;
	
		}
		
		
	
	}
	
	//=====Fonction qui determine la prochiane forme a colorier
	public int find_zero(){
		
		for(int i=0;i<5;i++){
			
			if(colored[i]==false)
				return i;	
		}
		//===Toutes les formes ont une couleur
		return -1;
	}
	public void refresh_zero(){
			for(int i=0;i<5;i++){
					colored[i] = false;
			
				}
		pwd.setLength(0);//On Clean le string.
	}
	
	public boolean get_colored(int i){
		
		return colored[i];
	}
	
	public void set_colored(boolean state, int i){
		
		colored[i] = state;
	}
	
	//méthode qui renvoie l'objet-dessin d'indice i
	public CircleView get_c(int i){
		
		return (c.get(i));
		
		}
	
	//=== Méthode utile pour le HASHAGE
	private static String convertToHex(byte[] data) throws java.io.IOException 
    {
            
            
           StringBuffer sb = new StringBuffer();
           String hex=null;
            
           hex=Base64.encodeToString(data, 0);
            
           sb.append(hex);
                        
           return sb.toString();
       }
    
	//========Méthode de HASHAGE======
	public String hashMe(StringBuilder password){
		
		MessageDigest mdSha1 = null;
		String hash ="";
        try
        {
          mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
          Log.e("CahierDeSons", "Erreur init. SHA1 Digest");
        }
        try {
            mdSha1.update(password.toString().getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try {
            hash=convertToHex(data);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
		
        return hash;
	}
	
	//=======Vérification du formulaire de connexion(prochain Script de connexion)
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void valider(){
		
		if (autoText.getText().toString().isEmpty() || pwd.toString().isEmpty()){
			
			Toast.makeText(getApplicationContext() , "Champs (prénom) ou (mot de passe) vide !", Toast.LENGTH_LONG).show();
		}
		else
		{
			//String s = this.hashMe(pwd);
			String s = pwd.toString();
			ElevesBDD eleveBDD = new ElevesBDD(this);
			eleveBDD.open();
			
			 eleveConnecte = eleveBDD.getEleveWith("prenom", autoText.getText().toString());
			if(eleveConnecte==null){
				Toast.makeText(getApplicationContext() , "Cette personne n'est pas inscrite !", Toast.LENGTH_LONG).show();
			}
			else{
					s=s.replaceAll("\\n","");//On nettoie le string
			
					if(s.toString().equals(eleveConnecte.getMotDePasse().toString())==true){
			
						Toast.makeText(getApplicationContext() , "Connexion réussie !", Toast.LENGTH_LONG).show();
						finish();
						Intent i;
			            i= new Intent(this,Accueil.class);
			            startActivity(i);
				
					}
					else{
						
						Toast.makeText(getApplicationContext() , "Mot de passe incorrect !", Toast.LENGTH_LONG).show();
					    refresh_zero();//On autorise à nouveau le coloriage
					    cpt--;
						if(cpt <=0){
							Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
							final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
							r.play();
							final AlertDialog.Builder alert = new AlertDialog.Builder(this);

							alert.setTitle("DEMANDE DE LA CLE PROFESSEUR ");
							alert.setMessage("Veuillez entrer la super clé SVP !");

							// Set an EditText view to get user input 
							final EditText input = new EditText(this);
							input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
							alert.setView(input);

							alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							  String value = input.getText().toString();
							  		if(value.equals("sup'galilée"))
							  		{
							  		
							  			Toast.makeText(getApplicationContext() , "Connexion réussie !", Toast.LENGTH_LONG).show();
							  			r.stop();
							  			finish();
										Intent i;
							            i= new Intent(getApplicationContext(),Accueil.class);
							            startActivity(i);
							  		}
							  		else{
							  			alert.setMessage("Clé incorrecte, Veuillez réessayer SVP !");
							  			
							  		}
							  		cpt =0;
							}
							});

							alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
							  public void onClick(DialogInterface dialog, int whichButton) {
							    
							  }
							});

							alert.show();
							
							 
						}
					}
			
			}
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*public void exporter()
	 {
		 String databaseName="cahierdeson";
		 try {
	            File sd = Environment.getExternalStorageDirectory();
	            File data = Environment.getDataDirectory();
	            
	            if (sd.canWrite()) {
	                String currentDBPath = "/data/"+getPackageName()+"//databases//"+databaseName;
	                String backupDBPath = "/"+"CahierDeSon"+"/"+"backupBDCahierDeSon.db";
	                File currentDB = new File(data, currentDBPath);
	                File backupDB = new File(sd, backupDBPath);

	                if (currentDB.exists()) {
	                    FileChannel src = new FileInputStream(currentDB).getChannel();
	                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                    dst.transferFrom(src, 0, src.size());
	                    src.close();
	                    dst.close();
	                }
	                else
	                {
	                	Toast.makeText(MainActivity.this,currentDBPath, Toast.LENGTH_LONG).show();
						
	                }
	  
	            }
	        } catch (Exception e) {

	        }
	 }*/
   
}










