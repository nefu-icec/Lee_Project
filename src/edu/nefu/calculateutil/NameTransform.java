package edu.nefu.calculateutil;

public class NameTransform 
{
	//������
	static String[] regionName={"ȫʡͨ��","�۽�����","�˹���Ҷ��","�˹�����","���ɽɽ��","С�˰��뱱��","С�˰�������","�Ź���붫��","�Ź��������"};
	static String[] regionValue={"Common","Njly","Rglys ","Rgyl ","Wdssd","Xxalbp ","Xxalnp ","Zgcldp","Zgclxp"};
	//ȫʡͨ��
	static String[] Common={"����","������","��ɼ","����","��ɼ","����","�˹�С����"};
	static String[] CommonValue={"hs","zzs","ls","cs","ys","qy","rgxhy"};
	//�۽�����
	static String[] Njly={"��Ҷ��","����","����"," ����"," ɽ��"};
	static String[] NjlyValue={"lys","hh","zs","bh","sy"};
	//�˹���Ҷ��
	static String[] Rglys={" ������ʡ��������","������ʡ�ϲ�����","������ʡ��������"};
	static String[] RglysValue={"hljdb","hljnb","hljbb"};
	//�˹�����
	static String[] Rgyl ={" ��������ԣ���ж���","�����������ж���"," ̩�������ϡ��ֵ����ж���","����������СҶ��"," ̩����СҶ��"," �ֵ顢���ϡ���������ԣ��СҶ��"};
	static String[] RgylValue={"ljfyxzdy","dmzzxzdy","tlgnldxzdy","dmzzxxyy","tlxxyy","ldgnljfyxxyy"};
	//���ɽɽ��
	static String[] Wdssd={"ˮ����","������","�Ʋ���","����","ɫ��","����","����","����","����","���","ɽ��"};
	static String[] WdssdValue={"sql","htq","hbl","ys","ss","fh","hh","bh","ds","sy"};
	//С�˰��뱱��
	static String[] Xxalbp={"��Ҷ��","������","ˮ����","�Ʋ���","����","ɫ��","����","����","����","����","���","ɽ��"};
	static String[] XxalbpValue={"lys","htq","sql","hbl","ys","ss","fh","zs","hh","bh","ds","sy"};
	//С�˰�������
	static String[] Xxalnp={"��Ҷ��","������","ˮ����","����","ɫ��","����","����","����","����","���","ɽ��"};
	static String[] XxalnpValue={"lys","htq","sql","ys","ss","fh","zs","hh","bh","ds","sy"};
	//�Ź���붫��
	static String[] Zgcldp={"������","ˮ����","�Ʋ���","����","ɫ��","����","����","����","����","���","ɽ��"};
	static String[] ZgcldpValue={"htq","sql","hbl","ys","ss","fh","zs","hh","bh","ds","sy"};
	//�Ź��������
	static String[] Zgclxp={"ˮ����","������","�Ʋ���","����","ɫ��","����","����","����","����","���","ɽ��","����"};
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
		if(region.equals("ȫʡͨ��")){
			for(int i=0;i<Common.length;i++)
				if(tree.equals(Common[i]))
					treeValue=CommonValue[i];
		}
		else if(region.equals("�۽�����")){
			for(int i=0;i<Njly.length;i++)
				if(tree.equals(Njly[i]))
					treeValue=NjlyValue[i];
		}
		else if(region.equals("�˹���Ҷ��")){
			for(int i=0;i<Rglys.length;i++)
				if(tree.equals(Rglys[i]))
					treeValue=RglysValue[i];
		}
		else if(region.equals("�˹�����")){
			for(int i=0;i<Rgyl.length;i++)
				if(tree.equals(Rgyl[i]))
					treeValue=RgylValue[i];
		}
		else if(region.equals("���ɽɽ��")){
			for(int i=0;i<Wdssd.length;i++)
				if(tree.equals(Wdssd[i]))
					treeValue=WdssdValue[i];
		}
		else if(region.equals("С�˰��뱱��")){
			for(int i=0;i<Xxalbp.length;i++)
				if(tree.equals(Xxalbp[i]))
					treeValue=XxalbpValue[i];
		}
		else if(region.equals("С�˰�������")){
			for(int i=0;i<Xxalnp.length;i++)
				if(tree.equals(Xxalnp[i]))
					treeValue=XxalnpValue[i];
		}
		else if(region.equals("�Ź���붫��")){
			for(int i=0;i<Zgcldp.length;i++)
				if(tree.equals(Zgcldp[i]))
					treeValue=ZgcldpValue[i];
		}
		else if(region.equals("�Ź��������")){
			for(int i=0;i<Zgclxp.length;i++)
				if(tree.equals(Zgclxp[i]))
					treeValue=ZgclxpValue[i];
		}
		return treeValue;
	}
}
