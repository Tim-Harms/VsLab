package hska.iwi.eShopMaster.controller;

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
	private static HttpURLConnection connection;

	public String execute() throws Exception {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			String urlstring = "http://localhost:8080/api/products?id=" + id;
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




			/*new ProductDAO().deleteById(id);
			{
				res = "success";
			}*/
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
