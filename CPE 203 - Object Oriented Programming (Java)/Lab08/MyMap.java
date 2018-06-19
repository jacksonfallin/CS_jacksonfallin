//import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// You may not use any library classes other than List and ArrayList,
// above, to implement your map.  If the string "java." or "javax."
// occurs in this file after this, your submission will be rejected.


// DO NOT MODIFY ANYTHING IN THIS FILE UNTIL THIS LINE!
// Fill in you class after this.  Your submission will be 
// rejected by checkgit and by the autograder
// if you modify anything before this comment block.

/**
 * This class implements a map from any key type K to any value
 * type V.  The K type must have a valid equals() and hashCode()
 * implementations.  MyMap<K, V> supports a public constructor that
 * takes a single int argument, giving the number of buckets for the
 * internal hashtable.
 * <p>
 * MyMap<K, V> supports a get() operation that takes
 * a key value, and delivers null if the key is not found, or a value
 * of type V (or a descendant of V) if the key is found.  The return type
 * of get() is V.
 * <p>
 * MyMap<K, V> further supports a put() operation that takes a key and a value,
 * in that order.  The value is associated with that key value, replacing any
 * other value that might have been stored at that key.
 * <p>
 * MyMap<K, V> also supports a method called getEntries() that takes no
 * arguments, and returns a List<MyMapEntry<K, V>> containing all of the
 * entries currently in the map.  MyMapEntry<K, V> has public final fields
 * called "key" and "value".
 * <p>
 * Finally, MyMap<K, V> supports a debugging method called getBuckets().
 * It delivers a List<List<MyMapEntry<K, V>>>, with one entry for each
 * bucket in the internal hash table.  Because this is just a debugging
 * method, it's OK to return internal data structures; MyMap<K, V> needen't
 * make a defensive copy.  (A defensive copy is when you make a copy of
 * a data structure and return the copy, so a caller can't modify your
 * internal data structures).
 * <p>
 * All of the above methods are public.
 */

public class MyMap<K, V> 
{
    int INVALID = -1;
    List<K> keys = new ArrayList<>();
    List<V> values = new ArrayList<>();
    int bucketSize = 0;


    public MyMap(int buckets) 
    {
        bucketSize = buckets;
    }

    public void put(K key, V value) 
    {
        int keyIndex = keys.indexOf(key);
        if (keyIndex == INVALID) 
        {
            keys.add(key);
            values.add(value);
        } else 
        {
            values.set(keyIndex, value);
        }
    }

    public V get(K key) 
    {
        int keyIndex = keys.indexOf(key);
        if (keyIndex != INVALID)
            return values.get(keyIndex);

        return null;
    }

    public List<MyMapEntry<K, V>> getEntries() 
    {
        ArrayList<MyMapEntry<K, V>> entries = new ArrayList<>();
        for (int index = 0; index < keys.size(); index++) 
        {
            entries.add(new MyMapEntry<>(keys.get(index), values.get(index)));
        }
        return entries;
    }

    public List<List<MyMapEntry<K, V>>> getBuckets() 
    {
        List<List<MyMapEntry<K, V>>> buckets = new ArrayList<>();
        Object[] objects = new Object[bucketSize];
        for (int index = 0; index < objects.length; index++) 
        {
            objects[index] = new ArrayList<>();
        }
        for (int index = 0; index < keys.size(); index++) 
        {
            int bucketIndex = keys.get(index).hashCode() % bucketSize;
            List<MyMapEntry<K, V>> entry = (List<MyMapEntry<K, V>>) objects[bucketIndex];
            entry.add(new MyMapEntry<>(keys.get(index), values.get(index)));

            objects[bucketIndex] = entry;


        }
        for (int index = 0; index < objects.length; index++) 
         {
            buckets.add((List<MyMapEntry<K, V>>) objects[index]);
        }
        return buckets;
    }


    static class MyMapEntry<K, V> 
    {
        public K key;
        public V value;

        public MyMapEntry(K key, V value) 
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() 
        {
            return "MapEntry(Point(" +
                    ((Point) key).x + "," + ((Point) key).y +
                    ")," + value.toString() +
                    ')';
        }
    }

}

