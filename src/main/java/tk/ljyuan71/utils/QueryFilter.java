package tk.ljyuan71.utils;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class QueryFilter {
	private HttpServletRequest request;
	/**
	 * 过滤参数
	 */
	private Map<String, Object> filters = new HashMap<String, Object>();
	/**
	 * 分页参数
	 */
	private ResponseWithPage responseWithPage;
	/**
	 * 
	 */
	private boolean needPage;
	
	public QueryFilter(HttpServletRequest request,boolean needPage) {
		this.request=request;
		this.needPage=needPage;
		try{
			if(needPage)
			{
				Integer offset = request.getParameter("offset") == null ? 0 :Integer.parseInt(request.getParameter("offset"));
				Integer limit = request.getParameter("limit") == null ? 10 :Integer.parseInt(request.getParameter("limit"));
				
				this.responseWithPage= new ResponseWithPage(offset+1,limit+offset);
			}
			Map map = RequestUtil.getQueryMap(request) ;
			filters=map;
		 }catch(Exception ex){
		 }
	}
	public QueryFilter(HttpServletRequest request,ResponseWithPage responseWithPage,boolean needPage) {
		this.request=request;
		this.needPage=needPage;
		this.responseWithPage=responseWithPage;
		try{
			Map map = RequestUtil.getQueryMap(request) ;
			filters=map;
		 }catch(Exception ex){
		 }
	}
	public QueryFilter() {
		
	}
	// -------------------------------------------------------------------------------------
	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public ResponseWithPage getResponseWithPage() {
		return responseWithPage;
	}
	public void setResponseWithPage(ResponseWithPage responseWithPage) {
		this.responseWithPage = responseWithPage;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public boolean isNeedPage() {
		return needPage;
	}
	public void setNeedPage(boolean needPage) {
		this.needPage = needPage;
	}

}
