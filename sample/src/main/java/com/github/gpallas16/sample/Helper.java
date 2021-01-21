package com.github.gpallas16.sample;

import java.util.Arrays;
import java.util.List;

public final class Helper {


    static List<MockModel> getSampleList() {
        return Arrays.asList(
                new MockModel("Lorem"),
                new MockModel("Ipsum"),
                new MockModel("Dolor"),
                new MockModel("Sit"),
                new MockModel("Amet")
        );
    }

}
