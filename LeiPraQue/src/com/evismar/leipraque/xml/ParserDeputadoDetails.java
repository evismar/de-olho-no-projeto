package com.evismar.leipraque.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.evismar.leipraque.beans.Deputado;



public class ParserDeputadoDetails {
    private Deputado deputado;
    private String text;
 
    
 
    public Deputado getDeputado() {
        return deputado;
    }
 
    public Deputado parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
 
            parser.setInput(is, null);
 
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = parser.getName();
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("deputado")) {
                        // create a new instance of Deputado
                        deputado = new Deputado();
                    }
                    break;
                    
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
 
                case XmlPullParser.END_TAG:

                    if (tagname.equalsIgnoreCase("Deputado")) {
                        deputado.setNomeParlamentar(text);
                    	
                    } else if (tagname.equalsIgnoreCase("email")) {
                        deputado.setEmail(text);
                    } else if (tagname.equalsIgnoreCase("telefone")) {
                        deputado.setTelefone(text);
                        break;
                    } 
                    break;


 
                default:
                    break;
                }
                eventType = parser.next();
            }
 
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return deputado;
    }
}