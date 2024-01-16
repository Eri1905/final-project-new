public abstract class MenuItems {
    String name;
    double price;

    public MenuItems(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return
                name + ' ' +
                "," + price;
    }
}
