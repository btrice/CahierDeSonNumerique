package Modeles;

import com.example.cahierdeson.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;

public class AlertFalse extends AlertDialog.Builder{
	
	public AlertFalse(Context arg0,String titre) {
		super(arg0);
		// TODO Auto-generated constructor stub
		// Setting Dialog Title
        this.setTitle(titre);

        // Setting Dialog Message
       
        // Setting Icon to Dialog
        this.setIcon(R.drawable.error);
        ImageView image=new ImageView(arg0);
        image.setImageResource(R.drawable.sad);
        this.setView(image);
   

        // Setting Negative "NO" Button
        this.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });


	}
}
