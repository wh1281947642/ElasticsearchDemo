package cn.itcast.elasticsearch.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * <p>
 * <code>Item</code>
 * </p>
 * @author huiwang45@iflytek.com
 * @description
 * @date 2019/12/23 17:29
 *  indexName 索引库
 *  type 表
 *  shards 分片
 */
@Document(indexName = "item",type = "docs",shards =1,replicas = 0)
public class Item {
    /***
     *
     */
    @Id
    Long id;
    /***
     * 标题
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    String title;
    /***
     * 分类
     */
    @Field(type = FieldType.Keyword)
    String category;
    /***
     * 品牌
     */
    @Field(type = FieldType.Keyword)
    String brand;
    /***
     * 价格
     */
    @Field(type = FieldType.Double)
    Double price;
    /***
     * 图片地址
     */
    @Field(type =FieldType.Keyword)
    String images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Item() {
    }

    public Item(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                '}';
    }
}