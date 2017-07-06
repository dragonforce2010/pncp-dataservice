package com.interview.pncp.dataservice.lambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.interview.pncp.dataservice.pojo.RestResult;

import java.util.Map;

/**
 * Created by michael.zhang on 7/5/17.
 */
public abstract class BaseHandler implements RequestHandler<Map<String, Object>, RestResult> {

    @Override
    public RestResult handleRequest(Map<String, Object> t, Context context) {
        return this.handle(t, context);
    }

    protected abstract RestResult handle(Map<String, Object> t, Context context);
}
