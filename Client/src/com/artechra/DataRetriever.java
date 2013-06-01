package com.artechra;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.query.*;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

public class DataRetriever extends AbstractBaseClient {

    public DataRetriever(String clientName, String regionName) {
        super(clientName, regionName) ;
    }

    public SelectResults<?> selectStarFromRegion(String regionName, int minKeyVal) {

        try {
            // Need to retrieve entrySet items as constituent parts (rather than '*') due to Gemfire bug 43673
            // which has been open since v6 in mid 2011:
            //    https://www.vmware.com/support/vfabric-gemfire/doc/BugNotesGemFire701.html
            SelectResults<?> ret = getRegion().query("SELECT e.key, e.value from /" + regionName + ".entrySet e where e.key >= " + minKeyVal) ;
            return ret ;
        } catch(Exception e) {
            throw new RuntimeException(e) ;
        }
    }

    public static void main(String[] args) {
        final String regionName = "numbersRegion" ;
        DataRetriever dr = new DataRetriever("DataRetriever", regionName) ;
        dr.addCacheListener(new NumberListener());
        System.out.println("Retrieving cache members from <" + regionName + "> with key value >= 2") ;
        SelectResults<?> r = dr.selectStarFromRegion("numbersRegion", 2) ;
        for (Object item : r) {
            System.out.println(r.getClass() + " " + item) ;
        }
        try {
            System.out.println("Listening for updates ... hit ENTER to exit") ;
            System.in.read() ;
        } catch(Exception e) {
            throw new RuntimeException(e) ;
        }
    }
}

class NumberListener extends CacheListenerAdapter {

    @Override
    public void afterCreate(EntryEvent event) {
        System.out.println("AfterCreate -- " + event) ;
    }

    @Override
    public void afterUpdate(EntryEvent event) {
        System.out.println("AfterUpdate -- " + event) ;
    }

    @Override
    public void close() {
        System.out.println("Region closed") ;
    }

}
