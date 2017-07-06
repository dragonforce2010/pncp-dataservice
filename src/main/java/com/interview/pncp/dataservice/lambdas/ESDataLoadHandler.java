package com.interview.pncp.dataservice.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.interview.pncp.dataservice.loader.CsvDataLoader;
import com.interview.pncp.dataservice.pojo.RestResult;

import java.util.Map;

public class ESDataLoadHandler extends BaseHandler{

    private CsvDataLoader csvDataLoader;

    public ESDataLoadHandler() {
        this.csvDataLoader = new CsvDataLoader();
    }

    @Override
    public RestResult handle(Map<String, Object> t, Context context) {
        return this.csvDataLoader.loadData2ES();
    }
}
