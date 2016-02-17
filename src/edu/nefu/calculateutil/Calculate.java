package edu.nefu.calculateutil;
/**
 * ��������
 * @author WJH
 *
 */
public class Calculate 
{
	public static double CalV(int region,int tree,double dgBest,double dgMiddle,double dgWorst,double density,double area)
	{
		double vBest=0;
		double vMiddle=0;
		double vWorst=0;
		switch(region)
		{
		case 0://ȫʡͨ��
			switch (tree) 
			{
			case 0://1 ����
				vBest=Common.hs(dgBest);
				vMiddle=Common.hs(dgMiddle);
				vWorst=Common.hs(dgWorst);
				break;
			case 1://2 ������
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 2://3 ��ɼ
				vBest=Common.ls(dgBest);
				vMiddle=Common.ls(dgMiddle);
				vWorst=Common.ls(dgWorst);
				break;
			case 3://4 ����
				vBest=Common.cs(dgBest);
				vMiddle=Common.cs(dgMiddle);
				vWorst=Common.cs(dgWorst);
				break;
			case 4://5 ��ɼ
				vBest=Common.ys(dgBest);
				vMiddle=Common.ys(dgMiddle);
				vWorst=Common.ys(dgWorst);
				break;
			case 5://6 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 6://7 �˹�С����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 6://�۽�����
			switch (tree) 
			{
			case 0://1 ��Ҷ��
				vBest=Njly.lys(dgBest);
				vMiddle=Njly.lys(dgMiddle);
				vWorst=Njly.lys(dgWorst);
				break;
			case 1://2 ����
				vBest=Njly.hh(dgBest);
				vMiddle=Njly.hh(dgMiddle);
				vWorst=Njly.hh(dgWorst);
				break;
			case 2://3 ����
				vBest=Njly.zs(dgBest);
				vMiddle=Njly.zs(dgMiddle);
				vWorst=Njly.zs(dgWorst);
				break;
			case 3://4 ����
				vBest=Njly.bh(dgBest);
				vMiddle=Njly.bh(dgMiddle);
				vWorst=Njly.bh(dgWorst);
				break;
			case 4://5 ɽ��
				vBest=Njly.sy(dgBest);
				vMiddle=Njly.sy(dgMiddle);
				vWorst=Njly.sy(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 7://�˹���Ҷ��
			switch (tree) {
			case 0://1 ������ʡ��������
				vBest=Rglys.hljdb(dgBest);
				vMiddle=Rglys.hljdb(dgMiddle);
				vWorst=Rglys.hljdb(dgWorst);
				break;
			case 1://2  ������ʡ�ϲ�����
				vBest=Rglys.hljnb(dgBest);
				vMiddle=Rglys.hljnb(dgMiddle);
				vWorst=Rglys.hljnb(dgWorst);
				break;
			case 2://3 ������ʡ��������
				vBest=Rglys.hljbb(dgBest);
				vMiddle=Rglys.hljbb(dgMiddle);
				vWorst=Rglys.hljbb(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 8://�˹�����
			switch (tree) 
			{
			case 0://1 ��������ԣ���ж���
				vBest=Rgyl.ljfyxzdy(dgBest);
				vMiddle=Rgyl.ljfyxzdy(dgMiddle);
				vWorst=Rgyl.ljfyxzdy(dgWorst);
				break;
			case 1://2  �����������ж���
				vBest=Rgyl.dmzzxzdy(dgBest);
				vMiddle=Rgyl.dmzzxzdy(dgMiddle);
				vWorst=Rgyl.dmzzxzdy(dgWorst);
				break;
			case 2://3  ̩�������ϡ��ֵ����ж���
				vBest=Rgyl.tlgnldxzdy(dgBest);
				vMiddle=Rgyl.tlgnldxzdy(dgMiddle);
				vWorst=Rgyl.tlgnldxzdy(dgWorst);
				break;
			case 3://4 ����������СҶ��
				vBest=Rgyl.dmzzxxyy(dgBest);
				vMiddle=Rgyl.dmzzxxyy(dgMiddle);
				vWorst=Rgyl.dmzzxxyy(dgWorst);
				break;
			case 4://5 ̩����СҶ��
				vBest=Rgyl.tlxxyy(dgBest);
				vMiddle=Rgyl.tlxxyy(dgMiddle);
				vWorst=Rgyl.tlxxyy(dgWorst);
				break;
			case 5://6 �ֵ顢���ϡ���������ԣ��СҶ��
				vBest=Rgyl.ldgnljfyxxyy(dgBest);
				vMiddle=Rgyl.ldgnljfyxxyy(dgMiddle);
				vWorst=Rgyl.ldgnljfyxxyy(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 3://���ɽɽ��
			switch (tree) 
			{
			case 0://1 ˮ����
				vBest=Wdssd.sql(dgBest);
				vMiddle=Wdssd.sql(dgMiddle);
				vWorst=Wdssd.sql(dgWorst);
				break;
			case 1://2  ������
				vBest=Wdssd.htq(dgBest);
				vMiddle=Wdssd.htq(dgMiddle);
				vWorst=Wdssd.htq(dgWorst);
				break;	
			case 2://3  �Ʋ���
				vBest=Wdssd.hbl(dgBest);
				vMiddle=Wdssd.hbl(dgMiddle);
				vWorst=Wdssd.hbl(dgWorst);
				break;
			case 3://4 ����
				vBest=Wdssd.ys(dgBest);
				vMiddle=Wdssd.ys(dgMiddle);
				vWorst=Wdssd.ys(dgWorst);
				break;
			case 4://5 ɫ��
				vBest=Wdssd.ss(dgBest);
				vMiddle=Wdssd.ss(dgMiddle);
				vWorst=Wdssd.ss(dgWorst);
				break;
			case 5://6 ����
				vBest=Wdssd.fh(dgBest);
				vMiddle=Wdssd.fh(dgMiddle);
				vWorst=Wdssd.fh(dgWorst);
				break;
			case 6://7  ����
				vBest=Wdssd.zs(dgBest);
				vMiddle=Wdssd.zs(dgMiddle);
				vWorst=Wdssd.zs(dgWorst);
				break;
			case 7://8 ����
				vBest=Wdssd.hh(dgBest);
				vMiddle=Wdssd.hh(dgMiddle);
				vWorst=Wdssd.hh(dgWorst);
				break;
			case 8://9 ����
				vBest=Wdssd.bh(dgBest);
				vMiddle=Wdssd.bh(dgMiddle);
				vWorst=Wdssd.bh(dgWorst);
				break;
			case 9://10 ���
				vBest=Wdssd.ds(dgBest);
				vMiddle=Wdssd.ds(dgMiddle);
				vWorst=Wdssd.ds(dgWorst);
				break;
			case 10://11 ɽ��
				vBest=Wdssd.sy(dgBest);
				vMiddle=Wdssd.sy(dgMiddle);
				vWorst=Wdssd.sy(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 1://С�˰��뱱��
			switch (tree) 
			{
			case 0://1 ��Ҷ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 1://2 ������
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 2://3 ˮ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 3: //4 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 4://5 ɫ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 5://6 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
			   break;
		   case 6://7  ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
			    break;
		    case 7://8 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 8:	//9 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 9:	//10 ���
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 10://11 ɽ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 2://С�˰�������
			switch (tree) 
			{
			case 0://1 ��Ҷ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 1://2 ������
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 2://3 ˮ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 3: //4 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 4://5 ɫ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 5://6 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
			   break;
		   case 6://7  ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
		    case 7://8 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 8:	//9 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 9:	//10 ���
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 10://11 ɽ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 5://�Ź���붫��
			switch (tree) 
			{
			case 0://1  ������
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 1://2 ˮ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 2://3  �Ʋ���
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 3: //4 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 4://5 ɫ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 5://6 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
			    break;
		   case 6://7  ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
		    case 7://8 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 8:	//9 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 9:	//10 ���
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 10://11 ɽ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			default:
				break;
			}
			break;
		case 4://�Ź��������
			switch (tree) 
			{
			case 0://1 ˮ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 1://2  ������
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 2://3  �Ʋ���
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 3: //4 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 4://5 ɫ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 5://6 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
			    break;
		    case 6://7  ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
		    case 7://8 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 8:	//9 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 9:	//10 ���
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
			case 10://11 ɽ��
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
		   case 11:	//12 ����
				vBest=Common.zzs(dgBest);
				vMiddle=Common.zzs(dgMiddle);
				vWorst=Common.zzs(dgWorst);
				break;
		   default:
				break;
			}
			break;  
		default:
			break;
		}
		double avg=(vBest+vMiddle+vWorst)/3;
		double V=avg*density*area;
        return V;
	}
}
