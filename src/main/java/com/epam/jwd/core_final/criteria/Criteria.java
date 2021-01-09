package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

import java.util.Objects;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {

    private final Long id;
    private final String name;

    public Criteria(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Criteria<?> criteria = (Criteria<?>) o;
        return Objects.equals(id, criteria.id) && Objects.equals(name, criteria.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder<T extends Builder<T>> {
        public Long id;
        public String name;

        public T withId(Long id) {
            this.id = id;
            return (T) this;
        }

        public T withName(String name) {
            this.name = name;
            return (T) this;
        }
    }
}
