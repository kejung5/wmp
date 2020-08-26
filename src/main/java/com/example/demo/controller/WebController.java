package com.example.demo.controller;

import com.example.demo.dto.ResultRequest;
import com.example.demo.dto.ResultResponse;
import com.example.demo.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @Autowired
    WebService webService;

    /**
     * 시작화면
     * @return
     */
    @RequestMapping(value = "/")
    public String main() {
        return "main";
    }

    /**
     * 결과조회
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/result")
    @ResponseBody
    public ResultResponse getResult(ResultRequest request) throws Exception {
        ResultResponse response = webService.getResult(request);
        return response;
    }

}