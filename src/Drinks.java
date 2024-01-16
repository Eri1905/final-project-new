public class Drinks extends MenuItems{
    public Drinks(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return super.toString()+",drinks";
    }
}
