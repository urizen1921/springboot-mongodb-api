package paulo.paula.legostore.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import paulo.paula.legostore.model.LegoSet;
import paulo.paula.legostore.model.LegoSetDifficulty;

import java.util.Collection;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor<LegoSet> {

    //Methods created by method convention automatically executes a query
    Collection<LegoSet> findAllByThemeContains(String theme);
    Collection<LegoSet> findAllByThemeContains(String theme, Sort sort);
    Collection<LegoSet> findAllByDifficultyAndNameStartsWith(LegoSetDifficulty difficulty, String name);
    Collection<LegoSet> findAllBy(TextCriteria textCriteria);

    //Query without method name convention
    //Because Delivery Price doesn't exist as a property
    //?0 first argument received //?1 second argument and so forth
    //Query is a MongoDB Query
    @Query("{'delivery.deliveryFee' : {$lt : ?0}}")
    Collection<LegoSet> findAllByDeliveryPriceLessThan(int price, Sort sort);

    @Query("{'reviews.rating' : {$eq : 10}}")
    Collection<LegoSet> findAllByGreatReviews();
}
