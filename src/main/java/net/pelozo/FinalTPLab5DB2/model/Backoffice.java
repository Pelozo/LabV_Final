package net.pelozo.FinalTPLab5DB2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users_backoffice")
public class Backoffice extends User{


}
