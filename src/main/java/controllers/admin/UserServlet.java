package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import beans.form_data.RegisterData;
import dao.UserDAO;
import entities.User;
import utils.JpaUtil;

@WebServlet({
	"/users/index",
	"/users/create",
	"/users/store",
	"/users/edit",
	"/users/update",
	"/users/delete",
	"/users/show",
})
public class UserServlet extends HttpServlet {
	private UserDAO userDAO;

    public UserServlet() {
        super();
        this.userDAO = new UserDAO();
    }

	protected void doGet(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		if (uri.contains("index")) {
			this.index(request, response);
		} else if (uri.contains("create")) {
			this.create(request, response);
		} else if (uri.contains("edit")) {
			this.edit(request, response);
		} else if (uri.contains("show")) {
			this.show(request, response);
		} else if (uri.contains("delete")) {
			this.delete(request, response);
		} else {
			// 404
		}
	}
	
	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		if (uri.contains("store")) {
			this.store(request, response);
		} else if (uri.contains("update")) {
			this.update(request, response);
		} else {
			// 404
		}
	}
	
	private void index(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		Date now = new Date();
		List<User> ds = this.userDAO.all();
		request.setAttribute("now", now);
		request.setAttribute("ds", ds);
		request.setAttribute("view",
			"/views/admin/users/index.jsp");
		request.getRequestDispatcher("/views/layout.jsp")
			.forward(request, response);
	}
	
	private void create(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		request.setAttribute("view",
			"/views/admin/users/create.jsp");
		request.getRequestDispatcher("/views/layout.jsp")
		.forward(request, response);
	}
	
	private void store(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		try {
			User entity = new User();
			BeanUtils.populate(entity,
				request.getParameterMap());
			
			this.userDAO.create(entity);
			response.sendRedirect("/SP22B2_SOF3011_IT16310"
				+ "/users/index");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/SP22B2_SOF3011_IT16310"
				+ "/users/create");
		}
	}
	
	private void edit(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);
		User entity = this.userDAO.findById(id);
		request.setAttribute("user", entity);
		request.setAttribute("view",
			"/views/admin/users/edit.jsp");
		request.getRequestDispatcher("/views/layout.jsp")
			.forward(request, response);
	}
	
	private void show(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
	}
	
	private void delete(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		try {
			int id = Integer.parseInt(idStr);
			User entity = this.userDAO.findById(id);
			this.userDAO.delete(entity);
			
			// TODO: thông báo thành công
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: thông báo thất bại
		}
		
		response.sendRedirect("/SP22B2_SOF3011_IT16310"
			+ "/users/index");
	}
	
	private void update(
		HttpServletRequest request,
		HttpServletResponse response
	) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		try {
			int id = Integer.parseInt(idStr);
			User oldValue = this.userDAO.findById(id);
			User newValue = new User();
			BeanUtils.populate(newValue,
				request.getParameterMap());
			
			newValue.setPassword( oldValue.getPassword() );
			this.userDAO.update(newValue);
			response.sendRedirect("/SP22B2_SOF3011_IT16310"
				+ "/users/index");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/SP22B2_SOF3011_IT16310"
				+ "/users/edit?id=" + idStr);
		}
	}
}
