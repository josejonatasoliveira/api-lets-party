package br.edu.fatec.service;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.json.simple.parser.ParseException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatec.model.Endereco;
import br.edu.fatec.repository.EnderecoRepositoy;

@Service("enderecoService")
public class EnderecoService {
	
	@Autowired
	EnderecoRepositoy enderecoRepositoy;
	
	public void salvarEndereco(Endereco endereco) throws IOException {
		
		if (endereco.getCep().length() == 8 
				&& endereco.getNome().contains(getEndereco(endereco.getCep()))
				&& endereco.getBairro().contains(getBairro(endereco.getCep()))
				&& endereco.getCidade().contains(getCidade(endereco.getCep()))
				&& endereco.getEstado().contains(endereco.getCep())) {
			enderecoRepositoy.save(endereco);
			
			
		}else {
			throw new IOException("Endereco invalido!");
		}
		
	}
	
	public String getEndereco(String CEP) throws IOException {

        try {

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
            for (Element urlEndereco : urlPesquisa) {
                return urlEndereco.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getBairro(String CEP) throws IOException {

        try {

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("td:gt(1)");
            for (Element urlBairro : urlPesquisa) {
                return urlBairro.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getCidade(String CEP) throws IOException {

        try {

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
            for (Element urlCidade : urlPesquisa) {
                return urlCidade.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getUF(String CEP) throws IOException {

        try {

            Document doc = Jsoup
                    .connect("http://www.qualocep.com/busca-cep/" + CEP)
                    .timeout(120000).get();
            Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
            for (Element urlUF : urlPesquisa) {
                return urlUF.text();
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }

    public String getLatLong(String CEP) throws IOException, ParseException {

        try {

            if (CEP.contains("-")) {
                Document doc = Jsoup
                        .connect(
                                "http://maps.googleapis.com/maps/api/geocode/xml?address="
                                        + CEP + "&language=pt-BR&sensor=true")
                        .timeout(120000).get();
                Elements lat = doc.select("geometry").select("location")
                        .select("lat");
                Elements lng = doc.select("geometry").select("location")
                        .select("lng");
                for (Element Latitude : lat) {
                    for (Element Longitude : lng) {
                        return Latitude.text() + "," + Longitude.text();
                    }

                }
            } else {

                StringBuilder cepHif = new StringBuilder(CEP);  
                cepHif.insert(CEP.length() - 3, '-');

                Document doc = Jsoup
                        .connect(
                                "http://maps.googleapis.com/maps/api/geocode/xml?address="
                                        + cepHif + "&language=pt-BR&sensor=true")
                        .timeout(120000).get();
                Elements lat = doc.select("geometry").select("location")
                        .select("lat");
                Elements lng = doc.select("geometry").select("location")
                        .select("lng");
                for (Element Latitude : lat) {
                    for (Element Longitude : lng) {
                        return Latitude.text() + "," + Longitude.text();
                    }

                }
            }

        } catch (SocketTimeoutException e) {

        } catch (HttpStatusException w) {

        }
        return CEP;
    }


}
