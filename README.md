# CacheExpiry

A Cache Expiry Service which expires elements in the cache based upon TTL.

If an object in the cache is accessed within the TTL, then the value stored is returned otherwise null.

Logic used:

To store the elements a ConcurrentHashmap is used. The class ExpiryCacheImpl implements ExpiryCache interface. 

Whenever an object is stored, the key,value,timeUnit and ttl values are supplied. Based upon these values, a cacheExpiryTime is computed. 

This is stored in a ExpiryTuple(both Key and the the expiryTime). Also, whenever an element is added to the cache, a ExpiryTuple value
is added to a PriorityBlockingQueue. The PriorityBlockingQueue acts as a heap where the hightest priority assigned is the lowest timeStamp for expirations.

The computeIfAbsent method in the Impl adds the elements to both map and Queue. 

There's another Consumer(backed by a thread pool) which is kicked in as soon as the Impl object is created. This consumer peeks the BlockingQueue
and as soon as it finds a Tuple for which the TimeStamp has expired, it goes ahead and removes the elements from the Queue and adds a NullTuple in 
the ConcurrentHashmap. The reason the Null Design pattern was added is that null values can't be used as either keys or values in a 
ConcurrentHashmap. 

Lastly, the get method checks the type of Object fetched from the Key. If it's an instance of NullTuple then null is returned otherwise the actual value.

The advantage of this Producer Consumer approach is that the adding elements to key and removal from the cache can happen in an async way.It can scale based 
upon the size of the thread pool. Also maintaining a heap helps as any element can be removed in O(1) time while reordering has an upper bound of
O(nlgn).

Please note that I have used Java 8 for this problem as Java 8 provides so many features(like lambas, improved Date prociessing utilities etc) which are very helpful.
