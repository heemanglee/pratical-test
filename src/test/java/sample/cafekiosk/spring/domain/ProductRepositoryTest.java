package sample.cafekiosk.spring.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.ProductSellingStatus.HOLD;
import static sample.cafekiosk.spring.domain.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.ProductSellingStatus.STOP_SELLING;
import static sample.cafekiosk.spring.domain.ProductType.HANDMADE;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
            .productNumber("001")
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("아메리카노")
            .price(4000)
            .build();
        Product product2 = Product.builder()
            .productNumber("001")
            .type(HANDMADE)
            .sellingStatus(HOLD)
            .name("카페라떼")
            .price(4500)
            .build();
        Product product3 = Product.builder()
            .productNumber("003")
            .type(HANDMADE)
            .sellingStatus(STOP_SELLING)
            .name("팥빙수")
            .price(7000)
            .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(
            List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("001", "카페라떼", HOLD)
            );
    }

}