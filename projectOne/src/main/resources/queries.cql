// Create User: Test
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('10', 'test', 'password', false, false, false, '9', 10000) if not exists

// Create User: Test super
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('9', 'test super', 'password', true, false, false, '8', 10000) if not exists

// Create User: Test dept head
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('8', 'test dept head', 'password', true, true, false, '7', 10000) if not exists

// Create User: Test benco 1
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('11', 'test benco 1', 'password', false, false, true, '7', 10000) if not exists

// Create User: Test benco 2
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('12', 'test benco 2', 'password', false, false, true, '8', 10000) if not exists

// Create User: Test top 1
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('7', 'test top 1', 'password', true, true, false, '6', 10000) if not exists

// Create User: Test top 2
insert into proj_1.employees (id, name, pass, superstatus, deptheadstatus, bencostatus, supervisorid, benefitsremaining)values ('6', 'test top 2', 'password', true, true, false, '7', 10000) if not exists

// Create Form: form 1
insert into proj_1.forms 
 ("id", "employeeid", "supervisorid",
  "deptheadid",	"bencoid",	"submitteddate", 
  "eventdate",	/*"superapproveddate", "deptapproveddate", 
  "benapproveddate",*/ "superapproval", "deptapproval", 
  "benapproval", "formapprovalstatus", "requestedammount", 
  "eventinfo", "gradeformat", "eventtype", 
  /*"attachments",*/ "justification", /*"denialjust", 
  "deniedby",*/ "lastupdated", "lastupdatedby")
 values 
 ('10', '10', '9', 
 '8', '11', '2021-04-29', 
 '2021-05-13', 
 false, false, 
 false, 'NEW', 100, 
 'Hotdog Making', 'percentile', 'OTHER', 
 'rly imprtant',  
 '2021-04-29', '10') 
 if not exists

// Create table: forms
CREATE TABLE "proj_1"."forms"(
	"id" ascii,
	"employeeid" ascii,
	"supervisorid" ascii,
	"deptheadid" ascii,
	"bencoid" ascii,
	"submitteddate" date,
	"eventdate" date,
	"superapproveddate" date,
	"deptapproveddate" date,
	"benapproveddate" date,
	"superapproval" boolean,
	"deptapproval" boolean,
	"benapproval" boolean,
	"formapprovalstatus" ascii,
	"requestedammount" int,
	"eventinfo" ascii,
	"gradeformat" ascii,
	"eventtype" ascii,
	"attachments" list<ascii>,
	"justification" ascii,
	"denialjust" ascii,
	"deniedby" ascii,
	"lastupdated" date,
	"lastupdatedby" ascii,
	PRIMARY KEY("id", "employeeid", "supervisorid", "deptheadid", "bencoid"))
WITH CUSTOM_PROPERTIES = {
	'capacity_mode':{'throughput_mode':'PAY_PER_REQUEST'}, 
	'point_in_time_recovery':{'status':'enabled'}
} AND CLUSTERING ORDER BY("employeeid" ASC, "supervisorid" ASC, "deptheadid" ASC, "bencoid" ASC)
