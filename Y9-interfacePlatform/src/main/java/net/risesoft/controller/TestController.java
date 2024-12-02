package net.risesoft.controller;


import net.risesoft.api.platform.org.OrgUnitApi;
import net.risesoft.model.user.UserInfo;
import net.risesoft.y9.Y9LoginUserHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SLlbbc
 */
@Controller
@RequestMapping("/api/rest/test")
public class TestController {

    private final OrgUnitApi orgUnitApi;

    public TestController(OrgUnitApi orgUnitApi) {
        this.orgUnitApi = orgUnitApi;
    }

    @ResponseBody
    @RequestMapping("/testMethod")
    public Map<String, Object> testMethod() {
        Map<String, Object> map = new HashMap<>();
        try {
            String tenantId = Y9LoginUserHolder.getTenantId();
            UserInfo userInfo = Y9LoginUserHolder.getUserInfo();
            map.put("租户ID", tenantId);
            map.put("登录用户信息", userInfo);
            map.put("登录用户部门信息", orgUnitApi.getOrgUnit(tenantId, userInfo.getParentId()).getData());
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
