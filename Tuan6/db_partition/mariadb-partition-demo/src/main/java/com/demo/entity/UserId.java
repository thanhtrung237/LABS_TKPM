package com.demo.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserId implements Serializable {
    private Long id;
    private Integer genderId;
}
