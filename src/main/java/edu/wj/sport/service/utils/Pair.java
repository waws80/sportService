package edu.wj.sport.service.utils;

public class Pair<F, S> {

    public F first;

    public S second;

    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }


    public static <F, S> Pair<F, S> of(F first, S second){
        return new Pair<>(first,second);
    }


    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
