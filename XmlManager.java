package classGenerator;

import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File;
import java.util.HashMap;
import java.util.Map;  

public class XmlManager {
    
    public XmlManager(){

    }

    public static Map<String,String> getLanguageProperties(String language){
        try   
        {  
            //creating a constructor of file class and parsing an XML file  
            File file = new File("/home/riana/Documents/S5/Naina-FRAMEWORK/Class-Generator/Xml.xml");  
            //an instance of factory that gives a document builder  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            //an instance of builder to parse the specified xml file  
            DocumentBuilder db = dbf.newDocumentBuilder();  
            Document doc = db.parse(file);  
            doc.getDocumentElement().normalize();  
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
            NodeList nodeList = doc.getElementsByTagName(language);
            Map<String,String> map = new HashMap<String,String>();
            // nodeList is not iterable, so we are using for loop  
            for (int itr = 0; itr < nodeList.getLength(); itr++)   
            {  
                Node node = nodeList.item(itr);  
                System.out.println("\nNode Name :" + node.getNodeName());  
                if (node.getNodeType() == Node.ELEMENT_NODE)   
                {  
                    Element eElement = (Element) node;  
                    System.out.println("package: "+ eElement.getElementsByTagName("package").item(0).getTextContent());
                    map.put("package",eElement.getElementsByTagName("package").item(0).getTextContent());
                    System.out.println("int4: "+ eElement.getElementsByTagName("int4").item(0).getTextContent());
                    map.put("int4",eElement.getElementsByTagName("int4").item(0).getTextContent());
                    System.out.println("serial: "+ eElement.getElementsByTagName("serial").item(0).getTextContent());
                    map.put("serial",eElement.getElementsByTagName("serial").item(0).getTextContent());
                    System.out.println("varchar: "+ eElement.getElementsByTagName("varchar").item(0).getTextContent());
                    map.put("varchar",eElement.getElementsByTagName("varchar").item(0).getTextContent());
                    System.out.println("date: "+ eElement.getElementsByTagName("date").item(0).getTextContent());
                    map.put("date",eElement.getElementsByTagName("date").item(0).getTextContent());
                    System.out.println("extension: "+ eElement.getElementsByTagName("extension").item(0).getTextContent());
                    map.put("extension",eElement.getElementsByTagName("extension").item(0).getTextContent());
                }  
            }
            return map;
        }   
        catch (Exception e)   
        {  
        e.printStackTrace();  
        }  
        return null;
            }
}
