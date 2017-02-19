package com.risingapp.test.model;

import lombok.Data;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Data
public class OvvaProgram {

    private OvvaImage image;
    private Integer realtime_begin;
    private Integer realtime_end;
    private Boolean will_broadcast_available;
    private Boolean is_on_the_air;
    private String title;
    private String subtitle;
}
