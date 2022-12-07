package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.CategoryDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CategoryManagerImpl implements CategoryManager{
	private CategoryDAO helper;
	
	public CategoryManagerImpl() {
		helper = new CategoryDAO();
	}

	public List<Category> getCategories() {
		return helper.getObjectList();
	}

	public Category getCategory(int id) {
		return helper.getObjectById(id);
	}

	public Category getCategoryByName(String name) {
		return helper.getObjectByName(name);
	}

	public void addCategory(String name) throws Exception{

		URL url = new URL("http://category:8081/apic/categories?name=" + name + "Test");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		DataOutputStream wr = new DataOutputStream (
				con.getOutputStream());
		wr.close();

		if(con.getResponseCode() > 299)
			throw new Exception();

	}

	public void delCategory(Category cat) {
	
// 		Products are also deleted because of relation in Category.java 
		helper.deleteById(cat.getId());
/*		try {
			URL url = new URL("http://category:8081/apic/categories?id=" + id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("DELETE");
			DataOutputStream wr = new DataOutputStream(
					con.getOutputStream());
			wr.close();

			if(con.getResponseCode() > 299)
				throw new Exception();
		} catch(Exception e){
			e.printStackTrace();
		}*/
	}

	public void delCategoryById(int id) {
		
		helper.deleteById(id);
	}
}
