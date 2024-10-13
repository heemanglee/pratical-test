package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType3() {
        // given
        ProductType handmade = ProductType.HANDMADE;
        ProductType bottle = ProductType.BOTTLE;
        ProductType bakery = ProductType.BAKERY;

        // when
        boolean result1 = ProductType.containsStockType(handmade);
        boolean result2 = ProductType.containsStockType(bottle);
        boolean result3 = ProductType.containsStockType(bakery);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType4(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
            Arguments.of(ProductType.HANDMADE, false),
            Arguments.of(ProductType.BOTTLE, true),
            Arguments.of(ProductType.BAKERY, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductTypesForCheckingStockType")
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    void containsStockType5(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }

}