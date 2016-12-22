/**
 * Created by zabor on 22.12.2016.
 */
public class Main {
        public static void main(String[] args) {
            CachedProxy<Calculator> cachedCalc = new CachedProxy<>();
            Calculator calc = new CalculatorImpl();
            calc = cachedCalc.cache(calc);
            System.out.println(calc.doHardWork(10, 20));
            System.out.println(calc.doHardWork(10, 30));
            System.out.println(calc.doHardWork(10, 20));
        }
}
