package ru.xidv.drankov.fassist.dm.dao;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.xidv.drankov.fassist.ser.operation_jsons.OperationJSON_without_acc;
import ru.xidv.drankov.fassist.ser.operation_jsons.OperationJSON_without_tag;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "operaion")
@NoArgsConstructor
public class Operation {


    @Id
    @GeneratedValue
    @Column(name = "OP_ID")
    @Getter
    private Long id;

    @Getter
    @Setter
    @OrderColumn
    private long timestamp;

    @OneToOne
    @JoinColumn(name = "OP_ID", insertable = false, updatable = false)
    @Getter
    @Setter
    private Operation next_op;


    @OneToOne
    @JoinColumn(name = "OP_ID", insertable = false, updatable = false)
    @Getter
    @Setter
    private Operation prev_op;

    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    @Getter
    private Category category;

    @ManyToOne
    @JoinColumn(name = "AC_ID")
    @Getter
    private Account account;

    @Getter
    private double balance;

    @Getter
    private double sum;

    @Getter
    @Setter
    private Byte flags;

    @Column(columnDefinition = "TEXT")
    @Setter
    private String notes;

    @Column(length = 75)
    @Setter
    private String memo;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "operation")
    @Getter
    @Setter
    private Set<OperAttach> attaches = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "operation")
    @Getter
    @Setter
    private Set<OperTag> tags = new HashSet<>();


    public Operation(
            Category category,
            Account account,
            double balance,
            double sum,
            String memo,
            long timestamp
    ) {
        this.category = category;
        this.account = account;
        this.balance = balance;
        this.sum = sum;
        this.memo = memo;
        this.timestamp = timestamp;
    }

    public OperationJSON_without_tag get_as_json_a() {
        return new OperationJSON_without_tag(
                id,
                account.getId(),
                category.getId(),
                timestamp,
                sum,
                memo,
                notes
        );
    }

    public OperationJSON_without_acc get_as_json_t() {
        return new OperationJSON_without_acc(
                id,
                category.getId(),
                timestamp,
                sum,
                memo,
                notes,
                getTags().stream().map(OperTag::getTag).collect(Collectors.toList()),
                getBalance()
        );
    }

    @Override
    public String toString() {
        return "Operation{" +
                "balance=" + balance +
                ", sum=" + sum +
                '}';
    }
}
