import{B as ie,C as re,D as ce,F as ue,G as de,o as pe,p as me,w as ve,H as ge,E as S}from"./element-plus-53a783a7.js";import"./vue-20eed888.js";import{u as he,e as fe,i as _e,a as ye,b as we,c as be,d as Te,f as Ce,g as Ee,h as xe,j as Se,k as Pe,l as ze,m as De}from"./echarts-cfb64df0.js";import{u as $e}from"./vue-i18n-d6e8419a.js";import{u as Le}from"./index-03a63030.js";import{y as Ae}from"./request-6d47a533.js";import{d as Oe,r as n,G as Ie,w as Me,f as Re,E as Ge,ai as ke,o as A,c as Ne,e as a,P as s,a as t,U as l,Q as U,b as F,T as O,u as g}from"./@vue-e95c845e.js";import{_ as Be}from"./y9plugin-components-412bad23.js";import"./lodash-es-2e98bc53.js";import"./@vueuse-409d7ed5.js";import"./@element-plus-57b52633.js";import"./@popperjs-c75af06c.js";import"./@ctrl-f8748455.js";import"./dayjs-e13f206c.js";import"./axios-db0ed51e.js";import"./async-validator-20f92749.js";import"./memoize-one-297ddbcb.js";import"./normalize-wheel-es-ed76fb12.js";import"./@floating-ui-606f2ab9.js";import"./zrender-f903d501.js";import"./tslib-54e39b60.js";import"./@intlify-d4a3a758.js";import"./lodash-11751b8b.js";import"./pinia-22d008e7.js";import"./nprogress-e9cc33fd.js";import"./vue-router-e03c3082.js";/* empty css                    *//* empty css                      *//* empty css                  */import"./y9plugin-sso-6f4b3ca3.js";import"./y9plugin-watermark-e18d3181.js";import"./v-viewer-decd5d79.js";import"./viewerjs-c43574b2.js";import"./vxe-table-3ccf2bcd.js";import"./xe-utils-90392958.js";import"./dom-zindex-d72a3c32.js";he([_e,ye,we,be,Te,Ce,Ee,xe,Se,Pe,ze,De]);const h=Ae(),Ve=async()=>await h({url:"/api/rest/statistics/interfaceOverview",method:"GET",cType:!1}),qe=async()=>await h({url:"/api/rest/statistics/runningCount",method:"GET",cType:!1}),je=async v=>await h({url:"/api/rest/statistics/trend",method:"GET",cType:!1,params:v}),Ue=async()=>await h({url:"/api/rest/statistics/logOverview",method:"GET",cType:!1}),Fe=async v=>await h({url:"/api/rest/statistics/exception",method:"GET",cType:!1,params:v}),He=async v=>await h({url:"/api/rest/statistics/realTimeLog",method:"GET",cType:!1,params:v}),Je={style:{height:"100%","overflow-y":"auto","overflow-x":"hidden","scrollbar-width":"none"}},Ke={class:"card-header"},Qe={class:"overview-content"},We={class:"stat-items"},Xe={class:"stat-item"},Ye={class:"stat-item"},Ze={class:"stat-item"},et={class:"stat-item"},tt={class:"stat-item"},at={class:"card-header"},lt={class:"card-header"},ot={class:"trend-content"},nt={class:"trend-statistics"},st={class:"stat-item"},it={class:"stat-item"},rt={class:"stat-item"},ct={class:"stat-item"},ut={class:"card-header"},dt=Oe({__name:"home",setup(v){Le(),$e();const P={table:{realTimeLog:{pageSize:10,refreshInterval:1e4},exception:{pageSize:5}}};n(12);const I=n(!1);Ie("sizeObjInfo");const _=n("day"),i=n(null),d=n({registerCount:0,publishCount:0,stopCount:0,runningCount:0}),y=n({normalCount:0,abnormalCount:0}),z=n({chartData:{xAxis:[],datas:{}}}),p=n({allTotal:0,todayAllTotal:0,allErrorTotal:0,todayAllErrorTotal:0}),M=e=>n({currentPage:1,pageSize:e,total:0,loading:!1}),m=M(P.table.realTimeLog.pageSize),D=M(P.table.exception.pageSize),w=n({customStyle:"default",hideOnSinglePage:!1,background:!1,layout:"prev, pager, next,sizes",currentPage:1,pageSize:10,total:0,pageSizeOpts:[5,10,20,50,1e3]}),b=n({customStyle:"default",hideOnSinglePage:!1,background:!1,layout:"prev, pager, next,sizes",currentPage:1,pageSize:5,total:0,pageSizeOpts:[5,10,20,50,1e3]}),R=async(e,o,r,E)=>{r.value.loading=!0;try{const c={page:r.value.currentPage,limit:r.value.pageSize},f=await e(c);f.code==="0"?(o.value=[],o.value=f.data||[],r.value.total=f.count||0):(o.value=[],r.value.total=0,S.error(E))}finally{r.value.loading=!1}},T=()=>R(He,N,w,"获取实时日志播报数据失败"),$=()=>R(Fe,B,b,"获取接口异常情况数据失败");async function H(){try{const e=await Ve();e.code==="0"?(d.value.registerCount=e.data.registerCount||0,d.value.publishCount=e.data.publishCount||0,d.value.stopCount=e.data.stopCount||0,d.value.runningCount=e.data.runningCount||0):S.error("获取接口概况数据失败")}catch(e){console.error("获取接口概况数据失败:",e)}}async function J(){try{const e=await qe();e.code==="0"?(y.value.normalCount=e.data.normalCount||0,y.value.abnormalCount=e.data.abnormalCount||0):S.error("获取接口运行状态数据失败")}catch(e){console.error("获取接口运行状态数据失败:",e)}}const G=async()=>{try{const e=await je({type:_.value});e.code==="0"&&e.data&&(z.value.chartData={xAxis:e.data.xList,datas:e.data.datas},Q())}catch{}};async function K(){try{const e=await Ue();e.code==="0"?(p.value.allErrorTotal=e.data.allErrorTotal||0,p.value.todayAllTotal=e.data.todayAllTotal||0,p.value.todayAllErrorTotal=e.data.todayAllErrorTotal||0):S.error("获取日志概况数据失败")}catch(e){console.error("获取日志概况数据失败:",e)}}Me(()=>_.value,()=>{G()},{immediate:!0});const Q=()=>{if(!i.value)return;const e={tooltip:{trigger:"item",axisPointer:{type:"cross"}},toolbox:{feature:{saveAsImage:{}}},legend:{top:"0%",left:"center",selectedMode:!0},grid:{left:"3%",right:"4%",bottom:"3%",top:"12%",containLabel:!0},xAxis:{type:"category",boundaryGap:!0,data:z.value.chartData.xAxis||[],axisLabel:{interval:0,rotate:30,align:"center"}},yAxis:{type:"value",scale:!0},series:Object.entries(z.value.chartData.datas||{}).map(([o,r])=>({name:o,type:"line",data:r,smooth:!0}))};i.value.setOption(e,{replaceMerge:["series"]})};function W(){i.value&&(i.value.dispose(),i.value=null);const e=document.getElementById("interface-call-situation");e&&(i.value=fe.init(e))}window.addEventListener("resize",()=>{i.value&&i.value.resize({animation:{duration:300}})});async function X(){I.value=!0;try{await Promise.all([H(),J(),G(),K(),$(),T()])}finally{I.value=!1}}const Y=()=>{L()},Z=()=>{k()},ee=e=>{w.value.currentPage=e,T()},te=e=>{b.value.currentPage=e,$()};function ae(e){w.value.pageSize=e,T()}function le(e){b.value.pageSize=e,$()}const C=n(null),oe=()=>{let e=m.value.currentPage;const o=Math.ceil(m.value.total/m.value.pageSize);e>=o||e>=10?e=1:e++,m.value.currentPage=e,T()},k=()=>{L(),C.value=setInterval(()=>{oe()},P.table.realTimeLog.refreshInterval)},L=()=>{C.value&&(clearInterval(C.value),C.value=null)};Re(()=>{W(),X(),k(),window.addEventListener("resize",()=>{i.value&&i.value.resize()})}),Ge(()=>{i.value&&(i.value.dispose(),i.value=null),L()});const N=n([]),B=n([]);return(e,o)=>{const r=ie,E=ke("y9Pagination"),c=re,f=ce,V=ue,q=de,x=pe,ne=me,se=ve,j=ge;return A(),Ne("div",Je,[a(se,{gutter:20,style:{height:"calc(100% - 7px)"}},{default:s(()=>[a(q,{span:11},{default:s(()=>[a(r,{class:"box-card"},{default:s(()=>[t("div",Ke,[t("span",null,l(e.$t("接口概况")),1),o[1]||(o[1]=t("div",{style:{height:"32px"}},null,-1))]),t("div",Qe,[t("div",We,[t("div",Xe,[t("h2",null,l(d.value.registerCount),1),t("span",null,l(e.$t("注册数")),1)]),t("div",Ye,[t("h2",null,l(d.value.publishCount),1),t("span",null,l(e.$t("发布数")),1)]),t("div",Ze,[t("h2",null,l(d.value.stopCount),1),t("span",null,l(e.$t("停用数")),1)]),t("div",et,[t("h2",null,l(y.value.normalCount),1),t("span",null,l(e.$t("运行正常数")),1)]),t("div",tt,[t("h2",null,l(y.value.abnormalCount),1),t("span",null,l(e.$t("运行异常数")),1)])])])]),_:1}),a(r,{class:"box-card"},{default:s(()=>[t("div",at,[t("span",null,l(e.$t("实时日志播报")),1),a(E,{config:w.value,onCurrentChange:ee,onSizeChange:ae},null,8,["config"])]),U((A(),F(V,{data:N.value,class:"custom-table real-time-log",onMouseenter:Y,onMouseleave:Z},{default:s(()=>[a(c,{label:"序号",width:"60",align:"center"},{default:s(u=>[O(l((g(m).currentPage-1)*g(m).pageSize+u.$index+1),1)]),_:1}),a(c,{prop:"apiName",label:"接口名称","min-width":"140",align:"center","show-overflow-tooltip":""}),a(c,{prop:"responseMsg",label:"调用状态",width:"100",align:"center"},{default:s(({row:u})=>[a(f,{type:u.responseMsg==="成功"?"success":"danger"},{default:s(()=>[O(l(u.responseMsg),1)]),_:2},1032,["type"])]),_:1}),a(c,{prop:"requestStartTime",label:"调用时间","min-width":"180",align:"center","show-overflow-tooltip":""}),a(c,{prop:"applySystemName",label:"调用系统","min-width":"120",align:"center","show-overflow-tooltip":""})]),_:1},8,["data"])),[[j,g(m).loading]])]),_:1})]),_:1}),a(q,{span:13,style:{"margin-right":"-20px","padding-right":"13px"}},{default:s(()=>[a(r,{class:"box-card"},{default:s(()=>[t("div",lt,[t("span",null,l(e.$t("接口调用趋势图")),1),a(ne,{modelValue:_.value,"onUpdate:modelValue":o[0]||(o[0]=u=>_.value=u),style:{width:"120px"}},{default:s(()=>[a(x,{label:"当天",value:"day"}),a(x,{label:"近一周",value:"week"}),a(x,{label:"近一月",value:"month"}),a(x,{label:"近一年",value:"year"})]),_:1},8,["modelValue"])]),t("div",ot,[t("div",nt,[t("div",st,[t("h3",null,l(p.value.allTotal),1),t("span",null,l(e.$t("总调用量")),1)]),t("div",it,[t("h3",null,l(p.value.todayAllTotal),1),t("span",null,l(e.$t("今日调用量")),1)]),t("div",rt,[t("h3",null,l(p.value.allErrorTotal),1),t("span",null,l(e.$t("总异常量")),1)]),t("div",ct,[t("h3",null,l(p.value.todayAllErrorTotal),1),t("span",null,l(e.$t("今日异常量")),1)])]),o[2]||(o[2]=t("div",{id:"interface-call-situation",class:"chart-container"},null,-1))])]),_:1}),a(r,{class:"box-card"},{default:s(()=>[t("div",ut,[t("span",null,l(e.$t("接口异常情况")),1),a(E,{config:b.value,onCurrentChange:te,onSizeChange:le},null,8,["config"])]),U((A(),F(V,{data:B.value,class:"custom-table exception-log"},{default:s(()=>[a(c,{label:"序号",width:"60",align:"center"},{default:s(u=>[O(l((g(D).currentPage-1)*g(D).pageSize+u.$index+1),1)]),_:1}),a(c,{prop:"apiName",label:"接口名称","min-width":"140",align:"center","show-overflow-tooltip":""}),a(c,{prop:"applySystemName",label:"调用系统","min-width":"120",align:"center","show-overflow-tooltip":""}),a(c,{prop:"requestStartTime",label:"调用时间","min-width":"180",align:"center","show-overflow-tooltip":""}),a(c,{prop:"errorMessage",label:"异常描述","min-width":"160",align:"center","show-overflow-tooltip":""})]),_:1},8,["data"])),[[j,g(D).loading]])]),_:1})]),_:1})]),_:1})])}}});const Kt=Be(dt,[["__scopeId","data-v-edfe046f"],["__file","E:/workSpaceJDK11/y9-interface-platform/y9vue-interfacePlatform/src/views/home/home.vue"]]);export{Kt as default};