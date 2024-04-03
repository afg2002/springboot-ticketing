package springticketing.springticketing.service;

import springticketing.springticketing.models.ApiResponse;

public interface ResponseService {
    public  ApiResponse responseSuccess(Object data);
    public  ApiResponse responseErrorNotMatch(Object data);
    public  ApiResponse responseErrorNotFound(Object data);
    public  ApiResponse responseErrorGeneral(Object data);
    public  ApiResponse responseErrorUserNotMatch(Object data);
}
