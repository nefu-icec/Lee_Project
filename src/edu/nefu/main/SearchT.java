package edu.nefu.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * 按需查找数据
 * @author WJH
 *
 */
public class SearchT extends Activity{
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchrt);
		listView=(ListView)findViewById(R.id.ssp);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SearchT.this, R.array.tree, android.R.layout.simple_list_item_single_choice);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SearchT.this, Histories.class);
				String treeText=arg0.getItemAtPosition(arg2).toString();
				intent.putExtra("tree", treeText);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

}
