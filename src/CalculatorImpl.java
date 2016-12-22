/**
 * Created by zabor on 22.12.2016.
 */
public class CalculatorImpl implements Calculator {
    @Cached(CacheOnDisk = true, cachedArgument = 1,
            maxOutput = 100, key = "file", addToZip = false)
    public int doHardWork(int a, int b) {
        Sleep(1000);
        return a + b;
    }

    private void Sleep(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            return;
        }
    }

}
