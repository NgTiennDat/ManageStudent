public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        StudentUI ui = new StudentUI(manager);
        ui.displayMenu();
    }
}
