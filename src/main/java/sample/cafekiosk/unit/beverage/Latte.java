package sample.cafekiosk.unit.beverage;

import sample.cafekiosk.unit.CafeKiosk;

public class Latte extends CafeKiosk implements Beverage {

    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4500;
    }
}
