package org.example.miniusos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String indexNumber;

    public void updateFrom(Student studentUpdates) {
        if (studentUpdates.getFirstName() != null) {
            this.firstName = studentUpdates.getFirstName();
        }

        if (studentUpdates.getLastName() != null) {
            this.lastName = studentUpdates.getLastName();
        }

        if (studentUpdates.getIndexNumber() != null) {
            this.indexNumber = studentUpdates.getIndexNumber();
        }
    }
}
