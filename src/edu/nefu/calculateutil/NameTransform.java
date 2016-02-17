package edu.nefu.calculateutil;

public class NameTransform 
{
	//地区名
	static String[] regionName={"全省通用","嫩江流域","人工落叶松","人工杨类","完达山山地","小兴安岭北坡","小兴安岭南坡","张广才岭东坡","张广才岭西坡"};
	static String[] regionValue={"Common","Njly","Rglys ","Rgyl ","Wdssd","Xxalbp ","Xxalnp ","Zgcldp","Zgclxp"};
	//全省通用
	static String[] Common={"红松","樟子松","冷杉","赤松","云杉","青杨","人工小黑杨"};
	static String[] CommonValue={"hs","zzs","ls","cs","ys","qy","rgxhy"};
	//嫩江流域
	static String[] Njly={"落叶松","黑桦","柞树"," 白桦"," 山杨"};
	static String[] NjlyValue={"lys","hh","zs","bh","sy"};
	//人工落叶松
	static String[] Rglys={" 黑龙江省东部地区","黑龙江省南部地区","黑龙江省北部地区"};
	static String[] RglysValue={"hljdb","hljnb","hljbb"};
	//人工杨类
	static String[] Rgyl ={" 龙江、富裕县中东杨","杜蒙自治县中东杨"," 泰来、甘南、林甸县中东杨","杜蒙自治县小叶杨"," 泰来县小叶杨"," 林甸、甘南、龙江、富裕县小叶杨"};
	static String[] RgylValue={"ljfyxzdy","dmzzxzdy","tlgnldxzdy","dmzzxxyy","tlxxyy","ldgnljfyxxyy"};
	//完达山山地
	static String[] Wdssd={"水曲柳","胡桃秋","黄菠萝","榆树","色树","枫桦","柞树","黑桦","白桦","椴树","山杨"};
	static String[] WdssdValue={"sql","htq","hbl","ys","ss","fh","hh","bh","ds","sy"};
	//小兴安岭北坡
	static String[] Xxalbp={"落叶松","胡桃秋","水曲柳","黄菠萝","榆树","色树","枫桦","柞树","黑桦","白桦","椴树","山杨"};
	static String[] XxalbpValue={"lys","htq","sql","hbl","ys","ss","fh","zs","hh","bh","ds","sy"};
	//小兴安岭南坡
	static String[] Xxalnp={"落叶松","胡桃秋","水曲柳","榆树","色树","枫桦","柞树","黑桦","白桦","椴树","山杨"};
	static String[] XxalnpValue={"lys","htq","sql","ys","ss","fh","zs","hh","bh","ds","sy"};
	//张广才岭东坡
	static String[] Zgcldp={"胡桃秋","水曲柳","黄菠萝","榆树","色树","枫桦","柞树","黑桦","白桦","椴树","山杨"};
	static String[] ZgcldpValue={"htq","sql","hbl","ys","ss","fh","zs","hh","bh","ds","sy"};
	//张广才岭西坡
	static String[] Zgclxp={"水曲柳","胡桃秋","黄菠萝","榆树","色树","枫桦","柞树","黑桦","白桦","椴树","山杨","柳树"};
	static String[] ZgclxpValue={"sql","htq","hbl","ys","ss","fh","zs","hh","bh","ds","sy","ls"};
	
	public static String getRegionName(String region)
	{
		String regionReturn="";
		for(int i=0;i<regionValue.length;i++)
			if(region.equals(regionValue[i]))
				regionReturn =regionName[i];
		return regionReturn;
	}
	
	public static String getTreeName(String region,String tree)
	{
		String treeName="";
		if(region.equals("Common")){
			for(int i=0;i<Common.length;i++)
				if(tree.equals(CommonValue[i]))
					treeName=Common[i];
		}
		else if(region.equals("Njly")){
			for(int i=0;i<Njly.length;i++)
				if(tree.equals(NjlyValue[i]))
					treeName=Njly[i];
		}
		else if(region.equals("Rglys")){
			for(int i=0;i<Rglys.length;i++)
				if(tree.equals(RglysValue[i]))
					treeName=Rglys[i];
		}
		else if(region.equals("Rgyl")){
			for(int i=0;i<Rgyl.length;i++)
				if(tree.equals(RgylValue[i]))
					treeName=Rgyl[i];
		}
		else if(region.equals("Wdssd")){
			for(int i=0;i<Wdssd.length;i++)
				if(tree.equals(WdssdValue[i]))
					treeName=Wdssd[i];
		}
		else if(region.equals("Xxalbp")){
			for(int i=0;i<Xxalbp.length;i++)
				if(tree.equals(XxalbpValue[i]))
					treeName=Xxalbp[i];
		}
		else if(region.equals("Xxalnp")){
			for(int i=0;i<Xxalnp.length;i++)
				if(tree.equals(XxalnpValue[i]))
					treeName=Xxalnp[i];
		}
		else if(region.equals("Zgcldp")){
			for(int i=0;i<Zgcldp.length;i++)
				if(tree.equals(ZgcldpValue[i]))
					treeName=Zgcldp[i];
		}
		else if(region.equals("Zgclxp")){
			for(int i=0;i<Zgclxp.length;i++)
				if(tree.equals(ZgclxpValue[i]))
					treeName=Zgclxp[i];
		}
		return treeName;
	}
	
	public static String getRegionValue(String region)
	{
		String regionReturn="";
		for(int i=0;i<regionName.length;i++)
			if(region.equals(regionName[i]))
				regionReturn =regionValue[i];
		return regionReturn;
	}
	
	public static String getTreeValue(String region,String tree)
	{
		String treeValue="";
		if(region.equals("全省通用")){
			for(int i=0;i<Common.length;i++)
				if(tree.equals(Common[i]))
					treeValue=CommonValue[i];
		}
		else if(region.equals("嫩江流域")){
			for(int i=0;i<Njly.length;i++)
				if(tree.equals(Njly[i]))
					treeValue=NjlyValue[i];
		}
		else if(region.equals("人工落叶松")){
			for(int i=0;i<Rglys.length;i++)
				if(tree.equals(Rglys[i]))
					treeValue=RglysValue[i];
		}
		else if(region.equals("人工杨类")){
			for(int i=0;i<Rgyl.length;i++)
				if(tree.equals(Rgyl[i]))
					treeValue=RgylValue[i];
		}
		else if(region.equals("完达山山地")){
			for(int i=0;i<Wdssd.length;i++)
				if(tree.equals(Wdssd[i]))
					treeValue=WdssdValue[i];
		}
		else if(region.equals("小兴安岭北坡")){
			for(int i=0;i<Xxalbp.length;i++)
				if(tree.equals(Xxalbp[i]))
					treeValue=XxalbpValue[i];
		}
		else if(region.equals("小兴安岭南坡")){
			for(int i=0;i<Xxalnp.length;i++)
				if(tree.equals(Xxalnp[i]))
					treeValue=XxalnpValue[i];
		}
		else if(region.equals("张广才岭东坡")){
			for(int i=0;i<Zgcldp.length;i++)
				if(tree.equals(Zgcldp[i]))
					treeValue=ZgcldpValue[i];
		}
		else if(region.equals("张广才岭西坡")){
			for(int i=0;i<Zgclxp.length;i++)
				if(tree.equals(Zgclxp[i]))
					treeValue=ZgclxpValue[i];
		}
		return treeValue;
	}
}
