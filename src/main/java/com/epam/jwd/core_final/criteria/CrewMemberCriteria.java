package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

import java.util.Objects;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {

    private final Role role;
    private final Rank rank;
    private final Boolean isReadyForNextMissions;

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CrewMemberCriteria that = (CrewMemberCriteria) o;
        return role == that.role && rank == that.rank && Objects.equals(isReadyForNextMissions, that.isReadyForNextMissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), role, rank, isReadyForNextMissions);
    }

    @Override
    public String toString() {
        return "CrewMemberCriteria{" +
                "role=" + role +
                ", rank=" + rank +
                ", isReadyForNextMissions=" + isReadyForNextMissions +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Criteria.Builder<Builder> {
        private Role role;
        private Rank rank;
        private Boolean isReadyForNextMissions;

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withRank(Rank rank) {
            this.rank = rank;
            return this;
        }

        public Builder readyForNextMissions(Boolean isReadyForNextMissions) {
            this.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public CrewMemberCriteria build() {
            return new CrewMemberCriteria(this);
        }
    }

    private CrewMemberCriteria(Builder builder) {
        super(builder.id, builder.name);
        this.role = builder.role;
        this.rank = builder.rank;
        this.isReadyForNextMissions = builder.isReadyForNextMissions;
    }
}
