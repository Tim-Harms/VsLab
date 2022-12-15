package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.CategoryDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


public class CategoryManagerImpl implements CategoryManager{
	private CategoryDAO helper;
	
	public CategoryManagerImpl() {
		helper = new CategoryDAO();
	}

	public List<Category> getCategories() {

		System.out.println("arrived in get categories method");
		try {
			URL url = new URL("http://categoryservice:8081/apic/categories");
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
				List<Category> categoriyList = mapper.readValue(response.toString(), new TypeReference<List<Category>>(){});
				System.out.println("Object List: " + categoriyList.toString());
				//System.out.println("Object List first object id: " + categoriyList.get(0).getId());
				//System.out.println("Object List first object name: " + categoriyList.get(0).getName());
				return categoriyList;//TODO: return this List
				//TODO: Category Objects have empty list of products

			} else {
				System.out.println("GET request did not work.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}




		return null;


		//return helper.getObjectList();
	}

	public Category getCategory(int id) {

		List<Category> allCategories = getCategories();

		for(Category c : allCategories)
		{
			if(c.getId() == id)
				return c;
		}

		System.out.println("Category with given Id does not exist");
		return null;

	/*	System.out.println("arrived in get categorie method");
		try {
			URL url = new URL("http://categoryservice:8081/apic/categorie?id=" + id);
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
				Category categoriy = mapper.readValue(response.toString(), new TypeReference<Category>(){});
				System.out.println("Category Object: " + categoriy.toString());
				//TODO: return this object
				//TODO: Get list of products

			} else {
				System.out.println("GET request did not work.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}





		return helper.getObjectById(id);*/
	}

	public Category getCategoryByName(String name) {
		//TODO: NoUsages (notactually a todo but I like highlighting xD)
		return helper.getObjectByName(name);
	}

	public void addCategory(String name) throws Exception{
		System.out.println("arrived in add category method");
		URL url = new URL("http://categoryservice:8081/apic/categories?name=" + name);
		System.out.println("url: " + url);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		DataOutputStream wr = new DataOutputStream (
				con.getOutputStream());
		wr.close();

		if(con.getResponseCode() > 299){
			System.out.println("response code was > 299");
			throw new Exception();
		}

		System.out.println("arrived in end of add categroymethod");
	}

	public void delCategory(Category cat) {
		//TODO: NoUsages (notactually a todo but I like highlighting xD)
// 		Products are also deleted because of relation in Category.java 
//		helper.deleteById(cat.getId());
		System.out.println("arrived in delCategory Method");
		delCategoryById(cat.getId());
	}

	public void delCategoryById(int id) {
		//helper.deleteById(id);
		System.out.println("arrived in delCategoryByID Method");
		try {
			ProductManagerImpl productManager = new ProductManagerImpl();
			productManager.deleteProductsByCategoryId(id);
			URL url = new URL("http://categoryservice:8081/apic/categories?id=" + id);
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
	}
}
