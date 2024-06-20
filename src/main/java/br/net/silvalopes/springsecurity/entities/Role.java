package br.net.silvalopes.springsecurity.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_roles")
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    private String name;

    public enum Values {
        ADMIN(1L),
        BASIC(2L);

        Long roleId;

        Values(Long roleId) {
            this.roleId = roleId;
        }

        public Long getRoleId() {
            return roleId;
        }

    }
}
