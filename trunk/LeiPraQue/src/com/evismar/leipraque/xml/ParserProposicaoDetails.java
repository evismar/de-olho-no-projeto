package com.evismar.leipraque.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.evismar.leipraque.beans.Deputado;
import com.evismar.leipraque.beans.Proposicao;

public class ParserProposicaoDetails {
    private Proposicao proposicao;
    private Deputado deputado;
    private String text;
 

 
    public Proposicao getproposicao() {
        return proposicao;
    }
 
    public Proposicao parse(InputStream is) {
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
                    if (tagname.equalsIgnoreCase("proposicao")) {
                        // create a new instance of proposicao
                        proposicao = new Proposicao();
                        deputado = new Deputado();
                    }
                    break;
                    
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
 
                case XmlPullParser.END_TAG:

                    if (tagname.equalsIgnoreCase("nomeProposicao")) {
                        proposicao.setNome(text);
                        
                    	
                    } else if (tagname.equalsIgnoreCase("DataApresentacao")) {
                        proposicao.setDatApresentacao(text);
                    } else if (tagname.equalsIgnoreCase("Ementa")) {
                        proposicao.setTextoEmenta(text);
                    }else if (tagname.equalsIgnoreCase("tipoProposicao")) {
                        proposicao.setTipoProposicao(text);
                    } else if (tagname.equalsIgnoreCase("tema")) {
                        proposicao.setTema(text);
                    }else if (tagname.equalsIgnoreCase("ExplicacaoEmenta")) {
                        proposicao.setExplicacaoEmenta(text);
                    }else if (tagname.equalsIgnoreCase("RegimeTramitacao")) {
                        proposicao.setRegimeTramitacao(text);
                    } else if (tagname.equalsIgnoreCase("UltimoDespacho")) {
                        proposicao.setUltimoDespacho(text);
                    }else if (tagname.equalsIgnoreCase("Situacao")) {
                        proposicao.setSituacao(text);
                    } else if (tagname.equalsIgnoreCase("LinkInteiroTeor")) {
                        proposicao.setLinkInteiroTeor(text);
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
        return proposicao;
    }
}