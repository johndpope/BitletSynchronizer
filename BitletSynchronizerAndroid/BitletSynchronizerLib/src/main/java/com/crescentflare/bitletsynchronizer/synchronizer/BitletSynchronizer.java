package com.crescentflare.bitletsynchronizer.synchronizer;

import com.crescentflare.bitletsynchronizer.bitlet.BitletHandler;
import com.crescentflare.bitletsynchronizer.bitlet.BitletResultObserver;
import com.crescentflare.bitletsynchronizer.cache.BitletCache;
import com.crescentflare.bitletsynchronizer.cache.BitletCacheEntry;
import com.crescentflare.bitletsynchronizer.cache.BitletMemoryCache;

/**
 * Bitlet Synchronizer synchronizer: synchronizes bitlets
 * A singleton to access and handle bitlets easily
 */
public class BitletSynchronizer
{
    // ---
    // Singleton instance
    // ---

    public static BitletSynchronizer instance = new BitletSynchronizer();


    // --
    // Members
    // --

    private BitletCache cache = new BitletMemoryCache();


    // ---
    // Loading
    // ---

    public <T> void load(BitletHandler<T> bitletHandler, BitletResultObserver.CompletionListener<T> completionListener)
    {
        load(bitletHandler, null, false, completionListener);
    }

    public <T> void load(BitletHandler<T> bitletHandler, String cacheKey, BitletResultObserver.CompletionListener<T> completionListener)
    {
        load(bitletHandler, cacheKey, false, completionListener);
    }

    @SuppressWarnings("unchecked")
    public <T> void load(BitletHandler<T> bitletHandler, String cacheKey, boolean forced, final BitletResultObserver.CompletionListener<T> completionListener)
    {
        if (cache != null && cacheKey != null)
        {
            cache.createEntryIfNeeded(cacheKey, bitletHandler);
            BitletCacheEntry entry = cache.getEntry(cacheKey);
            entry.load(forced, new BitletResultObserver<>(new BitletResultObserver.CompletionListener<Object>()
            {
                @Override
                public void onSuccess(Object bitlet)
                {
                    completionListener.onSuccess((T)bitlet);
                }

                @Override
                public void onError(Throwable exception)
                {
                    completionListener.onError(exception);
                }
            }));
        }
        else
        {
            bitletHandler.load(new BitletResultObserver<>(completionListener));
        }
    }

    public <T> void load(BitletHandler<T> bitletHandler, BitletResultObserver.SimpleCompletionListener<T> completionListener)
    {
        load(bitletHandler, null, false, completionListener);
    }

    public <T> void load(BitletHandler<T> bitletHandler, String cacheKey, BitletResultObserver.SimpleCompletionListener<T> completionListener)
    {
        load(bitletHandler, cacheKey, false, completionListener);
    }

    @SuppressWarnings("unchecked")
    public <T> void load(BitletHandler<T> bitletHandler, String cacheKey, boolean forced, final BitletResultObserver.SimpleCompletionListener<T> completionListener)
    {
        if (cache != null && cacheKey != null)
        {
            cache.createEntryIfNeeded(cacheKey, bitletHandler);
            BitletCacheEntry entry = cache.getEntry(cacheKey);
            entry.load(forced, new BitletResultObserver<>(new BitletResultObserver.SimpleCompletionListener<Object>()
            {
                @Override
                public void onFinish(Object bitlet, Throwable exception)
                {
                    completionListener.onFinish((T)bitlet, exception);
                }
            }));
        }
        else
        {
            bitletHandler.load(new BitletResultObserver<>(completionListener));
        }
    }


    // ---
    // Cache control
    // ---

    public Object getCachedBitlet(String cacheKey)
    {
        if (cache != null && cache.getEntry(cacheKey) != null)
        {
            return cache.getEntry(cacheKey).getBitletData();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getCachedBitlet(String cacheKey, Class<T> classType)
    {
        Object bitlet = getCachedBitlet(cacheKey);
        if (classType.isInstance(bitlet))
        {
            return (T)bitlet;
        }
        return null;
    }

    public BitletCacheEntry.State getCacheState(String cacheKey)
    {
        if (cache != null && cache.getEntry(cacheKey) != null && cache.getEntry(cacheKey).getState() != null)
        {
            return cache.getEntry(cacheKey).getState();
        }
        return BitletCacheEntry.State.Unavailable;
    }

    public void clearCache()
    {
        clearCache("*", true);
    }

    public void clearCache(String filter)
    {
        clearCache(filter, true);
    }

    public void clearCache(String filter, boolean recursive)
    {
        if (cache != null)
        {
            cache.clear(filter, recursive);
        }
    }

    public BitletCache getCache()
    {
        return cache;
    }

    public void setCache(BitletCache cache)
    {
        this.cache = cache;
    }
}
