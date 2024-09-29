package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        // 'in' 절이므로 중복 조회가 안 되는 문제 발생
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        List<Product> duplicateProducts = findProductsBy(products, productNumbers);

        Order order = Order.create(duplicateProducts, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    private static List<Product> findProductsBy(List<Product> products,
        List<String> productNumbers) {
        Map<String, Product> productMap = products.stream()
            .collect(Collectors.toMap(product -> product.getProductNumber(), product -> product));

        return productNumbers.stream()
            .map(product -> productMap.get(product))
            .collect(Collectors.toList());
    }

}
