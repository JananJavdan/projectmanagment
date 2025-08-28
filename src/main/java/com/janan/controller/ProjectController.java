package com.janan.controller;

import com.janan.model.Chat;
import com.janan.model.Invitation;
import com.janan.model.Project;
import com.janan.model.User;
import com.janan.response.MessageResponse;
import com.janan.service.InvitationService;
import com.janan.service.ProjectService;
import com.janan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false)String category,
            @RequestParam(required = false)String tag,
            @RequestParam("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.getProjectByTeam(user,category,tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project>getProjectById(
            @PathVariable Long projectId,
           @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project projects=projectService.getProjectById(projectId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project>createProject(

            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project createProject=projectService.createProject(project,user);
        return new ResponseEntity<>(createProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId")
    public ResponseEntity<Project>updateProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project updateProject=projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updateProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId")
    public ResponseEntity<MessageResponse>deleteProject(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt

    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
       projectService.deleteProject(projectId,user.getId());
        MessageResponse res=new MessageResponse("project deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false)String keyword,

            @RequestParam("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects=projectService.searchProject(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat>getChatByProjectId(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat=projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse>inviteProject(
            @RequestBody InviteRequest inviteRequest,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(inviteRequest.getEmail(), inviteRequest.getProjectId());
        MessageResponse res=new MessageResponse("project invited successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation>acceptInviteProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String jwt,
            @RequestBody Project project
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation=invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(),user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }

}
