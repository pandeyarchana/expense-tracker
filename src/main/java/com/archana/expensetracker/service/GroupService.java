package com.archana.expensetracker.service;

import com.archana.expensetracker.model.Group;
import com.archana.expensetracker.model.User;
import com.archana.expensetracker.repository.GroupRepository;
import com.archana.expensetracker.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "create a group")
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Operation(summary = "add user to a group")
    public void addUserToGroup(Long groupId, Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (groupOpt.isPresent() && userOpt.isPresent()) {
            Group group = groupOpt.get();
            User user = userOpt.get();
            group.getUsers().add(user);
            groupRepository.save(group);
        }
    }

    @Operation(summary = "get group details for a given groupId")
    public Optional<Group> getGroup(Long id) {
        return groupRepository.findById(id);
    }

    @Operation(summary = "get all group details")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
