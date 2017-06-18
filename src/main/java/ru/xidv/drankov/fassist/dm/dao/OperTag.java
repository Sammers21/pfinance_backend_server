package ru.xidv.drankov.fassist.dm.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "OperTag")
@NoArgsConstructor
public class OperTag {

    @Id
    @GeneratedValue
    @Column(name = "TAG_ID")
    @Getter
    private Long id;


    @Column(length = 50)
    @Getter
    private String tag;

    @ManyToOne
    @JoinColumn(name = "OP_ID")
    @Getter
    private Operation operation;

    public OperTag(String tag, Operation operation) {
        this.tag = tag;
        this.operation = operation;
    }
}
