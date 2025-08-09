package store_package;

public class Product {

    private String name;
    private String id;
    private String img;
    private double price;

    public Product(String id, String name, String img, double price) {
        this.name = name;
        this.id = id;
        this.img = img;
        this.price = price;
    }

    public String getName() { return name;    }

    public String getId() { return id;    }

    public String getImg() { return img;    }

    public double getPrice() { return price;    }
}
