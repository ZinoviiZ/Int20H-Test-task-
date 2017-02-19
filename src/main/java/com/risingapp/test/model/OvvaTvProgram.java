package com.risingapp.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by zinoviyzubko on 19.02.17.
 */
@Data
public class OvvaTvProgram {

    private String date;
    private List<OvvaProgram> programs;
}
