package com.globant.demo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;

public class NetClientGet {

	// http://localhost:8080/search?name=J
	public static void main(String[] args) {

		try {

			URL url = new URL("http://localhost:8080/search?name=J");
			HttpURLConnection conn = request(url, "application/json", "GET", null);

			System.out.println("\n###### SEARCH AS XML ########");

			url = new URL("http://localhost:8080/search?name=B");
			request(url, "application/xml", "GET", null);

			System.out.println("\n###### CONSUMING PUT METHOD ########");
			
			url = new URL("http://localhost:8080/");
			String parameters = "username=Hernan";
			request(url, "application/json", "PUT", parameters.getBytes());
			
			System.out.println("\n###### SEARCH AS XML AGAIN ########");
			url = new URL("http://localhost:8080/search?name=");
			request(url, "application/xml", "GET", null);

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static HttpURLConnection request(URL url, String accept, String method, byte[] data)
			throws IOException, ProtocolException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setRequestProperty("Accept", accept);
		if (Optional.ofNullable(data).isPresent()) {
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(data);
			outputStream.flush();
		}

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		return conn;
	}

}