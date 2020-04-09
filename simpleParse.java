import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import java.net.URLConnection;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import javax.xml.transform.TransformerException;
public class simpleParse
{
    private Picture image;
    public static void normal(String sub) throws Exception
    {
        String XML = refrenceParser.Parser(sub);
        MakingObject[] storage = refrenceParser.stringSearcher(XML);
        for(int i = 0; i<storage.length;i++)
        {
            refrenceParser.fromURL((XML.substring(storage[i].getFirst(),storage[i].getSecond())));
        }
    }
    
    public static void manipulated(String sub, int howMany) throws Exception
    {
        String XML = refrenceParser.Parser(sub);
        MakingObject[] storage = refrenceParser.stringSearcher(XML);
        if(howMany > storage.length){howMany = storage.length;}
        for(int i = 0; i<howMany;i++)
        {
            refrenceParser.fromURL((XML.substring(storage[i].getFirst(),storage[i].getSecond())));
        }
    } 
    
    public static void sized(String sub, int howMany, int width, int height) throws Exception
    {
        String XML = refrenceParser.Parser(sub);
        MakingObject[] storage = refrenceParser.stringSearcher(XML);
        if(howMany > storage.length){howMany = storage.length;}
        for(int i = 0; i<howMany;i++)
        {
            refrenceParser.fromURLsized((XML.substring(storage[i].getFirst(),storage[i].getSecond())),width,height);
        }
    }
    
    public static void original(String sub) throws Exception
    {
        String XML = refrenceParser.Parser(sub);
        MakingObject[] storage = refrenceParser.stringSearcher(XML);
        for(int i = 0; i<storage.length;i++)
        {
            refrenceParser.fromURLoriginal((XML.substring(storage[i].getFirst(),storage[i].getSecond())));
        }
    }
    
    public static void USER(String user) throws Exception
    {
        String XML = refrenceParser.userParser(user);
        MakingObject[] storage = refrenceParser.stringSearcher(XML);
        for(int i = 0; i<storage.length;i++)
        {
            refrenceParser.fromURLoriginal((XML.substring(storage[i].getFirst(),storage[i].getSecond())));
        }
    }
}

class refrenceParser
{
    public static String Parser(String subreddit) throws Exception
    {
        subreddit = "https://www.reddit.com/r/"+subreddit+".xml";
        return getStringFromDocument(ReadXMLFromURL(subreddit));
    }
    
    public static String userParser(String user) throws Exception
    {
        user = "https://www.reddit.com/u/"+user+".xml";
        return getStringFromDocument(ReadXMLFromURL(user));
    }

    public static MakingObject[] stringSearcher(String str)
    {
        String findStr = "https://i.redd.it/";
        int lastIndex = 0;
        int httpCount = 0;
        int i = 0;
        while(lastIndex != -1){
            lastIndex = str.indexOf(findStr,lastIndex);
            if(lastIndex != -1){
                httpCount++;
                lastIndex += findStr.length();
            }
        }
        int[] httpArr = new int[httpCount];
        lastIndex = 0;
        while(lastIndex != -1){
            lastIndex = str.indexOf(findStr,lastIndex);
            if(lastIndex != -1){
                httpArr[i] = lastIndex;
                i++;
                lastIndex += findStr.length();
            }
        }               
        MakingObject[] completeArr = new MakingObject[httpCount];
        for(int a = 0; a<httpArr.length; a++) //System.out.println(str.substring(httpArr[a],str.indexOf(".jpg",httpArr[a]+2)+4));
        {
            completeArr[a] = new MakingObject(httpArr[a],str.indexOf(".jpg",httpArr[a])+4);
            try{
            int png = str.substring(httpArr[a],str.indexOf(".png",httpArr[a])+4).length();
            int jpg = str.substring(httpArr[a],str.indexOf(".jpg",httpArr[a])+4).length();
            if(jpg > png){completeArr[a] = new MakingObject(httpArr[a],str.indexOf(".png",httpArr[a])+4); }}
            catch(Exception IO){}
        }
        return completeArr;
    }

    public static String getStringFromDocument(Document doc) throws TransformerException 
    {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    public static Document ReadXMLFromURL(String urlIN) throws Exception //https://www.reddit.com/r/cats.xml
    {
        URL url = new URL(urlIN); //ftp://ftp.bom.gov.au/anon/gen/fwo/IDV10752.xml
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", ""); //not default OR //"User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"   
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(conn.getInputStream());        
        return doc;
        //TransformerFactory transformerFactory= TransformerFactory.newInstance();
        //Transformer xform = transformerFactory.newTransformer();
        //xform.transform(new DOMSource(doc), new StreamResult(System.out));
    }

    public static void fromURL(String inputURL)
    {
        Picture URL = new Picture(1,1);
        URL.fromURL(inputURL);
        images show = new images(URL);
        show.resize(500,500);
        show.show();
    }
    
    public static void fromURLsized(String inputURL, int width, int height)
    {
        Picture URL = new Picture(1,1);
        URL.fromURL(inputURL);
        images show = new images(URL);
        show.resize(width,height);
        show.show();
    }
    
    public static void fromURLoriginal(String inputURL)
    {
        Picture URL = new Picture(1,1);
        URL.fromURL(inputURL);
        URL.show();
    }
}

class MakingObject
{
    private int first;
    private int second;
    public MakingObject(int a, int b)
    {
        first = a;
        second = b;
    }

    public int getFirst()
    {
        return first;
    }

    public int getSecond()
    {
        return second;
    }
}

class images
{
    private Picture image;
    public images(Picture image)
    {
        this.image = image;
    }

    public void show()
    {
        image.show();
    } 

    public void resize(int width, int height)
    {
        Picture tempImage = new Picture(width, height);
        for(int x = 0; x<width; x++)
        {
            for(int y = 0; y<height; y++)
            {
                int xL = (int)(x*(image.getWidth()/(double)width));
                int yL = (int)(y*(image.getHeight()/(double)height));
                tempImage.getPixel(x,y).setColor(image.getPixel(xL,yL).getColor());
            }
        }
        image = tempImage;
    }
}