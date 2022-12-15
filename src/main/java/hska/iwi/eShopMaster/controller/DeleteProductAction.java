package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteProductAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666796923937616729L;

	private int id;

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			/*new ProductDAO().deleteById(id);
			{
				res = "success";
			}*/


			ProductManager productManager = new ProductManagerImpl();

			try {
				productManager.deleteProductById(id);
				//categories = categoryManager.getCategories();
			}catch(Exception e){
				System.out.println("DeleteProductAction execute failed");
				e.printStackTrace();
			}
		}

		return res;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
