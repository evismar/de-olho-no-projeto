package com.evismar.leipraque.activities;
 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import com.evismar.leipraque.R;
import com.evismar.leipraque.adjclasses.TextViewEx;
import com.evismar.leipraque.beans.Deputado;
import com.evismar.leipraque.beans.Proposicao;
import com.evismar.leipraque.xml.ParserProposicoes;
import com.evismar.leipraque.xml.ReturnXML;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class LastProposicoes extends Activity {
 
    ListView listView;
    private static List<Proposicao> proposicoes = null;
    public static InputStream xmlData = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);	
        onClickItem();
        Toast.makeText(getBaseContext(),
                "Conectando ao Servidor",
                Toast.LENGTH_SHORT).show();
        
		ReturnXML returnProposicoes = new ReturnXML(this);
		returnProposicoes.setURL(getURL());

		returnProposicoes.start();		
		synchronized(this) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		returnProposicoes.interrupt();
		String xmlProposicoes = returnProposicoes.getResult();
		populateListProposicoes(xmlProposicoes);	
    }

    
 

    
    private void populateListProposicoes(String str){
    	InputStream is = new ByteArrayInputStream(str.getBytes());
        listView = (ListView) findViewById(R.id.list);
      //  List<Proposicao> proposicoes = null;
        ParserProposicoes parser = new ParserProposicoes();
      
		proposicoes = parser.parse(is);
		ArrayAdapter<Proposicao> adapter = new MyListAdapter();
			listView.setAdapter(adapter);
    }
    
    private class MyListAdapter extends ArrayAdapter<Proposicao>{
    	public MyListAdapter(){
    		super(LastProposicoes.this, R.layout.list_item_proposicoes, proposicoes);
    	}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if(itemView == null){
				itemView = getLayoutInflater().inflate(R.layout.list_item_proposicoes, parent, false);
			}
			Proposicao currentProposicao = proposicoes.get(position);
			
			TextView nomeText = (TextView) itemView.findViewById(R.id.textViewNome);
			nomeText.setText(currentProposicao.getNome());
			
			TextView dataText = (TextView) itemView.findViewById(R.id.textViewData);
			String string = currentProposicao.getDatApresentacao();
			String[] parts = string.split(" ");
			String part1 = parts[0]; 
			dataText.setText(part1);
			
			TextViewEx  ementaText = (TextViewEx) itemView.findViewById(R.id.textViewEmenta);
			ementaText.setText(currentProposicao.getTextoEmenta(), true);
			return itemView;
		}
    	
    }

    
    private void onClickItem(){
    	ListView list = (ListView) findViewById(R.id.list);
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paret, View viewClicked, int positon,
					long id) {
				Proposicao clickedProposicao = proposicoes.get(positon);
				Deputado autor = proposicoes.get(positon).getAutor();
				String propNome = clickedProposicao.getNome();
				String[] parts = propNome.split(" ");
				String part1 = parts[0]; 
				String part2 = parts[1];
				String[] parts2 = part2.split("/");
				String part21 = parts2[0];
				String part22 = parts2[1];
				String urlProp = "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ObterProposicao?tipo="+
				part1+"&numero="+part21+"&ano="+part22;


				
	            Intent intent = new Intent(LastProposicoes.this, ProposicaoDetails.class);
	            Bundle b = new Bundle();
	            b.putString("urlKey", urlProp);
	            
	            b.putString("depIdKey", autor.getIdeCadastro());
	            b.putString("depNomeKey", autor.getNomeParlamentar());
	            b.putString("depPartidoKey", autor.getPartido());
	            b.putString("depEstKey", autor.getUf());
//	            Log.d("UUUUURLLLL", autor.getNomeParlamentar());
	            Log.d("UUUUURLLLL", clickedProposicao.getAutor().getNomeParlamentar());
	            
	            intent.putExtras(b); 
//	            
//	            
//	            
	            startActivity(intent);
//	            finish();
//				startActivity(new Intent(LastProposicoes.this, ProposicaoDetails.class));
				
				
			}
		}); 
		
    }
    
    public String getURL(){ 
    	String url;
    	String currentYear;
    	String finalDate;
    	String firstDate;
        Calendar now = Calendar.getInstance();
        Format formato = new SimpleDateFormat("dd/MM/yyyy");
        
        finalDate =  formato.format(now.getTime());
        now.add(Calendar.DAY_OF_MONTH, -10);
        firstDate =  formato.format(now.getTime());

        now = Calendar.getInstance();
        formato = new SimpleDateFormat("yyyy");
        
        currentYear = formato.format(now.getTime());
        url = "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano="+currentYear+"&datApresentacaoIni="+firstDate+"&datApresentacaoFim="+finalDate+"&parteNomeAutor=&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=";
        
        return url;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
 
}
