package com.revature.project_one.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_one.models.Employee;
import com.revature.project_one.services.UserService;
import com.revature.project_one.services.UserServiceImpl;

import io.javalin.http.Context;

public class UserController {

	private static final Logger log = LogManager.getLogger(UserController.class);
	private static UserService us = new UserServiceImpl();

	public static void login(Context ctx) {
		log.trace("Logging in");
		if (ctx.sessionAttribute("User") != null) {
			log.warn("User already logged in");
			ctx.status(204);
			return;
		}
		String empID = ctx.formParam("empID");

		log.trace("Getting user: " + empID);
		Employee e = us.getUser(empID);
		if (e.equals(null)) {
			ctx.status(401);
		} else if (ctx.formParam("pass").equals(e.getPasscode())) {
			log.trace("Logging in: generic");
			ctx.sessionAttribute("User", e);
			if (!(ctx.formParam("position") == null)) {
				if (ctx.formParam("position").equals("super") && e.isSuperStatus()) {
					log.trace("Logging in as supervisor");
					ctx.sessionAttribute("Super", true);

				} else if (ctx.formParam("position").equals("benco") && e.isBenCoStatus()) {
					log.trace("Logging in as benco");
					ctx.sessionAttribute("BenCo", true);
				}
			}

			ctx.json(e);
		}
	}

	public static void logout(Context ctx) {
		log.trace("Logging out user");
		ctx.sessionAttribute("User", null);
		ctx.sessionAttribute("Super", null);
		ctx.sessionAttribute("BenCo", null);
	}

}
