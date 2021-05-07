package com.revature.project_one;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_one.controllers.FormController;
import com.revature.project_one.controllers.UserController;

import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.http.Context;

public class Driver {

	private static final Logger log = LogManager.getLogger(Driver.class);

	public static void main(String[] args) {
		Javalin app = Javalin.create().start(8080);

		app.routes(() ->

		{
			// login path
			path("login", () -> {
				post(UserController::login);

			});
			// employee path
			path("employees", () -> {
				post(UserController::logout);
				path(":userid", () -> {
					path("forms", () -> {
						get((FormController::getUserForms));
						post(FormController::createNewForm);
						path(":formid", () -> {
							get(FormController::getUserForm);
							put(FormController::updateForm);
							post(FormController::uploadFile);
							path(":attachid", () -> {
								get(FormController::downloadFile);
							});
						});
					});
				});
			});
			// supervisor path
			path("supervisors", () -> {
				post(UserController::logout);
				path(":userid", () -> {
					path("forms", () -> {
						get(FormController::getForApproval);
						path(":formid", () -> {
							get(FormController::getUserForm);
							put(FormController::updateStatus);
							path(":attachid", () -> {
								get(FormController::downloadFile);
							});
						});
					});
				});
			});
			// benco path
			path("bencos", () -> {
				post(UserController::logout);
				path(":userid", () -> {
					path("forms", () -> {
						get(FormController::getForApproval);
						path(":formid", () -> {
							get(FormController::getUserForm);
							put(FormController::updateStatus);
							path(":attachid", () -> {
								get(FormController::downloadFile);
							});
						});
					});
				});
			});
		});
	}

}
