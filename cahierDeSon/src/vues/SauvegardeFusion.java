package vues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import BD.ElevesBDD;
import BD.ModulesBDD;
import Modeles.Eleve;
import Modeles.Module;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.example.cahierdeson.MainActivity;

public class SauvegardeFusion {

	private Context ctx;
	private InputStream input;
	private JsonReader reader;
    private OutputStream output;
	private JsonWriter writer;
	private String prenom = null, mot_de_pass= null, classe = null;
    private ArrayList<Module> toutlesmodules; 
    private String graph = null, imgB = null, imgR = null, son = null, video = null, date = null;
	private String filepath;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
 
	public SauvegardeFusion(Context c) {
		// TODO Auto-generated constructor stub
		ctx=c;
	}
	
	
	public boolean importerModuleEleveJsonInsertBD()
	{
		int i;
		 filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom();
			if((new File(filepath)).exists())
			{
				filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom()+"/"+MainActivity.eleveConnecte.getPrenom()+"_"+"Module"+"_"+Build.SERIAL+".json";
				
				// on verifie que le fichier provient d'une autre tablette
				if(filepath.indexOf(Build.SERIAL)==-1)
				{
					toutlesmodules = new ArrayList<Module>();
					try {
						input = new FileInputStream(filepath);
						try {
							reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
							try {
								reader.beginArray();
								 while (reader.hasNext()) {
									reader.beginObject();
								     while (reader.hasNext()) {
								       String name = reader.nextName();
								       if (name.equals("graph")) {
								         graph = reader.nextString();
								       } else if (name.equals("imgB")) {
								    	   imgB = reader.nextString();
								       }else if (name.equals("imgR")) {
								    	   imgR = reader.nextString();
								       }else if (name.equals("son")) {
								    	   son = reader.nextString();
								       }else if (name.equals("video")) {
								    	   video = reader.nextString();
								       }else if (name.equals("date")) {
								    	   date = reader.nextString();
								       }else if (name.equals("prenom")) {
								    	   prenom = reader.nextString();
								       }else {
								           reader.skipValue();
								       }
								       
								       toutlesmodules.add(new Module(graph,imgB,imgR,son,video,date,prenom));
								     }
								     reader.endObject();	
							     }
							     reader.endArray();
							     reader.close();
							     //insertion des modules dans la BD
							     ModulesBDD bd = new  ModulesBDD(ctx);
							     bd.open();
							     i=0;
							     while(i<toutlesmodules.size())
							     {
							    	 bd.insertModule(toutlesmodules.get(i));
							    	 i++;
							     }
							     bd.close();
							     
							     return true;
							     
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						     
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
		return false;
	}
	
	
	// nom du fichier: prenom_module_N�deserie tablette
	public void exporterModuleEleveJson()
	{
		  int i=0;
		// on recup�re tous les modules cr�es par l'�l�ve
		 ModulesBDD M = new ModulesBDD(ctx);
	        M.open();
		    toutlesmodules = M.getAllModulesEleve(MainActivity.eleveConnecte.getPrenom()); 
		    if(toutlesmodules!=null)
			 {
		    	while(i<toutlesmodules.size())
			    {
			    	   Log.i("TEST ALL MODULE:",toutlesmodules.get(i).getGrapheme());
			    	   i++;
			    }
			    M.close();
			}
		filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom();
		if((new File(filepath)).exists())
		{
			filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom()+"/"+MainActivity.eleveConnecte.getPrenom()+"_"+"Module"+"_"+Build.SERIAL+".json";
			try {
				output = new FileOutputStream(filepath);
				try {
						writer = new JsonWriter(new OutputStreamWriter(output, "UTF-8"));
						writer.setIndent("  ");
					    writer.beginArray();
					i=0;
					 while(i<toutlesmodules.size())
					 {
					    writer.beginObject();
					    writer.name("graph").value(toutlesmodules.get(i).getGrapheme());
						writer.name("imgB").value(toutlesmodules.get(i).getLienImageBorel());
						writer.name("imgR").value(toutlesmodules.get(i).getLienImageReferente());
						writer.name("son").value(toutlesmodules.get(i).getLienSon());
						writer.name("video").value(toutlesmodules.get(i).getLienVideo());
						writer.name("date").value(toutlesmodules.get(i).getDateCreation());
						writer.name("prenom").value(toutlesmodules.get(i).getIdEleve());
						writer.endObject();
						i++;
					}
					 writer.endArray();
					 writer.close();
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	
	
	
	public boolean importerEleveJsonInsertBD()
	{
		
	    filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom();
		if((new File(filepath)).exists())
		{
			filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom()+"/"+MainActivity.eleveConnecte.getPrenom()+"_SAVE"+".json";
			
			// on verifie que le fichier provient d'une autre tablette
			if(filepath.indexOf(Build.SERIAL)==-1)
			{
				try {
					input = new FileInputStream(filepath);
					try {
						reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
						try {
							reader.beginArray();
							 while (reader.hasNext()) {
								reader.beginObject();
							     while (reader.hasNext()) {
							       String name = reader.nextName();
							       if (name.equals("prenom")) {
							         prenom = reader.nextString();
							       } else if (name.equals("mot_de_pass")) {
							    	   mot_de_pass = reader.nextString();
							       }else if (name.equals("classe")) {
							    	   classe = reader.nextString();
							       }else {
							           reader.skipValue();
							       }
							     }
							     reader.endObject();	
						     }
						     reader.endArray();
						     reader.close();
						     //insertion de l'�l�ve dans la bd
						     Eleve el= new Eleve(prenom,mot_de_pass,classe);
						     ElevesBDD bd = new ElevesBDD(ctx);
						     bd.open();
						     bd.insertEleve(el);
						     bd.close();
						     
						     return true;
						     
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					     
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return false;
		
	}
	
	// nom du fichier: prenom_N�deserie tablette	
	public void exporterEleveJson()
	{
		
	
		filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom();
		if((new File(filepath)).exists())
		{
			filepath = Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom()+"/"+MainActivity.eleveConnecte.getPrenom()+"_"+Build.SERIAL+".json";
			try {
				output = new FileOutputStream(filepath);
				try {
					writer = new JsonWriter(new OutputStreamWriter(output, "UTF-8"));
					writer.setIndent("  ");
					writer.beginArray();
					writer.beginObject();
					writer.name("prenom").value(MainActivity.eleveConnecte.getPrenom());
					writer.name("mot_de_pass").value(MainActivity.eleveConnecte.getMotDePasse());
					writer.name("classe").value(MainActivity.eleveConnecte.getClasse());
					writer.endObject();
					writer.endArray();
					writer.close();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	
	}
	


}
