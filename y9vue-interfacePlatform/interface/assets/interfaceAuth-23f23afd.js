import{E as L,a as me,b as ce,l as Ve,K as ze}from"./element-plus-53a783a7.js";import"./vue-20eed888.js";import{u as ve,b as ge}from"./index-03a63030.js";import{u as ye}from"./vue-i18n-d6e8419a.js";import{s as be,d as he,g as ne,a as ue,c as Se,b as Ne}from"./authInterface-473123dc.js";import{d as ke,G as Ce,r as o,k as t,ah as D,ai as K,o as Q,c as De,e as T,P as y,u as d,L as $,T as O,U,b as pe,S as fe,i as we,F as _e}from"./@vue-e95c845e.js";import{_ as Te}from"./y9plugin-components-412bad23.js";const xe=ke({__name:"interfaceDictVal",props:{isView:{type:Boolean,default:()=>!1}},setup(ee,{expose:ae}){const w=Ce("sizeObjInfo"),j=ve(),{t:e}=ye();o();const r=o({}),q=o(),N=o(),W=o(),B=o(),x=o(),V=o(),R=o();o();const A=ee;let s=o({rowKey:"id",load:(l,g,u)=>{r.value.parameterId=x.value,r.value.isPrimary=B.value,r.value.pid=l.id,ne(r.value).then(_=>{u(_.data)})},lazy:!0,headerBackground:!0,pageConfig:{background:!1,currentPage:1,pageSize:5,total:0},columns:[{type:"index",title:t(()=>e("序号")),width:80,fixed:"left"},{title:t(()=>e("显示值")),key:"showVal"},{title:t(()=>e("字段值")),key:"fieldVal"},{title:t(()=>e("参数名称")),key:"parameterName"},{title:t(()=>e("参数key")),key:"fieldName"},{title:t(()=>e("操作")),width:150,fixed:"right",render:l=>A.isView?D("div",[D("span",{onClick:()=>{X(l.id)}},e("详情")),D("span",{class:"leftMargin",onClick:()=>{oe(l.id)}},e("编辑")),D("span",{class:"leftMargin",onClick:()=>{se(l.id)}},e("删除"))]):D("div",[D("span",{onClick:()=>{X(l.id)}},e("详情"))])}],tableData:[]});const E=o({filtersValueCallBack:l=>{r.value=l},itemList:[{type:"input",value:"",key:"fieldVal",label:t(()=>e("字段值")),labelWidth:"42px",span:j.device==="mobile"?24:6},{type:"input",value:"",key:"showVal",label:t(()=>e("显示值")),labelWidth:"42px",span:j.device==="mobile"?24:6},{type:"slot",slotName:"slotSearch",span:6},{type:"slot",slotName:"slotBtns",span:j.device==="mobile"?24:6,justify:"flex-end"}],showBorder:!0,borderRadio:"4px"});async function b(){s.value.loading=!0,r.value.parameterId=x.value,r.value.isPrimary=B.value,V.value=="是"&&r.value.pid==null?(delete s.value.pageConfig,s.value.pageConfig=!1,r.value.page=1,r.value.limit=5,r.value.pid="0"):V.value=="否"&&(r.value.page=s.value.pageConfig.currentPage,r.value.limit=s.value.pageConfig.pageSize,delete r.value.pid);let l=await ne(r.value);s.value.tableData=l.data||[],V.value=="否"&&(s.value.pageConfig.total=l.count||0),s.value.loading=!1}function h(l){s.value.tableData=[],s.value.pageConfig.pageSize=l,b()}function z(l){s.value.tableData=[],s.value.pageConfig.currentPage=l,b()}function te(){s.value.tableData=[],s.value.pageConfig&&(s.value.pageConfig.currentPage=1,s.value.pageConfig.pageSize=5),b()}function M(){q.value.elTableFilterRef.onReset(),s.value.tableData=[],s.value.pageConfig&&(s.value.pageConfig.currentPage=1,s.value.pageConfig.pageSize=5),r.value.fieldVal="",r.value.showVal="",r.value.pid="0",b()}function le(l,g,u,_,S,f){S=="否"&&(delete s.value.pageConfig,s.value.pageConfig={background:!1,currentPage:1,pageSize:5,total:0},s.value.pageConfig.currentPage=1,s.value.pageConfig.pageSize=5,s.value.tableData=[]),x.value=l,B.value=g,W.value=u,N.value=_,V.value=S,R.value=f,b()}const J=o(),ie=(l,g,u)=>{let _=ge("number",g,!0);_.valid?u():u(new Error(_.msg))};let m=o({show:!1,title:t(()=>e("新增权限值")),showFooter:!0,onOkLoading:!0,onOk:l=>new Promise(async(g,u)=>{var S;await((S=J.value)==null?void 0:S.elFormRef).validate(async f=>{if(f){let k=J.value.model,C=new FormData;for(let i in k)k[i]!=null&&i!="createTime"&&i!="updateTime"&&C.append(i,k[i]);k.pid!=null&&C.append("pid",k.pid),(await be(C)).code==0&&(L({message:"数据保存成功",type:"success"}),g(),b()),u()}else u()})})}),n=o({model:{parameterName:W.value,fieldName:N.value,parameterId:x.value,isPrimary:B.value,isTree:V.value,parameterType:R.value},rules:{parameterName:[{required:!0,message:t(()=>e("名称不能为空")),trigger:"blur"}],fieldName:[{required:!0,message:t(()=>e("参数key不能为空")),trigger:"blur"}],isTree:[{required:!0,message:t(()=>e("是否树形不能为空")),trigger:"blur"}],sort:[{required:!0,message:t(()=>e("排序不能为空")),trigger:"blur"},{validator:ie,trigger:"blur"}],fieldVal:[{required:!0,message:t(()=>e("字段值不能为空")),trigger:"blur"}],showVal:[{required:!0,message:t(()=>e("显示值不能为空")),trigger:"blur"}],pid:[{required:!0,message:t(()=>e("父级不能为空")),trigger:"blur"}]},itemList:[{type:"input",label:t(()=>e("显示值")),prop:"showVal"},{type:"input",label:t(()=>e("字段值")),prop:"fieldVal"},{type:"input",label:t(()=>e("排序")),prop:"sort",props:{type:"number",max:999,min:0}},{type:"input",label:t(()=>e("参数名称")),prop:"parameterName",props:{disabled:!0}},{type:"input",label:t(()=>e("参数key")),prop:"fieldName",props:{disabled:!0}}],descriptionsFormConfig:{labelWidth:"200px",labelAlign:"center"}});function c(l,g){if(V.value=="是"){let u={type:"select",label:t(()=>e("选择父级")),prop:"pid",props:{options:[{label:t(()=>e("本级")),value:"0"}]}},_={parameterId:x.value,limit:1e5,page:1};ne(_).then(S=>{for(let f of S.data)if(f.id!=n.value.model.id){let k={label:t(()=>e(f.showVal)),value:f.id};u.props.options.push(k)}if(n.value.itemList[0].prop=="pid"?n.value.itemList[0].props.options=u.props.options:n.value.itemList.unshift(u),l=="")for(let f of n.value.itemList)f.prop!="parameterName"&&f.prop!="fieldName"&&(f.props==null?f.props={disabled:!1}:f.props.disabled=!1);else ue({id:l}).then(k=>{if(n.value.model=k.data,g)for(let C of n.value.itemList)C.props==null?C.props={disabled:!0}:C.props.disabled=!0;else for(let C of n.value.itemList)C.prop!="parameterName"&&C.prop!="fieldName"&&(C.props==null?C.props={disabled:!1}:C.props.disabled=!1)})})}else n.value.itemList[0].prop=="pid"&&n.value.itemList.splice(0,1)}function I(){m.value.show=!0,n.value.model={},n.value.model.parameterName=W.value,n.value.model.fieldName=N.value,n.value.model.parameterId=x.value,n.value.model.isPrimary=B.value,n.value.model.isTree=V.value,n.value.model.parameterType=R.value;for(let l of n.value.itemList)l.prop!="parameterName"&&l.prop!="fieldName"&&(l.props==null?l.props={disabled:!1}:l.props.disabled=!1);m.value.okText="保存",m.value.title=t(()=>e("新增权限信息")),c("",!1)}async function oe(l){n.value.model.id==l,c(l,!1),m.value.okText="保存",m.value.title=t(()=>e("编辑权限信息")),m.value.show=!0}async function X(l){n.value.model.id==l,c(l,!0),m.value.okText=!1,m.value.title=t(()=>e("查看权限信息")),m.value.show=!0}async function se(l){me.confirm("是否确认删除这条数据","删除数据确认",{confirmButtonText:"确定",cancelButtonText:"取消",type:"info",draggable:!0}).then(()=>{he({id:l,type:"interface"}).then(u=>{u.status=="success"?L({type:"info",message:"删除成功"}):L({type:"warning",message:"删除失败"+u.msg})})}).catch(()=>{L({type:"info",message:"删除失败"})})}return ae({initTableData:le,resetTable:()=>{r.value.pid=void 0,s.value.tableData=[]}}),(l,g)=>{const u=ce,_=K("y9Table"),S=K("y9Form"),f=K("y9Dialog");return Q(),De(_e,null,[T(_,{config:d(s),filterConfig:E.value,onOnCurrPageChange:z,ref_key:"filterRef",ref:q,onOnPageSizeChange:h},{slotSearch:y(()=>[T(u,{size:d(w).buttonSize,style:$({fontSize:d(w).baseFontSize}),class:"global-btn-main",type:"primary",onClick:te},{default:y(()=>[O(U(l.$t("查询")),1)]),_:1},8,["size","style"]),T(u,{size:d(w).buttonSize,style:$({fontSize:d(w).baseFontSize}),class:"el-button el-button--default global-btn-third",onClick:M},{default:y(()=>[O(U(l.$t("重置")),1)]),_:1},8,["size","style"])]),slotBtns:y(()=>[A.isView?(Q(),pe(u,{key:0,size:d(w).buttonSize,style:$({fontSize:d(w).baseFontSize}),class:"global-btn-main",type:"primary",onClick:I},{default:y(()=>[O(U(l.$t("新增")),1)]),_:1},8,["size","style"])):fe("",!0)]),_:1},8,["config","filterConfig"]),T(f,{config:d(m),"onUpdate:config":g[0]||(g[0]=k=>we(m)?m.value=k:m=k)},{default:y(()=>[T(S,{ref_key:"ruleFormRef",ref:J,config:d(n)},null,8,["config"])]),_:1},8,["config"])],64)}}});const Fe=Te(xe,[["__file","E:/workSpaceJDK11/y9-interface-platform/y9vue-interfacePlatform/src/views/auth/interfaceDictVal.vue"]]),Be=ke({__name:"interfaceAuth",props:{isView:{type:Boolean,default:()=>!1},isDisabled:{type:Boolean,default:()=>!1},selectData:{type:String},isShow:{type:Boolean,default:()=>!0}},setup(ee,{expose:ae}){const w=Ce("sizeObjInfo"),j=ve(),{t:e}=ye(),r=o({}),q=o(),N=o(!1),W=o("查看权限值");o();const B=o(),x=o(),V=o(),R=o(),A=o(),H=o(),s=o(),E=o(!1),b=o(),h=ee;let z=o({headerBackground:!0,pageConfig:!1,height:200,columns:[{type:"selection",width:60,selectable:()=>!!(h.isView&&h.isDisabled)},{type:"index",title:t(()=>e("序号")),width:80,fixed:"left"},{title:t(()=>e("参数名称")),key:"parameterName"},{title:t(()=>e("参数key")),key:"fieldName"},{title:t(()=>e("参数类型")),key:"parameterType"},{title:t(()=>e("排序")),key:"sort"},{title:t(()=>e("操作")),width:300,fixed:"right",render:a=>a.parameterType=="私有"&&h.isView?D("div",[D("span",{onClick:()=>{g(a.id)}},e("详情")),D("span",{class:"leftMargin",onClick:()=>{l(a.id)}},e("编辑")),D("span",{class:"leftMargin",onClick:()=>{u(a.id)}},e("删除")),D("span",{class:"leftMargin",onClick:()=>{se(a.parameterId,"N",a.parameterName,a.fieldName,a.isTree)}},e("配置权限值"))]):D("div",[D("span",{onClick:()=>{g(a.id)}},e("详情")),D("span",{class:"leftMargin",onClick:()=>{X(a.parameterId,"N",a.parameterName,a.fieldName,a.isTree)}},e("查看权限值"))])}],tableData:[]});const te=o({filtersValueCallBack:a=>{r.value=a},itemList:[{type:"slot",slotName:"slotBtns",span:(j.device==="mobile",24),justify:"flex-end"}],showBorder:!0});async function M(){z.value.loading=!0,r.value.page=1,r.value.limit=999999;let a;if(console.log(h.isView),h.isView)a=await Ne(r.value);else{let i={checkedIds:h.selectData==null||h.selectData==""||h.selectData==null?"-1":h.selectData,page:1,limit:999999};a=await Se(i)}z.value.tableData=a.data||[],z.value.loading=!1;for(let i of z.value.tableData)h.selectData.indexOf(i.id)!=-1&&(console.log(i),q.value.elTableRef.toggleRowSelection(i.id,!0))}function le(){z.value.tableData=[],M()}function J(){q.value.elTableFilterRef.onReset(),z.value.tableData=[],r.value.fieldName="",r.value.parameterName="",M()}function ie(){z.value.tableData=[],M()}const m=o(),n=(a,i,p)=>{let v=ge("number",i,!0);v.valid?p():p(new Error(v.msg))};let c=o({show:!1,title:t(()=>e("新增权限配置")),showFooter:!0,onOkLoading:!0,onOk:a=>new Promise(async(i,p)=>{var P;await((P=m.value)==null?void 0:P.elFormRef).validate(async re=>{if(re){let Y=m.value.model,Z=new FormData;for(let G in Y)Y[G]!=null&&G!="createTime"&&G!="updateTime"&&Z.append(G,Y[G]);(await be(Z)).code==0&&(L({message:"数据保存成功",type:"success"}),i(),M()),p()}else p()})})}),I=o({model:{isPrimary:"Y",parameterType:"私有"},rules:{parameterName:[{required:!0,message:t(()=>e("名称不能为空")),trigger:"blur"}],fieldName:[{required:!0,message:t(()=>e("参数key不能为空")),trigger:"blur"}],isTree:[{required:!0,message:t(()=>e("是否树形不能为空")),trigger:"blur"}],sort:[{required:!0,message:t(()=>e("排序不能为空")),trigger:"blur"},{validator:n,trigger:"blur"}]},itemList:[{type:"input",label:t(()=>e("参数名称")),prop:"parameterName"},{type:"input",label:t(()=>e("参数key")),prop:"fieldName"},{type:"select",label:t(()=>e("是否树形")),prop:"isTree",props:{options:[{label:t(()=>e("是")),value:"是"},{label:t(()=>e("否")),value:"否"}]}},{type:"input",label:t(()=>e("排序")),prop:"sort",props:{type:"number",max:999,min:0}}],descriptionsFormConfig:{labelWidth:"200px",labelAlign:"center"}});function oe(){I.value.model={isPrimary:"Y",parameterType:"私有"};for(let a of I.value.itemList)a.props==null?a.props={disabled:!1}:a.props.disabled=!1;c.value.okText="保存",c.value.title=t(()=>e("新增权限信息")),c.value.show=!0}function X(a,i,p,v,P){B.value=a,x.value=i,V.value=p,R.value=v,A.value=P,H.value="私有",E.value=!1,N.value=!0}function se(a,i,p,v,P){B.value=a,x.value=i,V.value=p,R.value=v,A.value=P,H.value="私有",E.value=!0,N.value=!0}function de(){s.value.initTableData(B.value,x.value,V.value,R.value,A.value,H.value)}async function l(a){let p=await ue({id:a});I.value.model=p.data;for(let v of I.value.itemList)v.props==null?v.props={disabled:!1}:v.props.disabled=!1;c.value.title=t(()=>e("编辑权限信息")),c.value.okText="保存",c.value.show=!0}async function g(a){let p=await ue({id:a});I.value.model=p.data;for(let v of I.value.itemList)v.props==null?v.props={disabled:!0}:v.props.disabled=!0;c.value.okText=!1,c.value.title=t(()=>e("查看权限信息")),c.value.show=!0}async function u(a){me.confirm("是否确认删除这条数据","删除数据确认",{confirmButtonText:"确定",cancelButtonText:"取消",type:"info",draggable:!0}).then(()=>{he({id:a,type:"interface"}).then(p=>{p.status=="success"?L({type:"info",message:"删除成功"}):L({type:"warning",message:"删除失败"+p.msg})})}).catch(()=>{})}function _(){s.value.resetTable(),N.value=!1}function S(){N.value=!1}function f(){console.log(b.value);let a="";if(b.value!=null){for(let i of b.value)a+=i.parameterId+",";a.length!=0&&(a=a.slice(0,a.length-1))}return a}function k(){let a=[];if(b.value!=null)for(let i of b.value)a.push(i.fieldName);return a}return ae({initTableData:ie,getCheckData:f,restTable:()=>{z.value.tableData=[]},getCheckDataKeys:k}),(a,i)=>{const p=ce,v=K("y9Table"),P=K("y9Form"),re=K("y9Dialog"),Y=Ve,Z=ze;return Q(),De(_e,null,[h.isShow?(Q(),pe(v,{key:0,selectedVal:b.value,"onUpdate:selectedVal":i[0]||(i[0]=F=>b.value=F),filterConfig:te.value,config:d(z),ref_key:"filterRef",ref:q},{slotSearch:y(()=>[T(p,{size:d(w).buttonSize,style:$({fontSize:d(w).baseFontSize}),class:"global-btn-main",type:"primary",onClick:le},{default:y(()=>[O(U(a.$t("查询")),1)]),_:1},8,["size","style"]),T(p,{size:d(w).buttonSize,style:$({fontSize:d(w).baseFontSize}),class:"el-button el-button--default global-btn-third",onClick:J},{default:y(()=>[O(U(a.$t("重置")),1)]),_:1},8,["size","style"])]),slotBtns:y(()=>[h.isView?(Q(),pe(p,{key:0,size:d(w).buttonSize,style:$({fontSize:d(w).baseFontSize}),class:"global-btn-main",type:"primary",onClick:oe},{default:y(()=>[O(U(a.$t("新增")),1)]),_:1},8,["size","style"])):fe("",!0)]),_:1},8,["selectedVal","filterConfig","config"])):fe("",!0),T(re,{config:d(c),"onUpdate:config":i[1]||(i[1]=F=>we(c)?c.value=F:c=F)},{default:y(()=>[T(P,{ref_key:"ruleFormRef",ref:m,config:d(I)},null,8,["config"])]),_:1},8,["config"]),T(Z,{onClosed:_,onOpened:de,modelValue:N.value,"onUpdate:modelValue":i[4]||(i[4]=F=>N.value=F),title:W.value},{footer:y(()=>[T(p,{class:"el-button el-button--default global-btn-third",onClick:i[3]||(i[3]=F=>S())},{default:y(()=>i[5]||(i[5]=[O("关闭")])),_:1})]),default:y(()=>[T(Y),T(Fe,{ref_key:"dictValRef",ref:s,isView:E.value,"onUpdate:isView":i[2]||(i[2]=F=>E.value=F)},null,8,["isView"])]),_:1},8,["modelValue","title"])],64)}}});const Ee=Te(Be,[["__file","E:/workSpaceJDK11/y9-interface-platform/y9vue-interfacePlatform/src/views/auth/interfaceAuth.vue"]]);export{Ee as i};