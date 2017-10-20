package com.iqes.entity;

import javax.persistence.*;

@Table(name = "TABLE_NUMBER")
@Entity
public class TableNumber extends IdEntity{

    /**
     * name
     */
    private String name;
    private TableType tableType;
    /**
     * 桌子所在区域
     */
    private String area;

    @JoinColumn(name = "table_type_id")
    @ManyToOne
    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TableNumber that = (TableNumber) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
