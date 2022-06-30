package com.alone.sell.handler;

import com.alone.sell.common.exception.ResponseBankException;
import com.alone.sell.common.exception.SellAuthorizeException;
import com.alone.sell.common.exception.SellException;
import com.alone.sell.common.config.ProjectConfig;
import com.alone.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Chri61
 * @Date 2022/3/30 0030
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectConfig projectConfig;

    //拦截登录的异常
    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(projectConfig.getWechatOpenAuthoize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?redirectUrl=")
                .concat(projectConfig.getSell())
                .concat("/sell/seller/login")
        );
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVO.errorCodeMessage(e.getCode(), e.getMessage());
    }


    //返回值必须是void  才生效
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void testBankException(){
    }
}
