package sample.cafekiosk.spring.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

class ProductNumberFactoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductNumberFactory productNumberFactory;

    @Test
    @DisplayName("상품 등록시에 주문 번호가 순차적으로 생성된다.")
    void createNextProductNumber() {
        // given
        Product product1 = createProduct("001");
        Product product2 = createProduct("002");
        productRepository.saveAll(List.of(product1, product2));

        // when
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        // then
        assertThat(nextProductNumber).isEqualTo("003");
    }

    private static Product createProduct(String productNumber) {
        return Product.builder()
            .productNumber(productNumber)
            .build();
    }

}