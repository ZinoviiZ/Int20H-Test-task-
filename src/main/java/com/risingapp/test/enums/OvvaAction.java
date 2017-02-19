package com.risingapp.test.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@AllArgsConstructor
@Getter
public enum  OvvaAction {

    GET_TV_PROGRAM("https://api.ovva.tv/v2/:lang/tvguide/");

    private String url;
}
