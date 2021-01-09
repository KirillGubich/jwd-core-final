package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;
import java.util.Objects;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {

    private final Map<Role, Short> crew;
    private final Long flightDistance;
    private final Long minFlightDistance;
    private final Long maxFlightDistance;
    private final Boolean isReadyForNextMissions;

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Long getMinFlightDistance() {
        return minFlightDistance;
    }

    public Long getMaxFlightDistance() {
        return maxFlightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpaceshipCriteria that = (SpaceshipCriteria) o;
        return Objects.equals(crew, that.crew) && Objects.equals(flightDistance, that.flightDistance) && Objects.equals(minFlightDistance, that.minFlightDistance) && Objects.equals(maxFlightDistance, that.maxFlightDistance) && Objects.equals(isReadyForNextMissions, that.isReadyForNextMissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), crew, flightDistance, minFlightDistance, maxFlightDistance, isReadyForNextMissions);
    }

    @Override
    public String toString() {
        return "SpaceshipCriteria{" +
                "crew=" + crew +
                ", flightDistance=" + flightDistance +
                ", minFlightDistance=" + minFlightDistance +
                ", maxFlightDistance=" + maxFlightDistance +
                ", isReadyForNextMissions=" + isReadyForNextMissions +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Criteria.Builder<Builder> {

        private Map<Role, Short> crew;
        private Long flightDistance;
        private Long minFlightDistance;
        private Long maxFlightDistance;
        private Boolean isReadyForNextMissions;

        public Builder withCrew(Map<Role, Short> crew) {
            this.crew = crew;
            return this;
        }

        public Builder withFlightDistance(Long flightDistance) {
            this.flightDistance = flightDistance;
            return this;
        }

        public Builder withFlightDistanceGreaterThan(Long minFlightDistance) {
            this.minFlightDistance = minFlightDistance;
            return this;
        }

        public Builder withFlightDistanceLessThan(Long maxFlightDistance) {
            this.maxFlightDistance = maxFlightDistance;
            return this;
        }

        public Builder readyForNextMissions(Boolean isReadyForNextMissions) {
            this.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public SpaceshipCriteria build() {
            return new SpaceshipCriteria(this);
        }
    }

    public SpaceshipCriteria(Builder builder) {
        super(builder.id, builder.name);
        this.crew = builder.crew;
        this.flightDistance = builder.flightDistance;
        this.minFlightDistance = builder.minFlightDistance;
        this.maxFlightDistance = builder.maxFlightDistance;
        this.isReadyForNextMissions = builder.isReadyForNextMissions;
    }
}
