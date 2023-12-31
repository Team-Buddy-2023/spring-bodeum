package buddy.springbodeum.fluffy;

import buddy.springbodeum.chat.data.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "fluffy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fluffy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "fluffy")
    private List<Chat> chat;

    public Fluffy(String name, String description) {
        this.name = name;
        this.description = description;
    }
}