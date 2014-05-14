package vues;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

   private SurfaceHolder holdMe;
   private Camera theCamera;

   public ShowCamera(Context context,Camera camera) {
      super(context);
      theCamera = camera;
      holdMe = getHolder();
      holdMe.addCallback(this);
      holdMe.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
   }


   @Override
   public void surfaceCreated(SurfaceHolder holder) {
      try   {
         theCamera.setPreviewDisplay(holder);
         Camera.Parameters p = theCamera.getParameters();
         theCamera.setParameters(p);
         theCamera.startPreview(); 
        // }
      } catch (IOException e) {
    	  Log.i("erreur surfaceCreated", "Error setting camera preview: " + e.getMessage());
      }
      
   }
      public void surfaceChanged(SurfaceHolder hold, int format, int w, int h) {
          // If your preview can change or rotate, take care of those events here.
          // Make sure to stop the preview before resizing or reformatting it.

          if (holdMe.getSurface() == null){
            // preview surface does not exist
            return;
          }

          // stop preview before making changes
          try {
              theCamera.stopPreview();
          } catch (Exception e){
            // ignore: tried to stop a non-existent preview
          }

          // set preview size and make any resize, rotate or
          // reformatting changes here

          // start preview with new settings
          try {
              theCamera.setPreviewDisplay(holdMe);
              theCamera.startPreview();

          } catch (Exception e){
              Log.i("error surfaceChanged", "Error starting camera preview: " + e.getMessage());
          }
      }
   
/**
 * retourne le srface holder de show view
 * @return
 */
      
      public SurfaceHolder getSurfaceHolder(){
    	  return this.holdMe;
      }
   @Override
   public void surfaceDestroyed(SurfaceHolder arg0) {
   }

}