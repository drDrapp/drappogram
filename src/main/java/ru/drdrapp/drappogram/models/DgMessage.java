package ru.drdrapp.drappogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "dg_message")
public class DgMessage {

    public static final int START_SEQ = 10;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dg_message_id_gen")
    @SequenceGenerator(name = "dg_message_id_gen", sequenceName = "dg_message_id_seq", initialValue = DgMessage.START_SEQ, allocationSize = 1)
    private Long id;

    @Column(name = "text", nullable = false)
    @NotBlank(message = "Сообщение не должно быть пустым")
    @Length(max = 2048, message = "Слишком длинный текст сообщения")
    private String text;

    @Column(name = "tag")
    @Length(max = 128, message = "Слишком длинный тег")
    private String tag;

    @Column(name = "filename")
    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name="dg_message_dg_user_id_fk"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DgUser author;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "text = " + text + ", " +
                "tag = " + tag + ", " +
                "filename = " + filename + ")";
    }
}