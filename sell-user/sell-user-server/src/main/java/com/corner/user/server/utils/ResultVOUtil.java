package com.corner.user.server.utils;


import com.corner.user.server.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }
    public static ResultVO error(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("失败");
        resultVO.setData(object);
        return resultVO;
    }
}
