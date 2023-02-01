package com.yury.codingTask.points.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yury.codingTask.points.Response.GetPointsResponse;
import com.yury.codingTask.points.delegate.PointsDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
public class MainController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PointsDelegate pointsDelegate;


    @GetMapping("/get_points")
    public ResponseEntity<GetPointsResponse> getPoints() {

        GetPointsResponse response = pointsDelegate.getPoints();

        return new ResponseEntity(response, HttpStatus.OK);
    }


}