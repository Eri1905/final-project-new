public class Starters extends Food{
    public Starters(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return super.toString()+",starters";
    }
}
