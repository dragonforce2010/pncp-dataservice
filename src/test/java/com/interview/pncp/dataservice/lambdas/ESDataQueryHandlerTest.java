package com.interview.pncp.dataservice.lambdas;

import com.interview.pncp.dataservice.constants.CommonConstants;
import com.interview.pncp.dataservice.pojo.RestResult;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by michael.zhang on 7/6/17.
 */
public class ESDataQueryHandlerTest {
    private ESDataQueryHandler esDataLoadHandler;

    public ESDataQueryHandlerTest() {
        this.esDataLoadHandler = new ESDataQueryHandler();
    }
    @Test
    public void handle() throws Exception {
        RestResult restResult = this.esDataLoadHandler.handle(new HashMap<String, Object>(){{
            this.put(CommonConstants.ES_QUERY_PARAM_KEY_INDEX, "pncp");
            this.put(CommonConstants.ES_QUERY_PARAM_KEY_TYPE, "finance");
            this.put(CommonConstants.ES_QUERY_PARAM_KEY_KEYWORD, "TRUSTED");
            this.put(CommonConstants.ES_QUERY_PARAM_KEY_FIELDS, new String[]{"SPONSOR_DFE_NAME"});
        }}, null);
        System.out.print(restResult.getSuccessmessage());
    }

}