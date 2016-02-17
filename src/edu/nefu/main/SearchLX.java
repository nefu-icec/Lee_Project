package edu.nefu.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * 按需查找数据
 * @author WJH
 *
 */
public class SearchLX extends Activity{
	
	private EditText lbh;
	private EditText xbh;
	private Button lxb;
	private String tl,tx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seartchlx);
		lbh=(EditText)findViewById(R.id.lbh);
		xbh=(EditText)findViewById(R.id.xbh);
		lxb=(Button)findViewById(R.id.lxb);
		lxb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tl=lbh.getText().toString().trim();
				tx=xbh.getText().toString().trim();
				if(!tl.equals("")&&!tx.equals("")){
					Intent intent=new Intent(SearchLX.this, Histories.class);
					intent.putExtra("lbh", tl);
					intent.putExtra("xbh", tx);
					setResult(RESULT_OK, intent);
					finish();
				}
				else{
					setResult(RESULT_CANCELED);
				}
			}
		});
	}

}
