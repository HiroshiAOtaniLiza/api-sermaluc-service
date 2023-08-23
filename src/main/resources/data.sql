INSERT INTO feature(NAME, ROLES) VALUES('User','VIEW-EDIT-CREATE-DELETE');
INSERT INTO feature(NAME, ROLES) VALUES('Class','CREATE');
INSERT INTO feature(NAME, ROLES) VALUES('Class Manager','VIEW');
INSERT INTO feature(NAME, ROLES) VALUES('Role','VIEW-EDIT-CREATE-DELETE');
INSERT INTO feature(NAME, ROLES) VALUES('System Status','VIEW');
INSERT INTO feature(NAME, ROLES) VALUES('System Settings','VIEW-EDIT');
INSERT INTO feature(NAME, ROLES) VALUES('Custom User Fields','VIEW-EDIT');
INSERT INTO feature(NAME, ROLES) VALUES('Active Users','VIEW-EDIT');
INSERT INTO feature(NAME, ROLES) VALUES('School Calendar','VIEW-CREATE-DELETE');
INSERT INTO feature(NAME, ROLES) VALUES('LTI Settings','EDIT');
INSERT INTO feature(NAME, ROLES) VALUES('LTI Links','CREATE');
INSERT INTO feature(NAME, ROLES) VALUES('Purchase Codes','EDITDELETE');

INSERT INTO USUARIO(nombre, apellidos, correo, contrasenia, feature_id) VALUES('Hiroshi','Otani Liza','hiroshi258@gmail.com','$2a$10$b9lPDZG/zXx9IDxlh7BGp.okPoFvRqkA3GozJ9WwM22i4WzgQmhw6', 1);
INSERT INTO USUARIO(nombre, apellidos, correo, contrasenia, feature_id) VALUES('Jose','Armando Ruiz','jose258@gmail.com','$2a$10$b9lPDZG/zXx9IDxlh7BGp.okPoFvRqkA3GozJ9WwM22i4WzgQmhw6', 2);
INSERT INTO USUARIO(nombre, apellidos, correo, contrasenia, feature_id) VALUES('Alberto','Diaz Pacheco','alberto258@gmail.com','$2a$10$b9lPDZG/zXx9IDxlh7BGp.okPoFvRqkA3GozJ9WwM22i4WzgQmhw6', 3);