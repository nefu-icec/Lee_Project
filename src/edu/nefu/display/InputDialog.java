package edu.nefu.display;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.nefu.calculateutil.Calculate;
import edu.nefu.main.R;
/**
 * 输入模块
 * @author WJH
 *
 */
public class InputDialog extends Activity{

	private Spinner region,tree;
	private EditText db,dm,dw,de,ar;
	private Button ok,cancel;
	String treeText="";
	String regionText="";
	String dbText="";
	String dmText="";
	String dwText="";
	String deText="";
	String arText="";
	int idb=0,idm=0,idw=0,ide=0,iar=0;
	ArrayAdapter<CharSequence> treeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputdialog);
		region=(Spinner)findViewById(R.id.s_region);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.region, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		region.setAdapter(adapter);
		region.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				regionText=arg0.getItemAtPosition(arg2).toString();
				setTreeAdatpter(regionText);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		tree=(Spinner)findViewById(R.id.s_tree);
		db=(EditText)findViewById(R.id.db);
		dm=(EditText)findViewById(R.id.dm);
		dw=(EditText)findViewById(R.id.dw);
		de=(EditText)findViewById(R.id.de);
		ar=(EditText)findViewById(R.id.ar);
		ok=(Button)findViewById(R.id.in_ok);
        ok.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isSuccesfull()){
					Intent intent=new Intent();
					intent.putExtra("region", regionText);
					intent.putExtra("tree", treeText);
					intent.putExtra("ar", iar);
					intent.putExtra("db", idb);
					intent.putExtra("dm", idm);
					intent.putExtra("dw", idw);
					intent.putExtra("de", ide);
					intent.putExtra("calV", Calculate.CalV(transformStringToInt(regionText), transformStringToInt(treeText), idb, idm, idw, ide, iar));
					setResult(RESULT_OK, intent);
					finish();
				}
				else{
					AlertDialog.Builder builder=new AlertDialog.Builder(InputDialog.this);
					builder.setTitle(R.string.app_name).setNegativeButton("知道了", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						    
						}
					}).setMessage("请按规定输入");
					Dialog dialog=builder.create();
					dialog.show();
				}
			}
		});
		cancel=(Button)findViewById(R.id.in_cancel);
        cancel.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				setResult(RESULT_CANCELED);
			}
		});
	}
	
	public boolean isSuccesfull(){
		dbText=db.getText().toString().trim();
        dmText=dm.getText().toString().trim();
        dwText=dw.getText().toString().trim();
        deText=de.getText().toString().trim();
        arText=ar.getText().toString().trim();
		if(dbText.length()>0){
			idb=Integer.parseInt(dbText);
		}
		else{
			return false;
		}
		if(dmText.length()>0){
			idm=Integer.parseInt(dmText);
		}
		else{
			return false;
		}if(dwText.length()>0){
			idw=Integer.parseInt(dwText);
		}
		else{
			return false;
		}
		if(deText.length()>0){
			ide=Integer.parseInt(deText);
		}
		else{
			return false;
		}
		if(arText.length()>0){
			iar=Integer.parseInt(arText);
		}
		else{
			return false;
		}
		if(idb>=idm&&idm>=idw){
			return true;
		}
		return false;
	}
	
	public void setTreeAdatpter(String region){
		int r=transformStringToInt(region);
		switch(r){
		case 0:
			treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_common, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			break;
        case 1:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_xxalbp, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			break;
        case 2:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_xxalnp, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
        case 3:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_wdssd, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
        case 4:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_zgclxp, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
        case 5:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_zgcldp, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
        case 6:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_njly, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
        case 7:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_rglys, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
        case 8:
        	treeAdapter=ArrayAdapter.createFromResource(InputDialog.this, R.array.tree_rgyl, android.R.layout.simple_spinner_item);
			treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tree.setAdapter(treeAdapter);
			tree.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					treeText=arg0.getItemAtPosition(arg2).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
        	break;
		}
	}
	
	public int transformStringToInt(String region){
		int result=0;
		if(region.equals("全省通用")){
			result=0;
		}
		else if(region.equals("小兴安岭北坡")){
			result=1;
		}
        else if(region.equals("小兴安岭南坡")){
        	result=2;
		}
        else if(region.equals("完达山山地")){
        	result=3;
		}
        else if(region.equals("张广才岭西坡")){
        	result=4;
		}
        else if(region.equals("张广才岭东坡")){
        	result=5;
		}
        else if(region.equals("嫩江流域")){
        	result=6;
		}
        else if(region.equals("人工落叶松")){
        	result=7;
		}
        else if(region.equals("人工杨类")){
        	result=8;
		}
		return result;
	}

}
