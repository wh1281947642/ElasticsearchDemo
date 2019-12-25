package cn.itcast.elasticsearch;

import cn.itcast.elasticsearch.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * <p>
 * <code>ItemRepository</code>
 * </p>
 * Description:
 *
 * @author huiwang45
 * @date 2019/12/24 16:00
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

    List<Item> findByTitle(String title);

    List<Item> findByPriceBetween(Double d1,Double d2);


}
