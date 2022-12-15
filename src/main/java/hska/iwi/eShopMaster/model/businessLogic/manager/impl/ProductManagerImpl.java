package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.*;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductManagerImpl implements ProductManager {
	private ProductDAO helper;
	
	public ProductManagerImpl() {
		helper = new ProductDAO();
	}

	public List<Product> getProducts() {

		System.out.println("arrived in get products method");
		try {
			URL url = new URL("http://productservice:8082/api/products/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println("Response: " + response.toString());


				//Parse Json
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				List<Product> productList = mapper.readValue(response.toString(), new TypeReference<List<Product>>(){});
				System.out.println("Product List: " + productList.toString());
				//System.out.println("Product List first object id: " + productList.get(0).getId());
				//System.out.println("Product List first object name: " + productList.get(0).getName());
				//System.out.println("Product List first category: " + productList.get(0).getcategoryId());


				//Get Mapping List
				List<ProductCategoryMapping> mappings = mapper.readValue(response.toString(), new TypeReference<List<ProductCategoryMapping>>(){});


				//Get Categories and Map
				CategoryManager categoryManager = new CategoryManagerImpl();
				List<Category> categories = categoryManager.getCategories();
				for (Product p : productList) //Todo: there is probably a more elegant way than this to map the categories to the categoryIds
				{
					for (Category c : categories)
					{
						for (ProductCategoryMapping m : mappings)
						{
							if(m.getId() == p.getId() && m.getCategoryId() == c.getId())
							{
								p.setCategory(c);
							}
						}
					}
				}

				return productList;
			} else {
				System.out.println("GET request did not work.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}



		return null;
		//return helper.getObjectList();
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {

	    if(searchDescription == null)
	        searchDescription = "";

	    if(searchMinPrice == null)
	        searchMinPrice = 0.0;

        if(searchMaxPrice == null)
            searchMaxPrice = Double.MAX_VALUE;


        try {
            URL url = new URL("http://productservice:8082/api/products?searchQuery=" + searchDescription + "&minPrice=" + searchMinPrice + "&maxPrice=" + searchMaxPrice);
            System.out.println("URL:" + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println("Response: " + response.toString());


                //Parse Json
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<Product> productList = mapper.readValue(response.toString(), new TypeReference<List<Product>>() {
                });
                System.out.println("Product List: " + productList.toString());




				//Get Mapping List
				List<ProductCategoryMapping> mappings = mapper.readValue(response.toString(), new TypeReference<List<ProductCategoryMapping>>(){});


				//Get Categories and Map
				CategoryManager categoryManager = new CategoryManagerImpl();
				List<Category> categories = categoryManager.getCategories();
				for (Product p : productList) //Todo: there is probably a more elegant way than this to map the categories to the categoryIds
				{
					for (Category c : categories)
					{
						for (ProductCategoryMapping m : mappings)
						{
							if(m.getId() == p.getId() && m.getCategoryId() == c.getId())
							{
								p.setCategory(c);
							}
						}
					}
				}


                return productList;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;


	/*	List<Product> allProducts = getProducts();
		List<Product> productsThatFitSearchValues = new ArrayList<Product>();


		for(Product p : allProducts)
		{

			if(searchMinPrice != null && searchMaxPrice != null && searchDescription != null && !searchDescription.isEmpty()) //All 3 filled out
			{
				if(p.getPrice() >= searchMinPrice && p.getPrice() <= searchMaxPrice && p.getName().contains(searchDescription))
					productsThatFitSearchValues.add(p);
			}
			else if(searchMinPrice != null && searchMaxPrice != null) //Description empty
			{
				if(p.getPrice() >= searchMinPrice && p.getPrice() <= searchMaxPrice)
					productsThatFitSearchValues.add(p);
			}
			else if(searchMaxPrice != null && searchDescription != null && !searchDescription.isEmpty()) //MinPrice empty
			{
				if(p.getPrice() <= searchMaxPrice && p.getName().contains(searchDescription))
					productsThatFitSearchValues.add(p);
			}
			else if(searchMinPrice != null && searchDescription != null && !searchDescription.isEmpty()) //MaxPrice empty
			{
				if(p.getPrice() >= searchMinPrice && p.getName().contains(searchDescription))
					productsThatFitSearchValues.add(p);
			}
			else if(searchMinPrice != null) //Just MinPrice
			{
				if(p.getPrice() >= searchMinPrice)
					productsThatFitSearchValues.add(p);
			}
			else if(searchMaxPrice != null) //Just MaxPrice
			{
				if(p.getPrice() <= searchMaxPrice)
					productsThatFitSearchValues.add(p);
			}
			else if(searchDescription != null && !searchDescription.isEmpty()) //Just Description
			{
				if(p.getName().contains(searchDescription))
					productsThatFitSearchValues.add(p);
			} //TODO: What the fuck is this even?
		}

		return productsThatFitSearchValues;*/

		//return new ProductDAO().getProductListByCriteria(searchDescription, searchMinPrice, searchMaxPrice);
	}

	public Product getProductById(int id) {

		List<Product> allProducts = getProducts();

		for(Product p : allProducts)
		{
			if(p.getId() == id)
				return p;
		}

		System.out.println("Product with given Id does not exist");
		return null;
	/*	try {
			URL url = new URL("http://productservice:8082/api/products?id=" + id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println("Response: " + response.toString());


				//Parse Json
				ObjectMapper mapper = new ObjectMapper();
				Product product = mapper.readValue(response.toString(), new TypeReference<Product>(){});
				System.out.println("Product: " + product.toString());
				return product;

			} else {
				System.out.println("GET request did not work.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}


		return null;*/

		//return helper.getObjectById(id);
	}

	public Product getProductByName(String name) {
		return helper.getObjectByName(name);
	} //TODO: No usages
	
	public int addProduct(String name, double price, int categoryId, String details) { //Todo: details with space are not supported
		System.out.println("arrived in add product method");
		int productId = -1;

		CategoryManager categoryManager = new CategoryManagerImpl();
		try{
			Category category = categoryManager.getCategory(categoryId);

			if(category != null){
				Product product;
				if(details == null){
					product = new Product(name, price, category);
				} else{
					product = new Product(name, price, category, details);
				}

				//helper.saveObject(product);
				URL url = new URL("http://productservice:8082/api/products?name=" + name + "&price=" + price + "&categoryId=" + categoryId + "&details=" + details); //TODO Parameters in Body
				System.out.println("url: " + url);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				DataOutputStream wr = new DataOutputStream (
						con.getOutputStream());
				wr.close();

				if(con.getResponseCode() > 299){
					System.out.println("response code was > 299");
                    System.out.println(con.getResponseCode());
					throw new Exception();
				}

				productId = product.getId();
			}
		}catch(Exception e){
			System.out.println("Exception in add product method");
			return -1;
		}
			 
		return productId;
	}
	

	public void deleteProductById(int id) {
		System.out.println("arrived in delCategoryByID Method");
		try {
			URL url = new URL("http://productservice:8082/api/products?id=" + id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty(
					"Content-Type", "application/x-www-form-urlencoded" );
			con.setRequestMethod("DELETE");
			con.connect();

			if(con.getResponseCode() > 299) {
				System.out.println("responseCode was > 299");
				throw new Exception();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		//helper.deleteById(id);
	}

	public void deleteProductsByCategoryId(int categoryId) { //TODO implement
		// TODO Auto-generated method stub
		List<Product> products = getProducts();
		//products.stream().filter(product -> product.getCategory().getId() == categoryId)

		for(Product product : products){
			if(product.getCategory().getId() == categoryId){
				deleteProductById(product.getId());
			}
		}
	}

}
