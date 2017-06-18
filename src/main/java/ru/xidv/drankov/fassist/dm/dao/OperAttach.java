package ru.xidv.drankov.fassist.dm.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity(name = "\"operattachment\"")
@NoArgsConstructor
public class OperAttach {
    @Id
    @GeneratedValue
    @Column(name = "OP_AT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OP_ID")
    @Getter
    private Operation operation;

    @Column(length = 255)
    private String url;

    public OperAttach(Operation operation, String url) {
        this.operation = operation;
        this.url = url;
    }
}
