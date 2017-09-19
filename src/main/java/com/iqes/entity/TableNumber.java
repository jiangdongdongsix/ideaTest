package com.iqes.entity;

import javax.persistence.*;

@Table(name = "TABLE_NUMBER")
@Entity
public class TableNumber extends IdEntity{

    //name
    private String name;
    private TableType tableType;

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

    @Override
    public boolean equals(Object obj) {

        if (obj!=null&&obj.getClass()==this.getClass()) {
            TableNumber tableNumber = (TableNumber) obj;

            if (tableNumber.getName()==null||name==null) {
                return false;
            }else {
                return tableNumber.getName().equalsIgnoreCase(name);
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (31 * hash +  id);
        hash = 31 * hash + (null == name ? 0 : name.hashCode());
        return hash;
    }
}
