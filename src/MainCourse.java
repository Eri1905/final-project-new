public class MainCourse extends Food{
    public MainCourse(String name, double price) {
        super(name, price);
    }

    @Override
    public String toString() {
        return super.toString()+",main";
    }
}
