package com.yc.dao;

import java.util.List;

public interface OpRecordDao {
    public void insertOpRecord(OpRecord opRecord);

    public List<OpRecord>findOpRecord(int accountid);

    public List<OpRecord> findOpRecord(int account,String opType);

    public List<OpRecord> findOpRecord(OpRecord opRecord);
}
