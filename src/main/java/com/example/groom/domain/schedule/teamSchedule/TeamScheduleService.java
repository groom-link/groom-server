package com.example.groom.domain.schedule.teamSchedule;

import com.example.groom.common.exception.CustomException;
import com.example.groom.common.exception.ErrorCode;
import com.example.groom.domain.schedule.dto.ScheduleDto;
import com.example.groom.domain.schedule.teamSchedule.dto.*;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TeamScheduleService {

    private final TeamScheduleRepository teamScheduleRepository;
    private final TeamScheduleUserService teamScheduleUserService;

    private final UnableScheduleService unableScheduleService;

    public TeamScheduleDetailDto createTeamSchedule(TeamScheduleDto teamScheduleDto) {

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

        return TeamScheduleDetailDto.of(teamSchedule);
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

    public TeamScheduleListResponseDto searchByCondition(Pageable pageable, TeamScheduleSearchCondition teamScheduleSearchCondition) {
        Slice<TeamSchedule> teamScheduleList = teamScheduleRepository.searchByCondition(pageable, teamScheduleSearchCondition);

        List<TeamScheduleListDto> teamScheduleListDtoList = teamScheduleList.getContent().stream()
                .map(TeamScheduleListDto::of)
                .toList();

        return TeamScheduleListResponseDto.of(teamScheduleListDtoList, teamScheduleList.getNumber(), teamScheduleList.isLast());
    }

    public List<Long> getParticipants(Long teamScheduleId) {
        return teamScheduleRepository.getParticipants(teamScheduleId);
    }

    public List<ScheduleDto> getRecommendSchedule(Long roomId, LocalDate date) {
        List<ScheduleDto> recommendSchedule = new ArrayList<>();
        Set<ScheduleDto> unableScheduleSet = new HashSet<>();

        // TODO: 2022-10-21 1. 불가능한 스케줄 리스트 가져오기

        List<Long> participants = teamScheduleRepository.getParticipants(roomId);

        TeamScheduleSearchCondition teamScheduleSearchCondition = new TeamScheduleSearchCondition();
        teamScheduleSearchCondition.setStartTime(date.atTime(0, 00, 00));
        teamScheduleSearchCondition.setEndTime(date.plusDays(14).atTime(23, 59, 59));

        // 제외할 시간의 끝 시간과 비교하기 위해 탐색 끝 시간 추가
        unableScheduleSet.add(new ScheduleDto(teamScheduleSearchCondition.getEndTime(), teamScheduleSearchCondition.getEndTime()));

        // 팀원들의 불가한 시간
        participants.stream().forEach(participant -> {
            teamScheduleSearchCondition.setUserId(participant);

            unableScheduleSet.addAll(teamScheduleRepository.searchByCondition(teamScheduleSearchCondition));
        });

        // 모임의 불가한 시간
        unableScheduleSet.addAll(unableScheduleService.searchUnableSchedule(roomId));


        unableScheduleSet.stream().sorted((o1, o2) -> {
            if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return -1;
            } else if (o1.getStartTime().isAfter(o2.getStartTime())) {
                return 1;
            } else {
                if (o1.getEndTime().isAfter(o2.getEndTime())) {
                    return -1;
                } else if (o1.getEndTime().isBefore(o2.getEndTime())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        // TODO: 2022-10-24 1. 추천 스케출 리스트 뽑기
        // 시작하는 날 0시 0분이 첫 기준
        AtomicReference<LocalDateTime> markTime = new AtomicReference<>(date.atTime(0, 00));

        unableScheduleSet.stream().forEach(scheduleDto -> {
            System.out.println("sheduleDto = " + scheduleDto);

            // 시작 시간이 마크 시간보다 빠르면 가능한 시간에 추가
            if (markTime.get().isBefore(scheduleDto.getStartTime())) {
                recommendSchedule.add(new ScheduleDto(markTime.get(), scheduleDto.getStartTime()));
            }

            // 마크 시간이 끝나는 시간보다 빠르면 마크 시간을 끝나는 시간으로 변경
            if (markTime.get().isBefore(scheduleDto.getEndTime())) {
                markTime.set(scheduleDto.getEndTime());
            }
        });

        return recommendSchedule;
    }

    public TeamScheduleDetailDto getTeamSchedule(Long teamScheduleId) {
        return TeamScheduleDetailDto.of(teamScheduleRepository.findById(teamScheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)));
    }
}
