
ALTER TABLE bill_header MODIFY COLUMN BILL_SEQUENCE INT AUTO_INCREMENT;

ALTER TABLE bill_header
DROP FOREIGN KEY FKewmlfkukce74p1064c3rxtqjl;

ALTER TABLE pledge
DROP FOREIGN KEY FKbi280u1ljn3crqviboipwl6sn;

ALTER TABLE customer MODIFY COLUMN CustomerID bigint AUTO_INCREMENT;

ALTER TABLE bill_header
ADD CONSTRAINT FKewmlfkukce74p1064c3rxtqjl
FOREIGN KEY (customerid) REFERENCES customer(CustomerID);

ALTER TABLE product_type MODIFY COLUMN product_type_no BIGINT AUTO_INCREMENT;

INSERT INTO `krishnag`.`roles` (`id`, `name`) VALUES ('1', 'ROLE_USER');
INSERT INTO `krishnag`.`roles` (`id`, `name`) VALUES ('3', 'ROLE_MODERATOR');
INSERT INTO `krishnag`.`roles` (`id`, `name`) VALUES ('2', 'ROLE_ADMIN');

SET SQL_SAFE_UPDATES = 0;

UPDATE bill_header
SET bill_header.BILL_DATE = STR_TO_DATE(BILL_DATE, '%d/%m/%Y');

SET SQL_SAFE_UPDATES = 1;

ALTER TABLE bill_header
MODIFY COLUMN BILL_DATE DATE;





















alter table customer add proof varchar(200);

alter table customer add proof_type char(1);

ALTER TABLE deepak.customer AUTO_INCREMENT=5667;

ALTER TABLE deepak.bill_header AUTO_INCREMENT=17118

ALTER TABLE customer MODIFY COLUMN phone_no BIGINT;

ALTER TABLE customer MODIFY COLUMN mobile_no BIGINT;

ALTER TABLE Customer

DROP COLUMN PhoneNo,

DROP COLUMN MobileNo;

