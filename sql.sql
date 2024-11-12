CREATE TABLE user
(
    id         VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    dob        date         NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id    VARCHAR(255) NOT NULL,
    roles_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_name)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_name) REFERENCES roles (name);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);




CREATE TABLE roles
(
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (name)
);

CREATE TABLE roles_permissions
(
    role_name        VARCHAR(255) NOT NULL,
    permissions_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles_permissions PRIMARY KEY (role_name, permissions_name)
);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permissions_name) REFERENCES permission (name);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_name) REFERENCES roles (name);



CREATE TABLE permission
(
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_permission PRIMARY KEY (name)
);