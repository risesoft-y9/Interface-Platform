
import Request from "@/api/lib/request";

const platformRequest = Request();


/**
 * 获取黑名单管理列表
 * @param params 
 * @returns 
 */
export const getPage = async (params) => {
    return await platformRequest({
        url: "/api/rest/blacklisting/getPage",
        method: 'GET',
        cType: false,
        params: params,
    });
};

/**
 * 保存信息
 * @param dataJson 
 * @returns 
 */
export const saveInfo = async (data) => {
    return await platformRequest({
        url: "/api/rest/blacklisting/saveInfo",
        method: 'POST',
        dataType: 'json',
        cType: false,
        data: data
    });
};

/**
 * 获取值
 * @param params 
 * @returns 
 */
export const getInfoById = async (params) => {
    return await platformRequest({
        url: "/api/rest/blacklisting/getInfoById",
        method: 'GET',
        cType: false,
        params: params,
    });
};
/**
 * 删除
 * @param params 
 * @returns 
 */
export const delInfoById = async (params) => {
        return await platformRequest({
            url: "/api/rest/blacklisting/delInfoById",
            method: 'GET',
            cType: false,
            params: params,
        });
};

/**
 * 修改启用停用状态
 * @param dataJson 
 * @returns 
 */
export const updateEnable = async (data) => {
    return await platformRequest({
        url: "/api/rest/blacklisting/updateEnable",
        method: 'POST',
        dataType: 'json',
        cType: false,
        data: data
    });
};