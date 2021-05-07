package com.revature.project_one.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_one.models.Form;
import com.revature.project_one.models.Validation;
import com.revature.project_one.services.FormService;
import com.revature.project_one.services.FormServiceImpl;
import com.revature.project_one.util.S3Util;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class FormController {

	private static final Logger log = LogManager.getLogger(UserController.class);
	private static FormService fs = new FormServiceImpl();

	public static void getUserForms(Context ctx) {
		log.trace("Retrieving Forms");
		if (loggedIn(ctx)) {
			ctx.json(fs.getForms(ctx.sessionAttribute("User")));
			log.trace("Returned File JSON");
		}
	}

	public static void getUserForm(Context ctx) {
		if (loggedIn(ctx)) {
			log.trace("Retrieving specific form");
			fs.getForm(ctx.pathParam("formid"));
		}
	}

	public static void getForApproval(Context ctx) {
		if (loggedInAsSuper(ctx) || loggedInAsBenCo(ctx)) {
			log.trace("Retrieving Assigned Forms");
			ctx.json(fs.getFormsSuper(ctx.sessionAttribute("User")));
		} else {
			log.warn("User tried to retrieve assigned forms");
			ctx.status(403);
		}
	}

	public static void createNewForm(Context ctx) {
		if (loggedIn(ctx)) {
			log.trace("Parse given form");
			Form f = ctx.bodyAsClass(Form.class);
			Validation result;

			log.trace("Creating New Form");
			result = fs.newForm(ctx.sessionAttribute("User"), f);

			switch (result) {
			case VALID:
				log.debug("Form: " + ctx.bodyAsClass(Form.class) + " made by: " + ctx.sessionAttribute("User"));
				ctx.status(201); // Created
				break;
			case INVALID:
				log.debug("Submitted form is invalid.");
				ctx.status(422); // Unprocessable Entity
				break;
			case EMPTY:
				log.debug("Submitted form is blank.");
				ctx.status(400); // Something is missing
				break;
			}

		}

	}

	public static void updateForm(Context ctx) {
		if (loggedIn(ctx)) {
			log.trace("Updating Form");
			Validation result = fs.updateForm(ctx.sessionAttribute("User"), ctx.bodyAsClass(Form.class));

			switch (result) {
			case VALID:
				log.debug("Form update: " + ctx.bodyAsClass(Form.class) + " made by: " + ctx.sessionAttribute("User"));
				ctx.status(200);
				break;
			case INVALID:
				log.debug("Not logged in as Form owner");
				ctx.status(403);
				break;
			}
		}
	}

	public static void updateStatus(Context ctx) {
		if (loggedInAsSuper(ctx) || loggedInAsBenCo(ctx)) {
			log.trace("Setting form to: " + ctx.formParam("status"));
			fs.updateForm(ctx.sessionAttribute("User"), ctx.pathParam("formid"), ctx.formParam("status"));
			log.trace("Form status updated");
		} else {
			log.warn("User tried to update form status");
			ctx.status(403);
		}
	}



	public static void uploadFile(Context ctx) {
		if (loggedIn(ctx)) {
			S3Util bucket = S3Util.getInstance();
			String formID = ctx.pathParam("formid");
			List<String> fileNames = new ArrayList<String>();
			Form f = fs.getForm(formID);
			ctx.uploadedFiles().forEach(x -> {
				String temp = formID + x.getFilename();
				try {
					bucket.uploadToBucket(temp, x.getContent().readAllBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				f.addAttachment(temp);
			});
			fs.updateForm(ctx.sessionAttribute("User"), f, false);
		}

	}

	public static void downloadFile(Context ctx) {
		if (loggedIn(ctx)) {
			S3Util bucket = S3Util.getInstance();
			ctx.result(bucket.getObject(ctx.pathParam("attachid")));
		}

	}

	private static boolean loggedIn(Context ctx) {
		if (ctx.sessionAttribute("User") != null) {
			return true;
		}
		log.warn("No login found");
		ctx.status(401);
		return false;
	}

	private static boolean loggedInAsSuper(Context ctx) {
		if (ctx.sessionAttribute("Super") != null) {
			return true;
		}
		return false;
	}

	private static boolean loggedInAsBenCo(Context ctx) {
		if (ctx.sessionAttribute("BenCo") != null) {
			return true;
		}
		return false;
	}

}
