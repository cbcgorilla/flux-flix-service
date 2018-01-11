package com.neofinance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class FsOwner {
    public static enum Type {
        TYPE_PUBLIC, TYPE_PRIVATE
    }

    @Id
    private Long id;
    private Type type;
    private String username;
}
