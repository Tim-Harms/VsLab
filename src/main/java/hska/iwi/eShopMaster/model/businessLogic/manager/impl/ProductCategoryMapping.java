package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

public class ProductCategoryMapping {
    private int id;
    private int categoryId;

    public ProductCategoryMapping() {
    }

    public ProductCategoryMapping(int id, int categoryId) {
        this.id = id;
        this.categoryId = categoryId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
