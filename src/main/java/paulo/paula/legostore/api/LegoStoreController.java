package paulo.paula.legostore.api;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.*;
import paulo.paula.legostore.model.LegoSet;
import paulo.paula.legostore.model.LegoSetDifficulty;
import paulo.paula.legostore.model.QLegoSet;
import paulo.paula.legostore.persistence.LegoSetRepository;

import java.util.Collection;

@RestController
@RequestMapping("legostore/api")
public class LegoStoreController {

    private LegoSetRepository legoSetRepository;

    public LegoStoreController(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @PostMapping
    public void insert(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.insert(legoSet);
    }

    @GetMapping("/all")
    public Collection<LegoSet> all() {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        return this.legoSetRepository.findAll(sortByThemeAsc);
    }

    @PutMapping
    public void update(@RequestBody LegoSet legoSet) {
        this.legoSetRepository.save(legoSet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.legoSetRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LegoSet byId(@PathVariable String id) {
        LegoSet legoSet = this.legoSetRepository.findById(id).orElse(null);
        return legoSet;
    }

    @GetMapping("/byTheme/{theme}")
    public Collection<LegoSet> byTheme(@PathVariable String theme) {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        return this.legoSetRepository.findAllByThemeContains(theme, sortByThemeAsc);
    }

    @GetMapping("hardThatStartWithM")
    public Collection<LegoSet> hardThatStartWithM() {
        return this.legoSetRepository.findAllByDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
    }

    @GetMapping("/byDeliveryFeeLessThan/{price}")
    public Collection<LegoSet> byDeliveryFeeLessThan(@PathVariable int price) {
        Sort sortByNameAsc = Sort.by("name").ascending();
        return this.legoSetRepository.findAllByDeliveryPriceLessThan(price, sortByNameAsc);
    }

    @GetMapping("greatReviews")
    public Collection<LegoSet> byGreatReviews() {
        return this.legoSetRepository.findAllByGreatReviews();
    }

    @GetMapping("bestBuys")
    public Collection<LegoSet> bestBuys() {
        //build the query
        QLegoSet query = new QLegoSet("query");
        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isTrue();
        Predicate smallDeliveryFeeFilter = query.deliveryInfo.deliveryFee.lt(50);
        Predicate hasGreatReviews = query.reviews.any().rating.eq(10);

        Predicate bestBuysFilter = inStockFilter
                .and(smallDeliveryFeeFilter)
                .and(hasGreatReviews);

        //pass the query to the interface method findAll()
        return (Collection<LegoSet>) this.legoSetRepository.findAll(bestBuysFilter);
    }

    @GetMapping("fullTextSearch/{text}")
    public Collection<LegoSet> fullTextSearch(@PathVariable String text) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
        return this.legoSetRepository.findAllBy(textCriteria);
    }
}
