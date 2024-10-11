package sample.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.STOP_SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("특정 시간동안 주문된 주문 목록을 조회할 수 있다.")
    void findOrdersBy() {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.now().minusHours(10);
        LocalDateTime endDateTime = LocalDateTime.now();
        OrderStatus completedStatus = OrderStatus.INIT;

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
        List<Product> savaedProducts = productRepository.saveAll(
            List.of(product1, product2, product3));

        Order order = Order.create(savaedProducts, registeredDateTime);
        orderRepository.save(order);

        //when
        List<Order> orders = orderRepository.findOrdersBy(registeredDateTime.minusHours(1),
            endDateTime,
            completedStatus);

        //then
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0)).extracting("orderStatus", "totalPrice", "registeredDateTime")
            .containsExactly(OrderStatus.INIT, 15_500, registeredDateTime);
        assertThat(orders.get(0).getOrderProducts()).extracting("order", "product")
            .containsExactlyInAnyOrder(
                tuple(order, product1),
                tuple(order, product2),
                tuple(order, product3)
            );
    }

    private Product createProduct(String productNumber, ProductType type,
        ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }

}