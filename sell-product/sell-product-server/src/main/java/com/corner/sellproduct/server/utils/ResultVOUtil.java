package com.corner.sellproduct.server.utils;

import com.corner.sellproduct.server.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO result = new ResultVO<>();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }
}
