package com.ms.userservice.service;

import com.ms.userservice.entity.User;
import com.ms.userservice.repository.UserRepository;
import com.ms.userservice.vo.Department;
import com.ms.userservice.vo.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User save(User user) {
        log.info("Inside save() of user service");
        return userRepository.saveAndFlush(user);
    }

    public ResponseTemplateVO getUserByDepartment(Long userId) {
        log.info("Inside getUserByDepartment() of user service");
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        User user = userRepository.findById(userId).get();
        Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDeptId(), Department.class);
        responseTemplateVO.setUser(user);
        responseTemplateVO.setDepartment(department);
        return responseTemplateVO;
    }
}
