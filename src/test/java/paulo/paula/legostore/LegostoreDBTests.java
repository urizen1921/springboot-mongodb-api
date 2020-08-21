package paulo.paula.legostore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import paulo.paula.legostore.model.*;
import paulo.paula.legostore.persistence.LegoSetRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class LegostoreDBTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LegoSetRepository legoSetRepository;

    @Before
    public void before() {
        this.legoSetRepository.deleteAll();

        LegoSet milleniumFalcon = new LegoSet(
                "Millenium Falcon",
                "Star Wars",
                LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
                Arrays.asList(
                        new ProductReview("Dan", 7),
                        new ProductReview("Paulo", 10),
                        new ProductReview("Jovem", 5)
                ),
                new PaymentOptions(PaymentType.CREDIT_CARD, 0)
        );

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                "City",
                LegoSetDifficulty.MEDIUM,
                new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
                Arrays.asList(
                        new ProductReview("Paulo", 3),
                        new ProductReview("Jovem", 7),
                        new ProductReview("Dan", 3)
                ),
                new PaymentOptions(PaymentType.PAYPAL, 1)
        );

        this.legoSetRepository.insert(milleniumFalcon);
        this.legoSetRepository.insert(skyPolice);


    }

    @Test
    public void findAllByGreatReviews_should_return_GTTen() {
        List<LegoSet> results = (List<LegoSet>) this.legoSetRepository.findAllByGreatReviews();

        assertEquals(1, results.size());
        assertEquals("Millenium Falcon", results.get(0).getName());
    }

}
