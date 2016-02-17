package edu.nefu.display;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;
import cn.creable.gridgis.controls.MapControl;
import cn.creable.gridgis.controls.MapView;
import cn.creable.gridgis.shapefile.ShapefileLayer;
import edu.nefu.constant.Constant;
/**
 * UCmap提供的地图视图
 * @author WJH
 *
 */
public class MyMapView extends MapView{
	
	Context context=null;
	
	public MyMapView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		this.context=arg0;
	}
	
	@Override
	protected void onSizeChanged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSizeChanged(arg0, arg1, arg2, arg3);
		String path="";
        if(Constant.ishasSD==true){
        	path=Environment.getExternalStorageDirectory().getPath()+Constant.display_folder+Constant.display_file;    	
		}else{
			path=Constant.default_display_file;
		}
		MapControl mapControl=getMapControl();
		mapControl.showScaleBar(10, getResources().getDisplayMetrics().xdpi/1.91f, 20, (int)(getResources().getDisplayMetrics().ydpi-30), Color.BLACK, Color.RED, 4, 20);
		if(mapControl.getMap()==null){
			mapControl.loadMap(path, (byte)0);
			mapControl.setPanTool();
			ShapefileLayer.openUndoRedo();
			DisplayMain.flag=false;
		}
//		else{
//			DisplayMain.flag=false;
//		}
		GPSCustomDraw ycd=new GPSCustomDraw(mapControl, context);
		mapControl.setCustomDraw(ycd);
	}

}
