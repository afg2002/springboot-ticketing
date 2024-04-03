package springticketing.springticketing.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseConstant {

    public static final String RESPONSE_CODE_SUCCESS = "00";
    public static final String RESPONSE_MESSAGE_SUCCESS = "Success";
    public static final String RESPONSE_CODE_NOT_MATCH = "01";
    public static final String RESPONSE_MESSAGE_NOT_MATCH = "User & Password Not Match.";
    public static final String RESPONSE_CODE_NOT_FOUND = "02";
    public static final String RESPONSE_MESSAGE_NOT_FOUND = "Data Not Found.";

    public static final String RESPONSE_CODE_INVALID_TOKEN = "03";
    public static final String RESPONSE_MESSAGE_INVALID_TOKEN = "Token Invalid.";
    public static final String RESPONSE_CODE_DUPLICATE = "04";
    public static final String RESPONSE_MESSAGE_DUPLICATE = "Duplicate Data.";
    public static final String RESPOND_CODE_USER_NOT_MATCH = "05";
    public static final String RESPONSE_MESSAGE_USER_NOT_MATCH = "User Not Match.";
    public static final String RESPONSE_CODE_ERROR_GENERAL = "99";
    public static final String RESPONSE_MESSAGE_ERROR_GENERAL= "Something Error.";




}
