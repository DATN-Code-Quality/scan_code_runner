package org.hcmus.datn.temporal.model.response;

import org.hcmus.datn.common.Constant;

import java.util.LinkedHashMap;

public class Response {
    private int error;
    private Object data;

    public Response(int error, Object data) {
        this.error = error;
        this.data = data;
    }

    public static Response toResponse(LinkedHashMap<String, Object> map){
        return new Response((Integer) map.get(Constant.DATABASE_SERVICE_RESPONSE_ERROR), map.get(Constant.DATABASE_SERVICE_RESPONSE_DATA) );
    }

    public int getError() {
        return error;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "error=" + error +
                ", data=" + data +
                '}';
    }
}
