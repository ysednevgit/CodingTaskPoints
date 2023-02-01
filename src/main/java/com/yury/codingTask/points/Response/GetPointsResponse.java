package com.yury.codingTask.points.Response;

import com.yury.codingTask.points.pojo.CustomerPoints;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetPointsResponse {

    private List<CustomerPoints> customerPointsList = new ArrayList<>();
}
