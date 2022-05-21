package be.kuleuven.pt_mytodolist.model;

public class Category {
    private String CatId;
    private String catName;
    private int catIcon;

    public Category(String CatId, String catName, int catIcon) {
        this.CatId = CatId;
        this.catName = catName;
        this.catIcon = catIcon;
    }

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        this.CatId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(int catIcon) {
        this.catIcon = catIcon;
    }
}
