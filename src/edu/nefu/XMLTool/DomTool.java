package edu.nefu.XMLTool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import edu.nefu.calculateutil.NameTransform;
import edu.nefu.database.History;
import edu.nefu.database.Task;
/**
 * XML½âÎö¹¤¾ß
 * @author WJH
 *
 */
public class DomTool {

	private static byte[] buffer=null;
	private static ArrayList<Task> result=null;
	private static ArrayList<History> result_history=null;
	private static String version=null;
	private static String apkurl=null;
	
	public static void init() {
		version=null;
		apkurl=null;
		buffer=null;
		result=new ArrayList<Task>();
		result_history=new ArrayList<History>();
	}
	
	public static ArrayList<Task> getParseResult_Task(String xml){
		init();
		try {
			buffer=xml.getBytes("UTF-8");
			ByteArrayInputStream bais=new ByteArrayInputStream(buffer);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse(bais);
			Element rootElement=document.getDocumentElement(); 
	        NodeList items=rootElement.getChildNodes();
	        for(int i=0;i<items.getLength();i++){
	        	Node node=items.item(i);
	        	if(node.getNodeName().equals("data")){
	        		String id="";
	        		String riqi="";
	        		String region="";
	        		String tree="";
	        		int lbh=-1;
	        		int xbh=-1;
	        		int state=-1;
	        		NodeList child=node.getChildNodes();
	        		for(int j=0;j<child.getLength();j++){
	        			Node cn=child.item(j);
	        			if(cn.getNodeName().equals("id")){
	        				id=cn.getTextContent();
	        			}
	        			if(cn.getNodeName().equals("riqi")){
	        				riqi=cn.getTextContent();
	        			}
	        			else if(cn.getNodeName().equals("region")){
	        				region=cn.getTextContent();
	        			}
	        			else if(cn.getNodeName().equals("tree")){
	        				tree=cn.getTextContent();
	        			}
	        			else if(cn.getNodeName().equals("lb")){
	        				lbh=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("xb")){
	        				xbh=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("state")){
	        				state=Integer.parseInt(cn.getTextContent());
	        			}
	        		}
	        		Task task=new Task(id, riqi, region, tree, lbh, xbh, state);
	        		result.add(task);
	        	}else{
	        		return null;
	        	}
	        	
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<History> getParseResult_History(String xml){
		init();
		try {
			buffer=xml.getBytes("UTF-8");
			ByteArrayInputStream bais=new ByteArrayInputStream(buffer);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse(bais);
			Element rootElement=document.getDocumentElement(); 
	        NodeList items=rootElement.getChildNodes();
	        for(int i=0;i<items.getLength();i++){
	        	Node node=items.item(i);
	        	if(node.getNodeName().equals("data")){
	        		String id="";
	        		String riqi="";
	        		String region="";
	        		String tree="";
	        		int lbh=-1;
	        		int xbh=-1;
	        		int db=-1;
	        		int dm=-1;
	        		int dw=-1;
	        		int de=-1;
	        		int ar=-1;
	        		NodeList child=node.getChildNodes();
	        		for(int j=0;j<child.getLength();j++){
	        			Node cn=child.item(j);
	        			if(cn.getNodeName().equals("id")){
	        				id=cn.getTextContent();
	        			}
	        			if(cn.getNodeName().equals("riqi")){
	        				riqi=cn.getTextContent();
	        			}
	        			else if(cn.getNodeName().equals("region")){
	        				region=cn.getTextContent();
	        			}
	        			else if(cn.getNodeName().equals("tree")){
	        				tree=cn.getTextContent();
	        			}
	        			else if(cn.getNodeName().equals("lb")){
	        				lbh=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("xb")){
	        				xbh=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("dgBest")){
	        				db=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("dgMiddle")){
	        				dm=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("dgWorst")){
	        				dw=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("density")){
	        				de=Integer.parseInt(cn.getTextContent());
	        			}
	        			else if(cn.getNodeName().equals("area")){
	        				ar=Integer.parseInt(cn.getTextContent());
	        			}
	        		}
	        		History history=new History(id, riqi, region, tree, lbh, xbh, db, dm, dw, de, ar);
	        		result_history.add(history);
	        	}else{
	        		return null;
	        	}
	        	
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result_history;
	}
	
	public static String generateXML(ArrayList<History> histories){
		init();
		StringWriter stringWriter=new StringWriter();
        XmlSerializer serializer=Xml.newSerializer();
        try {
            serializer.setOutput(stringWriter);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "checkdata");
            for(History history:histories){
            	serializer.startTag("", "data");
            	serializer.startTag("", "id");  
                serializer.text(String.valueOf(history.id));  
                serializer.endTag("", "id");  
            	serializer.startTag("", "lb");  
                serializer.text(String.valueOf(history.lbh));  
                serializer.endTag("", "lb");  
                serializer.startTag("", "xb");  
                serializer.text(String.valueOf(history.xbh));  
                serializer.endTag("", "xb");  
            	serializer.startTag("", "region");  
                serializer.text(NameTransform.getRegionValue(history.region));  
                serializer.endTag("", "region");  
                serializer.startTag("", "tree");  
                serializer.text(NameTransform.getTreeValue(history.region, history.tree));  
                serializer.endTag("", "tree");
                serializer.startTag("", "dgBest");  
                serializer.text(String.valueOf(history.db));  
                serializer.endTag("", "dgBest"); 
                serializer.startTag("", "dgMiddle");  
                serializer.text(String.valueOf(history.dm));  
                serializer.endTag("", "dgMiddle"); 
                serializer.startTag("", "dgWorst");  
                serializer.text(String.valueOf(history.dw));  
                serializer.endTag("", "dgWorst"); 
                serializer.startTag("", "area");  
                serializer.text(String.valueOf(history.ar));  
                serializer.endTag("", "area"); 
                serializer.startTag("", "density");  
                serializer.text(String.valueOf(history.de));  
                serializer.endTag("", "density"); 
                serializer.endTag("", "data");
            }
            serializer.endTag("", "checkdata");
            serializer.endDocument();
            serializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
	
	public static String[] getParseResult_Update(String xml){
		init();
		try {
			buffer=xml.getBytes("UTF-8");
			ByteArrayInputStream bais=new ByteArrayInputStream(buffer);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document=builder.parse(bais);
			Element rootElement=document.getDocumentElement();
	        NodeList items=rootElement.getChildNodes();
	        for(int i=0;i<items.getLength();i++){
	        	Node node=items.item(i);
	        	if(node.getNodeName().equals("version")){
	        		version=node.getTextContent();
	        	}
	        	else if(node.getNodeName().equals("apkurl")){
	        		apkurl=node.getTextContent();
	        	}
	        }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return new String[]{version, apkurl};
	}
	
}
