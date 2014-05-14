package Modeles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class MessageYesNo extends AlertDialog.Builder{

	public MessageYesNo(Context arg0) {
		super(arg0);
		this.setTitle("Comfirmation !");
		this.setMessage("Vouler vous sauvegardez le son enregistré?");
		
		this.setNegativeButton("Oui", new DialogInterface.OnClickListener()
		{
            public void onClick(DialogInterface dialog, int which) {
            
            // on enregistre le son dans la bd
            	
         }
            });
		
		this.setNeutralButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            
            	// on supprime le fichier enregistrer
            	
            	
            }
        });
		
	}
	

}
