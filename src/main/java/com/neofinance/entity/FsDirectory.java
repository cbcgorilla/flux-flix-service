package com.neofinance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class FsDirectory {
    @Id
    private Long id;
    private String path;
    private String parent;
    private List<Long> owerId;
}
