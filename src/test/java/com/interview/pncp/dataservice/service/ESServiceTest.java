package com.interview.pncp.dataservice.service;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael.zhang on 7/5/17.
 */
public class ESServiceTest {
    private ESService esService = new ESService();

    @Test
    public void index() throws Exception {
        Map<String, String> paramData = new HashMap<String, String>(){{
            this.put("firstName", "Michael");
            this.put("lastName", "Zhang");
            this.put("age", "18");
        }};
        Map<String, Object> response = this.esService.index("pncp", "finance", "1", paramData);
        System.out.println("Done");
    }

    @Test
    public void query() {
        Map<String, Object> response = this.esService.query("pncp", "finance", "TRUSTED INSURANCE", new String[]{"SPONSOR_DFE_NAME"});
        System.out.println("Done");
    }

}