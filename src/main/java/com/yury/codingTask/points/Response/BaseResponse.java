package com.yury.codingTask.points.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseResponse {

    private HttpStatus status;

}
