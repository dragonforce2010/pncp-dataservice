package com.interview.pncp.dataservice.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.interview.pncp.dataservice.constants.CommonConstants;
import com.interview.pncp.dataservice.pojo.RestResult;
import com.interview.pncp.dataservice.service.ESService;
import com.interview.pncp.dataservice.service.IESService;

import java.util.ArrayList;
import java.util.Map;

public class ESDataQueryHandler extends BaseHandler{

    private IESService esService;

    public ESDataQueryHandler(){
        this.esService = new ESService();
    }

    @Override
    public RestResult handle(Map<String, Object> map, Context context) {
        String index = String.valueOf(map.get(CommonConstants.ES_QUERY_PARAM_KEY_INDEX));
        String type = String.valueOf(map.get(CommonConstants.ES_QUERY_PARAM_KEY_TYPE));
        String keyword = String.valueOf(map.get(CommonConstants.ES_QUERY_PARAM_KEY_KEYWORD));
        ArrayList<String> fieldArrayList = (ArrayList) map.get(CommonConstants.ES_QUERY_PARAM_KEY_FIELDS);

        try {
            String[] fieldArray = new String[fieldArrayList.size()];
            Map<String, Object> response = this.esService.query(index, type, keyword, fieldArrayList.toArray(fieldArray));
            return RestResult.newInstance(200, response.get(CommonConstants.ES_RESPONSE_KEY), "Success", null);
        } catch (Exception e) {
            return RestResult.newInstance(500, null, null, e.getMessage());
        }
    }
}
