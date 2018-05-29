package com.fintec.utils;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer responseCode;
	
	private String responseMessage;
	
	private T responseData;

	public BaseResponse() {
		super();
	}

	public void setSuccessResponse(T data){
		this.setResponseCode(ResponseCode.Success.value());
		this.setResponseMessage(CodeMessage.Success.value());
		this.setResponseData(data);
	}
	
	public void setEmptyResponse(){
		this.setResponseCode(ResponseCode.Empty.value());
		this.setResponseMessage(CodeMessage.RecordNotFound.value());
	}
	
	public void setRequireParamResponse(){
		   this.setResponseCode(ResponseCode.RequireParam.value());
		   this.setResponseMessage(CodeMessage.RequireParam.value());
	}

	public void setProcessFailResponse(){
		   this.setResponseCode(ResponseCode.ProcessFail.value());
		   this.setResponseMessage(CodeMessage.ProcessFail.value());
	}
	
	
	public void setServiceExceptionResponse(){
		   this.setResponseCode(ResponseCode.ProcessFail.value());
		   this.setResponseMessage(CodeMessage.ServiceException.value());
	}
	
	public void setDuplicateResponse(){
		   this.setResponseCode(ResponseCode.Duplicate.value());
		   this.setResponseMessage(CodeMessage.Duplicate.value());
	}
	
	public void setDatabaseViolationResponse(){
		   this.setResponseCode(ResponseCode.DatabaseViolation.value());
		   this.setResponseMessage(CodeMessage.DatabaseException.value());
	}
	
	public void setRecordAlreadyExistsException(){
		   this.setResponseCode(ResponseCode.Duplicate.value());
		   this.setResponseMessage(CodeMessage.RecordAlreadyExists.value());
	}
	
	public void setCreateOrUpdateUserException(){
		   this.setResponseCode(ResponseCode.UserCreationFailed.value());
		   this.setResponseMessage(CodeMessage.createOrUpdateUserFailed.value());
	}
	
	public void setEmailIdAlreadyExistsException(){
		   this.setResponseCode(ResponseCode.EmailAlreadyExists.value());
		   this.setResponseMessage(CodeMessage.EmailAlreadyExistsException.value());
	}
	
	public void setUserAccountDeactivatedException(){
		   this.setResponseCode(ResponseCode.UserDeactivated.value());
		   this.setResponseMessage(CodeMessage.UserDeactivatedException.value());
	}
	
	public void setInvalidRequestData(){
		   this.setResponseCode(ResponseCode.InvalidRequestData.value());
		   this.setResponseMessage(CodeMessage.InvalidRequestData.value());
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public T getResponseData() {
		return responseData;
	}

	public void setResponseData(T responseData) {
		this.responseData = responseData;
	}
}

 enum ResponseCode {
	Success(200),
	ProcessFail(201),
	RequireParam(202),
	Duplicate(203),
	DatabaseViolation(204),
	UserCreationFailed(205),
	EmailAlreadyExists(206),
	UserDeactivated(207),
	InvalidRequestData(208),
	Empty(209);
	
	private final int val;
	
	private ResponseCode(int val) {
		this.val = val;
	}
	public int value() {
		return val;
	}
}

 enum CodeMessage{
    Success("Success / Record Found"),
    RecordNotFound("Record Not Found"),
    RequireParam("WebMethod Request Parameter Requires"),
    RecordModifyFailure("Insert / Update / Delete Failure"),
    ProcessFail("Process Fail"),
    ServiceException("Service Exception"),
    DatabaseException("Database Exception"),
    Duplicate("Record Already Exist"),
	NotMatched("Invalid Username or Password."),
    RecordAlreadyExists("Record Already Exists"),//used to check mapping already available or not in db
    createOrUpdateUserFailed("createOrUpdateUserFailed"),//used to check mapping already available or not in db
    UserDeactivatedException("User Account Deactivated"),//used to check whether user account is deactivated (used for employer deactivate functionality)
    EmailAlreadyExistsException("EmailId already Exists"),//used to check emailId already available or not in db --- User Creation
    InvalidRequestData("Provide correct details");
    CodeMessage(String val) {
        this.val = val;
    }
 
    private final String val;
    
    public String value(){
        return val;
    }
}
