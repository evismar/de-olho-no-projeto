package com.evismar.leipraque.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.evismar.leipraque.beans.Deputado;
import com.evismar.leipraque.beans.Proposicao;

public class ParserProposicoes {
	List<Proposicao> proposicoes;
	private Proposicao proposicao;
	private Deputado deputado;
	private String text;

	public ParserProposicoes() {
		proposicoes = new ArrayList<Proposicao>();
	}

	public List<Proposicao> getproposicoes() {
		return proposicoes;
	}

	public List<Proposicao> parse(InputStream is) {
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();
			int nome = 0;
			int qtdAutores = 0;
			String nomeAutor = "";
			parser.setInput(is, null);

			int eventType = parser.getEventType();
	while (eventType != XmlPullParser.END_DOCUMENT) {

		String tagname = parser.getName();
		switch (eventType) {
		case XmlPullParser.START_TAG:
			if (tagname.equalsIgnoreCase("proposicao")) {
				// create a new instance of proposicao
				nome = 0;
				qtdAutores = 0;

				proposicao = new Proposicao();
				deputado = new Deputado();
			}
			break;

		case XmlPullParser.TEXT:
			text = parser.getText();
			break;

		case XmlPullParser.END_TAG:
			if (tagname.equalsIgnoreCase("proposicao")) {
				// add proposicao object to list
				proposicoes.add(proposicao);
			 
			} else if (tagname.equalsIgnoreCase("nome")) {
				if (nome == 0) {
					proposicao.setNome(text);
				}
				nome++;
			}else if (tagname.equalsIgnoreCase("datApresentacao")) {
				proposicao.setDatApresentacao(text);
			} else if (tagname.equalsIgnoreCase("txtEmenta")) {
				proposicao.setTextoEmenta(text);
			}  
			
			
				else if (tagname.equalsIgnoreCase("idecadastro")) {
				proposicao.getAutor().setIdeCadastro(text);
			} else if (tagname.equalsIgnoreCase("txtNomeAutor")) {
				proposicao.getAutor().setNomeParlamentar(text);
			} else if (tagname.equalsIgnoreCase("txtSiglaPartido")) {
				proposicao.getAutor().setPartido(text);
			} else if (tagname.equalsIgnoreCase("txtSiglaUF")) {
				proposicao.getAutor().setUf(text);
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

return proposicoes;
}
	
	
	
	
	
	
	
	
	
}