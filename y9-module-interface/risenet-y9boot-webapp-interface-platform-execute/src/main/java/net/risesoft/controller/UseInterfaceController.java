package net.risesoft.controller;

import com.alibaba.fastjson.JSONArray;
import net.risesoft.log.OperationTypeEnum;
import net.risesoft.log.annotation.RiseLog;
import net.risesoft.service.UseInterfaceService;
import net.risesoft.y9public.entity.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部调用
 *
 * @author duanzhixin
 */
@Controller
@RequestMapping("/openInterface")
public class UseInterfaceController {

    @Autowired
    private UseInterfaceService useInterfaceService;

    /**
     * 接口转发
     *
     * @param userKey
     * @return
     */
    @RiseLog(operationType = OperationTypeEnum.SEND, operationName = "接口转发")
    @RequestMapping("/forward")
    public ResponseEntity personPage(HttpServletRequest request, String userKey,HttpServletResponse responseRt) {
        if (StringUtils.isBlank(userKey)) {
            userKey = request.getHeader("userKey");
        }
        return useInterfaceService.forward(userKey, request,responseRt);
    }



}