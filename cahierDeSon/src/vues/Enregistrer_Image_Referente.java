package vues;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import com.example.cahierdeson.MainActivity;
import com.example.cahierdeson.R;
import BD.ModulesBDD;
import Modeles.AlertFalse;
import Modeles.AlertTrue;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaActionSound;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
/**
 * cette vue permet de capturer une image et l'enregistrer dans le internal storage 
 * et enregistrer le lien dans la BD
 * @author Ammoula
 *
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class Enregistrer_Image_Referente extends Activity{


	private boolean sauvegarder=false;
	private boolean imagePrise=false;
	private  ImageButton butonRetake;
	private ImageButton butonValider;
	private ImageButton captureButton;
   private Camera cameraObject;
   private ShowCamera showCamera;
   private MediaRecorder mMediaRecorder;
   private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
   public static final int MEDIA_TYPE_IMAGE = 1;
   private Uri fileUri;
   private byte[] data; //contiendra la photo prise
   private FrameLayout preview;
   private String lien_image;
   private AlertDialog.Builder confirmation;
   
   //construire le son au moment de capture de l'image
   private Camera.ShutterCallback shutter= new ShutterCallback(){
	   public void onShutter(){
		   MediaActionSound sound=new MediaActionSound();
		   sound.play(MediaActionSound.SHUTTER_CLICK);
		   
	   }
   };
   
   //si l'image est prise on enregistre cette image dans un tableau de data pour le manipuler apr�s 
   private PictureCallback mPicture = new PictureCallback() {

	      @Override
	      public void onPictureTaken(byte[] dat, Camera camera) {
	    	  //Toast.makeText(Enregistrer_Image_Referente .this,"data takin picture+",Toast.LENGTH_LONG).show();
	    	 data=dat;
	    	 //Toast.makeText(Enregistrer_Image_Referente .this,"data takin picture+"+data,Toast.LENGTH_LONG).show();
	    	 
	    	  
	      }
	     // camera.release();
	   };

   
   /** Check if this device has a camera */
   private boolean checkCameraHardware(Context context) {
       if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
           // this device has a camera
           return true;
       } else {
           // no camera on this device
           return false;
       }
   }

   /** A safe way to get an instance of the Camera object. */
   public static Camera getCameraInstance(){
       Camera c = null;
       try {
           c = Camera.open(); // attempt to get a Camera instance
       }
       catch (Exception e){
           // Camera is not available (in use or does not exist)
       }
       return c; // returns null if camera is unavailable
   }
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.enregistrer_image_referente);
      //pic = (ImageView)findViewById(R.id.imageView1);
      cameraObject = getCameraInstance();
      //setFrontCamera();
      showCamera = new ShowCamera(this, cameraObject);
      preview = (FrameLayout) findViewById(R.id.camera_preview);
      preview.addView(showCamera);
      //buttons
      butonRetake=(ImageButton) findViewById(R.id.retake);
      butonValider=(ImageButton) findViewById(R.id.enregistrerPhoto);
      captureButton = (ImageButton) findViewById(R.id.take);
      //hide buttons retake and valdate
      butonRetake.setVisibility(View.INVISIBLE);
      butonValider.setVisibility(View.INVISIBLE);
      //label eleve couran
      TextView labelEleveCourant=(TextView)findViewById(R.id.labelEleveConnecte);
      labelEleveCourant.setText(MainActivity.eleveConnecte.getPrenom());
      
      //label de grapheme courant
      
      TextView graphemeCourant=(TextView)findViewById(R.id.graphemeCourant);
      if(MainActivity.moduleCourant!=null)graphemeCourant.setText(MainActivity.moduleCourant.getGrapheme());
      // Add a listener to the Capture button
      ActionnerBoutonMenu();
      captureButton.setOnClickListener(
          new View.OnClickListener() {
              public void onClick(View v) {
                  // get an image from the camera
            	  cameraObject.takePicture(shutter, null,mPicture);
            	  //show the button retake and validate
            	  butonRetake.setVisibility(View.VISIBLE);
            	  butonValider.setVisibility(View.VISIBLE);
            	  captureButton.setVisibility(View.INVISIBLE);
            	  Enregistrer_Image_Referente.this.imagePrise=true;
            	  //cameraObject.release();
              }
          }
      );
      
      //add listener to retake button
      
      butonRetake.setOnClickListener(new View.OnClickListener() {
    	  @Override
    	  public void onClick(View v) {
    	  cameraObject.release();
          cameraObject = getCameraInstance();
          showCamera = new ShowCamera(Enregistrer_Image_Referente .this, cameraObject);
          preview.removeAllViews();//= (FrameLayout) findViewById(R.id.camera_preview);
          preview.addView(showCamera);
          Enregistrer_Image_Referente .this.data=null;
    	  butonValider.setVisibility(View.INVISIBLE);
    	  butonRetake.setVisibility(View.INVISIBLE);
    	  captureButton.setVisibility(View.VISIBLE);
    	  }
    	  }); 
      
      //add listener to valdate button
      butonValider.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SaveImage(true);
		 if(MainActivity.IsvueConsulter){
				Intent i;
			       i= new Intent(Enregistrer_Image_Referente.this,ConsultationActivity.class);
			       MainActivity.IsvueConsulter=false;
			       startActivity(i);
			       
		 }
		}
	});
      
      
       
   }
   public void onBackPressed(){
		
		finish();
		Intent i;
       i= new Intent(this,Accueil.class);
       startActivity(i);
	}
 

   
 /* ** Create a file Uri for saving an image */
   private Uri getOutputMediaFileUri(int type){
         return Uri.fromFile(getOutputMediaFile(type));
   }

   /** Create a File for saving an image */
   private File getOutputMediaFile(int type){
       // To be safe, you should check that the SDCard is mounted
       // using Environment.getExternalStorageState() before doing this.
	  // on recup�re l'ann�e courante car /CLASSE_AnneeCourante/
       Calendar calendar = Calendar.getInstance();
       int year = calendar.get(Calendar.YEAR);
       File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/"+"CahierDeSon"+"/"+MainActivity.eleveConnecte.getClasse()+"_"+year+"/"+MainActivity.eleveConnecte.getPrenom()+"/"+"Image");
       // Create the storage directory if it does not exist
       if (! mediaStorageDir.exists()){
           if (! mediaStorageDir.mkdirs()){
               Log.d("chier de son", "failed to create directory");
               return null;
           }
       }
       // Create a media file name with grapheme name.jpg
       //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
       File mediaFile;
       if (type == MEDIA_TYPE_IMAGE){
    	   lien_image=mediaStorageDir.getPath() + File.separator +"referente_"+MainActivity.moduleCourant.getGrapheme()+ ".jpg";
           mediaFile = new File(lien_image);
       } else{
           return null;
       }

       return mediaFile;
   }
   
   
   @Override
   protected void onPause() {
       super.onPause();
       releaseMediaRecorder();       // if you are using MediaRecorder, release it first
       releaseCamera();              // release the camera immediately on pause event
   }

   private void releaseMediaRecorder(){
       if (mMediaRecorder != null) {
           mMediaRecorder.reset();   // clear recorder configuration
           mMediaRecorder.release(); // release the recorder object
           mMediaRecorder = null;
           cameraObject.lock();           // lock camera for later use
       }
   }

   private void releaseCamera(){
       if (cameraObject != null){
    	   cameraObject.release();        // release the camera for other applications
    	   cameraObject = null;
       }
   }
   
  /*
   * utiliser la cam�ra en face 
   */
  
  public void setFrontCamera(){
   Camera.Parameters parameters = cameraObject.getParameters();
   parameters.set("camera-id", 2);
   // (800, 480) is also suppor2ed front camera preview size at Samsung Galaxy S.
   //parameters.setPreviewSize(640, 480); 
   cameraObject.setParameters(parameters);
   
  }
   /* Checks if external storage is available for read and write */
   public boolean isExternalStorageWritable() {
       String state = Environment.getExternalStorageState();
       if (Environment.MEDIA_MOUNTED.equals(state)) {
           return true;
       }
       return false;
   }
   
 /***
  * 
  *   enregistrer la photo prise
  */
 public void   SaveImage(Boolean show ){
	 File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
	 
     if (pictureFile == null){
   	  
         Log.i("errorPictureTaken", "Error creation media file, check storage permissions: ");
         return;
     }
     try {
   	  FileOutputStream fos = new FileOutputStream(pictureFile);
   	  Toast.makeText(Enregistrer_Image_Referente .this,"taken",Toast.LENGTH_LONG).show();
         
         if(data!=null)
           {
       	 
       	  //Toast.makeText(Enregistrer_Image_Referente .this,"data not null+"+data,Toast.LENGTH_LONG).show();
       	  //on enregistre la photo prise dans l'enmplacement fourni
       	   fos.write(data);
       	   //w have to update link image in actual module
       	   MainActivity.moduleCourant.setLienImageReferente(lien_image);
       	   //we have to store the image link in data base 
       	   ModulesBDD modulebdd=new ModulesBDD(Enregistrer_Image_Referente .this);
       	   String idEleve= MainActivity.eleveConnecte.getPrenom();
       	   String grapheme= MainActivity.moduleCourant.getGrapheme();
       	   modulebdd.open();
       	   if(modulebdd.updateModules(grapheme, idEleve, MainActivity.moduleCourant))
       	   {
       		   if(MainActivity.IsvueConsulter==false){
       			   if(show){
       				   AlertTrue alert=new AlertTrue(Enregistrer_Image_Referente .this);
       			  
       			   		alert.show();
       			   	}
       		   
       		   Enregistrer_Image_Referente.this.sauvegarder=true;
       		   butonValider.setVisibility(View.INVISIBLE);
       	       butonRetake.setVisibility(View.VISIBLE);
       	       captureButton.setVisibility(View.INVISIBLE);   
       		   }
       	   }
       	   else{
       		   AlertFalse alert=new AlertFalse(Enregistrer_Image_Referente .this,"erreur");//Enregistrer_Image_Referente .this);
				   alert.show();
       	   }

       	   
       	   fos.close(); 
           }
         //Bitmap x = (Bitmap) data
         
        
     } catch (FileNotFoundException e) {
         Log.i("errorPictureTaken", "File not found: " + e.getMessage());
     } catch (IOException e) {
   	  Log.i("errorPictureTaken", "Error accessing file: " + e.getMessage());
     }
	
}
	 
 /**
  * on actionne les boutons de la barre de menu
  */
   
   private void ActionnerBoutonMenu() {
  	// TODO Auto-generated method stub
	  
       ImageButton btnVideo=(ImageButton) findViewById(R.id.boutonVideo);
       ImageButton btnImage=(ImageButton) findViewById(R.id.boutonImage);
       ImageButton btnGrapheme=(ImageButton) findViewById(R.id.boutonGrapheme);
       ImageButton btnaccueil= (ImageButton) findViewById(R.id.boutonHome);
       ImageButton btnSon=(ImageButton) findViewById(R.id.boutonSon);
       btnSon.setOnClickListener(new BoutonMenuListener(Enregistrer_Son.class));
       btnVideo.setOnClickListener(new BoutonMenuListener(Enregistrer_Video.class));
       btnImage.setOnClickListener(new BoutonMenuListener(Enregistrer_Video.class));
       btnGrapheme.setOnClickListener(new BoutonMenuListener(Enregistrer_Photo.class));
       btnaccueil.setOnClickListener(new BoutonMenuListener(Accueil.class));
	   
  }
   
   private class BoutonMenuListener implements View.OnClickListener{
	   
		private Class<?> Classe;
		public BoutonMenuListener(Class<?> c){
			super();
			Classe=c;
			 //on cr�� la boite de dialogue de confirmation
				confirmation = new AlertDialog.Builder(Enregistrer_Image_Referente .this);
				confirmation.setTitle("Confirmation !");
				confirmation.setMessage("Vouler vous sauvegardez la photo prise ?");
		       ImageView image=new ImageView(Enregistrer_Image_Referente .this);
		        image.setImageResource(R.drawable.save);
		        confirmation.setView(image);
		        confirmation.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
		        
		          @Override
		          public void onClick(DialogInterface dialog, int which) {
		                
		               // on enregistre la photo dans la bd et sur le disque
		        	 
		             dialog.dismiss();
		             SaveImage(false);
		             Toast.makeText(Enregistrer_Image_Referente .this,"photo sauvegard�e", Toast.LENGTH_SHORT).show();
		             finish();
		       	    Intent intent = new Intent(Enregistrer_Image_Referente .this,Classe);
		       	    //Toast.makeText(Enregistrer_Image_Referente .this,"activite destinatrice"+Classe.toString(), Toast.LENGTH_SHORT).show();
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		          }
		        
		       });
		        
		        
		        confirmation.setNegativeButton("NON", new DialogInterface.OnClickListener() {
		        
		          @Override
		          public void onClick(DialogInterface dialog, int which) {
		        	  //on ne fait rien on passe � la vu demand�e
		        	    dialog.dismiss();
		        	    Toast.makeText(Enregistrer_Image_Referente .this,"photo supprim�e", Toast.LENGTH_SHORT).show();
		        	    // on va au menu accueil
		        	    finish();
		        	    Intent intent = new Intent(Enregistrer_Image_Referente .this,Classe);
		        	    //Toast.makeText(Enregistrer_Image_Referente .this,"activite destinatrice"+Classe.toString(), Toast.LENGTH_SHORT).show();
						startActivity(intent);
					   // overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		              
		              
		          }
		        
		       });
		       
			
			
		}  
	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// s il a pris la photo et n as pas enregistrer on lui propose d'enregistrer
			if(Enregistrer_Image_Referente .this.imagePrise==true && Enregistrer_Image_Referente .this.sauvegarder==false ){
				// boite de message voulez vous sauvegarder ? Yes No 
				this.creerConfirmation();
				AlertDialog alert = confirmation.create();
			    alert.show();
				
			}else{
			finish();	 
	       	 Intent intent = new Intent(Enregistrer_Image_Referente .this,Classe);
	       	 //Toast.makeText(Enregistrer_Image_Referente .this,"activite destinatrice"+Classe.toString(), Toast.LENGTH_SHORT).show();
				startActivity(intent);
				//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
				
			}
			
	    }
   
   public void creerConfirmation(){
	   confirmation = new AlertDialog.Builder(Enregistrer_Image_Referente .this);
		confirmation.setTitle("Confirmation !");
		confirmation.setMessage("Vouler vous sauvegardez la photo prise ?");
      ImageView image=new ImageView(Enregistrer_Image_Referente .this);
       image.setImageResource(R.drawable.save);
       confirmation.setView(image);
       confirmation.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
       
         @Override
         public void onClick(DialogInterface dialog, int which) {
               
              // on enregistre la photo dans la bd et sur le disque
       	 
            dialog.dismiss();
            SaveImage(false);
            Toast.makeText(Enregistrer_Image_Referente .this,"photo sauvegard�e", Toast.LENGTH_SHORT).show();
            finish();
      	    Intent intent = new Intent(Enregistrer_Image_Referente .this,Classe);
      	    //Toast.makeText(Enregistrer_Image_Referente .this,"activite destinatrice"+Classe.toString(), Toast.LENGTH_SHORT).show();
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
         }
       
      });
       
       
       confirmation.setNegativeButton("NON", new DialogInterface.OnClickListener() {
       
         @Override
         public void onClick(DialogInterface dialog, int which) {
       	  //on ne fait rien on passe � la vu demand�e
       	    dialog.dismiss();
       	    Toast.makeText(Enregistrer_Image_Referente .this,"photo supprim�e", Toast.LENGTH_SHORT).show();
       	    // on va au menu accueil
       	    finish();
       	    Intent intent = new Intent(Enregistrer_Image_Referente .this,Classe);
       	   // Toast.makeText(Enregistrer_Image_Referente .this,"activite destinatrice"+Classe.toString(), Toast.LENGTH_SHORT).show();
				startActivity(intent);
			   // overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
             
             
         }
       
      });
      
	
   }
   
   }
   
   
   
}

