package net.risesoft.controller;

import com.alibaba.fastjson.JSONArray;
import net.risesoft.log.OperationTypeEnum;
import net.risesoft.log.annotation.RiseLog;
import net.risesoft.service.UseInterfaceService;
import net.risesoft.y9public.entity.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    public ResponseEntity personPage(HttpServletRequest request, String userKey) {
        if (StringUtils.isBlank(userKey)) {
            userKey = request.getHeader("userKey");
        }
        ResponseEntity responseEntity = ResponseEntity.ok(useInterfaceService.forward(userKey, request));
        return responseEntity;
    }


    /**
     * 测试接口转发
     *
     * @param userKey
     * @return
     */
    @RiseLog(operationType = OperationTypeEnum.SEND, operationName = "接口转发")
    @RequestMapping("/test")
    @ResponseBody
    public Map test(HttpServletRequest request, String userKey) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println("requestHeader:" + name + "--" + value);
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            System.out.println("requestParams:" + name + "--" + value);
        }
        Map map1 = readBody(request);
        if(map1!=null){
            for (Object key : map1.keySet()) {
                System.out.println("cccc:" + key.toString() + "--" + map1.get(key).toString());
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("test", true);
        map.put("data", new Parameter());
        map.put("msg", "测试中文转发");
        return map;
    }

    //读取body数据
    private Map readBody(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String inputStr;
            String key = "";
            Boolean isVal = false;
            while ((inputStr = streamReader.readLine()) != null) {
                if (StringUtils.isNotBlank(inputStr) && isVal) {
                    isVal = false;
                    map.put(key, inputStr);
                }
                if (inputStr.indexOf("form-data; name=\"") != -1) {
                    key = inputStr.substring(inputStr.indexOf("form-data; name=\"") + 17, inputStr.length() - 1);
                    isVal = true;
                }
                response.append(inputStr);
            }
            map = JSONArray.parseObject(response.toString(), Map.class);
            return map;
        } catch (Exception e) {
//			e.printStackTrace();
            return map;
        }
    }
}
