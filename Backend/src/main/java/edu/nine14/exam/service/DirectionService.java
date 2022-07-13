package edu.nine14.exam.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.nine14.exam.entity.Direction;
import edu.nine14.exam.dao.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DirectionService {
    @Autowired
    private DirectionRepository directionRepository;
}
