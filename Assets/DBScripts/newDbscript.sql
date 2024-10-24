SELECT * FROM ambikam.roles;
SELECT * FROM ambikam.user_roles;
SELECT * FROM ambikam.users;

INSERT INTO `ambikam`.`roles` (`id`, `name`) VALUES ('1', 'ROLE_USER');
INSERT INTO `ambikam`.`roles` (`id`, `name`) VALUES ('3', 'ROLE_MODERATOR');
INSERT INTO `ambikam`.`roles` (`id`, `name`) VALUES ('2', 'ROLE_ADMIN');


INSERT INTO `ambikam`.`users` (`email`, `password`, `reset_password_token`, `username`) 
VALUES ('roshankag2003@gmail.com', 'Roshan@2003', 'zVV8AM1N8lFdTKyezpq7UQgJppYJ5i', 'Roshan');

INSERT INTO `ambikam`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `ambikam`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '2');
INSERT INTO `ambikam`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '3');

ALTER TABLE bill_header MODIFY COLUMN BILL_SEQUENCE INT AUTO_INCREMENT;

ALTER TABLE ambikam.bill_header 
DROP FOREIGN KEY FKewmlfkukce74p1064c3rxtqjl;

ALTER TABLE customer MODIFY COLUMN CustomerID bigint AUTO_INCREMENT;

ALTER TABLE ambikam.bill_header 
ADD CONSTRAINT FKewmlfkukce74p1064c3rxtqjl 
FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID);

SET SQL_SAFE_UPDATES = 0;

UPDATE bill_header
SET bill_header.BILL_DATE = STR_TO_DATE(BILL_DATE, '%d/%m/%Y');

SET SQL_SAFE_UPDATES = 1;

ALTER TABLE bill_header
MODIFY COLUMN BILL_DATE DATE;

SET SQL_SAFE_UPDATES = 0;

UPDATE bill_header
SET REDEMPTION_DATE = CASE 
    WHEN REDEMPTION_DATE = '' THEN NULL
    ELSE STR_TO_DATE(REDEMPTION_DATE, '%d/%m/%Y')
END;

SET SQL_SAFE_UPDATES = 1;

ALTER TABLE bill_header
MODIFY COLUMN REDEMPTION_DATE DATE;

INSERT INTO  ambikam.parameters(param_seq, param_id, PARAM_VALUE, param_example) VALUES
(44, 'GOLD_INTREST_LESS_THAN_5000', 3, 36),
(45, 'GOLD_INTREST_LESS_THAN_10000', 2.5, 24),
(46, 'GOLD_INTREST_LESS_THAN_20000', 2, NULL),
(47, 'GOLD_INTREST_LESS_THAN_50000', 2, NULL),
(48, 'GOLD_INTREST_LESS_THAN_100000', 2, NULL),
(49, 'GOLD_INTREST_MORE_THAN_100000', 1.5, NULL),
(50, 'SILVER_INTREST', 4, NULL),
(51, 'REDEEM_INTERST', 1.33, 1.33),
(52, 'PRINT_RULES', 'Y', 'Y/N'),
(53, 'WHATSAPP', 'Y', 'Y/N'),
(54, 'PLEDGE_PRINT', 'Y', 'Y/N'),
(55, 'REDEEM_PRINT', 'Y', 'Y/N'),
(56, 'PHOTO_FOLDER', 'C:\\Users\\Roshan B T\\backend', 'Y/N'),
(57, 'PLEDGE_RULES', 'The final due date for pledged items is only 1 year and 7 days: Interest must be paid once every three months without fail. The last date to redeem the pledged items: Reason for borrowing: My monthly income:', NULL),
(58, 'CGST', '0.14', 'Example CGST'),
(59, 'SGST', '0.14', 'Example SGST'),
(60, 'GST_Number', '22AAAAA0000A1Z5', 'Example SGST');


SELECT product_no, COUNT(*)
FROM bill_detail
GROUP BY product_no
HAVING COUNT(*) > 1;

DELETE bd1
FROM bill_detail bd1
INNER JOIN (
    SELECT product_no
    FROM bill_detail
    GROUP BY product_no
    HAVING COUNT(*) > 1
) dup
ON bd1.product_no = dup.product_no;

SELECT product_no, COUNT(*)
FROM bill_detail
GROUP BY product_no
HAVING COUNT(*) > 1;

ALTER TABLE bill_detail
MODIFY COLUMN product_no INT AUTO_INCREMENT UNIQUE;

SELECT * FROM bill_detail ORDER BY product_no DESC;














