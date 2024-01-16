public class Desserts extends Food{
    public Desserts(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return super.toString()+",desserts";
    }
}
