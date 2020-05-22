package com.dongtian.orderingsystem.utils;

import com.dongtian.orderingsystem.vo.ResultVo;

public class ResultVoUtils {

    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg( "success" );
        resultVo.setCode(0);
        resultVo.setData( object );
        return resultVo;
    }

    public static ResultVo success(){
        return success( null );
    }

    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
