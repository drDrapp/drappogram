package ru.drdrapp.drappogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "dg_message")
public class DgMessage {

    public static final int START_SEQ = 10;

    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dg_message_id_gen")
    @SequenceGenerator(name = "dg_message_id_gen", sequenceName = "dg_message_id_seq", initialValue = DgMessage.START_SEQ, allocationSize = 1)
    private Long id;

    @Column(name = "text", nullable = false)
    @NotBlank(message = "Text may not be blank")
    private String text;

    @Column(name = "tag")
    private String tag;

    @Column(name = "filename")
    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name="dg_message_dg_user_id_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DgUser author;

    public DgMessage(@NonNull String text, String tag, DgUser author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }
}