package edu.nefu.database;
/**
 * 任务表对应的类
 * @author WJH
 *
 */
public class Task {
	
	public String id;
	public String riqi;
	public String region;
	public String tree;
	public int lbh;
	public int xbh;
	public int state;
	
	public Task(String id, String riqi, String region, String tree, int lbh, int xbh, int state) {
		// TODO Auto-generated constructor stub
		this.riqi=riqi;
		this.region=region;
		this.tree=tree;
		this.lbh=lbh;
		this.xbh=xbh;
		this.state=state;
		this.id=id;
	}

}
