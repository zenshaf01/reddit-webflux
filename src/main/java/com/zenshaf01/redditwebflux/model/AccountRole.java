package com.zenshaf01.redditwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.beans.Transient;

@Table(schema = "reddit", name = "account_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole implements Persistable<String> {

    @Column("account_id")
    private Long accountId;

    @Column("role_id")
    private Long roleId;

    @Override
    @Transient
    public String getId() {
        return accountId + "_"  + roleId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }
}
