package com.example.nefrology_app.helpers;
// TODO:: подключить библиатеку - валидатор
public class Validator {
    public static boolean max(int rule, int current){
        return rule < current;
    }

    public static boolean min(int rule, int current){
        return rule > current;
    }

    public static boolean between(int ruleFrom, int ruleTo, int current){
        return current > ruleFrom && current < ruleTo;
    }

    public static boolean pattern(String rule, String current){
        return current.matches(rule);
    }
}
