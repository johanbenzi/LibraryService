CREATE TABLE USERS
(
    USER_ID                 BIGINT PRIMARY KEY NOT NULL,
    CREATED_DATE_TIME       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_DATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE BOOKS
    ADD COLUMN USER_ID BIGINT REFERENCES USERS (USER_ID),
    ADD COLUMN CREATED_DATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN LAST_MODIFIED_DATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE CATEGORIES
    ADD COLUMN CREATED_DATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN LAST_MODIFIED_DATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
