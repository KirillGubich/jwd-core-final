package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {

    private final LocalDate startDate;
    private final LocalDate earliestStartDate;
    private final LocalDate latestStartDate;
    private final LocalDate endDate;
    private final LocalDate earliestEndDate;
    private final LocalDate latestEndDate;
    private final Long distance;
    private final Long minDistance;
    private final Long maxDistance;
    private final Spaceship assignedSpaceship;
    private final List<CrewMember> assignedCrew;
    private final MissionResult missionResult;

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEarliestStartDate() {
        return earliestStartDate;
    }

    public LocalDate getLatestStartDate() {
        return latestStartDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getEarliestEndDate() {
        return earliestEndDate;
    }

    public LocalDate getLatestEndDate() {
        return latestEndDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Long getMinDistance() {
        return minDistance;
    }

    public Long getMaxDistance() {
        return maxDistance;
    }

    public Spaceship getAssignedSpaceship() {
        return assignedSpaceship;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightMissionCriteria that = (FlightMissionCriteria) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(earliestStartDate, that.earliestStartDate) && Objects.equals(latestStartDate, that.latestStartDate) && Objects.equals(endDate, that.endDate) && Objects.equals(earliestEndDate, that.earliestEndDate) && Objects.equals(latestEndDate, that.latestEndDate) && Objects.equals(distance, that.distance) && Objects.equals(minDistance, that.minDistance) && Objects.equals(maxDistance, that.maxDistance) && Objects.equals(assignedSpaceship, that.assignedSpaceship) && Objects.equals(assignedCrew, that.assignedCrew) && missionResult == that.missionResult;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, earliestStartDate, latestStartDate, endDate, earliestEndDate, latestEndDate, distance, minDistance, maxDistance, assignedSpaceship, assignedCrew, missionResult);
    }

    @Override
    public String toString() {
        return "FlightMissionCriteria{" +
                "startDate=" + startDate +
                ", earliestStartDate=" + earliestStartDate +
                ", latestStartDate=" + latestStartDate +
                ", endDate=" + endDate +
                ", earliestEndDate=" + earliestEndDate +
                ", latestEndDate=" + latestEndDate +
                ", distance=" + distance +
                ", minDistance=" + minDistance +
                ", maxDistance=" + maxDistance +
                ", assignedSpaceship=" + assignedSpaceship +
                ", assignedCrew=" + assignedCrew +
                ", missionResult=" + missionResult +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Criteria.Builder<Builder> {
        private LocalDate startDate;
        private LocalDate earliestStartDate;
        private LocalDate latestStartDate;
        private LocalDate endDate;
        private LocalDate earliestEndDate;
        private LocalDate latestEndDate;
        private Long distance;
        private Long minDistance;
        private Long maxDistance;
        private Spaceship assignedSpaceship;
        private List<CrewMember> assignedCrew;
        private MissionResult missionResult;

       public Builder withStartDate(LocalDate startDate) {
           this.startDate = startDate;
           return this;
       }

        public Builder withStartDateLaterThan(LocalDate earliestStartDate) {
            this.earliestStartDate = earliestStartDate;
            return this;
        }

        public Builder withStartDateEarlierThan(LocalDate latestStartDate) {
            this.latestStartDate = latestStartDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withEndDateLaterThan(LocalDate earliestEndDate) {
            this.earliestEndDate = earliestEndDate;
            return this;
        }

        public Builder withEndDateEarlierThan(LocalDate latestEndDate) {
            this.latestEndDate = latestEndDate;
            return this;
        }

        public Builder withDistance(Long distance) {
           this.distance = distance;
           return this;
        }

        public Builder withDistanceGreaterThan(Long minDistance) {
            this.minDistance = minDistance;
            return this;
        }

        public Builder withDistanceLessThan(Long maxDistance) {
            this.maxDistance = maxDistance;
            return this;
        }

        public Builder withAssignedShip(Spaceship assignedSpaceship) {
            this.assignedSpaceship = assignedSpaceship;
            return this;
        }

        public Builder withAssignedCrew(List<CrewMember> assignedCrew) {
            this.assignedCrew = assignedCrew;
            return this;
        }

        public Builder withMissionResult(MissionResult missionResult) {
           this.missionResult = missionResult;
           return this;
        }

        public FlightMissionCriteria build() {
           return new FlightMissionCriteria(this);
        }
    }

    public FlightMissionCriteria(Builder builder) {
        super(builder.id, builder.name);
        this.startDate = builder.startDate;
        this.earliestStartDate = builder.earliestStartDate;
        this.latestStartDate = builder.latestStartDate;
        this.endDate = builder.endDate;
        this.earliestEndDate = builder.earliestEndDate;
        this.latestEndDate = builder.latestEndDate;
        this.distance = builder.distance;
        this.minDistance = builder.minDistance;
        this.maxDistance = builder.maxDistance;
        this.assignedSpaceship = builder.assignedSpaceship;
        this.assignedCrew = builder.assignedCrew;
        this.missionResult = builder.missionResult;
    }
}
