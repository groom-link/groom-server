package com.example.groom.domain.schedule.teamSchedule;

import com.example.groom.domain.schedule.teamSchedule.dto.TeamSchedulePostDto;
import com.example.groom.domain.schedule.teamSchedule.dto.TeamScheduleSearchCondition;
import com.example.groom.entity.domain.schedule.TeamSchedule;
import com.example.groom.entity.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room/{roomId}/schedule")
public class TeamScheduleController {

    private final TeamScheduleService teamScheduleService;

    @PostMapping
    public TeamSchedule postTeamSchedule(@RequestBody TeamSchedulePostDto teamSchedulePostDto) {
        return teamScheduleService.createTeamSchedule(teamSchedulePostDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {

        teamScheduleService.deleteTeamSchedule(id);

        return id;
    }

    @PatchMapping("/{teamScheduleId}/participation/{userId}")
    public void updateParticipation(@PathVariable Long teamScheduleId, @PathVariable Long userId, @RequestParam RequestStatus status) {
        teamScheduleService.updateParticipation(teamScheduleId, userId, status);
    }

    @GetMapping("/search")
    public Slice<TeamSchedule> searchByCondition(Pageable pageable, TeamScheduleSearchCondition teamScheduleSearchCondition) {
        return teamScheduleService.searchByCondition(pageable, teamScheduleSearchCondition);
    }

    @GetMapping("/participants/{teamScheduleId}")
    public List<Long> getParticipants(@PathVariable Long teamScheduleId) {
        return teamScheduleService.getParticipants(teamScheduleId);
    }

}