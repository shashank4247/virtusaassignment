/*1. SQL: The "E-Commerce Logistics" Tracker
Business Case: "SwiftShip" is a third-party logistics provider. They handle thousands of packages daily. Their current challenge is "Lost in Transit" items and identifying which delivery partners are underperforming.
Problem Statement
Create a tracking database that identifies delayed shipments and ranks delivery partners based on their "Success Rate."
Student Tasks:
1.	Schema Design: Create Partners, Shipments, and DeliveryLogs.
2.	Delayed Shipment Query: Write a query to find all shipments where the ActualDeliveryDate is greater than the PromisedDate.
3.	Performance Ranking: Use COUNT and GROUP BY to show how many "Successful" vs. "Returned" deliveries each Partner handled.
4.	The "Zone" Filter: Identify the most popular "Destination City" for orders placed in the last 30 days to help the warehouse plan truck routes.
Deliverable: A SQL script that outputs a "Partner Scorecard" showing which company has the fewest delays.
________________________________________

*/
CREATE TABLE Partners (
    PartnerID INT PRIMARY KEY,
    PartnerName VARCHAR(100)
);

CREATE TABLE Shipments (
    shippingID INT PRIMARY KEY,
    PartnerID INT,
    Actual_date DATE,
    Promised_date DATE,
    Destinationcity VARCHAR(100),
    Status VARCHAR(100),
    FOREIGN KEY (PartnerID) REFERENCES Partners(PartnerID)
);

CREATE TABLE DeliveryLogs (
    shippingID INT,
    PartnerID INT,
    Status VARCHAR(100),
    FOREIGN KEY (PartnerID) REFERENCES Partners(PartnerID),
    FOREIGN KEY (shippingID) REFERENCES Shipments(shippingID)
);

INSERT INTO Partners (PartnerID, PartnerName)
VALUES
    (101,'FastTrack Logistics'),
    (102,'DLF logistics'),
    (103,'KPN Fresh'),
    (104,'Metro Industries');

INSERT INTO Shipments (shippingID, PartnerID, Actual_date, Promised_date, Destinationcity, Status)
VALUES
    (1, 101, '2026-04-10', '2026-04-08', 'Hyderabad', 'Delivered'),
    (2, 102, '2026-04-05', '2026-04-07', 'Mumbai', 'Delivered'),
    (3, 103, '2026-04-12', '2026-04-10', 'Delhi', 'Cancelled'),
    (4, 101, '2026-04-06', '2026-04-06', 'Chennai', 'Delivered'),
    (5, 104, '2026-04-15', '2026-04-12', 'Bangalore', 'Delivered'),
    (6, 102, '2026-04-08', '2026-04-09', 'Hyderabad', 'In Transit'),
    (7, 103, '2026-04-11', '2026-04-09', 'Mumbai', 'Delivered'),
    (8, 104, '2026-04-07', '2026-04-10', 'Delhi', 'Cancelled');


INSERT INTO DeliveryLogs (shippingID, PartnerID, Status)
VALUES
    (1, 101, 'Shipped'),
    (2, 102, 'Delivered'),
    (3, 103, 'Cancelled'),
    (4, 104, 'Delivered'),
    (5, 101, 'Cancelled');
SELECT s.shippingID, s.PartnerID 
FROM Shipments s
WHERE s.Actual_date > s.Promised_date;
SELECT PartnerID,
       COUNT(CASE WHEN Status = 'Delivered' THEN 1 END) AS Success,
       COUNT(CASE WHEN Status = 'Cancelled' THEN 1 END) AS Returned
FROM Shipments
GROUP BY PartnerID;
select Destinationcity,count(*) totalorders from Shipments
WHERE actual_date >= CURDATE() - INTERVAL 30 DAY
group by Destinationcity;
