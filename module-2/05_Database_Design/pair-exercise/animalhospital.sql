START TRANSACTION;

CREATE TABLE petowner
(
   owner_id SERIAL NOT NULL,
   first_name VARCHAR(64) NOT NULL,
   last_name VARCHAR(64) NOT NULL,
   address VARCHAR(64) NOT NULL,
   city VARCHAR(64) NOT NULL,
   district VARCHAR(64) NOT NULL,
   zipcode VARCHAR(64) NOT NULL,
   
   CONSTRAINT pk_owner_id PRIMARY KEY (owner_id)

);


CREATE TABLE pet
(
   pet_id SERIAL NOT NULL,
   petname VARCHAR(64) NOT NULL, 
   pettype VARCHAR(64) NOT NULL,
   age INT,
   dob DATE NOT NULL,
   owner_id INT NOT NULL,
   
   CONSTRAINT pk_pet_id PRIMARY KEY (pet_id),
   CONSTRAINT fk_owner_id_pet FOREIGN KEY (owner_id) REFERENCES petowner(owner_id)
);


CREATE TABLE vetprocedure
(

	procedure_id INT NOT NULL,
	procedure_name VARCHAR(64) NOT NULL,
	price INT NOT NULL,	

	constraint pk_procedureid primary key (procedure_id)
);

CREATE TABLE hospital
(
   hospital_id INT NOT NULL,
   hospital_name VARCHAR(64) NOT NULL,
   address VARCHAR(64) NOT NULL,
   city VARCHAR(64) NOT NULL,
   district VARCHAR(64) NOT NULL,
   zipcode VARCHAR(64) NOT NULL, 
   tax_percent INT NOT NULL,
   
   CONSTRAINT pk_hospital_id PRIMARY KEY(hospital_id)
);	
  

CREATE TABLE pet_visit

(
	pet_id INT NOT NULL,
	owner_id INT NOT NULL,
	procedure_id int not null,
	proceduredate DATE NOT NULL,
	invoice_no INT NOT NULL,
	hospital_id INT NOT NULL,

	CONSTRAINT pk_pet_id_pet_visit primary key (pet_id,procedure_id),
	CONSTRAINT fk_pet_id foreign key (pet_id) references pet(pet_id),
        CONSTRAINT fk_procedure_id foreign key (procedure_id) references vetprocedure(procedure_id),
        CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES petowner(owner_id),
        CONSTRAINT fk_hospital_id FOREIGN KEY(hospital_id) REFERENCES hospital(hospital_id)

);

COMMIT;


 