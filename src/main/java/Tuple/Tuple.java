package Tuple;

import java.util.List;

public class Tuple {
    private Integer first;
    private List<Integer> second;
    private Integer third;

    public Tuple(Integer first, List<Integer> second, Integer third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    public Integer getFirst() {
        return first;
    }
    public List<Integer> getSecond() {
        return second;
    }
    public Integer getThird() {
        return third;
    }

    @Override
    public String toString() {
        return first + ", " + second + ", " + third;
    }
}
