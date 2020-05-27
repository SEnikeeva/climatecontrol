package ru.itis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.File;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@ToString(exclude = "owner")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String path;
    private Long size;

    @Transient
    private String fileName;

    @Transient
    private File sourceFile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    @PostLoad
    public void loadFile() {
        // persistent(path) -> transient(sourceFile, fileName)
        sourceFile = new File(path);
        fileName = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf("."));
        log.info("Load file for " + fileName);
    }


}

