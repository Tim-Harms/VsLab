package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1254575994729199914L;
	
	private int catId;
	private List<Category> categories;

	private static HttpURLConnection connection;

	public String execute() {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			String urlstring = "http://localhost:8081/apic/categories?id=" + catId;
			//urlstring = "http://localhost:8081/apic/categories?name=testcatRestAPI";

			try {
				URL url = new URL(urlstring);
				connection = (HttpURLConnection) url.openConnection();

				//Request setup
				connection.setRequestMethod("DELETE");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);

				int status = connection.getResponseCode();
				System.out.println(status);

				res = "success";

			} catch (MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
			/*// Helper inserts new Category in DB:
			CategoryManager categoryManager = new CategoryManagerImpl();
		
			categoryManager.delCategoryById(catId);

			categories = categoryManager.getCategories();
				
			res = "success";*/

		}
		
		return res;
		
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
