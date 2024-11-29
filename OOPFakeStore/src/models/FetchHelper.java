package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FetchHelper {
	public List<Product> fetch() {
        try {
            URI uri = new URI("https://fakestoreapi.com/products");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
            	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                Type productListType = new TypeToken<List<Product>>() {}.getType();
                List<Product> products = gson.fromJson(response.toString(), productListType);
                return products;
            } else {
                System.out.println("HTTP Error: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
}
