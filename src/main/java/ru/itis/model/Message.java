package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(name = "time", nullable = false)
    private Date time;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "chat_id", nullable = false)
//    private Chat chat_id;

}
