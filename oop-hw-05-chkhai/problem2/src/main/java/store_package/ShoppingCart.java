package store_package;

import java.util.HashMap;

public class ShoppingCart {

    private HashMap<String, Integer> mp;

    public  ShoppingCart() {
        this.mp = new HashMap<>();
    }

    public int getProductQuantity(String id){
        if(mp.containsKey(id)) return mp.get(id);
        return 0;
    }

    public void addProduct(String id){
        mp.putIfAbsent(id, 0);
        mp.put(id, mp.get(id)+1);
    }

    public void changeQuantity(String id, int quantity) {
        if (quantity < 0) return;
        mp.put(id, quantity);
    }

    public HashMap<String, Integer> getShoppingCart() {
        return mp;
    }
}
