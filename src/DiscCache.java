import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by zabor on 22.12.2016.
 */
public class DiscCache implements AutoCloseable {
    private final Scanner scanner;

    public DiscCache(String key) throws IOException {
        scanner =  new Scanner(new File(key));
    }

    public boolean contains(List<Object> thisQuery) {
        return true;
    }

    public Object getCache(List<Object> thisQuery) {
        return null;
    }

    public static void put(List<Object> thisQuery, Object anws) {
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
