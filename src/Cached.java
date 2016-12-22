/**
 * Created by zabor on 22.12.2016.
 */
public @interface Cached {

    boolean cacheOnDisk() default false;

    int cachedArgument() default -1;

    int maxOutput() default -1;

    String key() default "myFile.txt";

    boolean addToZip() default false;
}
