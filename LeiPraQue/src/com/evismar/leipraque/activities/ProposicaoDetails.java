package com.evismar.leipraque.activities;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.evismar.leipraque.R;
import com.evismar.leipraque.adjclasses.TextViewEx;
import com.evismar.leipraque.beans.Deputado;
import com.evismar.leipraque.beans.Proposicao;
import com.evismar.leipraque.image.ImageLoader;
import com.evismar.leipraque.xml.ParserDeputadoDetails;
import com.evismar.leipraque.xml.ParserProposicaoDetails;
import com.evismar.leipraque.xml.ReturnXML;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ProposicaoDetails extends ActionBarActivity {

	Proposicao proposicao = null;
	ListView listViewDeputados = null;
	
	private String urlProposicao = null;
	private String depId = null;
	private String depNome = null;
	private String depPartido = null;
	private String depEstado = null;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proposicao_details);
		
		ReturnXML returnProposicao = new ReturnXML(this);
		ReturnXML returnDeputado = new ReturnXML(this);
		urlProposicao = getIntent().getStringExtra("urlKey");
		depId = getIntent().getStringExtra("depIdKey");
		depNome = getIntent().getStringExtra("depNomeKey");
		depPartido = getIntent().getStringExtra("depPartidoKey");
		depEstado = getIntent().getStringExtra("depEstKey");
		
	       // Loader image - will be shown before loading image
        int loader = R.drawable.loader;
         
        // Imageview to show
        ImageView image = (ImageView) findViewById(R.id.imageDeputado);
         
        // Image url
        String image_url = "http://www.camara.gov.br/internet/deputado/bandep/"+depId+".jpg";
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        // image - ImageView
        imgLoader.DisplayImage(image_url, loader, image);

		returnProposicao.setURL(urlProposicao);

		returnProposicao.start();		
		synchronized(this) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		returnProposicao.interrupt();


		
		returnDeputado.setURL("http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDetalhesDeputado?ideCadastro="+depId+"&numLegislatura=");

		returnDeputado.start();		
		synchronized(this) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		returnDeputado.interrupt();
		String xmlProposicao = returnProposicao.getResult();
		String xmlDeputado = returnDeputado.getResult();
		showDetails(xmlProposicao, xmlDeputado);	
		


	}

	public void goToSo(View view) {
		goToUrl(proposicao.getLinkInteiroTeor());
	}

	private void goToUrl(String url) {

		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}


	private void showDetails(String prop, String dep) {
		InputStream propIs = new ByteArrayInputStream(prop.getBytes());
		ParserProposicaoDetails propParser = new ParserProposicaoDetails();
		proposicao = propParser.parse(propIs);
		
		InputStream depIs = new ByteArrayInputStream(dep.getBytes());
		ParserDeputadoDetails depParser = new ParserDeputadoDetails();
		Deputado deputado = depParser.parse(depIs);

		TextView nomeText = (TextView) findViewById(R.id.textViewNome);
		nomeText.setText(proposicao.getNome());

		TextView dataText = (TextView) findViewById(R.id.textViewData);
		dataText.setText(proposicao.getDatApresentacao());

		TextViewEx ementaText = (TextViewEx) findViewById(R.id.textViewEmenta);
		ementaText.setText(proposicao.getTextoEmenta(), true);

		TextView tipoProposicao = (TextView) findViewById(R.id.textTipoProposicao);
		tipoProposicao.setText(proposicao.getTipoProposicao());

		TextView tema = (TextView) findViewById(R.id.textViewTema);
		tema.setText(proposicao.getTema());

		TextViewEx explicacaoEmenta = (TextViewEx) findViewById(R.id.textViewExplicacaoEmenta);
		TextView explicacaoEmentaLabel = (TextView) findViewById(R.id.textViewExpLabel);
		explicacaoEmenta.setText(checkSize(proposicao.getExplicacaoEmenta(), explicacaoEmentaLabel, explicacaoEmenta),
				true);

		TextView regimeTram = (TextView) findViewById(R.id.textViewRegimeTramitacao);
		TextView regimeTramLabel = (TextView) findViewById(R.id.TextViewRegTramLabel);
		regimeTram.setText(checkSize(proposicao.getRegimeTramitacao(), regimeTramLabel, regimeTram));

		TextView ultimoDesp = (TextView) findViewById(R.id.textViewUltimoDespacho);
		TextView ultimoDespLabel = (TextView) findViewById(R.id.textViewUltDespLabel);
		ultimoDesp.setText(checkSize(proposicao.getUltimoDespacho(), ultimoDespLabel, ultimoDesp));

		TextView situacao = (TextView) findViewById(R.id.textViewSituacao);
		TextView situacaoLabel = (TextView) findViewById(R.id.TextViewSitLabel);
		situacao.setText(checkSize(proposicao.getSituacao(), situacaoLabel, situacao));
		

		TextView autor = (TextView) findViewById(R.id.textViewAutorNome);
		autor.setText(depNome);

 
		TextView partido = (TextView) findViewById(R.id.textViewAutorPartido);
		String depPartidoCleaned = depPartido.replace(" ", "");
		partido.setText(depPartidoCleaned+" / "+depEstado);

		TextView telefone = (TextView) findViewById(R.id.textViewAutorTel);
		telefone.setText("+55 61 "+deputado.getTelefone());
		
		TextView email = (TextView) findViewById(R.id.textViewAutorEmail);
		email.setText(deputado.getEmail());
		


	}

	public String checkSize(String str, TextView label, TextView text) {

		int length = str.length();
		if (length < 6) {
			label.setVisibility(View.GONE);
			text.setVisibility(View.GONE);
			return "";
			
		} else {
			return str;
		}
	}

}
