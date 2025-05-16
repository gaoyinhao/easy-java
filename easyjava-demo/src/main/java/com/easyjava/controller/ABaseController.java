package com.easyjava.controller;

import com.easyjava.entity.enums.ResponseCodeEnum;;
import com.easyjava.entity.vo.ResponseVO;;
/**
 * @author gao98
 */
public class ABaseController {
    protected static final String STATUS_SUCCESS="success";
    protected static final String STATUS_ERROR="error";
    protected <T>ResponseVO getSuccessResponseVo(T t){
        ResponseVO<T>responseVO=new ResponseVO<>();
        responseVO.setStatus(STATUS_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }
 }
