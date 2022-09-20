package com.example.groom.domain.schedule;

import com.example.groom.domain.schedule.dto.ScheduleSearchCondition;
import com.example.groom.entity.domain.schedule.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.groom.entity.domain.schedule.QSchedule.schedule;


public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory query;

    @Autowired
    private EntityManager em;

    public ScheduleRepositoryCustomImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Schedule> searchByCondition(Pageable pageable, ScheduleSearchCondition scheduleSearchCondition) {
        List<Schedule> scheduleList = query
                .selectFrom(schedule)
                .where(schedule.owner.contains(scheduleSearchCondition.getOwner()),
                        schedule.startTime.between(scheduleSearchCondition.getStartTime(), scheduleSearchCondition.getEndTime()))
                .or(schedule.owner.contains(scheduleSearchCondition.getOwner()),
                        schedule.endTime.between(scheduleSearchCondition.getStartTime(), scheduleSearchCondition.getEndTime()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int size = query
                .selectFrom(schedule)
                .where(schedule.owner.contains(scheduleSearchCondition.getOwner()),
                        schedule.startTime.between(scheduleSearchCondition.getStartTime(), scheduleSearchCondition.getEndTime()))
                .or(schedule.owner.contains(scheduleSearchCondition.getOwner()),
                        schedule.endTime.between(scheduleSearchCondition.getStartTime(), scheduleSearchCondition.getEndTime()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().size();

        return new PageImpl<>(scheduleList, pageable, size);
    }
}
