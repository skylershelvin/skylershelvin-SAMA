package org.skylershelvin.SAMABackened.API.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;

@Getter
@Setter
public class DataChange <T>{

    private ChangeType changeType;
    private T data;

    public DataChange(){

    }

    public DataChange(ChangeType changeType, T data){
        this.changeType = changeType;
        this.data = data;
    }



    public enum ChangeType{
        INSERT,
        UPDATE,
        DELETE
    }
}
