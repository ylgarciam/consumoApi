package ConsumoApiMarvel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import ConsumoApiMarvelConstants.ConsumoApiConstantes;

public class ConsumoApi {
	public static void main(String[] args) throws NoSuchAlgorithmException {}
	/**
	 * Método principal con la logica para elconsumo de los servicios de marvel mediante una URL
	 * @param apiUrl URL del servicio que se desea consumir
	 * @return Respuesta del servicio consumido
	 * @throws NoSuchAlgorithmException En caso de ocurrir un error
	 */
	private String getUrl(String apiUrl) throws NoSuchAlgorithmException {
		String textoOriginal = "1" + ConsumoApiConstantes.privateKey + ConsumoApiConstantes.publicKey;
		StringBuilder response = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = textoOriginal.getBytes();
			byte[] digest = md.digest(bytes);
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}

			apiUrl = apiUrl + "?ts=" + 1 + "&apikey="
					+ ConsumoApiConstantes.publicKey + "&hash=" + sb.toString();

			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
				System.out.println(response.toString());
			} else {
				System.out.println("Error en la solicitud del servicio " + responseCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 	response.toString();
	}
	/**
	 * Método para buscar todos los datos existentes
	 * @return String respuesta del servicio consumido 
	 * @throws NoSuchAlgorithmException En caso de ocurrir un error
	 */
	public String getCharacters() throws NoSuchAlgorithmException {
		String url = ConsumoApiConstantes.urlMarvel;
		return this.getUrl(url);
	}
	/**
	 * Método para consumir por id el servicio 
	 * @param characterId Id con el que se realizará la petición al servicio de marvel
	 * @return respuesta del Servicio
	 * @throws NoSuchAlgorithmException En caso de ocurrir algun error
	 */
	public String getCharactersById(String characterId) throws NoSuchAlgorithmException {
		String url = ConsumoApiConstantes.urlMarvel+"/"+ characterId;
		return this.getUrl(url);
	}

}