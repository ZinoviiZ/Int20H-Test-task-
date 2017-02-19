package com.risingapp.test.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Getter
@AllArgsConstructor
public enum OvvaLanguage {
    RU("ru"),
    UA("ua");

    private String value;
}
