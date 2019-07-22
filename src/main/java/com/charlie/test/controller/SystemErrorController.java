package com.charlie.test.controller;

import com.charlie.test.exception.CustomizeErrorCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一处理错误
 * 例如客户端访问错误的路径导致404
 */
@Controller
@RequestMapping(value = "/error")
public class SystemErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    ModelAndView errorHtml(HttpServletRequest request, Model model){
        HttpStatus status =  getStatus(request);
        if(status.is4xxClientError()){
            model.addAttribute("message", CustomizeErrorCode.CLIENT_ERROR.getMessage());
        }else if(status.is5xxServerError()){
            model.addAttribute("message", CustomizeErrorCode.SERVER_ERROR.getMessage());
        }

        return  new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
