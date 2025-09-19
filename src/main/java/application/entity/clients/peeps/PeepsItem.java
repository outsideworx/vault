package application.entity.clients.peeps;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "PEEPS")
public final class PeepsItem {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String link;
    @Transient
    private Boolean delete;
}