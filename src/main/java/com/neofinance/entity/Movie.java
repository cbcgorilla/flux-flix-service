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
public class Movie {
    @Id
    private String id;

    private String name, movieClass;
}