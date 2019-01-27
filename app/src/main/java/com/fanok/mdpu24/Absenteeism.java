package com.fanok.mdpu24;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Absenteeism {

    private static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("UA"));
    private String name;
    private HashMap<Date, Integer> absenteeism = new HashMap<>();

    public Absenteeism(String name) {
        this.name = name;
    }


    public void setMark(String date, int absenteeism) throws ParseException {
        this.absenteeism.put(ft.parse(date), absenteeism);
    }

    public HashMap<Date, Integer> getAbsenteeism() {
        return absenteeism;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return absenteeism.size();
    }


}
