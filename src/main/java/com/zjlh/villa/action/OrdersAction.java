package com.zjlh.villa.action;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zjlh.villa.entity.Member;
import com.zjlh.villa.entity.Orders;
import com.zjlh.villa.entity.weixin.po.PayReturn;
import com.zjlh.villa.entity.weixin.po.PrePayReturn;
import com.zjlh.villa.service.MemberService;
import com.zjlh.villa.service.OrdersService;

@Controller
@Namespace("/order")
@Results({ @Result(name = "pay", location = "/WEB-INF/content/mobile/pay.jsp") })
public class OrdersAction extends ActionSupport {
	@Autowired
	private OrdersService os;
	@Autowired
	private MemberService ms;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	Logger logger = Logger.getLogger(OrdersAction.class);  

	// 微信返回 fail 失败，success 成功
	private static final String STATUC_SUCCESS = "success";
	private static final String STATUC_FAIL = "fail";

	@Action("/order/createOrder")
	public void createOrder() throws IOException, ParseException,
			org.apache.http.ParseException, IllegalAccessException,
			InstantiationException, InvocationTargetException,
			IntrospectionException {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		
		String data = request.getParameter("data");
		JSONObject obj = JSONObject.fromObject(data);

		
		
		// 更新用户资料
		int memberid = obj.getInt("member");
		String truename = obj.getString("truename");
		String email = obj.getString("email");
		String phone = obj.getString("phone");
		
		if (StringUtils.isBlank(truename) || StringUtils.isBlank(email) || StringUtils.isBlank(phone)) {
			out.print(0);
			out.close();
			return;
		}
		
		ms.update(memberid, truename, email, phone);
		request.getSession().setAttribute("member", ms.getMember(memberid));

		// 格式化日期
		String startDay = obj.getString("startDay");
		String endDay = obj.getString("endDay");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = sdf.parse(startDay);
		Date d2 = sdf.parse(endDay);

		Orders orders = (Orders) JSONObject.toBean(obj, Orders.class);

		// 设置状态、日期
		orders.setState(0);
		orders.setStartDay(d1);
		orders.setEndDay(d2);
		orders.setOrderTime(new Date());

		// 设置openid

		orders.setOpenid(obj.getString("openid"));

		// 设置别墅名称

		orders.setVillaName(obj.getString("villaName"));

		String ip = request.getRemoteAddr();

		// 创建订单
		PrePayReturn prePayReturn = os.createOrder(orders, ip);

		// 设置session
		//request.getSession().setAttribute("data", prePayReturn);

		JSONObject object = JSONObject.fromObject(prePayReturn);
		
		logger.info("prePayRetuan="+object);
		
		out.print(object);
		out.close();

		//
		// return "pay";

	}

	@Action("/order/calculate")
	public void calculate() throws ParseException, IOException {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();

		Double normalPrice = Double.parseDouble(request
				.getParameter("normalPrice"));
		Double specialPrice = Double.parseDouble(request
				.getParameter("specialPrice"));

		int startPeriod = Integer.parseInt(request.getParameter("startPeriod"));
		int endPeriod = Integer.parseInt(request.getParameter("endPeriod"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDay = sdf.parse(request.getParameter("startDay"));
		Date endDayDate = sdf.parse(request.getParameter("endDay"));
		
		int villaid = Integer.parseInt(request.getParameter("villaid"));

		
		boolean available = os.available(startDay, endDayDate, startPeriod, endPeriod, villaid);
		
		String result = "false";
		
		if (available) {
			result = os.calculate(startDay, endDayDate, startPeriod,
					endPeriod, normalPrice, specialPrice);
		}
		

		PrintWriter out = response.getWriter();
		out.print(result);
		out.close();

	}

	@Action("/order/notify")
	public void payNotify() throws IOException {

		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		PayReturn payReturn = null;

		ServletInputStream in = request.getInputStream();
		// 转换微信post过来的xml内容
		XStream xs = new XStream(new DomDriver());
		xs.alias("xml", PayReturn.class);
		String xmlMsg = inputStream2String(in);
		payReturn = (PayReturn) xs.fromXML(xmlMsg);
		
		logger.info("payRetuan"+JSONObject.fromObject(payReturn));

		if ("SUCCESS".equals(payReturn.getResult_code())) {
			os.paidSuccess(Integer.parseInt(payReturn.getOut_trade_no()));
		}

		logger.info("******************微信后台 支付是否成功的标志**************************"+payReturn.getResult_code());
	}

	public static final String inputStream2String(InputStream in)
			throws UnsupportedEncodingException, IOException {
		if (in == null)
			return "";

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}

	@Action("/order/qryOrder")
	public void qryOrder() throws IOException {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();

		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);

		Map<String, Object> map = os.qryOrder(object.getString("id"),
				object.getString("orderTimeStart"),
				object.getString("orderTimeEnd"),
				object.getString("useTimeStart"),
				object.getString("useTimeEnd"), object.getString("state"),
				Integer.parseInt(object.getString("pageNo")),
				Integer.parseInt(object.getString("pageSize")));

		JSONObject obj = JSONObject.fromObject(map);
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.close();
	}

	
	@Action("/order/delOrder")
	public void delOrder() throws IOException{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		
		int id = Integer.parseInt(request.getParameter("id"));
		os.delOrder(id);
		PrintWriter out = response.getWriter();
		out.print(true);
		out.close();
	}
	
	@Action("/order/qryOrderByMember")
	public void qryOrderByMember() throws IOException{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
		
		Map<String,Object> map = os.qryOrderByMember(object.getInt("memberid"), object.getInt("pageNo"), object.getInt("pageSize"));
		
		JSONObject obj = JSONObject.fromObject(map);
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.close();

	
	}
	
	@Action("/order/qryOrderedOrder")
	public void qryOrderedOrder() throws IOException{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		int villaid = Integer.parseInt(request.getParameter("villaid"));
		String date = request.getParameter("date");
		
		Map<String, Object> list = os.qryOrderedOrder(villaid, date);
		JSONObject object = JSONObject.fromObject(list);
		PrintWriter out = response.getWriter();
		out.print(object);
		out.close();
		
	}
	
	
	@Action("/order/complete")
	public void complete() throws IOException {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		  
		int id = Integer.parseInt(request.getParameter("id"));
		os.complete(id);
		
		PrintWriter out = response.getWriter();
		out.print(true);
		out.close();
		
	}
	
	
	
}
