package com.example.nefrology_app.utils;

import android.util.Log;

import com.example.nefrology_app.R;

// FIXME:: не прислали понятной формулы((
public class Calculator {
//    private final double genderCF;
//
//    private final double genderAlpha; // коэффициент пола Альфа
//    private final double genderKappa; // коэффициент пола Каппа
//
//
//    private final double racialCF; // коэффициент расы

    private final double kreatininLevel; // коэффициент пола Каппа

    private int age;

    private Gender gender;
    private Race race;

    public Calculator(Gender gender, Race race, int age, double kreatininLevel) {
        this.age = age;
        this.kreatininLevel = kreatininLevel;
        this.gender = gender;
        this.race = race;
    }

    public double getResult(){
         return 1 * ( kreatininLevel / gender.getGenderKappa()) *
                 gender.genderAlpha * gender.getGenderCF() * age;
    }

    public enum Gender {
        //коэффициент пола.
        // Мужчины Пол = 1;
        // альфа = -0.411; каппа = 0.9.
        // Женщины Пол = 1.018;
        // альфа = -0.329; каппа = 0.7.
        MALE("Мужской", 1.018, -0.329, 0.7),
        FEMALE("Женский", 1.0, -0.411, 0.9);
        private String gender;
        private double genderCF;
        private double genderAlpha;
        private double genderKappa;

        Gender(String gender, double genderCF, double genderAlpha, double genderKappa) {
            this.gender = gender;
            this.genderCF = genderCF;
            this.genderAlpha = genderAlpha;
            this.genderKappa = genderKappa;
        }

        public String getGender() {
            return gender;
        }

        public double getGenderCF() {
            return genderCF;
        }

        public double getGenderAlpha() {
            return genderAlpha;
        }

        public double getGenderKappa() {
            return genderKappa;
        }
    }

    public enum Race {
        WHITE("Европиоидная", 1.159), NEGROID("Негроидная", 1.0);

        private String race;
        private double racialCF;

        Race(String gender, double racialCF) {
            this.race = gender;
            this.racialCF = racialCF;
        }

        public String getRace() {
            return race;
        }

        public double getRacialCF() {
            return racialCF;
        }
    }

}
