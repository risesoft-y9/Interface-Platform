import{_ as m,c as h,y as p,i as u,$ as w}from"./index-03a63030.js";import{a as f}from"./axios-db0ed51e.js";import{a as d,E as g}from"./element-plus-53a783a7.js";const y={siteTitle:"Y9-ADMIN-VUE",topNavEnable:!0,headFixed:!0,tabNavEnable:!0,homeRouteItem:{icon:"control",title:"index-layout.menu.home.workplace",path:"/home/workplace",component:()=>m(()=>import("./index-03a63030.js").then(l=>l.A),["assets/index-03a63030.js","assets/lodash-11751b8b.js","assets/axios-db0ed51e.js","assets/pinia-22d008e7.js","assets/vue-20eed888.js","assets/@vue-e95c845e.js","assets/@vueuse-409d7ed5.js","assets/nprogress-e9cc33fd.js","assets/vue-router-e03c3082.js","assets/y9plugin-sso-6f4b3ca3.js","assets/vue-i18n-d6e8419a.js","assets/@intlify-d4a3a758.js","assets/y9plugin-watermark-e18d3181.js","assets/y9plugin-components-412bad23.js","assets/element-plus-53a783a7.js","assets/lodash-es-2e98bc53.js","assets/@element-plus-57b52633.js","assets/@popperjs-c75af06c.js","assets/@ctrl-f8748455.js","assets/dayjs-e13f206c.js","assets/async-validator-20f92749.js","assets/memoize-one-297ddbcb.js","assets/normalize-wheel-es-ed76fb12.js","assets/@floating-ui-606f2ab9.js","assets/element-plus-a8b870f7.css","assets/v-viewer-decd5d79.js","assets/viewerjs-c43574b2.js","assets/viewerjs-407616cb.css","assets/vxe-table-3ccf2bcd.js","assets/xe-utils-90392958.js","assets/dom-zindex-d72a3c32.js","assets/vxe-table-4981fa8d.css","assets/y9plugin-components-93dd4548.css","assets/remixicon-bbff3e99.css","assets/index-5955d5e2.css","assets/animate-2d4c25b0.css","assets/normalize-9d9ae4af.css"])},siteTokenKey:"y9AT",ajaxHeadersTokenKey:"x-token",ajaxResponseNoVerifyUrl:["/user/login","/user/info"],iconfontUrl:[]},t=y,{t:r}=h.global;function E(l=""){let i=new Set;const n=f.create({baseURL:"http://localhost:7055/interfacePlatform/",withCredentials:!0,timeout:0});return n.interceptors.request.use(e=>{e.headers["Content-Type"]="application/x-www-form-urlencoded;charset=UTF-8",e.cType&&(e.headers.userLoginName=e.data.userLoginName),e.JSON&&(e.headers["Content-Type"]="application/json");const s=p.getObjectItem(t.siteTokenKey,"access_token");return s&&(e.headers.Authorization="Bearer "+s),e},e=>Promise.reject(e)),n.interceptors.response.use(e=>{setTimeout(()=>{i.delete(e.config.url)},600);let s;e.data?s=e.data:s=e;const{code:o}=s;if(o!==0){const c=e.config.url.split("?")[0].replace(e.config.baseURL,""),a=t.ajaxResponseNoVerifyUrl.includes(c);switch(o){case 40101:case 40101:case 40102:case 40102:case 401:a||d({title:r("提示"),showClose:!1,closeOnClickModal:!1,closeOnPressEscape:!1,message:r("当前用户登入信息已失效，请重新登入再操作"),beforeClose:(_,b,k)=>{u(t.serverLoginUrl)?window.location.href=t.serverLoginUrl:window.location.reload()}});break;case 40300:window.location.href="/interface//401";break;case 40400:window.location.href="/interface//404";break;case 5e4:return s;default:a||console.error(s.msg);break}return o===101e3?Promise.resolve(s):s}else return s},e=>(f.isCancel(e)?console.warn(e):(g({message:e.message,type:"error",duration:5*1e3}),i.delete(e.config.url),e.message.includes("401")&&d({title:r("提示"),showClose:!1,closeOnClickModal:!1,closeOnPressEscape:!1,message:r("当前用户登入信息已失效，请重新登入再操作"),beforeClose:(s,o,c)=>{if(u(t.serverLoginUrl))window.location.href=t.serverLoginUrl;else{const a={to:{path:window.location.pathname},logoutUrl:"http://localhost:7055/sso/logout?service=http://localhost:7070/interface/",__y9delete__:()=>{console.log("删除前执行的函数")}};w.ssoLogout(a)}}})),Promise.reject(e))),n}export{t as s,E as y};