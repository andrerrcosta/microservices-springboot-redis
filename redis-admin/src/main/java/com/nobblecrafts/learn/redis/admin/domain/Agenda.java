package com.nobblecrafts.learn.redis.admin.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@With
@Table(name = "Agenda")
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String title;

    @Column(name = "start", nullable = false)
    private Date start;

    private Date end;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "agenda_associate",
            joinColumns = {@JoinColumn(name = "agenda_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "associate_id")}
    )
    private Set<Associate> associates = new HashSet<>();

    @Builder.Default
    @ElementCollection
    private Map<Long, String> votes = new HashMap<>();

    @Builder.Default
    private Boolean isClosed = false;

    @Builder.Default
    private Boolean isOpen = false;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Associate that = (Associate) obj;
        return id.equals(that.id);
    }

}
