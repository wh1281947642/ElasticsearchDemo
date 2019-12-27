package cn.itcast.elasticsearch.test;

import cn.itcast.elasticsearch.ItemRepository;
import cn.itcast.elasticsearch.pojo.Item;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregator;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * <code>ElasticsearchTest</code>
 * </p>
 *
 * @author huiwang45@iflytek.com
 * @description
 * @date 2019/12/23 18:16
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testIndex(){
        //创建索引
        this.elasticsearchTemplate.createIndex(Item.class);
        //创建映射
        this.elasticsearchTemplate.putMapping(Item.class);

    }

    @Test
    public void  testCreate(){
        Item item = new Item(4L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        this.itemRepository.save(item);

    }

    @Test
    public void  testCreate1(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        // 接收对象集合，实现批量新增
        this.itemRepository.saveAll(list);
    }

    @Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void  testFind(){
        //根据主键查询
        //Optional<Item> item = this.itemRepository.findById(1L);
        //System.out.println(item.get());

        //查询全部并降序排列
        Iterable<Item> items = this.itemRepository.findAll(Sort.by("price").descending());
        items.forEach(item1 -> {
            System.out.println(item1.toString());
        });
    }

    @Test
    public void  findByTitle(){
        //自定义方法
        //List<Item> items = this.itemRepository.findByTitle("手机");
        List<Item> items = this.itemRepository.findByPriceBetween(3699d,4499d);

        items.forEach(item -> {
            System.out.println(item.toString());
        });
    }

    /**
     * 基本查询
     * @description
     * @author huiwang45@iflytek.com
     * @date 2019/12/24 20:45
     * @param
     * @return
     */
    @Test
    public void  testSearch(){
        //通过查询构建器工具构建查询条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "手机");
        //执行查询，获取结果集
        Iterable<Item> items = this.itemRepository.search(queryBuilder);
        items.forEach(item -> {
            System.out.println(item.toString());
        });
    }


    /**
     * 自定义构建器
     * @description
     * @author huiwang45@iflytek.com
     * @date 2019/12/24 20:47
     * @param
     * @return
     */
    @Test
    public void testNative(){
        //构建自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));
        //执行查询，获取分页结果集
        Page<Item> itemsPage = this.itemRepository.search(queryBuilder.build());
        //总页数
        System.out.println(itemsPage.getTotalPages());
        //总条数
        System.out.println(itemsPage.getTotalElements());
        List<Item> items = itemsPage.getContent();
        items.forEach(item -> {
            System.out.println(item.toString());
        });
    }

    @Test
    public void  testPage(){
        //构建自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本的查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("category", "手机"));
        //添加分页条件，页码从零开始
        //queryBuilder.withPageable(PageRequest.of(1,2));
        //添加排序条件
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        //执行查询，获取分页结果集
        Page<Item> itemsPage = this.itemRepository.search(queryBuilder.build());
        //总页数
        System.out.println(itemsPage.getTotalPages());
        //总条数
        System.out.println(itemsPage.getTotalElements());
        List<Item> items = itemsPage.getContent();
        items.forEach(item -> {
            System.out.println(item.toString());
         });
    }

    @Test
    public void  testAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand"));
        //添加结果集过滤，不包括任何字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>)this.itemRepository.search(queryBuilder.build());
        //解析聚合结果集,根据聚合的类型以及字段的类型，要进行强转，brand-字符串类型，聚合类型-词条聚合
        //brandAgg,通过聚合名称获取聚合对象
        StringTerms brandAgg = (StringTerms)itemPage.getAggregation("brandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        });
    }

    @Test
    public void  testSubAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brandAgg").field("brand")
                .subAggregation(AggregationBuilders.avg("price_avg").field("price")));
        //添加结果集过滤，不包括任何字段
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行聚合查询
        AggregatedPage<Item> itemPage = (AggregatedPage<Item>)this.itemRepository.search(queryBuilder.build());
        //解析聚合结果集,根据聚合的类型以及字段的类型，要进行强转，brand-字符串类型，聚合类型-词条聚合
        //brandAgg,通过聚合名称获取聚合对象
        StringTerms brandAgg = (StringTerms)itemPage.getAggregation("brandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = brandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            //获取子聚合的map聚合：key-聚合名称，value-对应的子聚合对象
            Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
            InternalAvg price_avg = (InternalAvg)stringAggregationMap.get("price_avg");
            System.out.println(price_avg.getValue());
        });
    }
}
