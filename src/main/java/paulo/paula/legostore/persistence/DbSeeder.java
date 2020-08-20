package paulo.paula.legostore.persistence;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import paulo.paula.legostore.model.DeliveryInfo;
import paulo.paula.legostore.model.LegoSet;
import paulo.paula.legostore.model.LegoSetDifficulty;
import paulo.paula.legostore.model.ProductReview;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Service
public class DbSeeder implements CommandLineRunner {

    private LegoSetRepository legoSetRepository;

    public DbSeeder(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @Override
    public void run(String... args) throws Exception {

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
                )
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
                )
        );

        LegoSet londonBridge = new LegoSet(
                "London Bridge",
                "City",
                LegoSetDifficulty.HARD,
                new DeliveryInfo(LocalDate.now().plusDays(2), 25, true),
                Arrays.asList(
                        new ProductReview("Paulo", 3),
                        new ProductReview("Jovem", 7),
                        new ProductReview("Dan", 3)
                )
        );

        Collection<LegoSet> initialProducts = Arrays.asList(milleniumFalcon, skyPolice, londonBridge);
        this.legoSetRepository.insert(initialProducts);

    }
}
