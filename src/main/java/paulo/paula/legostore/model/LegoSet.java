package paulo.paula.legostore.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Document(collection = "legosets")
public class LegoSet {

    @Id
    private String id;

    @TextIndexed
    private String name;

    @TextIndexed
    @Indexed(direction = IndexDirection.ASCENDING)
    private String theme;

    private LegoSetDifficulty difficulty;
    private Collection<ProductReview> reviews = new ArrayList<>();

    @Field("delivery")
    private DeliveryInfo deliveryInfo;

    @Transient
    private int nbParts;

    public LegoSet(String name,
                   String theme,
                   LegoSetDifficulty difficulty,
                   DeliveryInfo deliveryInfo,
                   Collection<ProductReview> reviews) {

        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.deliveryInfo = deliveryInfo;

        if(reviews != null) {
            this.reviews = reviews;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public LegoSetDifficulty getDifficulty() {
        return difficulty;
    }

    public Collection<ProductReview> getReviews() {
        return Collections.unmodifiableCollection(this.reviews);
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public int getNbParts() {
        return nbParts;
    }
}