package Modeles;
import com.example.cahierdeson.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;

public class AlertTrue extends AlertDialog.Builder{
	
		public AlertTrue(Context arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
			// Setting Dialog Title
	        this.setTitle("Bien !");

	        // Setting Dialog Message
	        //this.setMessage("Do you want to save this file?");

	        // Setting Icon to Dialog
	        this.setIcon(R.drawable.ok);
	        ImageView image=new ImageView(arg0);
	        image.setImageResource(R.drawable.happy);
	        this.setView(image);
	   

	        // Setting Negative "NO" Button
	        this.setNegativeButton("ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });

		}
		

	}



