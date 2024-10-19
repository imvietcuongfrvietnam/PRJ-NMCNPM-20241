public class HelloWorld {
    public static int a_out;
    public static int array_out[];
    public static A A_out;
    public static void main(String[] args) {
        System.out.println(a_out); // a_out=0;
        int a_in = 5;
        System.out.println(a_in); // loi bien dich
        System.out.println(array_out); // array_out=null;
        int array_in[] = new int[5];
        System.out.println(array_in); // loi bien dich
        HelloWorld.A_out = new A();
        System.out.println(HelloWorld.A_out); // A_out=null;
        A A_in = new A();
        System.out.println(A_in);
        //System.out.println(A_in); // loi bien dich
    }
}
 
class A {
}