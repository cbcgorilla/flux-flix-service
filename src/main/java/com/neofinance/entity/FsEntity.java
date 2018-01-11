package com.neofinance.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class FsEntity {
    @Id
    private Long id;
    private String filename;
    private long contentLength;
    private String contentType;
    private Date uploadDate;
    private FsCategory category;
    private Long directoryId;
    private List<Long> ownerId;
}
