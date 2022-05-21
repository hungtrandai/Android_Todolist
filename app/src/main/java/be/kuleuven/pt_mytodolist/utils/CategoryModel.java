package be.kuleuven.pt_mytodolist.utils;

import java.util.ArrayList;
import java.util.Iterator;

import be.kuleuven.pt_mytodolist.model.Category;

public class CategoryModel {

    private static ArrayList<Category> catItems;

    public static ArrayList<Category> getCatItems(){
        if(catItems == null){
             catItems = new ArrayList<Category>();
             Category category = new Category("CAT01","STUDY",1);
             catItems.add(category);
             catItems.add(new Category("CAT02","HOME",1));
             catItems.add(new Category("CAT03","WORK",1));
             catItems.add(new Category("CAT04","RELAX",1));
             catItems.add(new Category("CAT05","SPORT",1));


        }
        return catItems;
    }


    public static String getCategoryById(String id){
        if(catItems == null) getCatItems();
        Iterator<Category> iteratorCategory = catItems.iterator();
        while (iteratorCategory.hasNext()){
            Category currentCategory = iteratorCategory.next();
            if(currentCategory.getCatId().equalsIgnoreCase(id)) return currentCategory.getCatName();
        }
        return null;

    }

}
