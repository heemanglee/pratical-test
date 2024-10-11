package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType() {
        //given
        ProductType type = ProductType.HANDMADE;

        //when
        boolean result = ProductType.containsStockType(type);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType2() {
        //given
        ProductType type = ProductType.BAKERY;

        //when
        boolean result = ProductType.containsStockType(type);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지 체크한다.")
    void containsStockTypeEx() { // 상대방이 생각하게끔 만드는 테스트 코드는 지양한다.
        //given
        ProductType[] productTypes = ProductType.values();

        for(ProductType type: productTypes) {
            if(type == ProductType.HANDMADE) {
                // when
                boolean result = ProductType.containsStockType(type);
                // then
                assertThat(result).isFalse();
            }

            if(type == ProductType.BOTTLE || type == ProductType.BAKERY) {
                // when
                boolean result = ProductType.containsStockType(type);
                // then
                assertThat(result).isTrue();
            }
        }
    }

}