package com.hike.cacheExpiry.tuples;

import com.hike.cacheExpiry.AbstractTuple;

import java.time.Instant;

/**
 * Created by RaoSa on 12/3/2015.
 */
public class ExpiryTuple extends AbstractTuple implements Comparable {

    private Instant expiryTimeStamp;
    private Object key;

    public Instant getExpiryTimeStamp() {
        return expiryTimeStamp;
    }

    public void setExpiryTimeStamp(Instant expiryTimeStamp) {
        this.expiryTimeStamp = expiryTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpiryTuple that = (ExpiryTuple) o;
        return !(key != null ? !key.equals(that.key) : that.key != null);

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        return result;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public ExpiryTuple(Object key) {
        this.key = key;
    }

    public ExpiryTuple(Instant expiryTimeStamp, Object key) {
        this.expiryTimeStamp = expiryTimeStamp;
        this.key = key;
    }

    @Override
    public int compareTo(Object o) {
        ExpiryTuple expiryTuple = (ExpiryTuple) o;
        if(expiryTuple.getExpiryTimeStamp().compareTo(expiryTimeStamp) == 1)
            return -1;
        else if(expiryTuple.getExpiryTimeStamp().compareTo(expiryTimeStamp) == 1)
            return 1;
        return 0;
    }

    @Override
    public boolean isNull() {
        return true;
    }

}
