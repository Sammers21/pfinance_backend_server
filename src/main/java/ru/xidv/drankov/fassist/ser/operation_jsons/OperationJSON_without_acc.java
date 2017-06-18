package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;

import java.util.List;

public class OperationJSON_without_acc {

    @Getter
    private long op_id;

    @Getter
    private long cat_id;

    @Getter
    private long timestamp;

    @Getter
    private double sum;

    @Getter
    private String memo;

    @Getter
    private String notes;

    @Getter
    private List<String> tags;

    @Getter
    private double balance;

    public OperationJSON_without_acc(long op_id, long cat_id, long timestamp, double sum, String memo, String notes, List<String> tags, double balance) {
        this.op_id = op_id;
        this.cat_id = cat_id;
        this.timestamp = timestamp;
        this.sum = sum;
        this.memo = memo;
        this.notes = notes;
        this.tags = tags;
        this.balance = balance;
    }
}
