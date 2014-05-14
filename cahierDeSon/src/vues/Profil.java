package vues;


import java.util.ArrayList;

import BD.ElevesBDD;
import Modeles.CircleView;

import com.example.cahierdeson.MainActivity;
import com.example.cahierdeson.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.view.Menu;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Profil extends Activity implements View.OnClickListener {
	
	AutoCompleteTextView autoText = null;// instance du text autoComplete
	TextView txt = null;//Instance du Labeltext
	Spinner classes = null;
	protected StringBuilder pwd = new StringBuilder("");//Mot de passe en clair
	protected ArrayList<CircleView> c = new ArrayList<CircleView>();// nos objets-dessins
	protected int[] colors = new int[5];
	protected boolean[] colored = new boolean[5];//tableau de verification : si tel objet est coloré 
	private enum Couleur{ RD,OG,GN,YL,GR,BE,WT;}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		
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
		ImageButton btn_nxt = (ImageButton) findViewById(R.id.imageButton1);
		
		//================================================
		
		//==========On instancie les Zones de texte
		txt = (TextView) findViewById(R.id.textView1);
		autoText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		classes = (Spinner) findViewById(R.id.spinner1);
		//classes.setEnabled(false);
		autoText.setEnabled(false);
		autoText.setText(MainActivity.eleveConnecte.getPrenom());
		//=====================================================
		decrypter(MainActivity.eleveConnecte.getMotDePasse());
		//=====================On dessine 
		c.add(new CircleView(this,1,colors[0]));
		c.add(new CircleView(this,1,colors[1]));
					
		c.add(new CircleView(this,3,colors[2]));
		 
		c.add(new CircleView(this,4,colors[3]));
		 
		c.add(new CircleView(this,5,colors[4]));
		
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
		
		//======================================
		
		//============== Appel sub
		
		
	}

	@Override
	public void onClick(View v){
		
		//zero sera l'indice de la forme qui n'est pas encore colorié
		int zero = find_zero();
		
		//
		
	
		if(v.getId()== R.id.imageButton1) {
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
	
	public void onBackPressed(){
		
		finish();
		Intent i;
        i= new Intent(this,Accueil.class);
        startActivity(i);
	}
	//====Décrypter mdp
	
	public void decrypter(String s){
		String sbs;
		Couleur couleur;
		for(int i =0;i<10;i=i+2){
			sbs = s.substring(i, i+2);
			int j = i/2;
			switch(Couleur.valueOf(sbs)){
			
				case RD:
					colors[j]=(Color.RED);
					break;
				case WT:
					colors[j]=(Color.WHITE);
					break;
				case YL:
					colors[j]=(Color.YELLOW);
					break;
				case GN:
					colors[j]=(Color.GREEN);
					break;
				case GR:
					colors[j]=(Color.LTGRAY);
					break;
				case BE:
					colors[j]=(Color.BLUE);
					break;
				case OG:
					colors[j]=(Color.MAGENTA);
					break;
			}
			
		
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
	
	public boolean get_colored(int i){
		
		return colored[i];
	}
	
	public void set_colored(boolean state, int i){
		
		colored[i] = state;
	}
	
	//méthode qui renvoie l'objet-dessin d'indice i
	public CircleView get_c(int i){
		
		return c.get(i);
		
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
	public void valider(){
		if (autoText.getText().toString().isEmpty() || pwd.toString().isEmpty()){
			
			Toast.makeText(getApplicationContext() , "Ouups! Clé manquante !", Toast.LENGTH_LONG).show();
		}
		else
		{
			MainActivity.eleveConnecte.setClasse(classes.getSelectedItem().toString());
			MainActivity.eleveConnecte.changeMotDePass(pwd.toString());
			ElevesBDD elevesBDD = new ElevesBDD(this);
			elevesBDD.open();
			boolean res;
			res = elevesBDD.updateEleves(MainActivity.eleveConnecte.getPrenom(),MainActivity.eleveConnecte);
			if(res == false){
				Toast.makeText(getApplicationContext() ,"Modification échouée" , Toast.LENGTH_LONG).show();
			}
			else{
			Toast.makeText(getApplicationContext() , "Modification réussie", Toast.LENGTH_LONG).show();
			finish();
			Intent i;
            i= new Intent(this,Accueil.class);
            startActivity(i);
	
					}
		}
		
	}
	

}


	