package com.hike.cacheExpiry.tuples;

import com.hike.cacheExpiry.AbstractTuple;

/**
 * Created by RaoSa on 12/3/2015.
 */
public class NullTuple extends AbstractTuple {

    @Override
    public boolean isNull() {
        return true;
    }
}
