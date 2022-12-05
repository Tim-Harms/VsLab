package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
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

public class AddProductAction extends ActionSupport {

	private static final long serialVersionUID = 39979991339088L;

	private String name = null;
	private String price = null;
	private int categoryId = 0;
	private String details = null;
	private List<Category> categories;

	private static HttpURLConnection connection;

	public String execute() throws Exception {
		String result = "input";
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			String urlstring = "http://localhost:8080/api/products?name=" + name + "?price=" + price + "?categoryId=" + categoryId + "?details=" + details;
			//urlstring = "http://localhost:8081/apic/categories?name=testcatRestAPI";

			try {
				URL url = new URL(urlstring);
				connection = (HttpURLConnection) url.openConnection();

				//Request setup
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);

				int status = connection.getResponseCode();
				System.out.println(status);

				result = "success";

			} catch (MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
			/*ProductManager productManager = new ProductManagerImpl();
			int productId = productManager.addProduct(name, Double.parseDouble(price), categoryId,
					details);

			if (productId > 0) {
				result = "success";
			}*/
		}

		return result;
	}

	@Override
	public void validate() {
		CategoryManager categoryManager = new CategoryManagerImpl();

		try{
			this.setCategories(categoryManager.getCategories());
			// Validate name:

			if (getName() == null || getName().length() == 0) {
				addActionError(getText("error.product.name.required"));
			}

			// Validate price:

			if (String.valueOf(getPrice()).length() > 0) {
				if (!getPrice().matches("[0-9]+(.[0-9][0-9]?)?")
						|| Double.parseDouble(getPrice()) < 0.0) {
					addActionError(getText("error.product.price.regex"));
				}
			} else {
				addActionError(getText("error.product.price.required"));
			}
		} catch(Exception e){
			addActionError(getText("error.product.price.required"));
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
