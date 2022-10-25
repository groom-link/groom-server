package com.example.groom.domain.schedule.teamSchedule;

import com.example.groom.common.exception.CustomException;
import com.example.groom.common.exception.ErrorCode;
import com.example.groom.domain.schedule.dto.ScheduleDto;
import com.example.groom.domain.schedule.teamSchedule.dto.TeamScheduleDto;
import com.example.groom.domain.schedule.teamSchedule.dto.TeamScheduleSearchCondition;
import com.example.groom.domain.schedule.teamScheduleUser.TeamScheduleUserService;
import com.example.groom.domain.schedule.teamScheduleUser.dto.TeamScheduleUserDto;
import com.example.groom.domain.schedule.unableSchedule.UnableScheduleService;
import com.example.groom.entity.domain.schedule.TeamSchedule;
import com.example.groom.entity.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamScheduleService {

    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamScheduleUserService teamScheduleUserService;

    private final UnableScheduleService unableScheduleService;

    public TeamSchedule createTeamSchedule(TeamScheduleDto teamScheduleDto) {

        TeamSchedule teamSchedule = TeamSchedule.of(teamScheduleDto);

        teamScheduleRepository.save(teamSchedule);

        // 팀 스케줄 참여자 중간테이블 생성
        teamScheduleDto.getParticipantsIds().forEach(participantId -> {
            TeamScheduleUserDto teamScheduleUserDto = new TeamScheduleUserDto();
            teamScheduleUserDto.setTeamScheduleId(teamSchedule.getId());
            teamScheduleUserDto.setUserId(participantId);
            teamScheduleUserDto.setStatus(RequestStatus.PENDING);

            teamScheduleUserService.createTeamScheduleUser(teamScheduleUserDto);
        });

        return teamSchedule;
    }

    public void deleteTeamSchedule(Long id) {
        if (teamScheduleRepository.existsById(id)) {
            teamScheduleRepository.deleteById(id);
        } else {
            throw new CustomException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
    }

    public void updateParticipation(Long teamScheduleId, Long userId, RequestStatus status) {
        teamScheduleRepository.updateParticipation(teamScheduleId, userId, status);
    }

    public Slice<TeamSchedule> searchByCondition(Pageable pageable, TeamScheduleSearchCondition teamScheduleSearchCondition) {
        return teamScheduleRepository.searchByCondition(pageable, teamScheduleSearchCondition);
    }

    public List<Long> getParticipants(Long teamScheduleId) {
        return teamScheduleRepository.getParticipants(teamScheduleId);
    }

    public List<ScheduleDto> getRecommendSchedule(Long roomId, LocalDate date) {
        List<ScheduleDto> recommendSchedule = null;
        Set<ScheduleDto> unableScheduleSet = null;

        // TODO: 2022-10-21 1. 불가능한 스케줄 리스트 가져오기

        List<Long> participants = teamScheduleRepository.getParticipants(roomId);

        participants.stream().forEach(participant -> {
            TeamScheduleSearchCondition teamScheduleSearchCondition = new TeamScheduleSearchCondition();
            teamScheduleSearchCondition.setUserId(participant);
            teamScheduleSearchCondition.setStartTime(LocalDateTime.from(date));
            teamScheduleSearchCondition.setEndTime(LocalDateTime.from(date.plusDays(14)));

            unableScheduleSet.addAll(teamScheduleRepository.searchByCondition(teamScheduleSearchCondition));
        });

        unableScheduleSet.addAll(unableScheduleService.searchUnableSchedule(roomId));

        // TODO: 2022-10-24 1. 불가능한 스케줄 리스트 가져오기


        return recommendSchedule;
    }
}
