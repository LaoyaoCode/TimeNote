package com.example.laoyao.timenote.Tools;

import android.provider.DocumentsContract;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Laoyao on 2017/8/22.
 */

public class XmlParser
{
    private static final String TAG = "XmlParser";
    //必应图片信息标签名
    private static final String BingImageTagName = "url" ;
    private static final String BingImageTagFather = "image" ;

    public static String GetBingImageMessage(String xmlData)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder bulider = factory.newDocumentBuilder();
            InputStream in = new   ByteArrayInputStream(xmlData.getBytes());
            Document doc = bulider.parse(in) ;
            Element root = doc.getDocumentElement();
            NodeList imageNode = root.getElementsByTagName(BingImageTagFather) ;

            if(imageNode.getLength() >= 1)
            {
                NodeList url =((Element) imageNode.item(0)).getElementsByTagName(BingImageTagName) ;

                if(url.getLength() >= 1)
                {
                    return url.item(0).getTextContent() ;
                }
                else
                {
                    return null ;
                }
            }
            else
            {
                return null ;
            }

        }
        catch (Exception e)
        {
            MLog.e(TAG , e.toString());
        }

        return null ;
    }
}
