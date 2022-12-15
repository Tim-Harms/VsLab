package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.io.DataOutputStream;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class AddCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6704600867133294378L;
	
	private String newCatName = null;
	
	private List<Category> categories;
	
	User user;

	private static HttpURLConnection connection;

	public String execute(){
		/*String res = "input";

			CategoryManager categoryManager = new CategoryManagerImpl();
			// Add category
		try {
			categoryManager.addCategory(newCatName);
			// Go and get new Category list
			//this.setCategories(categoryManager.getCategories());
			res = "success";
			System.out.println("arrived in end of execute method");
		} catch (Exception e){
			addActionError(getText("error.addcategory.failed"));
		}

		return res;*/

        String res = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        user = (User) session.get("webshop_user");
        if(user != null && (user.getRole().getTyp().equals("admin"))) {

            try {
                CategoryManager categoryManager = new CategoryManagerImpl();
                // Add category
                categoryManager.addCategory(newCatName);

                // Go and get new Category list
                this.setCategories(categoryManager.getCategories());

                res = "success";
            } catch(Exception e){
                System.out.println("AddCategoryAction execute failed");
                e.printStackTrace();
            }
        }

        return res;
	}
	
	@Override
	public void validate(){
		if (getNewCatName().length() == 0) {
			addActionError(getText("error.catname.required"));
		}
		// Go and get new Category list
		CategoryManager categoryManager = new CategoryManagerImpl();
		try{
			this.setCategories(categoryManager.getCategories());
		} catch(Exception e){
			addActionError(getText("error.getcategories.failed"));
		}

	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public String getNewCatName() {
		return newCatName;
	}

	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
}
