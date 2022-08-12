package com.nobblecrafts.learn.redis.admin.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@With
@Table(name = "agendas")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String title;

    @Column(name = "start_votation", nullable = false)
    private Date startVotation;

    private Date endVotation;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "agenda-associate")
    @JoinTable(
            name = "agenda_associate",
            joinColumns = {@JoinColumn(name = "agenda_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "associate_id")}
    )
    private Set<Associate> associates = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "agenda_votes")
    @MapKeyJoinColumn(name = "associate_id")
    @Column(name = "vote")
    private Map<Associate, String> votes = new HashMap<>();

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
