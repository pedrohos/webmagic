package us.codecraft.webmagic.utils;

import java.util.Map;

/**
 * @author code4crafter@gmail.com
 */
public class DoubleKeyMap<K1, K2, V> extends MultiKeyMapBase {
    private Map<K1, Map<K2, V>> map;

    public DoubleKeyMap() {
        init();
    }

    public DoubleKeyMap(Map<K1, Map<K2, V>> map) {
        this(map,DEFAULT_CLAZZ);
    }

    public DoubleKeyMap(Class<? extends Map> protoMapClass) {
        super(protoMapClass);
        init();
    }

    private void init() {
        if (map == null) {
            map = this.<K1, Map<K2, V>>newMap();
        }
    }

    /**
     * init map with protoMapClass
     *
     * @param map the origin map to contains the DoubleKeyMap
     * @param protoMapClass protoMapClass
     */
    @SuppressWarnings("rawtypes")
    public DoubleKeyMap(Map<K1, Map<K2, V>> map, Class<? extends Map> protoMapClass) {
        super(protoMapClass);
        this.map = map;
        init();
    }

    /**
     * @param key key
     * @return map
     */
    public Map<K2, V> get(K1 key) {
        return map.get(key);
    }

    /**
     * @param key1 key1
     * @param key2 key2
     * @return value
     */
    public V get(K1 key1, K2 key2) {
        if (get(key1) == null) {
            return null;
        }
        return get(key1).get(key2);
    }


    /**
     * @param key1 key1
     * @param submap submap
     * @return value
     */
    public V put(K1 key1, Map<K2, V> submap) {
        return put(key1, submap);
    }

    /**
     * @param key1 key1
     * @param key2 key2
     * @param value value
     * @return value
     */
    public V put(K1 key1, K2 key2, V value) {
        synchronized(this) {
            if (map.get(key1) == null) {
                //不加锁的话，多个线程有可能都会执行到这里
                map.put(key1, this.<K2, V>newMap());
            }
            return get(key1).put(key2, value);
        }
    }

    /**
     * @param key1 key1
     * @param key2 key2
     * @return value
     */
    public V remove(K1 key1, K2 key2) {
        synchronized(this) {
            if (get(key1) == null) {
                return null;
            }
            V remove = get(key1).remove(key2);
            if (get(key1).size() == 0) {
                remove(key1);
            }
            return remove;
        }
    }

    /**
     * @param key1 key1
     * @return map
     */
    public Map<K2, V> remove(K1 key1) {
        Map<K2, V> remove = map.remove(key1);
        return remove;
    }
}
