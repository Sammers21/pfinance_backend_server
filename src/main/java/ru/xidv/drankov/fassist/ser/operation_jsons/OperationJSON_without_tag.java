package ru.xidv.drankov.fassist.ser.operation_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OperationJSON_without_tag {

    @Getter
    private long op_id;

    @Getter
    private long account_id;

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

    public OperationJSON_without_tag(long op_id, long account_id, long cat_id, long timestamp, double sum, String memo, String notes) {
        this.op_id = op_id;
        this.account_id = account_id;
        this.cat_id = cat_id;
        this.timestamp = timestamp;
        this.sum = sum;
        this.memo = memo;
        this.notes = notes;
    }
}
