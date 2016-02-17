package edu.nefu.display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.nefu.main.R;
/**
 * 颜色的更改模块
 * @author WJH
 *
 */
public class Color extends Activity{

	private ListView color;
	private Intent intent=null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color);
		setTitle("更改颜色");
		color=(ListView)findViewById(R.id.color);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(Color.this, R.array.color, android.R.layout.simple_list_item_single_choice);
		color.setAdapter(adapter);
		color.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					intent=new Intent();
					intent.putExtra("currentcolor", 0xff63B8FF);
					break;
				case 1:
					intent=new Intent();
					intent.putExtra("currentcolor", 0xff5D478B);
					break;
				case 2: 
					intent=new Intent();
					intent.putExtra("currentcolor", 0xff71C671);
					break;
				default:
					break;
				}
				setResult(RESULT_OK, intent);
				finish();
			}
			
		});
	}

}
