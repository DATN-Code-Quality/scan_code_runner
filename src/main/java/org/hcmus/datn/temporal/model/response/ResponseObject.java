package org.hcmus.datn.temporal.model.response;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hcmus.datn.common.Constant;
import org.hcmus.datn.common.ErrorCode;

import java.util.LinkedHashMap;


public class ResponseObject {
    private int error;
    private Object data;

    public ResponseObject(int error, Object data) {
        this.error = error;
        this.data = data;
    }

    public static ResponseObject toResponse(LinkedHashMap<String, Object> map){
        return new ResponseObject((Integer) map.get(Constant.DATABASE_SERVICE_RESPONSE_ERROR), map.get(Constant.DATABASE_SERVICE_RESPONSE_DATA) );
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

    public static final ResponseObject SUCCESS = new ResponseObject(ErrorCode.SUCCESS.getValue(), "Successfully!");
    public static final ResponseObject FAILED = new ResponseObject(ErrorCode.FAILED.getValue(), "Failed");
    public static final ResponseObject INVALID_PARAMS = new ResponseObject(ErrorCode.INVALID_PARAMS.getValue(), "Params not valid");

}
