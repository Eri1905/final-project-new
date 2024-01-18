public abstract class MenuItems {
    protected String name;
    protected double price;

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
