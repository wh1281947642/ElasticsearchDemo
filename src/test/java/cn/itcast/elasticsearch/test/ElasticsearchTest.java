package cn.itcast.elasticsearch.test;

import cn.itcast.elasticsearch.pojo.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testIndex(){
        //创建索引
        this.elasticsearchTemplate.createIndex(Item.class);
        //创建映射
        this.elasticsearchTemplate.putMapping(Item.class);

    }
}
