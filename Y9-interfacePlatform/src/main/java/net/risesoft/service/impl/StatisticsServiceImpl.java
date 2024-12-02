package net.risesoft.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.risesoft.model.InterfaceStatus;
import net.risesoft.service.StatisticsService;
import net.risesoft.y9public.entity.CallApiRequestLogInfo;
import net.risesoft.y9public.entity.InterfaceLimitInfo;
import net.risesoft.y9public.repository.CallApiLogRepository;
import net.risesoft.y9public.repository.InterfaceLimitInfoRepository;
import net.risesoft.y9public.repository.InterfaceManageRepository;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author : lxd
 * @description : 用于统计首页可视化的统计数据
 * @createDate : 2024/10/30 14:48
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private InterfaceLimitInfoRepository interfaceLimitInfoRepository;

    @Autowired
    private InterfaceManageRepository interfaceManageRepository;

    @Autowired
    private CallApiLogRepository callApiLogRepository;


    @Override
    public Map<String, Object> getInterfaceOverview() {
        Map<String, Object> res = new HashMap<>();
        try {
            //停用数、发布数、注册数
            long stopCount = interfaceManageRepository.getStatusCount(InterfaceStatus.UN_APPROVE.getName());
            long publishCount = interfaceManageRepository.getStatusCount(InterfaceStatus.APPROVE.getName());
            long registerCount = interfaceManageRepository.getAllCount();
            Map<String, Long> data = MapBuilder.create(new HashMap<String, Long>())
                    .put("stopCount", stopCount)
                    .put("publishCount", publishCount)
                    .put("registerCount", registerCount)
                    .map();

            res.put("msg", "");
            res.put("code", "0");
            res.put("data", data);
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public Map<String, Object> getCallTrend(String type) {
//        Map<String, Object> res = new HashMap<>();
//        try {
//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime start = null;
//            String format = null;
//            DateHistogramInterval dateHistogramInterval = null;
//
//            switch (type) {
//                case "day":
//                    start = LocalDateTime.now().with(LocalTime.MIDNIGHT);
//                    format = "yyyy-MM-dd HH";
//                    dateHistogramInterval = DateHistogramInterval.HOUR;
//                    break;
//                case "week":
//                    start = now.minusWeeks(1);
//                    format = "yyyy-MM-dd";
//                    dateHistogramInterval = DateHistogramInterval.DAY;
//                    break;
//                case "month":
//                    start = now.minusMonths(1);
//                    format = "yyyy-MM-dd";
//                    dateHistogramInterval = DateHistogramInterval.DAY;
//                    break;
//                case "year":
//                    start = now.minusYears(1);
//                    format = "yyyy-MM";
//                    dateHistogramInterval = DateHistogramInterval.MONTH;
//                    break;
//                default:
//                    throw new RuntimeException("type 类型不存在：" + type);
//            }
//
//            //获取x轴的日期数据
//            List<String> xList = new ArrayList<>();
//            List<String> xFormatList = new ArrayList<>();
//            DateTimeFormatter xFormatter = DateTimeFormatter.ofPattern(format);
//            LocalDateTime tempDateTime = start;
//            while (!tempDateTime.isAfter(now)) {
//                xFormatList.add(tempDateTime.format(xFormatter));
//
//
//
//            Function<String, Map<String, Object>> getStatistics = (wildDate) -> {
//                try {
//                    SearchSourceBuilder ssb = new SearchSourceBuilder()
//                            .query(QueryBuilders.boolQuery()
//                                    .filter(QueryBuilders.existsQuery("apiName"))
//                                    .filter(QueryBuilders.wildcardQuery("requestStartTime", "*" + wildDate + "*"))
//                            )
//                            .aggregation(AggregationBuilders
//                                    .terms("sysName_agg")
//                                    .field("applySystemName")
//                                    .size(Integer.MAX_VALUE)
//                            ).size(0);
//
//                    switch (type) {
//                        case "day":
//                            xList.add(String.format("%02d点", tempDateTime.getHour()));
//                            tempDateTime = tempDateTime.plusHours(1);
//                            break;
//                        case "week":
//                        case "month":
//                            xList.add(String.format("%d年%02d月%02d日", tempDateTime.getYear(), tempDateTime.getMonthValue(), tempDateTime.getDayOfMonth()));
//                            tempDateTime = tempDateTime.plusDays(1);
//                            break;
//                        case "year":
//                            xList.add(String.format("%d年%02d月", tempDateTime.getYear(), tempDateTime.getMonthValue()));
//                            tempDateTime = tempDateTime.plusMonths(1);
//                    }
//
//
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName"))
//                            .filter(QueryBuilders.rangeQuery("requestStartTime").gte(start.format(formatter)).lte(now.format(formatter)))
//                    )
//                    .aggregation(AggregationBuilders
//                            .terms("sysName_agg")
//                            .field("applySystemName")
//                            .subAggregation(AggregationBuilders
//                                    .dateHistogram("t")
//                                    .field("requestStartTime")
//                                    .calendarInterval(dateHistogramInterval)
//                                    .format(format))
//                    ).size(0);
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            //解析获取趋势图数据
//            Map<String, List<Long>> tempData = new HashMap<>();
//            Terms terms = response.getAggregations().get("sysName_agg");
//            for (Terms.Bucket aBucket : terms.getBuckets()) {
//                Histogram histogram = aBucket.getAggregations().get("t");
//
//                //与x轴位置保持一致
//                List<Long> values = new ArrayList<>(Collections.nCopies(xList.size(), 0L));
//                for (Histogram.Bucket bucket : histogram.getBuckets()) {
//                    String time = bucket.getKeyAsString();
//                    int index = xFormatList.indexOf(time);
//                    if (index > -1){
//                        values.set(index, bucket.getDocCount());
//                    }
//                }
//
//                String sysName = aBucket.getKeyAsString();
//                tempData.put(sysName, values);
//            }
//
//            Map<Object, Object> data = MapBuilder.create()
//                    .put("xList", xList)
//                    .put("datas", tempData)
//                    .map();
//            res.put("data", data);
//            res.put("code", "0");
//            res.put("msg", "");
//            return res;
//        } catch (IOException e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public Map<String, Object> getLogOverview() {
//        Map<String, Object> res = new HashMap<>();
//        try {
//            //总调用量、总异常量
//            Supplier<Map> aStatistics = () -> {
//                SearchSourceBuilder ssb = new SearchSourceBuilder()
//                        .query(QueryBuilders.boolQuery()
//                                .filter(QueryBuilders.existsQuery("apiName")))
//                        .aggregation(AggregationBuilders
//                                .terms("status_agg")
//                                .field("responseMsg")
//                        ).size(0);
//
//                SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//                SearchResponse response = null;
//                try {
//                    response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Terms statusAgg = response.getAggregations().get("status_agg");
//                long allTotal = 0;
//                long allErrorTotal = 0;
//                for (Terms.Bucket bucket : statusAgg.getBuckets()) {
//                    String statusName = bucket.getKeyAsString();
//                    long callTotal = bucket.getDocCount();
//                    if ("失败".equals(statusName)) {
//                        allErrorTotal = callTotal;
//                    }
//                    allTotal += callTotal;
//                }
//
//                return MapBuilder.create(new HashMap<>())
//                        .put("allTotal", allTotal)
//                        .put("allErrorTotal", allErrorTotal)
//                        .map();
//            };
//
//            //今日调用量、今日异常量
//            Supplier<Map> bStatistics = () -> {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//                SearchSourceBuilder ssb = new SearchSourceBuilder()
//                        .query(QueryBuilders.boolQuery()
//                                .filter(QueryBuilders.existsQuery("apiName"))
//                                .filter(QueryBuilders.rangeQuery("requestStartTime")
//                                        .gte(LocalDateTime.now().with(LocalTime.MIDNIGHT).format(formatter))
//                                        .lt(LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT).format(formatter))
//                        ))
//                        .aggregation(AggregationBuilders
//                                .terms("status_agg")
//                                .field("responseMsg")
//                        ).size(0);
//
//                SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//                SearchResponse response = null;
//                try {
//                    response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Terms statusAgg = response.getAggregations().get("status_agg");
//                long todayAllTotal = 0;
//                long todayAllErrorTotal = 0;
//                for (Terms.Bucket bucket : statusAgg.getBuckets()) {
//                    String statusName = bucket.getKeyAsString();
//                    long callTotal = bucket.getDocCount();
//                    if ("失败".equals(statusName)) {
//                        todayAllErrorTotal = callTotal;
//                    }
//                    todayAllTotal += callTotal;
//                }
//
//                return MapBuilder.create(new HashMap<>())
//                        .put("todayAllTotal", todayAllTotal)
//                        .put("todayAllErrorTotal", todayAllErrorTotal)
//                        .map();
//            };
//
//            Map aMap = aStatistics.get();
//            Map bMap = bStatistics.get();
//
//            aMap.putAll(bMap);
//
//            res.put("data", aMap);
//            res.put("code", "0");
//            res.put("msg", "");
//            return res;
//        } catch (Exception e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public Map<String, Object> getException(int page, int limit) {
//        //apiName、applySystemName、requestStartTime、errorMessage
//        Map<String, Object> res = new HashMap<>();
//        try {
//            String[] includes = new String[]{"id", "apiName", "applySystemName", "requestStartTime", "errorMessage"};
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName"))
//                            .filter(QueryBuilders.termQuery("responseMsg", "失败")))
//                    .size(limit)
//                    .from((page - 1) * limit)
//                    .sort("requestStartTime", SortOrder.DESC)
//                    .fetchSource(includes, null);
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            List<Map<String, Object>> data = Arrays.stream(response.getHits().getHits())
//                    .map(SearchHit::getSourceAsMap)
//                    .peek(map -> {
//                        String requestStartTime = String.valueOf(map.get("requestStartTime"));
//                        map.put("requestStartTime", removeMilliseconds(requestStartTime));
//                    })
//                    .collect(Collectors.toList());
//
//            res.put("data", data);
//            res.put("code", "0");
//            res.put("count", response.getHits().getTotalHits().value);
//            res.put("msg", "");
//            return res;
//        } catch (Exception e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public Map<String, Object> getLogMonitoringOptions() {
//        Map<String, Object> res = new HashMap<>();
//        try {
//            List<String> aggFieldNames = List.of(
//                    "interfaceType", "deptName", "responseMsg", "authentic", "authenticationResult", "limit", "limitResult",
//                    "courtName"
//            );
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName")))
//                    .size(0);
//
//            for (String name : aggFieldNames) {
//                if ("deptName".equals(name)) {
//                    ssb.aggregation(AggregationBuilders
//                            .terms("deptName_agg")
//                            .field(name)
//                            .size(Integer.MAX_VALUE)
//                            .subAggregation(AggregationBuilders
//                                    .terms("applySystemName_agg")
//                                    .field("applySystemName")
//                                    .size(Integer.MAX_VALUE)
//                            ));
//                } else {
//                    ssb.aggregation(AggregationBuilders.terms(name + "_agg").field(name).size(Integer.MAX_VALUE));
//                }
//            }
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            Map data = new HashMap();
//            for (String aggFieldName : aggFieldNames) {
//                if ("deptName".equals(aggFieldName)) {
//                    Terms aTerms = response.getAggregations().get("deptName_agg");
//                    Map deptOptionMap = new HashMap();
//                    for (Terms.Bucket bucket : aTerms.getBuckets()) {
//                        Terms bTerms = bucket.getAggregations().get("applySystemName_agg");
//                        List<String> options = bTerms.getBuckets().stream()
//                                .map(t -> t.getKeyAsString())
//                                .collect(Collectors.toList());
//
//                        String dept = bucket.getKeyAsString();
//                        deptOptionMap.put(dept, options);
//                    }
//
//                    data.put(aggFieldName, deptOptionMap);
//                } else {
//                    Terms terms = response.getAggregations().get(aggFieldName + "_agg");
//                    List<String> options = terms.getBuckets().stream().map(t -> t.getKeyAsString()).collect(Collectors.toList());
//                    data.put(aggFieldName, options);
//                }
//            }
//
//            res.put("data", data);
//            res.put("code", "0");
//            res.put("msg", "");
//            return res;
//        } catch (Exception e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public Map<String, Object> getLogMonitoringInfo(Map<String, Object> conditionMap) {
//        Map<String, Object> res = new HashMap<>();
//        try {
////            String[] includes = new String[]{"id", "apiName", "applySystemName", "requestStartTime", "responseMsg"};
//
//            QueryBuilder queryBuilder = buildLogMQueryBuilder(conditionMap);
//            int pageSize = (int) conditionMap.getOrDefault("pageSize", 10);
//            int page = (int) conditionMap.getOrDefault("page", 1);
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(queryBuilder)
//                    .size(pageSize)
//                    .from((page - 1) * pageSize)
//                    .sort("requestStartTime", SortOrder.DESC);
////                    .fetchSource(includes, null);
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            Map<String, List<Map>> tempData = Arrays.stream(response.getHits().getHits())
//                    .map(SearchHit::getSourceAsMap)
//                    .peek(map -> {
//                        String requestStartTime = String.valueOf(map.get("requestStartTime"));
//                        map.put("requestStartTime", removeMilliseconds(requestStartTime));
//                    })
//                    .collect(Collectors.groupingBy(map -> String.valueOf(map.get("interfaceId")), Collectors.toList()));
//
//            //到限流表中根据interfaceId查找限流接口的信息，最后补充数据返回。（没有找到的证明非限流接口不做处理）
//            Set<String> ids = tempData.keySet();
//            List<InterfaceLimitInfo> interfaces = interfaceLimitInfoRepository.findByIds(ids);
//            interfaces.stream().forEach(entity -> {
//                String limitRule = "";
//                String type = entity.getThresholdType();
//                //自定义
//                if ("0".equals(type)) {
//                    limitRule = String.format("%.2f 次/秒", Double.parseDouble(entity.getLimitCount()) / Double.parseDouble(entity.getLimitTime()));
//                }
//                //阈值
//                if ("1".equals(type)) {
//                    limitRule = entity.getThresholdVal();
//                }
//
//                String finalLimitRule = limitRule;
//                Optional.ofNullable(tempData.get(entity.getInterfaceId()))
//                        .ifPresent(listMap -> listMap.forEach(m -> m.put("rule", finalLimitRule)));
//
//            });
//
//            //合并，并根据请求时间排序
//            List<Map> data = tempData.values().stream()
//                    .flatMap(Collection::stream)
//                    .sorted((o1, o2) -> String.valueOf(o2.get("requestStartTime")).compareTo(String.valueOf(o1.get("requestStartTime"))))
//                    .collect(Collectors.toList());
//
//            res.put("data", data);
//            res.put("code", "0");
//            res.put("count", response.getHits().getTotalHits().value);
//            res.put("msg", "");
//            return res;
//        } catch (Exception e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    private QueryBuilder buildLogMQueryBuilder(Map<String, Object> conditionMap) {
//
//
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.filter(QueryBuilders.existsQuery("apiName"));
//
//        List<String> termFieldNames = List.of(
//                "interfaceType", "deptName", "responseMsg", "authentic", "authenticationResult", "limit", "limitResult",
//                "applySystemName", "courtName"
//        );
//        List<String> wildFieldNames = List.of("apiName", "userName", "serverIP", "requestIP", "requestUserName", "limitTime");
//
//        if (conditionMap.get("requestStartTime") != null && StringUtils.isNotBlank(conditionMap.get("requestStartTime").toString())) {
//            String[] split = String.valueOf(conditionMap.get("requestStartTime")).split("~");
//            DateTimeFormatter aformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//            DateTimeFormatter bformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String startDate = LocalDateTime.parse(split[0], bformatter).format(aformatter);
//            String endDate = LocalDateTime.parse(split[1], bformatter).format(aformatter);
//
//            if (split.length == 2) {
//                queryBuilder.filter(QueryBuilders.rangeQuery("requestStartTime").gte(startDate).lte(endDate));
//            }
//        }
//
//        if (conditionMap.get("deptSystem") != null && StringUtils.isNotBlank(conditionMap.get("deptSystem").toString())) {
//            if (conditionMap.get("deptSystem") instanceof Map) {
//                Map<String, List<String>> map = (Map) conditionMap.get("deptSystem");
//                if (!map.isEmpty()) {
//                    queryBuilder.minimumShouldMatch(1);
//
//                    for (String deptName : map.keySet()) {
//                        BoolQueryBuilder tempQB = QueryBuilders.boolQuery();
//                        tempQB.filter(QueryBuilders.termQuery("deptName", deptName));
//                        if (map.get(deptName) != null && map.get(deptName).size() > 0) {
//                            tempQB.filter(QueryBuilders.termsQuery("applySystemName", map.get(deptName)));
//                        }
//
//                        queryBuilder.should(tempQB);
//                    }
//                }
//            }
//        }
//
//        for (String fieldName : termFieldNames) {
//            Optional.ofNullable(conditionMap.get(fieldName))
//                    .map(Object::toString)  // 将termValue转换为字符串，以便后续比较
//                    .filter(StringUtils::isNotBlank)
//                    .ifPresent(value -> queryBuilder.filter(QueryBuilders.termQuery(fieldName, value)));
//        }
//
//        for (String fieldName : wildFieldNames) {
//            Optional.ofNullable(conditionMap.get(fieldName))
//                    .map(Object::toString)  // 将termValue转换为字符串，以便后续比较
//                    .filter(StringUtils::isNotBlank)
//                    .ifPresent(value -> queryBuilder.filter(QueryBuilders.wildcardQuery(fieldName, "*" + value + "*")));
//        }
//
//        return queryBuilder;
//    }
//
//    @Override
//    public Map<String, Object> getRealTimeLog(int page, int limit) {
//        //apiName、applySystemName、requestStartTime、responseMsg
//        Map<String, Object> res = new HashMap<>();
//        try {
//            String[] includes = new String[]{"id", "apiName", "applySystemName", "requestStartTime", "responseMsg"};
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName")))
//                    .size(limit)
//                    .from((page - 1) * limit)
//                    .sort("requestStartTime", SortOrder.DESC)
//                    .fetchSource(includes, null);
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            List<Map<String, Object>> data = Arrays.stream(response.getHits().getHits())
//                    .map(SearchHit::getSourceAsMap)
//                    .peek(map -> {
//                        String requestStartTime = String.valueOf(map.get("requestStartTime"));
//                        map.put("requestStartTime", removeMilliseconds(requestStartTime));
//                    })
//                    .collect(Collectors.toList());
//
//            res.put("data", data);
//            res.put("code", "0");
//            res.put("count", response.getHits().getTotalHits().value);
//            res.put("msg", "");
//            return res;
//        } catch (Exception e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public Map<String, Object> getRunningCount() {
//        Map<String, Object> res = new HashMap<>();
//        try {
//            List<String> runningIds = interfaceManageRepository.getIdsByStatus(InterfaceStatus.APPROVE.getName());
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .size(0)
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName"))
//                            .filter(QueryBuilders.termsQuery("interfaceId", runningIds)))
//                    .aggregation(
//                            AggregationBuilders.terms("interfaceId_agg")
//                                    .field("interfaceId")
//                                    .size(Integer.MAX_VALUE)
//                                    .subAggregation(
//                                            AggregationBuilders.topHits("last")
//                                                    .sort("requestStartTime", SortOrder.DESC) // 根据requestStartTime字段降序排序
//                                                    .size(1) // 只获取一个顶部文档
//                                                    .fetchSource("responseMsg", null)));
//
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//
//            SearchResponse response = null;
//            try {
//                response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            long normalCount = 0;
//            long abnormalCount = 0;
//            Terms terms = response.getAggregations().get("interfaceId_agg");
//            for (Terms.Bucket bucket : terms.getBuckets()) {
//                TopHits topHits = bucket.getAggregations().get("last");
//
//                String msg = Arrays.stream(topHits.getHits().getHits())
//                        .map(hit -> String.valueOf(hit.getSourceAsMap().get("responseMsg")))
//                        .findFirst()
//                        .orElse(null);
//
//                if (StringUtils.isNotBlank(msg)) {
//                    if ("成功".equals(msg)) {
//                        normalCount++;
//                    } else {
//                        abnormalCount++;
//                    }
//                }
//            }
//
//            Map<Object, Object> data = MapBuilder.create(new HashMap<>())
//                    .put("normalCount", normalCount)
//                    .put("abnormalCount", abnormalCount)
//                    .map();
//
//            res.put("data", data);
//            res.put("code", "0");
//            res.put("msg", "");
//            return res;
//        } catch (Exception e) {
//            res.put("code", "1");
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    private String removeMilliseconds(String dateTime) {
//        if (StringUtils.isBlank(dateTime) || "null".equals(dateTime)) {
//            return "";
//        }
//
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, inputFormatter);
//        // 定义输出格式，不包括毫秒
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return localDateTime.format(outputFormatter);
//    }
        return null;
    }

    @Override
    public Map<String, Object> getLogOverview() {
        Map<String, Object> res = new HashMap<>();
        try {
//            //总调用量、总异常量
//            Supplier<Map> aStatistics = () -> {
//                SearchSourceBuilder ssb = new SearchSourceBuilder()
//                        .query(QueryBuilders.boolQuery()
//                                .filter(QueryBuilders.existsQuery("apiName")))
//                        .aggregation(AggregationBuilders
//                                .terms("status_agg")
//                                .field("responseMsg")
//                        ).size(0);
//
//                SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//                SearchResponse response = null;
//                try {
//                    response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Terms statusAgg = response.getAggregations().get("status_agg");
//                long allTotal = 0;
//                long allErrorTotal = 0;
//                for (Terms.Bucket bucket : statusAgg.getBuckets()) {
//                    String statusName = bucket.getKeyAsString();
//                    long callTotal = bucket.getDocCount();
//                    if ("失败".equals(statusName)) {
//                        allErrorTotal = callTotal;
//                    }
//                    allTotal += callTotal;
//                }
//
//                return MapBuilder.create(new HashMap<>())
//                        .put("allTotal", allTotal)
//                        .put("allErrorTotal", allErrorTotal)
//                        .map();
//            };
//
//            //今日调用量、今日异常量
//            Supplier<Map> bStatistics = () -> {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//                SearchSourceBuilder ssb = new SearchSourceBuilder()
//                        .query(QueryBuilders.boolQuery()
//                                .filter(QueryBuilders.existsQuery("apiName"))
//                                .filter(QueryBuilders.rangeQuery("requestStartTime")
//                                        .gte(LocalDateTime.now().with(LocalTime.MIDNIGHT).format(formatter))
//                                        .lt(LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT).format(formatter))
//                        ))
//                        .aggregation(AggregationBuilders
//                                .terms("status_agg")
//                                .field("responseMsg")
//                        ).size(0);
//
//                SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//                SearchResponse response = null;
//                try {
//                    response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Terms statusAgg = response.getAggregations().get("status_agg");
//                long todayAllTotal = 0;
//                long todayAllErrorTotal = 0;
//                for (Terms.Bucket bucket : statusAgg.getBuckets()) {
//                    String statusName = bucket.getKeyAsString();
//                    long callTotal = bucket.getDocCount();
//                    if ("失败".equals(statusName)) {
//                        todayAllErrorTotal = callTotal;
//                    }
//                    todayAllTotal += callTotal;
//                }
//
//                return MapBuilder.create(new HashMap<>())
//                        .put("todayAllTotal", todayAllTotal)
//                        .put("todayAllErrorTotal", todayAllErrorTotal)
//                        .map();
//            };
//
//            Map aMap = aStatistics.get();
//            Map bMap = bStatistics.get();
//
//            aMap.putAll(bMap);
//
//            res.put("data", aMap);
            res.put("code", "0");
            res.put("msg", "");
            return res;
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getException(int page, int limit) {
        //apiName、applySystemName、requestStartTime、errorMessage
        Map<String, Object> res = new HashMap<>();
        try {
//            String[] includes = new String[]{"id", "apiName", "applySystemName", "requestStartTime", "errorMessage"};
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName"))
//                            .filter(QueryBuilders.termQuery("responseMsg", "失败")))
//                    .size(limit)
//                    .from((page - 1) * limit)
//                    .sort("requestStartTime", SortOrder.DESC)
//                    .fetchSource(includes, null);
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            List<Map<String, Object>> data = Arrays.stream(response.getHits().getHits())
//                    .map(SearchHit::getSourceAsMap)
//                    .peek(map -> {
//                        String requestStartTime = String.valueOf(map.get("requestStartTime"));
//                        map.put("requestStartTime", removeMilliseconds(requestStartTime));
//                    })
//                    .collect(Collectors.toList());
//
//            res.put("data", data);
            res.put("code", "0");
//            res.put("count", response.getHits().getTotalHits().value);
            res.put("msg", "");
            return res;
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getLogMonitoringOptions() {
        Map<String, Object> res = new HashMap<>();
        try {
            Map<String,Set<String>> data = new HashMap();

            List<Map<String, Object>> dataGroup = callApiLogRepository.getDataGroup();
            List<Map<String, Object>> deptName = callApiLogRepository.getDataGroupByDeptName();
            for(Map<String,Object> map : dataGroup){
                for(String key : map.keySet()){
                    if(data.get(key)==null){
                        Set<String> set = new HashSet<>();
                        data.put(key,set);
                    }
                    if(map.get(key)!=null && !map.get(key).equals("null") && StringUtils.isNotBlank(map.get(key).toString())){
                        String[] strs = map.get(key).toString().split(",");
                        for(String str : strs){
                            data.get(key).add(str);
                        }
                    }
                }
            }
            List<Map<String,Object>> deptList = new ArrayList<>();
            for (Map<String,Object> map : deptName){
                if (map.get("deptName") != null && !map.get("deptName").equals("null") && StringUtils.isNotBlank(map.get("deptName").toString())) {
                    Map<String, Object> pMap = new HashMap<>();
                    pMap.put("label", map.get("deptName"));
                    pMap.put("value", map.get("deptName"));
                    if (map.get("applySystemName") != null && !map.get("applySystemName").equals("null") && StringUtils.isNotBlank(map.get("applySystemName").toString())) {
                        String[] applySystemNames = map.get("applySystemName").toString().split(",");
                        List<Map<String, Object>> cMapList = new ArrayList<>();
                        for (String applySystemName : applySystemNames) {
                            Map<String, Object> cMap = new HashMap<>();
                            cMap.put("label", applySystemName);
                            cMap.put("value", applySystemName);
                            cMapList.add(cMap);
                        }
                        pMap.put("children", cMapList);
                    }
                    deptList.add(pMap);
                }

            }
            Map rtData = new HashMap();
            rtData.putAll(data);
            rtData.put("deptName",deptList);
            res.put("data", rtData);
            res.put("code", "0");
            res.put("msg", "");
            return res;
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getLogMonitoringInfo(Map<String, Object> conditionMap) {
        Map<String, Object> res = new HashMap<>();
        try {
            Specification<CallApiRequestLogInfo> query = buildLogQueryBuilder(conditionMap);
            int pageSize = (int) conditionMap.getOrDefault("pageSize", 10);
            int page = (int) conditionMap.getOrDefault("page", 1);
            page = page -1;
            PageRequest pageRequest = PageRequest.of(page, pageSize,Sort.by("requestStartTime").descending());
            Page<CallApiRequestLogInfo> all = callApiLogRepository.findAll(query, pageRequest);
            List<Map> entiyList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            all.getContent().stream().forEach(m ->{
                Map<String,Object> map = mapper.convertValue(m,Map.class);
                entiyList.add(map);
            });
            Map<String, List<Map>> tempData = entiyList.stream()
                    .peek(map -> {
                        String requestStartTime = String.valueOf(map.get("requestStartTime"));
                        map.put("requestStartTime",removeMilliseconds(requestStartTime));
                    })
                    .collect(Collectors.groupingBy(map -> String.valueOf(map.get("interfaceId")), Collectors.toList()));

            //到限流表中根据interfaceId查找限流接口的信息，最后补充数据返回。（没有找到的证明非限流接口不做处理）
            Set<String> ids = new HashSet<>();
            for(String key: tempData.keySet()){
                if(tempData.get(key)!=null && tempData.get(key).get(0)!=null && "1".equals(tempData.get(key).get(0).get("isLimit").toString())){
                    ids.add(key);
                }
            }
            List<InterfaceLimitInfo> interfaces = interfaceLimitInfoRepository.findByIds(ids);
            interfaces.stream().forEach(entity -> {
                String limitRule = "";
                String type = entity.getThresholdType();
                //自定义
                if ("0".equals(type)) {
                    limitRule = String.format("%.2f 次/秒", Double.parseDouble(entity.getLimitCount()) / Double.parseDouble(entity.getLimitTime()));
                }
                //阈值
                if ("1".equals(type)) {
                    limitRule = entity.getThresholdVal();
                }

                String finalLimitRule = limitRule;
                Optional.ofNullable(tempData.get(entity.getInterfaceId()))
                        .ifPresent(listMap -> listMap.forEach(m -> m.put("rule", finalLimitRule)));

            });

            //合并，并根据请求时间排序
            List<Map> data = tempData.values().stream()
                    .flatMap(Collection::stream)
                    .sorted((o1, o2) -> String.valueOf(o2.get("requestStartTime")).compareTo(String.valueOf(o1.get("requestStartTime"))))
                    .collect(Collectors.toList());

            res.put("data", data);
            res.put("code", "0");
            res.put("count", all.getTotalElements());
            res.put("msg", "");
            return res;
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }
        return res;
    }


    private Specification<CallApiRequestLogInfo> buildLogQueryBuilder(Map<String, Object> conditionMap) {
        List<String> termFieldNames = List.of(
                "interfaceType", "deptName", "responseMsg", "authentic", "authenticationResult", "isLimit", "limitResult",
                "applySystemName", "courtName"
        );
        List<String> wildFieldNames = List.of("apiName", "userName", "serverIP", "requestIP", "requestUserName", "limitTime");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Specification<CallApiRequestLogInfo> spec= new Specification<CallApiRequestLogInfo>(){
            @Override
            public Predicate toPredicate(Root<CallApiRequestLogInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (conditionMap.get("requestStartTime") != null && StringUtils.isNotBlank(conditionMap.get("requestStartTime").toString())) {
                    String[] split = String.valueOf(conditionMap.get("requestStartTime")).split("~");
                    DateTimeFormatter aformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    DateTimeFormatter bformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String startDate = LocalDateTime.parse(split[0], bformatter).format(aformatter);
                    String endDate = LocalDateTime.parse(split[1], bformatter).format(aformatter);

                    if (split.length == 2) {
                        try {
                            predicates.add(criteriaBuilder.between(root.get("requestStartTime"),sdf.parse(startDate),sdf.parse(endDate)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (conditionMap.get("deptSystem") != null && StringUtils.isNotBlank(conditionMap.get("deptSystem").toString())) {
                    if (conditionMap.get("deptSystem") instanceof Map) {
                        Map<String, List<String>> map = (Map) conditionMap.get("deptSystem");
                        if (!map.isEmpty()) {
                            for (String deptName : map.keySet()) {
                                predicates.add(criteriaBuilder.equal(root.get("deptName"),deptName));
                                if (map.get(deptName) != null && map.get(deptName).size() > 0) {
                                    CriteriaBuilder.In<Object> applySystemNames = criteriaBuilder.in(root.get("applySystemName"));
                                    for (String it : map.get(deptName)) {
                                        applySystemNames.value(it);
                                    }
                                    predicates.add(applySystemNames);
                                }
                            }
                        }
                    }
                }
                for (String fieldName : termFieldNames) {
                    Optional.ofNullable(conditionMap.get(fieldName))
                            .map(Object::toString)  // 将termValue转换为字符串，以便后续比较
                            .filter(StringUtils::isNotBlank)
                            .ifPresent(value -> predicates.add(criteriaBuilder.equal(root.get(fieldName),value)));
                }

                for (String fieldName : wildFieldNames) {
                    Optional.ofNullable(conditionMap.get(fieldName))
                            .map(Object::toString)  // 将termValue转换为字符串，以便后续比较
                            .filter(StringUtils::isNotBlank)
                            .ifPresent(value -> predicates.add(criteriaBuilder.like(root.get(fieldName),"%"+value+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        return spec;
    }


    @Override
    public Map<String, Object> getRealTimeLog(int page, int limit) {
        //apiName、applySystemName、requestStartTime、responseMsg
        Map<String, Object> res = new HashMap<>();
        try {
//            String[] includes = new String[]{"id", "apiName", "applySystemName", "requestStartTime", "responseMsg"};
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName")))
//                    .size(limit)
//                    .from((page - 1) * limit)
//                    .sort("requestStartTime", SortOrder.DESC)
//                    .fetchSource(includes, null);
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//
//            List<Map<String, Object>> data = Arrays.stream(response.getHits().getHits())
//                    .map(SearchHit::getSourceAsMap)
//                    .peek(map -> {
//                        String requestStartTime = String.valueOf(map.get("requestStartTime"));
//                        map.put("requestStartTime", removeMilliseconds(requestStartTime));
//                    })
//                    .collect(Collectors.toList());
//
//            res.put("data", data);
            res.put("code", "0");
//            res.put("count", response.getHits().getTotalHits().value);
            res.put("msg", "");
            return res;
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getRunningCount() {
        Map<String, Object> res = new HashMap<>();
        try {
//            List<String> runningIds = interfaceManageRepository.getIdsByStatus(InterfaceStatus.APPROVE.getName());
//
//            SearchSourceBuilder ssb = new SearchSourceBuilder()
//                    .size(0)
//                    .query(QueryBuilders.boolQuery()
//                            .filter(QueryBuilders.existsQuery("apiName"))
//                            .filter(QueryBuilders.termsQuery("interfaceId", runningIds)))
//                    .aggregation(
//                            AggregationBuilders.terms("interfaceId_agg")
//                                    .field("interfaceId")
//                                    .size(Integer.MAX_VALUE)
//                                    .subAggregation(
//                                            AggregationBuilders.topHits("last")
//                                                    .sort("requestStartTime", SortOrder.DESC) // 根据requestStartTime字段降序排序
//                                                    .size(1) // 只获取一个顶部文档
//                                                    .fetchSource("responseMsg", null)));
//
//
//            SearchRequest request = new SearchRequest("y9_call_api_request_log_info").source(ssb);
//
//            SearchResponse response = null;
//            try {
//                response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            long normalCount = 0;
//            long abnormalCount = 0;
//            Terms terms = response.getAggregations().get("interfaceId_agg");
//            for (Terms.Bucket bucket : terms.getBuckets()) {
//                TopHits topHits = bucket.getAggregations().get("last");
//
//                String msg = Arrays.stream(topHits.getHits().getHits())
//                        .map(hit -> String.valueOf(hit.getSourceAsMap().get("responseMsg")))
//                        .findFirst()
//                        .orElse(null);
//
//                if (StringUtils.isNotBlank(msg)) {
//                    if ("成功".equals(msg)) {
//                        normalCount++;
//                    } else {
//                        abnormalCount++;
//                    }
//                }
//            }
//
//            Map<Object, Object> data = MapBuilder.create(new HashMap<>())
//                    .put("normalCount", normalCount)
//                    .put("abnormalCount", abnormalCount)
//                    .map();

//            res.put("data", data);
            res.put("code", "0");
            res.put("msg", "");
            return res;
        } catch (Exception e) {
            res.put("code", "1");
            e.printStackTrace();
        }
        return res;
    }

    private String removeMilliseconds(String dateTime) {
        if (StringUtils.isBlank(dateTime) || "null".equals(dateTime)) {
            return "";
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, inputFormatter);
        // 定义输出格式，不包括毫秒
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(outputFormatter);
    }
}