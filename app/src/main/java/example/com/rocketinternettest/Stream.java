package example.com.rocketinternettest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Kavya Shree, 13/02/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stream {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String brand;
    @DatabaseField
    private String price;
    @DatabaseField
    private String imageUrl;

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    @JsonProperty("brand")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("path")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}