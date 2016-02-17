package edu.nefu.display;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;
import cn.creable.gridgis.controls.App;
import cn.creable.gridgis.controls.ICustomDraw;
import cn.creable.gridgis.controls.ICustomDrawDataCenter;
import cn.creable.gridgis.controls.MapControl;
import cn.creable.gridgis.display.IDisplayTransformation;
import cn.creable.gridgis.geometry.IEnvelope;
import cn.creable.gridgis.geometry.IGeometry;
import cn.creable.gridgis.geometry.Point;
import cn.creable.gridgis.util.Image;
import cn.creable.ucmap.LBS;
import edu.nefu.main.R;
/**
 * 实现定位
 * @author WJH
 *
 */
public class GPSCustomDraw implements ICustomDraw,LocationListener,ICustomDrawDataCenter {
	
	private MapControl mapControl;
	public double lon,lat;
	public double acc;//是范围，只有基站定位的数据才有这个值
	public double x,y;
	private Image gps;
    private Image gps1;
    private boolean flag;
	private Paint paint;
	private IDisplayTransformation dt;
    private MyTimerTask timer;
	
	private LBS lbs;
	private Context context;
	
    private class MyTimerTask extends TimerTask
    {
		@Override
	    public void run() {
			if(lon!=0&&lat!=0&&mapControl.noCustomDraw==false)
				mapControl.repaint();
		}
	
	}
	
	public GPSCustomDraw(MapControl mapControl, Context context)
	{
		this.mapControl=mapControl;
		this.context=context;
		dt=mapControl.getDisplay().getDisplayTransformation();
		paint=new Paint();
		paint.setAntiAlias(true);
		BitmapDrawable bmpDraw=(BitmapDrawable)App.getInstance().getResources().getDrawable(R.drawable.gps);
		gps=new Image(bmpDraw.getBitmap());
		bmpDraw=(BitmapDrawable)App.getInstance().getResources().getDrawable(R.drawable.gps1);
		gps1=new Image(bmpDraw.getBitmap());
		Timer myTimer = new Timer();
		timer=new MyTimerTask();
		myTimer.schedule(timer, 500, 500);

		lbs=new LBS(context);
		lbs.openGPS(1000, 0.01f, this);
		lbs.getPositionByNetwork(this);
	}
	
	public void close()
	{
		lbs.closeGPS(this);
		x=0;
		y=0;
		mapControl.repaint();
	}

	@Override
	public void draw(Canvas g) {
		if(x!=0&&y!=0)
		{
			Point pt=new Point(x,y);
			Point result=new Point();
			dt.fromMapPoint(pt, result);//将图上坐标转换为屏幕坐标
			if((flag=!flag)){
				gps.draw(g, (int)result.getX()-gps.getWidth()/2, (int)result.getY()-gps.getWidth()/2, null);
			}
			else{
				gps1.draw(g, (int)result.getX()-gps1.getWidth()/2, (int)result.getY()-gps1.getWidth()/2, null);
			}
			pt=null;
			result=null;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		lon=location.getLongitude();
		lat=location.getLatitude();
		//将经纬度转换为图上坐标
	    x=lon;
	    y=lat;
		IEnvelope env=mapControl.getExtent();
		if(env.getXMin()>x||env.getYMax()<x||env.getYMin()>y||env.getYMax()<y)
		{
//			Point pt=new Point(x,y);
//			env.centerAt(pt);
//			mapControl.refresh(env);
			Toast.makeText(context, "视图越界", Toast.LENGTH_SHORT).show();
		}
		else
			mapControl.repaint();
	}

	@Override
	public void onProviderDisabled(String arg0) {

	}

	@Override
	public void onProviderEnabled(String arg0) {

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	@Override
	public IGeometry getGeometry(int index) {
		return new Point(x,y);
	}

	@Override
	public int getGeometryNum() {
		return 1;
	}

	@Override
	public void onGeometrySelected(int index, IGeometry geometry) {
		Toast.makeText(context, "图层"+index+"   "+geometry.asText(), Toast.LENGTH_SHORT).show();
	}

}
