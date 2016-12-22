import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zabor on 22.12.2016.
 */
public class CachedProxy<T> implements InvocationHandler {
    private Map<List<Object>, Object> cache;
    T currentObj;

    public T cache(T obj) {
        if (obj == null) {
            return null;
        }
        currentObj = obj;
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (int x = 0; x < method.getAnnotations().length; x++) {

            if (method.getAnnotations()[x].annotationType() == Cached.class)
                return processAnnotationCached(method, args,
                        (Cached) method.getAnnotations()[x]);
            if (method.getAnnotations()[x].annotationType() == RenewCache.class)
                dropCache(method, args);
        }
        return method.invoke(currentObj, args);
    }

    private void dropCache(Method method, Object[] args) {
        cache.clear();
    }

    private Object processAnnotationCached(Method method, Object[] args, Cached annotation) throws Throwable {
        List<Object> thisQuery = new ArrayList<>();
        thisQuery.add(method.getName());
        thisQuery.add(args);
        Object anws;
        if (annotation.cacheOnDisk()) {
            try (DiscCache diskCache = new DiscCache(annotation.key())) {
                if (diskCache.contains(thisQuery))
                    return diskCache.getCache(thisQuery);
                anws = method.invoke(currentObj, args);
                DiscCache.put(thisQuery, anws);
                return anws;
            } catch (IOException e) {
                System.out.println("Couldnt create output file!");
            }
        }
        if (cache.containsKey(thisQuery))
            return cache.get(thisQuery);
        anws = method.invoke(currentObj, args);
        cache.put(thisQuery, anws);
        return anws;
    }
}
