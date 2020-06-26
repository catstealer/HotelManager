package com.example.demo.specification;

public enum  SpecParams {
    FROM_DATE("fromDate"), TO_DATE("toDate"), AREA("area"), PERSON_AMOUNT("personAmount"), TAGS("tags"), TYPES("types");

    private String name;

    SpecParams(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
