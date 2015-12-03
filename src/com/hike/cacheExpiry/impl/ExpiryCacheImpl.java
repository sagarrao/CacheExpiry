package com.hike.cacheExpiry.impl;

import com.hike.cacheExpiry.tuples.ExpiryCache;
import com.hike.cacheExpiry.tuples.ExpiryTuple;
import com.hike.cacheExpiry.tuples.NullTuple;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.concurrent.*;


/**
 * Created by RaoSa on 12/2/2015.
 */
public class ExpiryCacheImpl implements ExpiryCache {

    private final PriorityBlockingQueue<ExpiryTuple> blockingQueue = new PriorityBlockingQueue<>();

    private final ConcurrentHashMap cache;

    private final NullTuple nullTuple;

    public ExpiryCacheImpl(){
        cache = new ConcurrentHashMap();
        nullTuple = new NullTuple();
        Executors.newFixedThreadPool(4).execute(new CacheExpiryConsumer());
    }

    @Override
    public void put(Object key, Object value, int ttl, TemporalUnit timeUnit) {

        cache.computeIfPresent(key,(k,v)->{
            ExpiryTuple tuple = new ExpiryTuple(key);
            if(blockingQueue.contains(tuple)){
                blockingQueue.remove(tuple);
                tuple.setExpiryTimeStamp(Instant.now().plus(ttl, timeUnit));
                blockingQueue.add(tuple);
            }
            return value;
        });

        cache.computeIfAbsent(key, k -> {
            ExpiryTuple tuple = new ExpiryTuple(Instant.now().plus(ttl, timeUnit), key);
            blockingQueue.add(tuple);
            return value;
        });
    }

    @Override
    public Object get(Object key) {
        if(cache.get(key) instanceof NullTuple)
            return null;
        return cache.get(key);
    }

    private final class CacheExpiryConsumer implements Runnable {
        @Override
        public void run() {
            while (true){
                Optional<ExpiryTuple> tuple = Optional.ofNullable(blockingQueue.peek());
                if(tuple.isPresent() && Instant.now().isAfter(tuple.get().getExpiryTimeStamp())){
                    cache.put(tuple.get().getKey(),nullTuple);
                    blockingQueue.remove();
                }
            }
        }
    }


}
