package com.iqes.entity;

import javax.persistence.*;

@Table(name = "TABLE_NUMBER")
@Entity
public class TableNumber extends IdEntity{

    private String name;
    private TableType tableType;
    private QueueInfo queueInfo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JoinColumn(name = "TABLE_TYPE_ID")
    @ManyToOne
    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    @OneToOne(mappedBy = "tableNumber")
    public QueueInfo getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(QueueInfo queueInfo) {
        this.queueInfo = queueInfo;
    }
}
