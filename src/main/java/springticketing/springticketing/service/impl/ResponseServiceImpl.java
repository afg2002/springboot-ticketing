package springticketing.springticketing.service.impl;

import org.springframework.stereotype.Service;
import springticketing.springticketing.constant.ResponseConstant;
import springticketing.springticketing.models.ApiResponse;
import springticketing.springticketing.service.ResponseService;

@Service
public class ResponseServiceImpl implements ResponseService {
    public ApiResponse responseSuccess(Object data){
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_SUCCESS);
        response.setResponseCode(ResponseConstant.RESPONSE_CODE_SUCCESS);
        response.setData(data);
        return response;
    }
    public  ApiResponse responseErrorNotMatch(Object data){
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_NOT_MATCH);
        response.setResponseCode(ResponseConstant.RESPONSE_CODE_NOT_MATCH);
        response.setData(data);
        return response;
    }
    public  ApiResponse responseErrorNotFound(Object data){
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_NOT_FOUND);
        response.setResponseCode(ResponseConstant.RESPONSE_CODE_NOT_FOUND);
        response.setData(data);
        return response;
    }
    public  ApiResponse responseErrorGeneral(Object data){
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_ERROR_GENERAL);
        response.setResponseCode(ResponseConstant.RESPONSE_CODE_ERROR_GENERAL);
        response.setData(data);
        return response;
    }

    @Override
    public ApiResponse responseErrorUserNotMatch(Object data) {
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_USER_NOT_MATCH);
        response.setResponseCode(ResponseConstant.RESPOND_CODE_USER_NOT_MATCH);
        response.setData(data);
        return response;
    }

    public  ApiResponse responseErrorToken(Object data){
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_INVALID_TOKEN);
        response.setResponseCode(ResponseConstant.RESPONSE_CODE_INVALID_TOKEN);
        response.setData(data);
        return response;
    }
    public  ApiResponse responseErrorDuplicate(Object data){
        ApiResponse response = new ApiResponse<>();
        response.setResponseMessage(ResponseConstant.RESPONSE_MESSAGE_DUPLICATE);
        response.setResponseCode(ResponseConstant.RESPONSE_CODE_DUPLICATE);
        response.setData(data);
        return response;
    }
}
