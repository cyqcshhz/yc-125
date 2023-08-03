package com.yc.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OpRecord {
    private int id;
    private int account;
    private double opmoney;
    private String optime;
    private OpType optype;
    private Integer transferid;

}
