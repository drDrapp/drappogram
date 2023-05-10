package ru.drdrapp.drappogram.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "dg_message")
public class DgMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dg_message_gen")
    @SequenceGenerator(name = "dg_message_gen", sequenceName = "dg_message_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "tag")
    private String tag;

    @Column(name = "filename")
    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private DgUser author;

    public DgMessage(String text, String tag, DgUser author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }
}