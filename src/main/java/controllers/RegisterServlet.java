package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import beans.form_data.RegisterData;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		request.getRequestDispatcher("/views/register.jsp")
		.forward(request, response);
	}

	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		// request.getParameterValues("param_name");
		
		RegisterData bean = new RegisterData();
		try {
			BeanUtils.populate(bean, request.getParameterMap());
		
			System.out.println(bean.getHoTen());
			System.out.println(bean.getEmail());
			System.out.println(bean.getGioiTinh());
			System.out.println(bean.getLoaiKH());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
