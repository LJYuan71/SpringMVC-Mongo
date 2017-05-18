package tk.ljyuan71.mongo;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Respond {
	private Integer status;
	private String cause;
	private List<?> data;
	private String message;
	//名称唯一性校验 ,字段合法性校验等 false 表示已经存在，校验不通过； true表示不存在，校验通过
	private Boolean checkResult;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * 名称唯一性校验 ,字段合法性校验等 false 表示已经存在，校验不通过； true表示不存在，校验通过
	 * @return
	 */
	public Boolean getCheckResult() {
		return checkResult;
	}

	/**
	 * 名称唯一性校验 ,字段合法性校验等 false 表示已经存在，校验不通过； true表示不存在，校验通过
	 * @param checkResult
	 */
	public void setCheckResult(Boolean checkResult) {
		this.checkResult = checkResult;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
}
