package com.interview.pncp.dataservice.loader;

import org.junit.Test;

/**
 * Created by michael.zhang on 7/5/17.
 */
public class CsvDataLoaderTest {
    private CsvDataLoader dataLoader = new CsvDataLoader();
    @Test
    public void loadData2ES() throws Exception {
        this.dataLoader.loadData2ES();
    }

}