package edu.nefu.database;
/**
 * 历史表对应的类
 * @author WJH
 *
 */
public class History {
	
	public String riqi;
	public String region;
	public String tree;
	public int lbh;
	public int xbh;
	public int db;
	public int dm;
	public int dw;
	public int de;
	public int ar;
	public String id;
	
	public History(String id,  String riqi, String region, String tree, int lbh, int xbh, int db, int dm, int dw, int de, int ar) {
		// TODO Auto-generated constructor stub
		this.riqi=riqi;
		this.region=region;
		this.tree=tree;
		this.lbh=lbh;
		this.xbh=xbh;
		this.db=db;
		this.dm=dm;
		this.dw=dw;
		this.de=de;
		this.ar=ar;
		this.id=id;
	}

}
