package Modeles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
	
	private int forme; // la forme à dessiner
	private int color;// la couleur de la forme
	private float rad = 1.9f;
	public CircleView(Context context, AttributeSet attrs) {
	  super(context, attrs);
	  // TODO Auto-generated constructor stub
	}
	public CircleView(Context context) {
	  super(context);
	  // TODO Auto-generated constructor stub
	}
	public CircleView(Context context, int forme, int color){
		super(context);
		this.forme = forme;
		this.color = color;
		
	}
	public CircleView(Context context, AttributeSet attrs, int defStyle) {
	  super(context, attrs, defStyle);
	  // TODO Auto-generated constructor stub
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
	  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	 // paint.setColor(Color.BLACK);
	  paint.setStyle(Paint.Style.STROKE);
	  paint.setStrokeWidth(3);
	  float cx = getMeasuredWidth() / 2;
	  float cy = getMeasuredHeight() / 2;
	  

	  float radius = Math.min(cx, cy)/2;
	  
	  System.out.println("cx ="+cx+" cy ="+cy+" radius ="+ radius);
	
	  //face
	  switch(this.forme)
	  {
	  	case 0:
	  		paint.setStyle(Paint.Style.FILL);
	  		canvas.drawCircle(cx, cy, radius, paint);
	  		//Oeil droit
		  //canvas.drawCircle(cx + 100, cy - 100, 20, paint);
	  		canvas.drawCircle(cx + (radius*1/2) , cy - (radius*1/2), 20, paint);
	  		//Oeil gauche
	  		//canvas.drawCircle(cx -100, cy - 100, 20, paint);
	  		canvas.drawCircle(cx - (radius*1/2) , cy - (radius*1/2), 20, paint);
	  		//nez
	  		canvas.drawRect(cx-(cx/2), cy+(cy/2), cx + (cx/2), cy-(cy/2), paint);
	  		//bouche
	  		//canvas.drawArc(new RectF(cx-100,cy+40,cx+100,cy+140), 0, 180, true, paint);
	  		canvas.drawArc(new RectF(cx- (radius*1/2),cy+40,cx+(radius*1/2),cy+(radius*3/4)), 0, 180, true, paint);
	  		//canvas.drawOval(new RectF(cx-100,cy+60,cx+100,cy+100), paint);
	  		break;
	  
	  	case 1:
	  		paint.setColor(color);
	  		paint.setStyle(Paint.Style.FILL_AND_STROKE);
	  		canvas.drawCircle(cx, cy, radius, paint);
	  		
	  		break;
	  	case 2:
	  		paint.setStyle(Paint.Style.FILL_AND_STROKE);
	  		paint.setColor(color);
	  		canvas.drawCircle(cx + (radius*1/2) , cy - (radius*1/2), 20, paint);
	  		
	  		break;
	  	case 3:
	  		paint.setStyle(Paint.Style.FILL_AND_STROKE);
	  		paint.setColor(color);
	  		canvas.drawRect(cx-(cx/4),cy - (cy/4) , cx +(cx/4) , cy +(cy/4), paint);
	  		
	  		break;
	  	case 4:
	  		paint.setStyle(Paint.Style.FILL_AND_STROKE);
	  		paint.setColor(color);
	  		canvas.drawArc(new RectF(cx-50,cy-30,cx+50,cy+10), 0, 180, true, paint);
	  		
	  		
	  		break;
	  	case 5:
	  		
	  		paint.setColor(color);
	  		
	  		canvas.drawCircle(cx, cy, (float)(radius*rad), paint);
	  }
	 
	  super.onDraw(canvas);
	}
	public int getForme() {
		return forme;
	}
	public void setForme(int forme) {
		this.forme = forme;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	}