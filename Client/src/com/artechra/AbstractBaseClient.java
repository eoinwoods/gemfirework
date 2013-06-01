package com.artechra;

import com.gemstone.gemfire.cache.CacheListener;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;

import static com.gemstone.gemfire.cache.client.ClientRegionShortcut.CACHING_PROXY;
import static com.gemstone.gemfire.cache.client.ClientRegionShortcut.PROXY;

public abstract class AbstractBaseClient {
    ClientCache myCache ;
    ClientRegionFactory myRegionFactory ;
    Region myRegion ;
    String regionName ;

    public AbstractBaseClient(String clientName, String regionName) {
        this.myCache = createCacheConnection(clientName) ;
        this.regionName = regionName ;
        this.myRegionFactory = this.myCache.createClientRegionFactory(CACHING_PROXY) ;
    }

    public void close() {
        this.myCache.close();
    }

    private ClientCache createCacheConnection(String clientName) {
        ClientCache cache = new ClientCacheFactory()
                .set("log-file", clientName + ".log")
                .set("cache-xml-file", "")
                .set("mcast-port", "0")
                .setPoolSubscriptionEnabled(true)
                .addPoolLocator("localhost", 10987).create() ;
        return cache ;
    }

    protected Region<Integer, String> getRegion() {
        if (this.myRegion == null) {
            this.myRegion = this.myRegionFactory.create(this.regionName) ;
            this.myRegion.registerInterestRegex(".*"); ;
            System.out.println("Created region " + this.myRegion + " from factory " + this.myRegionFactory) ;
            if (this.myRegion == null) {
                throw new IllegalStateException("Region '" + this.regionName + "' is null") ;
            }
        }
        return this.myRegion ;
    }

    protected void addCacheListener(CacheListener listener) {
        this.myRegionFactory.addCacheListener(listener) ;
        System.out.println("Installed listener " + listener + " into factory " + this.myRegionFactory) ;
    }
}
