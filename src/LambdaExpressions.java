public class LambdaExpressions {
    static void main() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("This is an anonymous class method");
            }
        };

        Runnable task1 = () -> System.out.println("This is using Lambda Expressions");

//        () -> {} This is a Lambda Expression in Java which is same as the Arrow functions in Javascript (At least syntax)

        Thread t1 = new Thread(task1); //Works just fine
    }
}
