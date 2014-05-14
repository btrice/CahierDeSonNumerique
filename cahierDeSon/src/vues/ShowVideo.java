package vues;

import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * @author Ammoula
 * permet de placer
 *
 */
public class ShowVideo extends SurfaceView implements SurfaceHolder.Callback
{
      //Create objects for MediaRecorder and SurfaceHolder.
      MediaRecorder recorder;
      SurfaceHolder holder;
      Camera camera;

      //Create constructor of Preview Class. In this, get an object of
      //surfaceHolder class by calling getHolder() method. After that add
      //callback to the surfaceHolder. The callback will inform when surface is
      //created/changed/destroyed. Also set surface not to have its own buffers.

      public ShowVideo(Context context,MediaRecorder temprecorder) {
	 super(context);
     recorder=temprecorder;
	 holder=getHolder();
	 holder.addCallback(this);
	 holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
       }

      // Implement the methods of SurfaceHolder.Callback interface

      // SurfaceCreated : This method gets called when surface is created.
      // In this, initialize all parameters of MediaRecorder object as explained
      // above.

      public void surfaceCreated(SurfaceHolder holder){
	  try{
        //recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		camera=this.getCameraInstance();
	   	recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
	   	recorder.setCamera(camera);
	   	recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
	    	recorder.setOutputFile("/sdcard/recordvideooutput.3gpp");
	    	recorder.setPreviewDisplay(holder.getSurface());
	   	recorder.prepare();
	    } catch (Exception e) {
	    	String message = e.getMessage();
      }
}
      
   // SurfaceDestroyed : This method gets called immediately before the
   // surface is being destroyed. Stop camera preview because surface will no
   // longer exist.

   public void surfaceDestroyed(SurfaceHolder holder)
   {
       if(recorder!=null)
       {
   	recorder.release();
   	recorder = null;
       }
   }

   //surfaceChanged : This method is called after the surface is created.
   public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
   {
   
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

}
